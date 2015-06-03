package Model;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Model {
	
	public static int curriculaCounter = 0;
	public static int roomCounter = 0;
	public static Map<Integer, Course> courses=new HashMap<Integer, Course>();
	public static Map<Integer, Integer> unasignedLecturesNumber=new HashMap<Integer, Integer>(); // courseId is the key
	public static Map<Integer, Map<Integer, Integer>> unassignedInCurricula= new HashMap<>(); // Map<courseId, Map<curriculaId, unassignedLecturesForCurricula>>
	public static ArrayList<Room> rooms=new ArrayList<Room>();
	public static ArrayList<Curriculum> curicula=new ArrayList<Curriculum>();
	public static ArrayList<UnavailabilityConstraint> constraints=new ArrayList<UnavailabilityConstraint>();
	public static Map<Integer, Integer> constraintsNumberForCourses=new HashMap<Integer, Integer>();
	public static Map<Integer, ArrayList<Tuple>> constraintsForCourses=new HashMap(); // Map <courseId, ArrayList<(dayId, timeSlotId)>>
	public static Timetable inicialTimetable;

	
	


}
