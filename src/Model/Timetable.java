package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Algorithms.Params;
import Algorithms.SubsidiaryMethods;

public class Timetable {
	 
	/**
	 * [day of the week from][time slot][room]
	 * [int from 0 to Params.workingdays][int from 0 to Params.timeSlotsCount][roomId]
	 */
	public Tuple<Integer, Integer> [][][] timetable;
	public int [][][] curriculaDayTimeSlot;
	public int [][][] roomDayTimeSlot;
	public int [][][] courseDayTimeSlot;
	public ArrayList [][] dayCurrCourse;
	
	int roomsCount;
	int curriculaNr;
			
	public Timetable(int roomsCount,int curriculaNr){
		timetable = new Tuple [Params.workingDays][Params.timeSlotsCount][roomsCount];
		curriculaDayTimeSlot= new int [curriculaNr][Params.workingDays][Params.timeSlotsCount];
		roomDayTimeSlot= new int [roomsCount][Params.workingDays][Params.timeSlotsCount];
		this.roomsCount=roomsCount;
		this.curriculaNr=curriculaNr;
		initializeTimetable();
		initializeCurriculaTable();
		initializeRoomsTable();
		initializeDaysTable();
	}
	
	/**
	 * 0 in timetable means that there is already no assignment for given day, time slot and room
	 */
	private void initializeTimetable(){
		for(int i=0; i<Params.workingDays; i++){
			for(int j=0; j<Params.timeSlotsCount; j++){
				for(int k=0; k<roomsCount; k++){
					timetable[i][j][k]=new Tuple<Integer, Integer>(0,0);
				}
			}
		}
	}
	
	private void initializeCurriculaTable(){
		for(int i=0; i<curriculaNr; i++){
			for(int j=0; j<Params.workingDays; j++){
				for(int k=0; k<Params.timeSlotsCount; k++){
					curriculaDayTimeSlot[i][j][k]=0;
				}
			}
		}
	}
	
	private void initializeRoomsTable(){
		for(int i=0; i<roomsCount; i++){
			for(int j=0; j<Params.workingDays; j++){
				for(int k=0; k<Params.timeSlotsCount; k++){
					roomDayTimeSlot[i][j][k]=0;
				}
			}
		}
	}
	private void initializeCoursTable(){
		for(int i=0; i<Model.courses.size(); i++){
			for(int j=0; j<Params.workingDays; j++){
				for(int k=0; k<Params.timeSlotsCount; k++){
					courseDayTimeSlot[i][j][k]=0;
				}
			}
		}
	}
	private void initializeDaysTable(){
		dayCurrCourse=new ArrayList[Params.workingDays][Model.curicula.size()];
		for(int day=0; day<Params.workingDays; day++){
			for(int curr=0; curr<Model.curicula.size(); curr++){
				dayCurrCourse[day][curr]=new ArrayList<>();
			}
		}
	}

    public Timetable getCopy(){
        Timetable copy = new Timetable(this.roomsCount, this.curriculaNr);
        SubsidiaryMethods.copySolution(timetable, copy.timetable);
        SubsidiaryMethods.copySolution(curriculaDayTimeSlot, copy.curriculaDayTimeSlot);
        SubsidiaryMethods.copySolution(courseDayTimeSlot, copy.courseDayTimeSlot);
        SubsidiaryMethods.copySolution(roomDayTimeSlot, copy.roomDayTimeSlot);
        copy.dayCurrCourse = dayCurrCourse.clone();
        return copy;
    }

	@Override
	public String toString() {
		return "Timetable [timetable=" + Arrays.deepToString(timetable) + "]";
	}
	
	

}
