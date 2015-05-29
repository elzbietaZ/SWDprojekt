package Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.Course;
import Model.UnavailabilityConstraint;

public class SubsidiaryMethods {
	
	
	public static Map<Integer,Integer> countConstraintNumberForCourses(ArrayList<UnavailabilityConstraint> constraints){
		 Map<Integer, Integer> constraintsNumberForCourses=new HashMap<Integer, Integer>();
		for (UnavailabilityConstraint unavailabilityConstraint : constraints) {
			int courseId=unavailabilityConstraint.getCourse().getId();
			if(constraintsNumberForCourses.containsKey(courseId)){
				int actualConstraintsNr=constraintsNumberForCourses.get(courseId);
				actualConstraintsNr++;
				constraintsNumberForCourses.replace(courseId, actualConstraintsNr);
			}
			else{
				constraintsNumberForCourses.put(courseId, 1);
			}
		}		
		return constraintsNumberForCourses;
	}


}
