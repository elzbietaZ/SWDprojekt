import Algorithms.Initialization;
import Algorithms.SubsidiaryMethods;
import Algorithms.TabuSearch;
import Algorithms.TargetFunction;
import Model.*;
import Tools.TimetablePrinter;

import java.util.ArrayList;
import java.util.List;


public class Launcher {

	private static final int NUMBER_OF_ITERATIONS = 1000;
	
	public static void main(String [] arg){
		DataPreparation dp=new DataPreparation();
		dp.readEcttFile("exampleData/myExample2.ctt");
		dp.runSubisidiaryMethods();
		Initialization initialization=new Initialization();
		initialization.makeInitialPlan();
		final TimetablePrinter printer= new TimetablePrinter(System.out);
		printer.printTimetable(Model.inicialTimetable.timetable);
		System.out.println("Nieprzypisane zaj�cia w potokach");
		System.out.println(Model.unassignedInCurricula);

		TargetFunction.initCoursesInCurriculums();
        System.out.println("Rate of compactness: " + TargetFunction.rateCompactness(Model.inicialTimetable.timetable));
        System.out.println("Rate of minimum number of working days: " + TargetFunction.rateMinNumberDays(Model.inicialTimetable.timetable));
        System.out.println("Rate of minimum working days for each course: " + TargetFunction.rateMinWorkingDays(Model.inicialTimetable.timetable));
		System.out.println("Target: " + TargetFunction.getTargetFunctionValue(Model.inicialTimetable.timetable));

		List<Tuple<Tuple[][][], Tuple[][][]>> tabuList = new ArrayList<>();
        Timetable bestSolution = Model.inicialTimetable.getCopy();
        Timetable currentSolution = Model.inicialTimetable.getCopy();

		int i = 0;
		while(TargetFunction.getTargetFunctionValue(bestSolution.timetable) != 0 && i < NUMBER_OF_ITERATIONS){
            if(tabuList.size() > 10){
                tabuList.remove(tabuList.get(0));
            }
			currentSolution.timetable = TabuSearch.getBestNeighbour(currentSolution, tabuList);
			if(TargetFunction.getTargetFunctionValue(currentSolution.timetable) < TargetFunction.getTargetFunctionValue(bestSolution.timetable)){
				SubsidiaryMethods.copySolution(currentSolution.timetable, bestSolution.timetable);
			}

//            printer.printTimetable(bestSolution.timetable);
			i++;
		}
		printer.printTimetable(bestSolution.timetable);
        System.out.println("Rate of compactness: " + TargetFunction.rateCompactness(bestSolution.timetable));
        System.out.println("Rate of minimum number of working days: " + TargetFunction.rateMinNumberDays(bestSolution.timetable));
        System.out.println("Rate of minimum working days for each course: " + TargetFunction.rateMinWorkingDays(bestSolution.timetable));
		System.out.println("Target: " + TargetFunction.getTargetFunctionValue(bestSolution.timetable));
	}
}

