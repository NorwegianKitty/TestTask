package filesOperation;

public enum OutputFilesEnum {
	
	DEFAULT("default.txt"),
	OUTPUT_FILE_INTEGER("integers.txt"),
	OUTPUT_FILE_FLOAT("floats.txt"),
	OUTPUT_FILE_STRING("strings.txt");
	
	private String directoryPath = System.getProperty("user.dir");
	private String fileName;
	private String outputFilePath;

	private OutputFilesEnum(String fileName) {
		this.fileName = fileName;
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
}
