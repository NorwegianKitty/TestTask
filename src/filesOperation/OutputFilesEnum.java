package filesOperation;


public enum OutputFilesEnum {
	
	DEFAULT("default.txt"),
	OUTPUT_FILE_INTEGER("integers.txt"),
	OUTPUT_FILE_FLOAT("floats.txt"),
	OUTPUT_FILE_STRING("strings.txt");
	
	private String directoryPath;
	private String fileName;
	private String outputFilePath;
	
	private final String DEFAULT_DIRECTORY_PATH;
	private final String DEFAULT_FILE_NAME;
	
	private OutputFilesEnum(String fileName) {
		
		DEFAULT_FILE_NAME = fileName;
		this.fileName = DEFAULT_FILE_NAME;
		
		DEFAULT_DIRECTORY_PATH = System.getProperty("user.dir");
		directoryPath = DEFAULT_DIRECTORY_PATH;
		
		refreshOutputFilePath();
	}
	
	public String getDirectoryPath() {
		return directoryPath;
	}

	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOutputFilePath() {
		return outputFilePath;
	}

	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}

	public void CorrectDirectoryPathOrFileName(String directoryPathOrFileName, boolean isPass) {
		
		if(isPass) {
			setDirectoryPath(directoryPathOrFileName);			
		} else {
			setFileName(directoryPathOrFileName + fileName);
		}
		
		refreshOutputFilePath();
	}
	
	public void refreshOutputFilePath() {
		outputFilePath = directoryPath + "\\" + fileName;
	}	

	public String getDefaultOutputPass() {
		return DEFAULT_DIRECTORY_PATH + "\\" + DEFAULT_FILE_NAME;
	}
}


