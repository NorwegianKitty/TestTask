package filesOperation;


public class Main {
	
	public static void main(String[] args) {
		
		String[] testArr = {"-a", "-p", "sample-", "-o", "-f",
				"C:\\Users\\Melinsa\\Desktop\\myJava", "in1.txt"};
		
		args = testArr;
		
		ArgsHandler argsHandler = new ArgsHandler(args);	
		
		try {
			argsHandler.ArgsPOAHandler(); // Обработка действий для арг. -p -o -a	
			
			// Те если набор входных файлов не пустой
			if(!argsHandler.getInputFiles().isEmpty()) {
				
				FileHandler fileHandler = new FileHandler(argsHandler);
				fileHandler.processFiles(); // Чтение, создание иди добавление, сортировка
				
				argsHandler.ArgsSFHandler(fileHandler.getFileStat()); // Отображение статистики
			}
			
		} catch (Exception e) {
			System.out.println("Ошибка: " + e.getMessage());
		}


		


	
	}

}
