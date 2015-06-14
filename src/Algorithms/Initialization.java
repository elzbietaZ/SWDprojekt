package Algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Model.Course;
import Model.Curriculum;
import Model.Model;
import Model.Room;
import Model.Tuple;

public class Initialization {

	public Initialization() {
	}

	public void makeInitialPlan() {

		Course course = getCourseToAssign();
		
		while (course != null) {
			int unassignedCount = Model.unasignedLecturesNumber.get(course.getId());
			Model.unasignedLecturesNumber.replace(course.getId(),
					unassignedCount - 1);

			Curriculum curr = getCurriculumToAssign(course);

			findTimeAndRoom(curr, course);

			course = getCourseToAssign();

		}

	}

	private void findTimeAndRoom(Curriculum curr, Course course) {

		int[][] curriculaDayTimeSlot = Model.inicialTimetable.curriculaDayTimeSlot[curr
				.getId()];

		for (int day = 0; day < Params.workingDays; day++) {
			for (int timePeriod = 0; timePeriod < Params.timeSlotsCount; timePeriod++) {
				if (doesCourseExistInGivenDay(day, curr.getId(), course)) {
					break;
				}
				int assignment = curriculaDayTimeSlot[day][timePeriod];
				if (assignment == 0 && constraintsFullfiled(course, day, timePeriod) && course.checkIfAssignmentPossible(day, timePeriod)) {
					Room foundAppropriateRoom = findRoom(curr, course, day, timePeriod);
					if (foundAppropriateRoom != null) {
						makeFinalAssignment(course, curr, day, timePeriod, foundAppropriateRoom);
						return;
					}
				}
			}
		}
	}

	private boolean doesCourseExistInGivenDay(int day, int currId, Course course) {
		if (Model.inicialTimetable.dayCurrCourse[day][currId].contains(course
				.getId())) {
			return true;
		}
		return false;
	}

	private Room findRoom(Curriculum curr, Course c, int day, int timeSlot) {

		Room smallerPossibleFreeRoom = null;
		int[][][] roomDayTimeSlot = Model.inicialTimetable.roomDayTimeSlot;
		for (int roomId = 0; roomId < Model.rooms.size(); roomId++) {
			Room room = Model.rooms.get(roomId);
			if (roomDayTimeSlot[roomId][day][timeSlot] == 0
					&& c.getNrOfStudents() <= room.getCapacity()) {
				if (smallerPossibleFreeRoom == null) {
					smallerPossibleFreeRoom = room;
				} else {
					if (room.getCapacity() < smallerPossibleFreeRoom
							.getCapacity()) {
						smallerPossibleFreeRoom = room;
					}
				}
			}
		}
		return smallerPossibleFreeRoom;

	}

	private void makeFinalAssignment(Course course, Curriculum curr, int day,
			int timeSlot, Room room) {
		course.makeAssignment(day, timeSlot, course);
		Model.inicialTimetable.dayCurrCourse[day][curr.getId()].add(course
				.getId());
		Model.inicialTimetable.curriculaDayTimeSlot[curr.getId()][day][timeSlot] = course
				.getId();
		Model.inicialTimetable.roomDayTimeSlot[room.getId()][day][timeSlot] = course
				.getId();
		Model.inicialTimetable.timetable[day][timeSlot][room.getId()] = new Tuple(
				curr.getId(), course.getName());
		
		assignInCurricula(course, curr);

	}

	private boolean constraintsFullfiled(Course c, int day, int timeSlot) {
		if (!Model.constraintsNumberForCourses.containsKey(c.getId())) {
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

	private Curriculum getCurriculumToAssign(Course course) {
		Map<Integer, Integer> map = Model.unassignedInCurricula.get(course
				.getId());
		Iterator it = map.entrySet().iterator();
		Map.Entry<Integer, Integer> selectedEntry = (Map.Entry) it.next();
		return Model.curicula.get(selectedEntry.getKey());
	}
	
	private void assignInCurricula(Course course, Curriculum curr){
		Map<Integer, Integer> map = Model.unassignedInCurricula.get(course
				.getId());
		int value = map.get(curr.getId());
		value--;
		if (value == 0) {
			map.remove(curr.getId());
		} else {
			map.replace(curr.getId(), value);
		}
		Model.unassignedInCurricula.replace(course.getId(), SubsidiaryMethods
				.sortByValue(Model.unassignedInCurricula.get(course.getId())));
	}

	private Course getCourseToAssign() {

		// Course courseToAssign=null;
		Model.unasignedLecturesNumber = SubsidiaryMethods
				.sortByValue(Model.unasignedLecturesNumber);
		ArrayList<Integer> moseConstraindedCoursesIds = new ArrayList<Integer>();
		Iterator it = Model.constraintsNumberForCourses.entrySet().iterator();
		boolean runAgain = true;
		Map.Entry<Integer, Integer> selectedEntry = (Map.Entry) it.next();
		int unassignedLecturesCount = 0;
		while (runAgain && it.hasNext()) {
			Map.Entry<Integer, Integer> entry = (Entry) it.next();
			unassignedLecturesCount = Model.unasignedLecturesNumber
					.get(selectedEntry.getKey());
			if (unassignedLecturesCount == 0) {
				selectedEntry = entry;
				unassignedLecturesCount = Model.unasignedLecturesNumber
						.get(selectedEntry.getKey());
			} else {
				if (selectedEntry.getValue().equals(entry.getValue())) {
					if (unassignedLecturesCount < Model.unasignedLecturesNumber
							.get(entry.getKey())) {
						selectedEntry = entry;
					}
				} else {
					runAgain = false;
				}
			}
		}
		if (unassignedLecturesCount == 0) {
			for (Map.Entry<Integer, Integer> entry : Model.unasignedLecturesNumber
					.entrySet()) {
				if (entry.getValue() > 0) {
					return Model.courses.get(entry.getKey());
				}
			}
			return null;
		}

		return Model.courses.get(selectedEntry.getKey());

	}

}
