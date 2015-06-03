package Tools;

import java.io.PrintStream;

import Model.Model;
import Model.Room;
import Model.Tuple;

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
	
	public void printTimetable(Tuple[][][] intArray) {
		printRooms();
		for(int i=0; i<intArray.length; i++){
			out.println("Dzieñ "+i+" :");
			print(intArray[i]);
		}
		
	}

}
