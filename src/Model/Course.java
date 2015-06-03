package Model;

import Algorithms.Params;

/**
 * Courses: <CourseID> <Teacher> <# Lectures> <MinWorkingDays> <# Students>
 */

public class Course {
	
	private int id;
	private String name;
	private Teacher teacher;
	private int nrOfLectures;
	private int minWorkingDays;
	private int nrOfStudents;
	private int [][] daysTimeSlotsAssignements;
	
	public Course(String name, Teacher teacher, int nrOfLectures,
			int minWorkingDays, int nrOfStudents) {
		this.id=name.hashCode();
		this.name = name;
		this.teacher = teacher;
		this.nrOfLectures = nrOfLectures;
		this.minWorkingDays = minWorkingDays;
		this.nrOfStudents = nrOfStudents;
		daysTimeSlotsAssignements= new int[Params.workingDays][Params.timeSlotsCount];
		intitializeAssignementsTable();
	}

	private void intitializeAssignementsTable() {
		for(int day=0; day<Params.workingDays; day++){
			for(int timeSlot=0; timeSlot<Params.timeSlotsCount; timeSlot++){
				daysTimeSlotsAssignements[day][timeSlot]=0;
			}
		}
	}
	
	public boolean checkIfAssignmentPossible(int day, int timeSlot){
		if(daysTimeSlotsAssignements[day][timeSlot]==0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void makeAssignment(int day, int timeSlot, Course c){
		daysTimeSlotsAssignements[day][timeSlot]=c.getId();
	}
	

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getNrOfLectures() {
		return nrOfLectures;
	}

	public void setNrOfLectures(int nrOfLectures) {
		this.nrOfLectures = nrOfLectures;
	}

	public int getMinWorkingDays() {
		return minWorkingDays;
	}

	public void setMinWorkingDays(int minWorkingDays) {
		this.minWorkingDays = minWorkingDays;
	}

	public int getNrOfStudents() {
		return nrOfStudents;
	}

	public void setNrOfStudents(int nrOfStudents) {
		this.nrOfStudents = nrOfStudents;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", teacher=" + teacher
				+ ", nrOfLectures=" + nrOfLectures + ", minWorkingDays="
				+ minWorkingDays + ", nrOfStudents=" + nrOfStudents + "]";
	}
	
	
	

}
