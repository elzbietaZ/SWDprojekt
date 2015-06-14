package Algorithms;

import Model.Course;
import Model.Curriculum;
import Model.Tuple;
import Model.UnavailabilityConstraint;

import java.util.*;

public class SubsidiaryMethods {

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	public static Map<Integer, Map<Integer, Integer>> countUnassignedForCurricula(ArrayList<Curriculum> curicula){
		Map<Integer, Map<Integer, Integer>> unassignedInCurricula= new HashMap<>();
		for(Curriculum curr: curicula){
			for(Course course:curr.getCourses()){
				Map<Integer, Integer> map;
				if(unassignedInCurricula.containsKey(course.getId())){
					map=unassignedInCurricula.get(course.getId());
				}
				else{
					map = new HashMap<>();
				}
				map.put(curr.getId(), course.getNrOfLectures());
				unassignedInCurricula.put(course.getId(), sortByValue(map));
			}
		}
		
		return unassignedInCurricula;
	}

	public static Map<Integer, Integer> countConstraintNumberForCourses(
			ArrayList<UnavailabilityConstraint> constraints) {
		Map<Integer, Integer> constraintsNumberForCourses = new HashMap<Integer, Integer>();
		Map<Integer, ArrayList<Tuple>> constraintsForCourses=new HashMap();
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
	
	public static Map<Integer, ArrayList<Tuple>> countConstraintForCourses(
			ArrayList<UnavailabilityConstraint> constraints) {
		Map<Integer, ArrayList<Tuple>> constraintsForCourses=new HashMap();
		for (UnavailabilityConstraint unavailabilityConstraint : constraints) {
			int courseId = unavailabilityConstraint.getCourse().getId();
			if (constraintsForCourses.containsKey(courseId)) {
				ArrayList<Tuple> list=constraintsForCourses.get(courseId);
				list.add(new Tuple(unavailabilityConstraint.getDay(), unavailabilityConstraint.getDayPeriod()));				
			} else {
				ArrayList<Tuple> list=new ArrayList<>();
				list.add(new Tuple(unavailabilityConstraint.getDay(), unavailabilityConstraint.getDayPeriod()));
				constraintsForCourses.put(courseId, list);
			}
		}
		return constraintsForCourses;
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

	public static void copySolution(Tuple[][][] solution, Tuple[][][] destination){
		for(int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution[i].length; j++) {
				System.arraycopy(solution[i][j], 0, destination[i][j], 0, solution[i][j].length);
			}
		}
	}

    public static void copySolution(int[][][] solution, int[][][] destination){
        if(solution != null){
            for(int i = 0; i < solution.length; i++) {
                for (int j = 0; j < solution[i].length; j++) {
                    System.arraycopy(solution[i][j], 0, destination[i][j], 0, solution[i][j].length);
                }
            }
        }

    }


}
