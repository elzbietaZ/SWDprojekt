import Algorithms.Initialization;
import Model.Model;


public class Launcher {
	
	public static void main(String [] arg){
		DataPreparation dp=new DataPreparation();
		dp.readEcttFile("exampleData/myExample.ctt");
		dp.runSubisidiaryMethods();
		Initialization initialization=new Initialization();
		initialization.makeInitialPlan();
	}

}

