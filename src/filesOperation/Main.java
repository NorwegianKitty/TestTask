package filesOperation;


public class Main {
	
	public static void main(String[] args) {
		
		String[] testArr = {"-a", "-p", "\\sample-", "-o",
				"C:\\Users\\Melinsa\\Desktop\\Course\\ITVDN\\Жаба\\2   Java Essential", "in1.txt", "in2.txt", ".txt23"};
		
		args = testArr;
		
		ArgsHandler argsHandler = new ArgsHandler(args);	
		
		try {
			argsHandler.ArgsPOAHandler(); // Обработка действий для арг. -p -o -a	
		} catch (Exception e) {
			System.out.println("Ошибка: " + e.getMessage());
		}

		// Те если набор входных файлов не пустой
		if(!argsHandler.getInputFiles().isEmpty()) {
			
			FileHandler fileHandler = new FileHandler(argsHandler);
			fileHandler.processFiles(); // Чтение, создание иди добавление, сортировка
		}

		argsHandler.ArgsSFHandler(); // Отображение статистики


	
	}

}
