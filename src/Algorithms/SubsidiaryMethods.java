package Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import Model.Course;
import Model.Curriculum;
import Model.UnavailabilityConstraint;
import Tools.ValueComparator;

public class SubsidiaryMethods {

	public static TreeMap<Integer, Integer> sortByValue(
			Map<Integer, Integer> map) {
		ValueComparator vc = new ValueComparator(map);
		TreeMap<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>(vc);
		sortedMap.putAll(map);
		return sortedMap;
	}

	public static Map<Integer, Integer> countConstraintNumberForCourses(
			ArrayList<UnavailabilityConstraint> constraints) {
		Map<Integer, Integer> constraintsNumberForCourses = new HashMap<Integer, Integer>();
		for (UnavailabilityConstraint unavailabilityConstraint : constraints) {
			int courseId = unavailabilityConstraint.getCourse().getId();
			if (constraintsNumberForCourses.containsKey(courseId)) {
				int actualConstraintsNr = constraintsNumberForCourses
						.get(courseId);
				actualConstraintsNr++;
				constraintsNumberForCourses.replace(courseId,
						actualConstraintsNr);
			} else {
				constraintsNumberForCourses.put(courseId, 1);
			}
		}
		return sortByValue(constraintsNumberForCourses);
	}

	public static Map<Integer, Integer> countUnassignedLecturesNumber(
			ArrayList<Curriculum> curicula) {
		Map<Integer, Integer> unasignedLecturesNumber = new HashMap<Integer, Integer>(); 
		for (Curriculum curriculum : curicula) {
			for (Course course : curriculum.getCourses()) {
				if (unasignedLecturesNumber.containsKey(course.getId())) {
					int actualUnassignedNr = unasignedLecturesNumber.get(course
							.getId());
					actualUnassignedNr += course.getNrOfLectures();
					unasignedLecturesNumber.replace(course.getId(),
							actualUnassignedNr);
				} else {
					unasignedLecturesNumber.put(course.getId(),
							course.getNrOfLectures());
				}
			}
		}

		return sortByValue(unasignedLecturesNumber);
	}

}
