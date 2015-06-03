package Model;

import java.util.Arrays;

import Algorithms.Params;

public class Timetable {
	 
	/**
	 * [day of the week from][time slot][room]
	 * [int from 0 to Params.workingdays][int from 0 to Params.timeSlotsCount][roomId]
	 */
	public Tuple<Integer, Integer> [][][] timetable;
	public int [][][] curriculaDayTimeSlot;
	public int [][][] roomDayTimeSlot;
	
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

	@Override
	public String toString() {
		return "Timetable [timetable=" + Arrays.deepToString(timetable) + "]";
	}
	
	

}
