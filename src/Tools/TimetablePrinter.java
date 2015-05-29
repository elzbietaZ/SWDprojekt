package Tools;

import java.io.PrintStream;

import Model.Model;
import Model.Room;

public class TimetablePrinter extends PrettyPrinter{

	PrintStream out=null;
	
	public TimetablePrinter(PrintStream out) {
		super(out);
		this.out=out;
	}
	
	public void printRooms(){
		for(Room room:Model.rooms){
			out.print(room.getName()+"/"+room.getCapacity()+"  ");
		}
		out.println();
	}
	
	public void printTimetable(int[][][] intArray) {
		printRooms();
		for(int i=0; i<intArray.length; i++){
			out.println("Dzie� "+i+" :");
			print(intArray[i]);
		}
		
	}

}
