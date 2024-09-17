package filesOperation;


public class Main {
	
	public static void main(String[] args) {
		
		ArgsHandler argsHandler = new ArgsHandler(args);	
		
		try {
			argsHandler.argsPOAHandler(); // Обработка действий для арг. -p -o -a	
			
			// Те если набор входных файлов не пустой
			if(!argsHandler.getInputFiles().isEmpty()) {
				
				FileHandler fileHandler = new FileHandler(argsHandler);
				fileHandler.processFiles(); // Чтение, создание или добавление, сортировка
				
				argsHandler.argsSFHandler(fileHandler.getFileStat()); // Отображение статистики
			}
			
		} catch (Exception e) {
			System.out.println("Ошибка: " + e.getMessage());
		}
	}
}
