package Model;

import java.util.Arrays;

import Algorithms.Params;

public class Timetable {
	 
	/**
	 * [day of the week from][time slot][room]
	 * [int from 0 to Params.workingdays][int from 0 to Params.timeSlotsCount][roomId]
	 */
	public int [][][] timetable;
	int roomsCount;
			
	public Timetable(int roomsCount){
		timetable = new int [Params.workingDays][Params.timeSlotsCount][roomsCount];
		this.roomsCount=roomsCount;
		initializeTimetable();
	}
	
	/**
	 * 0 in timetable means that there is already no assignment for given day, time slot and room
	 */
	private void initializeTimetable(){
		for(int i=0; i<Params.workingDays; i++){
			for(int j=0; j<Params.timeSlotsCount; j++){
				for(int k=0; k<roomsCount; k++){
					timetable[i][j][k]=0;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Timetable [timetable=" + Arrays.deepToString(timetable) + "]";
	}
	
	

}
