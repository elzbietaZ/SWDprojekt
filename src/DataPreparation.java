import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import Algorithms.SubsidiaryMethods;
import Model.Course;
import Model.Curriculum;
import Model.Model;
import Model.Room;
import Model.Teacher;
import Model.Timetable;
import Model.UnavailabilityConstraint;
import Tools.PrettyPrinter;
import Tools.TimetablePrinter;


public class DataPreparation {
	
	/**
	 *	Courses: <CourseID> <Teacher> <# Lectures> <MinWorkingDays> <# Students>
		Rooms: <RoomID> <Capacity>
		Curricula: <CurriculumID> <# Courses> <CourseID> ... <CourseID>
		Unavailability_Constraints: <CourseID> <Day> <Day_Period>

	 * @param cttFilePath
	 */
	
	public void readEcttFile(String cttFilePath){
		
		try {
			Scanner in = new Scanner(new FileReader(cttFilePath));
			String scannerValue=in.next();
			while(!scannerValue.equals("COURSES:")){
				scannerValue=in.next();
			}
			scannerValue=in.next();
			while(!scannerValue.equals("ROOMS:")){
				readCourse(in, scannerValue);
				scannerValue=in.next();
			}
			scannerValue=in.next();
			while(!scannerValue.equals("CURRICULA:")){
				readRoom(in, scannerValue);
				scannerValue=in.next();
			}
			scannerValue=in.next();
			while(!scannerValue.equals("UNAVAILABILITY_CONSTRAINTS:")){
				readCurriculum(in, scannerValue);
				scannerValue=in.next();
			}
			scannerValue=in.next();
			while(!scannerValue.equals("END.")){
				readUnavailabilityConstraints(in, scannerValue);
				scannerValue=in.next();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("No such file");
			e.printStackTrace();
		}
		
		
		
	}
	
	public void runSubisidiaryMethods(){
		System.out.println("Ograniczenia: ");
		Model.constraintsNumberForCourses=SubsidiaryMethods.countConstraintNumberForCourses(Model.constraints);
		Model.constraintsForCourses=SubsidiaryMethods.countConstraintForCourses(Model.constraints);
		System.out.println(Model.constraintsNumberForCourses);
		System.out.println(Model.constraintsForCourses);
		System.out.println("Nieprzypisane zajêcia: ");
		Model.unasignedLecturesNumber=SubsidiaryMethods.countUnassignedLecturesNumber(Model.curicula);
		System.out.println(Model.unasignedLecturesNumber);
		Model.unassignedInCurricula=SubsidiaryMethods.countUnassignedForCurricula(Model.curicula);
		System.out.println("Nieprzypisane w poszczególnych potokach");
		System.out.println(Model.unassignedInCurricula);
		Model.inicialTimetable=new Timetable(Model.rooms.size(), Model.curicula.size());
	}

	private void readUnavailabilityConstraints(Scanner in, String courseName) {
		int day=in.nextInt();
		int dayPeriod=in.nextInt();
		UnavailabilityConstraint constraint=new UnavailabilityConstraint(Model.courses.get(courseName.hashCode()), day, dayPeriod);
		Model.constraints.add(constraint);
		System.out.println(constraint);
		
	}

	private void readCurriculum(Scanner in, String name) {
		ArrayList<Course> courses=new ArrayList<>();
		int nrOfCourses=in.nextInt();
		int i=nrOfCourses;
		while(i>0){
			int courseId=in.next().hashCode();
			courses.add(Model.courses.get(courseId));
			i--;
		}
		Curriculum curr=new  Curriculum(name, nrOfCourses, courses);
		Model.curicula.add(curr);
		System.out.println(curr);
	}

	private void readRoom(Scanner in, String roomName) {
		int capacity=in.nextInt();
		Room room= new Room(roomName, capacity);
		Model.rooms.add(room);
		System.out.println(room);		
	}

	void readCourse(Scanner in, String courseName){
		String teacherName=in.next();
		int nrOfLectures=in.nextInt();
		int minWorkingDays=in.nextInt();
		int nrOfStudents=in.nextInt();
		Teacher teacher=new Teacher(teacherName);
		Course course=new Course(courseName, teacher, nrOfLectures, minWorkingDays, nrOfStudents);
		Model.courses.put(course.getId(), course);
		System.out.println(course);
	}

}
