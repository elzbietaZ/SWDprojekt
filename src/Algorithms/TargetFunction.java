package Algorithms;

import Model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arek on 2015-06-09.
 */
public class TargetFunction {

    private static List<Tuple<Integer, String>> coursesInCurriculums = new ArrayList<>();

    public static double getTargetFunctionValue(Tuple[][][] timetable){
        return rateCompactness(timetable) + rateMinNumberDays(timetable) + rateMinWorkingDays(timetable);
    }

    public static double rateCompactness(Tuple[][][] toRate){
        double rate = 0;
        for(int i = 0; i < toRate.length; i++){
            for (int j = 0; j < toRate[i].length; j++){
                for (int k = 0; k < toRate[i][j].length; k++){
                    if(coursesInCurriculums.contains(toRate[i][j][k])){
                        int min = j > 0 ? j-1 : 0;
                        int max = j < toRate[i].length-1 ? j+1 : toRate[i].length-1;
                        if ((toRate[i][min][k].equals(new Tuple<Integer, Integer>(0,0))
                                || !toRate[i][j][k].x.equals(toRate[i][min][k].x)
                                || toRate[i][j][k].equals(toRate[i][min][k]))
                                && (toRate[i][max][k].equals(new Tuple<Integer, Integer>(0,0))
                                || !toRate[i][j][k].x.equals(toRate[i][max][k].x))
                                || toRate[i][j][k].equals(toRate[i][max][k])){
                            rate++;
                        }
                    }
                }
            }
        }
        return rate;
    }

    public static double rateMinNumberDays(Tuple[][][] toRate){
        double rate = 0;
        for(Tuple<Integer, String> course : coursesInCurriculums) {
            int numberOfDays = 0;
            for (int i = 0; i < toRate.length; i++) {
                boolean foundInDay = false;
                for (int j = 0; j < toRate[i].length && !foundInDay; j++) {
                    for (int k = 0; k < toRate[i][j].length && !foundInDay; k++){
                        if(course.y.equals(toRate[i][j][k].y)){
                            foundInDay = !foundInDay;
                            numberOfDays++;
                        }
                    }
                }
            }
            if(numberOfDays < Model.courses.get(course.y.hashCode()).getMinWorkingDays())
            rate += Model.courses.get(course.y.hashCode()).getMinWorkingDays() - numberOfDays;
        }
        return rate;
    }

    public static double rateMinWorkingDays(Tuple[][][] toRate){
        double rate = 0;
        for(int i = 0; i < toRate.length; i++){
            for (int j = 0; j < toRate[i][0].length; j++){
                if(coursesInCurriculums.contains(toRate[i][0][j])){
                    rate++;
                }
            }
            for (int j = 0; j < toRate[i][toRate[i].length-1].length; j++){
                if(coursesInCurriculums.contains(toRate[i][toRate[i].length-1][j])){
                    rate++;
                }
            }
        }
        return rate;
    }

    public static void initCoursesInCurriculums(){
        for(Curriculum curriculum : Model.curicula){
            for(Course course : curriculum.getCourses()){
                coursesInCurriculums.add(new Tuple<Integer, String>(curriculum.getId(), course.getName()));
            }
        }
    }

    public static List<Tuple<Integer, String>> getCoursesInCurriculums() {
        return coursesInCurriculums;
    }

    public static void setCoursesInCurriculums(List<Tuple<Integer, String>> coursesInCurriculums) {
        TargetFunction.coursesInCurriculums = coursesInCurriculums;
    }
}
