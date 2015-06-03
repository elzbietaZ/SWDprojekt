import Algorithms.Initialization;
import Model.Model;
import Tools.TimetablePrinter;


public class Launcher {
	
	public static void main(String [] arg){
		DataPreparation dp=new DataPreparation();
		dp.readEcttFile("exampleData/myExample.ctt");
		dp.runSubisidiaryMethods();
		Initialization initialization=new Initialization();
		initialization.makeInitialPlan();
		final TimetablePrinter printer= new TimetablePrinter(System.out);
		printer.printTimetable(Model.inicialTimetable.timetable);
	}

}

