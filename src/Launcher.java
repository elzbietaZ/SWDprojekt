
public class Launcher {
	
	public static void main(String [] arg){
		DataPreparation dp=new DataPreparation();
		dp.readEcttFile("exampleData/DataFormat");
		dp.runSubisidiaryMethods();
	}

}
