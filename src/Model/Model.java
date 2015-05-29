package Model;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Model {
	
	public static Map<Integer, Course> courses=new HashMap<Integer, Course>();
	public static Map<Integer, Integer> unasignedLecturesNumber=new HashMap<Integer, Integer>(); // courseId is the key
	public static ArrayList<Room> rooms=new ArrayList<Room>();
	public static ArrayList<Curriculum> curicula=new ArrayList<Curriculum>();
	public static ArrayList<UnavailabilityConstraint> constraints=new ArrayList<UnavailabilityConstraint>();
	public static Map<Integer, Integer> constraintsNumberForCourses=new HashMap<Integer, Integer>();
	public static Timetable inicialTimetable;

	
	


}
