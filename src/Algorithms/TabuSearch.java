package Algorithms;

import Model.Tuple;

import java.util.List;

/**
 * Created by Arek on 2015-06-11.
 */
public class TabuSearch {

    public static Tuple<Integer, String>[][][] getBestNeighbour(Tuple[][][] initSolution, List<Tuple<Tuple[][][], Tuple[][][]>> tabuList){
        Tuple [][][] bestSolution = new Tuple[initSolution.length][initSolution[0].length][initSolution[0][0].length];
        SubsidiaryMethods.copySolution(initSolution, bestSolution);
        Tuple<Tuple[][][], Tuple[][][]> move = null;
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
                                newSolution = swapCourses(i, j, k, p, r, s, newSolution);
                                move = new Tuple<Tuple[][][], Tuple[][][]>(initSolution, newSolution);
                                if((TargetFunction.getTargetFunctionValue(newSolution) < TargetFunction.getTargetFunctionValue(bestSolution) || firstNeighbor)
                                        && !tabuList.contains(move)){
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
        }

        return bestSolution;
    }

    private static Tuple<Integer, String> [][][] swapCourses(int i, int j, int k, int p, int r, int s, Tuple [][][] solution){
        Tuple<Integer, String> tmp = solution[i][j][k];
        solution[i][j][k] = solution[p][r][s];
        solution[p][r][s] = tmp;
        return solution;
    }
}
