package filesOperation;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.io.Reader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class FileHandler {

	private ArgsHandler argsHandler;
	private FileStatistics fileStat = new FileStatistics();
	
	private boolean isOutIntFileExsist = false;
	private boolean isOutFloatFileExsist = false;
	private boolean isOutStrFileExsist = false;
	
	public FileHandler(ArgsHandler argsHandler) {
		this.argsHandler = argsHandler;
		
		if(argsHandler.isAdding()) {
			isOutIntFileExsist = true;
			isOutFloatFileExsist = true;
			isOutStrFileExsist = true;
		}
	}
	
	public void processFiles() {

	    for (String file : argsHandler.getInputFiles()) {
	        
	        Reader fileReader;
	        
	        try {
	            fileReader = new FileReader(file);
	            Scanner sc = new Scanner(fileReader);
	            
	            while (sc.hasNextLine()) {
	                String line = sc.nextLine();
	                
	                if (line != null && !line.isEmpty()) {
	                    writeFile(sort(line));
	                }
	            }
	            sc.close();
	        } catch (FileNotFoundException e) {
	            System.out.println("Ошибка при чтении файла: " + e.getMessage());
	        }
	    }    
	}
	
	public void writeFile(HashMap<String, Object> typeNameAndValue) {
		
		String key = typeNameAndValue.keySet().iterator().next();
		Object value = typeNameAndValue.get(key);
		String pass;
		
		switch(key) {
			case "int":
				pass = checkFilesPath(key);
				
				if (pass != null && value instanceof Integer) {
	                try (FileWriter writer = new FileWriter(pass, true)) {
	                    writer.write(value.toString());
	                    writer.write("\n");
	                    fileStat.updateInt((Integer) value);
	                } catch (Exception e) {
	                    System.out.println("Ошибка записи файла (" + key + ") "+ e.getMessage());
	                }
	            }
	            break;
				
			case "float":
	            pass = checkFilesPath(key);
	            
	            if (pass != null && value instanceof Float) {
	                try (FileWriter writer = new FileWriter(pass, true)) {
	                    writer.write(value.toString());
	                    writer.write("\n");
	                    fileStat.updateFloat((Float) value);
	                } catch (Exception e) {
	                    System.out.println("Ошибка записи файла (" + key + ") "+ e.getMessage());
	                }
	            }
	            break;

	        case "str":
	            pass = checkFilesPath(key);
	            
	            if (pass != null && value instanceof String) {
	                try (FileWriter writer = new FileWriter(pass, true)) {
	                    writer.write((String) value);
	                    writer.write("\n");
	                    fileStat.updateString((String) value);
	                } catch (Exception e) {
	                    System.out.println("Ошибка записи файла (" + key + ") "+ e.getMessage());
	                }
	            }
	            break;

	        default:
	            System.out.println("Неизвестный тип."); //Если неизвестный должно выбросить str, но мало ли
	            break;
		}
	}
	
	private String checkFilesPath(String typeToFile) {
		
		String pass = null;
		File file;
		
		switch(typeToFile) {
		
			case "int":
		        file = getFile(isOutIntFileExsist, ArgsHandler.getOutputFiles().OUTPUT_FILE_INTEGER);
		        
		        if (file == null) {
		            System.out.println("Запись в файл с int вестись не будет.");
		            return null;
		        }
		        return file.getAbsolutePath();
		        
			case "float":
				file = getFile(isOutFloatFileExsist, ArgsHandler.getOutputFiles().OUTPUT_FILE_FLOAT);
				
		        if (file == null) {
		            System.out.println("Запись в файл с float вестись не будет.");
		            return null;
		        }
		        return file.getAbsolutePath();
			
			case "str":
				file = getFile(isOutStrFileExsist, ArgsHandler.getOutputFiles().OUTPUT_FILE_STRING);
				
		        if (file == null) {
		            System.out.println("Запись в файл с String вестись не будет.");
		            return null;
		        }
		        return file.getAbsolutePath();		        				
			}		
		return pass;		
	}
	
	private File getFile(boolean isFileExist, OutputFilesEnum outputFile) {
		
	    File file;
	    
	    if (isFileExist) {
	        file = new File(outputFile.getOutputFilePath());
	        
	        if (!file.exists()) {
	            System.out.println("Файл не существует. Попробуем создать новый.");
	            file = createNewFile(outputFile.getOutputFilePath(), outputFile.getDefaultOutputPass(), outputFile);
	        }
	    } else {
	        file = createNewFile(outputFile.getOutputFilePath(), outputFile.getDefaultOutputPass(), outputFile);
	        isOutIntFileExsist = file != null;
	    }
	    return file;
	}

	private File createNewFile(String outputPath, String defaultPath, OutputFilesEnum outputFile) {
		
	    File file = new File(outputPath);
	    
	    try (FileWriter writer = new FileWriter(file)) {
	        outputFile.setOutputFilePath(outputPath);
	        return file;
	    } catch (Exception e) {
	        System.out.println("Не удалось создать файл по пути: " + file.getPath()
	        	+ ". Попробуем создать по пути по умолчанию.");
	        
	        file = new File(defaultPath);
	        
	        try (FileWriter writer = new FileWriter(file)) {
	            outputFile.setOutputFilePath(defaultPath);
	            return file;
	        } catch (Exception e2) {
	            System.out.println("Не удалось создать файл по пути по умолчанию: " + defaultPath);
	            return null;
	        }
	    }
	}

	private HashMap<String, Object> sort(String line) {

		HashMap<String, Object> typeNameAndValue = new HashMap<String, Object>();
		
		try {
			int iTemp = transformToInt(line);
			typeNameAndValue.put("int", iTemp);
		} catch (Exception e1) {
			try {
				float fTemp = transformToFloat(line);
				typeNameAndValue.put("float", fTemp);				
			} catch (Exception e2) {
				typeNameAndValue.put("str", line);
			}			
		}		
		return typeNameAndValue;
	}
	
	private int transformToInt(String str) throws Exception {
		
		try {
			int iTemp = Integer.parseInt(str);
			return iTemp;
		} catch (Exception e) {
			throw new Exception();
		}		
	}
	
	private float transformToFloat(String str) throws Exception {
		
		try {
			float fTemp = Float.parseFloat(str);
			return fTemp;
		} catch (Exception e) {
			throw new Exception();
		}		
	}
	
	public FileStatistics getFileStat() {
		return fileStat;
	}
	
	public void setFileStat(FileStatistics fileStat) {
		this.fileStat = fileStat;
	}
}


