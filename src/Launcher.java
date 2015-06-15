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
	private static final int compactnessParam = 4;
	private static final int minNumberDaysParam = 1;
	private static final int firstLastPeriodParam = 6;
	
	public static void main(String [] arg){
		DataPreparation dp=new DataPreparation();
		dp.readEcttFile("exampleData/myExample2.ctt");
		dp.runSubisidiaryMethods();
		Initialization initialization=new Initialization();
		initialization.makeInitialPlan();
		final TimetablePrinter printer= new TimetablePrinter(System.out);
		printer.printTimetable(Model.inicialTimetable.timetable);
		System.out.println("Nieprzypisane zajęcia w potokach");
		System.out.println(Model.unassignedInCurricula);

		TargetFunction.initCoursesInCurriculums();
        System.out.println("Rate of compactness: " + compactnessParam*TargetFunction.rateCompactness(Model.inicialTimetable.timetable));
        System.out.println("Rate of minimum number of working days: " + minNumberDaysParam*TargetFunction.rateMinNumberDays(Model.inicialTimetable.timetable));
        System.out.println("Rate of courses in first and last period: " + firstLastPeriodParam*TargetFunction.rateNumberOfCoursesInFirstAndLastPeriod(Model.inicialTimetable.timetable));
		System.out.println("Target: " + TargetFunction.getTargetFunctionValue(compactnessParam, minNumberDaysParam, firstLastPeriodParam, Model.inicialTimetable.timetable));

		List<Tuple<Tuple[][][], Tuple[][][]>> tabuList = new ArrayList<>();
        Timetable bestSolution = Model.inicialTimetable.getCopy();
        Timetable currentSolution = Model.inicialTimetable.getCopy();

		int i = 0;
		while(TargetFunction.getTargetFunctionValue(compactnessParam, minNumberDaysParam, firstLastPeriodParam, bestSolution.timetable) != 0 && i < NUMBER_OF_ITERATIONS){
            if(tabuList.size() > 50){
                tabuList.remove(tabuList.get(0));
            }
			currentSolution.timetable = TabuSearch.getBestNeighbour(currentSolution, tabuList, compactnessParam, minNumberDaysParam, firstLastPeriodParam);
			if(TargetFunction.getTargetFunctionValue(compactnessParam, minNumberDaysParam, firstLastPeriodParam, currentSolution.timetable) <
					TargetFunction.getTargetFunctionValue(compactnessParam, minNumberDaysParam, firstLastPeriodParam, bestSolution.timetable)){
				SubsidiaryMethods.copySolution(currentSolution.timetable, bestSolution.timetable);
			}

//            printer.printTimetable(bestSolution.timetable);
			i++;
		}
		printer.printTimetable(bestSolution.timetable);
        System.out.println("Rate of compactness: " + compactnessParam*TargetFunction.rateCompactness(bestSolution.timetable));
        System.out.println("Rate of minimum number of working days: " + minNumberDaysParam*TargetFunction.rateMinNumberDays(bestSolution.timetable));
        System.out.println("Rate of courses in first and last period: " + firstLastPeriodParam*TargetFunction.rateNumberOfCoursesInFirstAndLastPeriod(bestSolution.timetable));
		System.out.println("Target: " + TargetFunction.getTargetFunctionValue(compactnessParam, minNumberDaysParam, firstLastPeriodParam, bestSolution.timetable));
	}
}

