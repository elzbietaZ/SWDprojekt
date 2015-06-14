import Algorithms.Initialization;
import Algorithms.SubsidiaryMethods;
import Algorithms.TabuSearch;
import Algorithms.TargetFunction;
import Model.Model;
import Model.Tuple;
import Tools.TimetablePrinter;

import java.util.ArrayList;
import java.util.List;


public class Launcher {

	private static final int NUMBER_OF_ITERATIONS = 100;
	
	public static void main(String [] arg){
		DataPreparation dp=new DataPreparation();
		dp.readEcttFile("exampleData/myExample2.ctt");
		dp.runSubisidiaryMethods();
		Initialization initialization=new Initialization();
		initialization.makeInitialPlan();
		final TimetablePrinter printer= new TimetablePrinter(System.out);
		printer.printTimetable(Model.inicialTimetable.timetable);
		System.out.println("Nieprzypisane zajêcia w potokach");
		System.out.println(Model.unassignedInCurricula);

		TargetFunction.initCoursesInCurriculums();
		System.out.println(TargetFunction.getTargetFunctionValue(Model.inicialTimetable.timetable));

		List<Tuple<Tuple[][][], Tuple[][][]>> tabuList = new ArrayList<>();
		Tuple[][][] bestSolution = new Tuple[Model.inicialTimetable.timetable.length][Model.inicialTimetable.timetable[0].length][Model.inicialTimetable.timetable[0][0].length];
		Tuple[][][] currentSolution = new Tuple[Model.inicialTimetable.timetable.length][Model.inicialTimetable.timetable[0].length][Model.inicialTimetable.timetable[0][0].length];
		SubsidiaryMethods.copySolution(Model.inicialTimetable.timetable, bestSolution);
		SubsidiaryMethods.copySolution(Model.inicialTimetable.timetable, currentSolution);

		int i = 0;
		while(TargetFunction.getTargetFunctionValue(bestSolution) != 0 && i < NUMBER_OF_ITERATIONS){
			currentSolution = TabuSearch.getBestNeighbour(currentSolution, tabuList);
			if(TargetFunction.getTargetFunctionValue(currentSolution) < TargetFunction.getTargetFunctionValue(bestSolution)){
				SubsidiaryMethods.copySolution(currentSolution, bestSolution);
			}
			i++;
		}
		printer.printTimetable(bestSolution);
		System.out.println(TargetFunction.getTargetFunctionValue(bestSolution));
	}
}

