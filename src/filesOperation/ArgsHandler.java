package filesOperation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ArgsHandler {

	public static OutputFilesEnum outputFiles;
	//PRIVATE ЧТО ВЫШЕ
	private String[] args;
	private Set<String> inputFiles = new HashSet<>();
    private boolean isAdding = false; 
    
    // ✅
	public ArgsHandler(String[] args) {
		this.args = args;
	}
	
	// ✅
	public void ArgsPOAHandler() {
		
        final Iterator<String> argIterator = Arrays.asList(args).iterator();
		
        while (argIterator.hasNext()) {
            String arg = argIterator.next();
            boolean isSwitchWorked = false;
            System.out.println(arg);
            
            switch(arg) {  
            
            	case "-p": //Добавить проверку на .txt и тп
            		isSwitchWorked = true;
            		
            		if(argIterator.hasNext()) {
            			
            			String prefix = argIterator.next();
            			ArgPAction(prefix);
            		}
            		break;
            		
            	case "-o":
            		isSwitchWorked = true;
            		
            		if(argIterator.hasNext()) {
            			
            			String path = argIterator.next();
            			ArgOAction(path);
            		}
            		break;
            		
            	case "-a": 
            		isSwitchWorked = true;
            		
            		ArgAAction();            		
            		break;            	
            }
            
            if(!isSwitchWorked) {
            	
            	if (arg.endsWith(".txt")) {
            	    inputFiles.add(arg);
            	}             
            }
        }
	}

	// [x]
	public void ArgsSFHandler() {
		
		final Iterator<String> argIterator = Arrays.asList(args).iterator();
		
        while (argIterator.hasNext()) {
            String arg = argIterator.next();
        
            switch(arg) {
            	case "-s":
            		ArgSAction();
            		break;
            	case "-f":
            		ArgFAction();
            		break;            
            }        
        }
	}
	
	// Prefix ✅ try-catch ✅
	private void ArgPAction(String prefix) {
		
		// Если длинна префикса + самое длинное название (integers) > 255, то в Windows такое недопустимо =>
		if(prefix.length() + 8 > 255) { //Обрезаем префикс, оставляя в сохранности все каноничные названия файлов
			
			prefix = prefix.substring(0, 255 - 8);
			System.out.println("Префикс обрезан до " + prefix + ", чтобы длина названия выходных файлов"
					+ " не превышала 255.");
		}
		
		//Проверка на запрещенные к использованию символы в именах файлов (Windows)
		String invalidCharacters = "\\/:*?\"<>|";
		boolean isIncorrectChar = false;
		
		for(int i = 0; i < invalidCharacters.length() && !isIncorrectChar; i++) {
			
			char invalidChar = invalidCharacters.charAt(i);
			
			if(prefix.contains(String.valueOf(invalidChar))) {
				isIncorrectChar = true;
			}			
		}
		
		//Нет проверки на зарезервированные ключевые слова, тк в любом случае вкупе с именами файлов ошибки не выйдет
		if(isIncorrectChar == false) {
			try {
				for(OutputFilesEnum outputFiles : OutputFilesEnum.values()) {
					outputFiles.CorrectDirectoryPathOrFileName(prefix, false);
				}
			} catch (Exception e) {
				System.out.println("Ошибка: " + e.getMessage() + ", при попытки установки префикса для имен файлов.");
				System.out.println("Используется именование по умолчанию.");
			}
		} else {
			System.out.println("Ошибка при попытки установки префикса для имен файлов, префикс содержит"
					+ " недопустимые символы.");
			System.out.println("Используется именование по умолчанию.");
		}
	}
	
	// CustomDirectory ✅ try-catch не нужен
	private void ArgOAction(String path) {

		Path dirPath = Paths.get(path);
		
	    if (Files.exists(dirPath) && Files.isDirectory(dirPath)) {
	        for (OutputFilesEnum outputFiles : OutputFilesEnum.values()) {
	            outputFiles.CorrectDirectoryPathOrFileName(path, true);
	        }
	    } else {
	        System.out.println("Ошибка в параметре для аргумента \"-o\". Параметр: " + path);
	        System.out.println("Указанный путь не является директорией или не существует.");
	        System.out.println("Для записи файлов используется директория по умолчанию: "
	                    + OutputFilesEnum.DEFAULT.getDirectoryPath());
	    }
	}

	// Turn on adding mode ✅ try-catch не нужен
	private void ArgAAction() {
		isAdding = true;
	}
	
	// Display short statistics [x]
	private void ArgSAction() {
		
	}
	
	// Display full statistics [x]
	private void ArgFAction() {
		ArgSAction(); // и дополнить
	}
	

	public Set<String> getInputFiles() {
		return inputFiles;
	}
	

	public void setInputFiles(Set<String> inputFiles) {
		this.inputFiles = inputFiles;
	}
	

	public boolean isAdding() {
		return isAdding;
	}
	

	public void setAdding(boolean isAdding) {
		this.isAdding = isAdding;
	}

	
}
