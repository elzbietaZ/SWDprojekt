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

	public void makeInitialPlan(){
		
		int t=0;
		Course c=getCourseToAssign();
		while(c!=null){
			System.out.println(c.getName());
			System.out.println(Model.constraintsNumberForCourses);
			System.out.println(Model.unasignedLecturesNumber.toString());
			int unassignedCount=Model.unasignedLecturesNumber.get(c.getId());
			Model.unasignedLecturesNumber.replace(c.getId(), unassignedCount-1);
			
			Curriculum curr=getCurriculumToAssign(c);
			System.out.println(Model.unassignedInCurricula);
			
			//findTimeAndRoom(curr, c);
			
			c=getCourseToAssign();

		}
		
	}
	
	private void findTimeAndRoom(Curriculum curr, Course c) {
		
		int [][] curriculaDayTimeSlot=Model.inicialTimetable.curriculaDayTimeSlot[curr.getId()];
		
		for(int j=0; j<Params.workingDays; j++){
			for(int k=0; k<Params.timeSlotsCount; k++){
				int assignment=curriculaDayTimeSlot[j][k];
				if(assignment==0 && constraintsFullfiled(c,j,k)){
					Room foundAppropriateRoom=findAndAssignToRoom(curr,c,j,k);
					if(foundAppropriateRoom!=null) break;
				}
			}
		}
		
	}

	private Room findAndAssignToRoom(Curriculum curr, Course c, int day, int timeSlot) {
		
		Room smallerPossibleFreeRoom=null;
		int [][][] roomDayTimeSlot=Model.inicialTimetable.roomDayTimeSlot;
		for(int roomId=0; roomId<Model.rooms.size(); roomId++){
			Room room=Model.rooms.get(roomId);
			if(roomDayTimeSlot[roomId][day][timeSlot]==0 && c.getNrOfStudents()<room.getCapacity()){
				if(smallerPossibleFreeRoom==null){
					smallerPossibleFreeRoom=room;
				}
				else{
					if(room.getCapacity()<smallerPossibleFreeRoom.getCapacity()){
						smallerPossibleFreeRoom=room;
					}
				}
			}
		}
		return smallerPossibleFreeRoom;
		
	}

	private boolean constraintsFullfiled(Course c, int day, int timeSlot) {
		if(!Model.constraintsNumberForCourses.containsKey(c.getId())){
			return true;
		}
		else{
			List<Tuple> constraitsList=Model.constraintsForCourses.get(c.getId());
			for(Tuple tuple:constraitsList){
				if((int)tuple.x==day && (int)tuple.y==timeSlot){
					return false;
				}
			}
		}
		return true;
	}

	private Curriculum getCurriculumToAssign(Course course) {
		Map<Integer, Integer> map=Model.unassignedInCurricula.get(course.getId());
		Iterator it=map.entrySet().iterator();
		Map.Entry<Integer, Integer> selectedEntry=(Map.Entry) it.next();
		int value=selectedEntry.getValue();
		value--;
		if(value==0){
			map.remove(selectedEntry.getKey());
		}
		else{
			map.replace(selectedEntry.getKey(), value);
		}
		Model.unassignedInCurricula.replace(course.getId(),SubsidiaryMethods.sortByValue(Model.unassignedInCurricula.get(course.getId())));
		return Model.curicula.get(selectedEntry.getKey());
	}

	
	private void findTime(Course c){
		boolean assignmentDone=false;
		while(!assignmentDone){
			for(int i=0; i<Params.workingDays; i++){
				for(int j=0; j<Params.timeSlotsCount; j++){
					for(int k=0; k<Model.rooms.size(); k++){
						
					}
				}
			}
		}
	}

	private Course getCourseToAssign() {
		
		//Course courseToAssign=null;
		Model.unasignedLecturesNumber=SubsidiaryMethods.sortByValue(Model.unasignedLecturesNumber);
		ArrayList<Integer> moseConstraindedCoursesIds=new ArrayList<Integer>();
		Iterator it=Model.constraintsNumberForCourses.entrySet().iterator();
		boolean runAgain=true;
		Map.Entry<Integer, Integer> selectedEntry=(Map.Entry) it.next();
		int unassignedLecturesCount=0;
		while(runAgain && it.hasNext()){
			Map.Entry<Integer, Integer> entry=(Entry) it.next();
			unassignedLecturesCount=Model.unasignedLecturesNumber.get(selectedEntry.getKey());
			if(unassignedLecturesCount==0){
				selectedEntry=entry;
				unassignedLecturesCount=Model.unasignedLecturesNumber.get(selectedEntry.getKey());
			}
			else{
				if(selectedEntry.getValue().equals(entry.getValue())){
					if( unassignedLecturesCount<Model.unasignedLecturesNumber.get(entry.getKey())){
						selectedEntry=entry;
					}
				}
				else{
					runAgain=false;
				}
			}
		}
		if(unassignedLecturesCount==0){
			for(Map.Entry<Integer, Integer> entry :Model.unasignedLecturesNumber.entrySet()){
				if(entry.getValue()>0){
					return Model.courses.get(entry.getKey());
				}
			}
			return null;
		}
		
		return Model.courses.get(selectedEntry.getKey());
		
		
	}
	

}
