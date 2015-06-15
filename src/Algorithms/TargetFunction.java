package Algorithms;

import Model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arek on 2015-06-09.
 */
public class TargetFunction {

    private static List<Tuple<Integer, String>> coursesInCurriculums = new ArrayList<>();

    public static double getTargetFunctionValue(int compactnessParam, int minNumberDaysParam, int minWorkingDaysParam, Tuple[][][] timetable){
        return compactnessParam*rateCompactness(timetable) + minNumberDaysParam*rateMinNumberDays(timetable) + minWorkingDaysParam* rateNumberOfCoursesInFirstAndLastPeriod(timetable);
    }

    public static double rateCompactness(Tuple[][][] toRate){
        double rate = 0;
        for(int i = 0; i < toRate.length; i++){
            for (int j = 0; j < toRate[i].length; j++){
                for (int k = 0; k < toRate[i][j].length; k++){
                    if(coursesInCurriculums.contains(toRate[i][j][k])){
                        int earlier = j > 0 ? j-1 : 0;
                        int later = j < toRate[i].length-1 ? j+1 : toRate[i].length-1;
                        boolean isEarlier = false;
                        boolean isLater = false;
                        for(int p = 0; p < toRate[i][earlier].length; p++){
                            if(toRate[i][j][k].x.equals(toRate[i][earlier][p].x) &&
                                    !toRate[i][earlier][p].y.equals(0)){
                                isEarlier = true;
                            }
                        }
                        for(int p = 0; p < toRate[i][later].length; p++){
                            if(toRate[i][j][k].x.equals(toRate[i][later][p].x) &&
                                    !toRate[i][later][p].y.equals(0)){
                                isLater = true;
                            }
                        }
                        if (!(isEarlier || isLater)){
//                            System.out.println("Compact: " + toRate[i][j][k]+" "+i+" "+j+" "+k);
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

    public static double rateNumberOfCoursesInFirstAndLastPeriod(Tuple[][][] toRate){
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
