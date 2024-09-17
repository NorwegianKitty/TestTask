package filesOperation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class ArgsHandler {

	public static OutputFilesEnum outputFiles;
	private String[] args;
	private Set<String> inputFiles = new HashSet<>();
    private boolean isAdding = false; 
    
	public ArgsHandler(String[] args) {
		this.args = args;
	}
	
	public void ArgsPOAHandler() {
		
        final Iterator<String> argIterator = Arrays.asList(args).iterator();
		
        while (argIterator.hasNext()) {
            String arg = argIterator.next();
            boolean isSwitchWorked = false;
            
            switch(arg) {  
            	case "-p":
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

	public void ArgsSFHandler(FileStatistics fileStat) {
		
		final Iterator<String> argIterator = Arrays.asList(args).iterator();
		
        while (argIterator.hasNext()) {
            String arg = argIterator.next();
        
            switch(arg) {
            	case "-s":
            		ArgSAction(fileStat);
            		break;
            	case "-f":
            		ArgFAction(fileStat);
            		break;            
            }        
        }
	}
	
	// Пользовательский Префикс 
	private void ArgPAction(String prefix) {
		
		// Если длинна префикса + самое длинное название (integers) > 255, то в Windows такое недопустимо =>
		if(prefix.length() + 8 > 255) { // Обрезаем префикс, оставляя в сохранности все каноничные названия файлов
			
			prefix = prefix.substring(0, 255 - 8);
			System.out.println("Префикс обрезан до " + prefix + ", чтобы длина названия выходных файлов"
					+ " не превышала 255.");
		}
		
		// Проверка на запрещенные к использованию символы в именах файлов (Windows)
		String invalidCharacters = "\\/:*?\"<>|";
		boolean isIncorrectChar = false;
		
		for(int i = 0; i < invalidCharacters.length() && !isIncorrectChar; i++) {
			char invalidChar = invalidCharacters.charAt(i);
			
			if(prefix.contains(String.valueOf(invalidChar))) {
				isIncorrectChar = true;
			}			
		}
		
		// Нет проверки на зарезервированные ключевые слова, тк в любом случае вкупе с именами файлов ошибки не выйдет
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
	
	// Пользовательская директория для выходных файлов
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

	// Включаем режим добавление в существующие файлы 
	private void ArgAAction() {
		isAdding = true;
	}
	
	// Отобразить короткую статистику
	private HashMap<Integer, int[]> ArgSAction(FileStatistics fileStat) {
		
		int[] statArr = fileStat.shortStat();
		
		String[] fileNamesArr = {"File names:",
				outputFiles.OUTPUT_FILE_INTEGER.getFileName(),
				outputFiles.OUTPUT_FILE_FLOAT.getFileName(),
				outputFiles.OUTPUT_FILE_STRING.getFileName() };
		
		int[] indexArr = new int[3]; //Если в файле 0 записей (count), информацию о файле выводить не будем
		
		int maxFileNameLength = 0;
		
		for(int i = 0; i < statArr.length; i++) {
			
			if(statArr[i] > 0) {
				indexArr[i] = 1;
				
				if(fileNamesArr[i].length() > maxFileNameLength) {
					maxFileNameLength = fileNamesArr[i].length(); //Для форматирования
				}
			}
		}
		
		// Теперь у нас есть массив вида 1 0 1, где 1 - выводим данные и размер для форматирования
		if(indexArr[0] == indexArr[1] && indexArr[1] == indexArr[2] && indexArr[2] == 0) {
			return null; // у нас нет файлов для отображения статистики
		}
		
		int offset = maxFileNameLength + 5; // Отступ

		// Имена файлов
		for (int i = 0; i < fileNamesArr.length; i++) {
			
			if(i == 0 || indexArr[i-1] == 1) {
				System.out.print(String.format("%-" + offset + "s", fileNamesArr[i]));			
			}
		}
		
		System.out.println("\n");
		
		// Короткая статистика
		for (int i = 0; i < statArr.length; i++) {
			
			if(i == 0) {
				System.out.print(String.format("%-" + offset + "s", "Count:"));
			}
			
			if(indexArr[i] == 1) {				
				System.out.print(String.format("%-" + offset + "d", statArr[i]));			
			}
		}
		
		// Вернуть размер отступа и массив индексов действительных файлов
		HashMap<Integer, int[]> mapToreturn = new HashMap<Integer, int[]>();
		mapToreturn.put(offset, indexArr);
		
		return mapToreturn;
	}
	
	// Отобразить полную статистику
	private void ArgFAction(FileStatistics fileStat) {
		
		HashMap<Integer, int[]> tempMap = ArgSAction(fileStat); // Ключ - длина отсутпа, значение массив индексов
		
		if (tempMap != null && !tempMap.isEmpty()) {
		    int offset = tempMap.keySet().iterator().next();
		    int[] indexArr = tempMap.get(offset);
		    
			int[] iStat = fileStat.iReturnStat(); // i - int
			float[] fStat = fileStat.fReturnStat(); // f - float
			int[] strStat = fileStat.strReturnStat(); // s - String

			displayFunc(offset, indexArr, iStat, fStat, strStat, 0, "Min:");
			displayFunc(offset, indexArr, iStat, fStat, strStat, 1, "Max:");
			displayFuncSumAvg(offset, indexArr, iStat, fStat, 2, "Summa:", fileStat.shortStat());
		} else {
		    System.out.println("Статистика не может быть выведена!");
		}		
	}
	
	private void displayFunc(int offset, int[] indexArr, int[] iStat, float[] fStat, int[] strStat, int i
			, String text) {
		
		System.out.print("\n");
		System.out.print(String.format("%-" + offset + "s", text)); // Название выводимого измерения
		
		if(indexArr[0] == 1) System.out.print(String.format("%-" + offset + "d", iStat[i]));	
		if(indexArr[1] == 1) System.out.print(String.format("%-" + offset + "f", fStat[i]));	
		if(indexArr[2] == 1) System.out.print(String.format("%-" + offset + "d", strStat[i]));	
	}

	private void displayFuncSumAvg(int offset, int[] indexArr, int[] iStat, float[] fStat, int i,
			String text, int[] counter) {
		
		System.out.print("\n");
		System.out.print(String.format("%-" + offset + "s", text));
		
		if(indexArr[0] == 1) System.out.print(String.format("%-" + offset + "d", iStat[i]));	
		if(indexArr[1] == 1) System.out.print(String.format("%-" + offset + "f", fStat[i]));	
		if(indexArr[2] == 1) System.out.print(String.format("%-" + offset + "s", "-"));
		
		System.out.print("\n");
		System.out.print(String.format("%-" + offset + "s", "Average:"));
		
		try {
			if(indexArr[0] == 1) System.out.print(String.format("%-" + offset + "d", iStat[i]/counter[0]));			
		} catch (Exception e) {
			if(indexArr[0] == 1) System.out.print(String.format("%-" + offset + "f", iStat[i]/counter[0]));	
		}
		
		if(indexArr[1] == 1) System.out.print(String.format("%-" + offset + "f", fStat[i]/counter[1]));	
		if(indexArr[2] == 1) System.out.print(String.format("%-" + offset + "s", "-"));
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

	public static OutputFilesEnum getOutputFiles() {
		return outputFiles;
	}

	public static void setOutputFiles(OutputFilesEnum outputFiles) {
		ArgsHandler.outputFiles = outputFiles;
	}
}


