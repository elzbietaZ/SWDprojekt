package Algorithms;

import Model.Course;
import Model.*;
import Model.Timetable;
import Model.Tuple;

import java.util.List;

/**
 * Created by Arek on 2015-06-11.
 */
public class TabuSearch {

    public static Tuple[][][] getBestNeighbour(Timetable solution, List<Tuple<Tuple[][][], Tuple[][][]>> tabuList,
                                               int compactnessParam, int minNumberDaysParam, int minWorkingDaysParam){
        Tuple [][][] initSolution = solution.timetable;
        Tuple [][][] bestSolution = new Tuple[initSolution.length][initSolution[0].length][initSolution[0][0].length];
        SubsidiaryMethods.copySolution(initSolution, bestSolution);
        Tuple<Tuple[][][], Tuple[][][]> move = null;
        Tuple<Tuple[][][], Tuple[][][]> reverseMove = null;
        boolean firstNeighbor = true;

        for(int i = 0; i < bestSolution.length; i++) {
            for (int j = 0; j < bestSolution[i].length; j++) {
                for (int k = 0; k < bestSolution[i][j].length; k++) {
                    for(int p = 0; p < bestSolution.length; p++) {
                        for (int r = 0; r < bestSolution[i].length; r++) {
                            for (int s = 0; s < bestSolution[i][j].length; s++) {
                                if( i == p && j ==r && k ==s){
                                    continue;
                                }
                                Tuple[][][] newSolution = new Tuple[initSolution.length][initSolution[0].length][initSolution[0][0].length];
                                SubsidiaryMethods.copySolution(initSolution, newSolution);

                                if(!isSwapingAvailable(i, j, k, p, r, s, newSolution)){
                                    continue;
                                }
                                newSolution = swapCourses(i, j, k, p, r, s, newSolution);
                                move = new Tuple<Tuple[][][], Tuple[][][]>(initSolution, newSolution);
                                reverseMove = new Tuple<Tuple[][][], Tuple[][][]>(newSolution, initSolution);
                                if((TargetFunction.getTargetFunctionValue(compactnessParam, minNumberDaysParam, minWorkingDaysParam, newSolution) <
                                        TargetFunction.getTargetFunctionValue(compactnessParam, minNumberDaysParam, minWorkingDaysParam, bestSolution) || firstNeighbor)
                                        && !tabuList.contains(move) ){
                                    firstNeighbor = false;
                                    SubsidiaryMethods.copySolution(newSolution, bestSolution);
                                }
                            }
                        }
                    }
                }
            }
        }
        if(move != null){
            tabuList.add(move);
            tabuList.add(reverseMove);
        }
        return bestSolution;
    }

    private static Tuple<Integer, String> [][][] swapCourses(int i, int j, int k, int p, int r, int s, Tuple [][][] solution){
        Tuple<Integer, String> tmp = solution[i][j][k];
        solution[i][j][k] = solution[p][r][s];
        solution[p][r][s] = tmp;
        return solution;
    }

    private static boolean constraintsFullfiled(Course c, int day, int timeSlot) {
        if (c == null || !Model.constraintsNumberForCourses.containsKey(c.getId())) {
            return true;
        } else {
            List<Tuple> constraitsList = Model.constraintsForCourses.get(c
                    .getId());
            for (Tuple tuple : constraitsList) {
                if ((int) tuple.x == day && (int) tuple.y == timeSlot) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isSwapingAvailable(int i, int j, int k, int p, int r, int s, Tuple [][][] solution){
        return (isCourseToSwap(i, j, k, solution) ||
                isCourseToSwap(p, r, s, solution)) &&
                isSwapingFullfiedConstraints(i, j, k, p, r, solution) &&
                isSwapingFullfiedConstraints(p, r, s, i, j, solution) &&
                !(isSameCourseInDifferentRoom(i, j, k, p, r, s, solution) ||
                isSameCourseInDifferentRoom(p, r, s, i, j, k, solution)) &&
                !(isAnyCurriculaCourseInDifferentRoom(i, j, k, p, r, s, solution) ||
                isAnyCurriculaCourseInDifferentRoom(p, r, s, i, j, k, solution)) &&
                isCapacityEnought(i, j, k, s, solution) &&
                isCapacityEnought(p, r, s, k, solution) &&
                !(isSameCourseInDay(i, j, k, p, solution) || isSameCourseInDay(p, r, s, i, solution));
    }

    private  static boolean isCourseToSwap(int i, int j, int k, Tuple [][][] solution){
        return solution[i][j][k].y.hashCode() != 0;
    }

    private static boolean isSwapingFullfiedConstraints(int i, int j, int k, int p, int r, Tuple [][][] solution){
        return constraintsFullfiled(Model.courses.get(solution[i][j][k].y.hashCode()), p, r);
    }

    private static boolean isSameCourseInDifferentRoom(int i, int j, int k, int p, int r, int s, Tuple [][][] solution){
        for(int n = 0; n < solution[p][r].length; n++){
            if(n != s && !solution[p][r][n].y.equals(0) && solution[p][r][n].y.equals(solution[i][j][k].y)){
                return true;
            }
        }
        return false;
    }

    private static boolean isAnyCurriculaCourseInDifferentRoom(int i, int j, int k, int p, int r, int s, Tuple [][][] solution){
        for(int n = 0; n < solution[p][r].length; n++){
            if(n != s && !solution[p][r][n].y.equals(0) && solution[p][r][n].x == solution[i][j][k].x){
                return true;
            }
        }
        return false;
    }

    private static boolean isCapacityEnought(int i, int j, int k, int roomId, Tuple [][][] solution){
        if(solution[i][j][k].y.equals(0)){
            return true;
        }
        else{
            return Model.rooms.get(roomId).getCapacity() >= Model.courses.get(solution[i][j][k].y.hashCode()).getNrOfStudents();
        }

    }
    private static boolean isSameCourseInDay(int i, int j, int k, int p, Tuple [][][] solution){
        for(int m = 0; m < solution[p].length; m++){
            for(int n = 0; n < solution[p][m].length; n++){
                if(p != i && !solution[i][j][k].y.equals(0) && solution[i][j][k].equals(solution[p][m][n])){
                    return true;
                }
            }
        }
        return false;
    }
}
