package filesOperation;

public class Main {
	
	public static void main(String[] args) {
		
		String[] testArr = {"-a", "-p", "\\sample-", "-o",
				"C:\\Users\\Melinsa\\Desktop\\Course\\ITVDN\\Жаба\\2   Java Essential", "in1.txt", "in2.txt", ".txt23"};
		
		args = testArr;
		
		ArgsHandler argsHandler = new ArgsHandler(args);	
		
		
		System.out.println(ArgsHandler.outputFiles.OUTPUT_FILE_INTEGER.getOutputFilePath());
		System.out.println(ArgsHandler.outputFiles.OUTPUT_FILE_FLOAT.getOutputFilePath());
		System.out.println(ArgsHandler.outputFiles.OUTPUT_FILE_STRING.getOutputFilePath());
		
		System.out.println("====================================================");
		argsHandler.ArgsPOAHandler();
		
		System.out.println(ArgsHandler.outputFiles.OUTPUT_FILE_INTEGER.getOutputFilePath());
		System.out.println(ArgsHandler.outputFiles.OUTPUT_FILE_FLOAT.getOutputFilePath());
		System.out.println(ArgsHandler.outputFiles.OUTPUT_FILE_STRING.getOutputFilePath());
		
		//argsHandler.FileRecording();
		//argsHandler.ArgsSFHandler();
		System.out.println(argsHandler.getInputFiles());

		FileHandler fileHandler = new FileHandler(argsHandler);
	
	}

}

//isArgP = false; // Set custom prefix       
//isArgO = false; // Custom directory        
//isArgA = false; // Adding to files mod     
//isArgS = false; // Display short statistics
//isArgF = false; // Display full statistics 