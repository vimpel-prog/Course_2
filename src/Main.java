import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    static DateBook dateBook = new DateBook();
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            deleteTaskById(scanner);
                            break;
                        case 3:
                            printTaskForDay(scanner);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }

    private static void inputTask(Scanner scanner) {
        Task task;
        System.out.println("Введите название задачи: ");
        String taskName = scanner.useDelimiter("\n").next();
        System.out.println("Введите описание задачи: ");
        String taskDescription = scanner.useDelimiter("\n").next();
        System.out.println("Введите время выполнения задачи в формате dd.MM.yyyy HH:mm ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime taskDateTime = LocalDateTime.parse(scanner.useDelimiter("\n").next(), formatter);


        label1:
        while (true) {
            System.out.print("1.Одноразовая\n" +
                    "2.Ежедневная\n" +
                    "3.Еженедельная\n" +
                    "4.Ежемесячнаяя\n" +
                    "5.Ежегодная\n" + "" +
                    "Введите повторяемость задачи: ");
            int taskRepeat = scanner.nextInt();
            switch (taskRepeat) {
                case 1:
                    task = new OneTimeTask(taskName, taskDescription, taskDateTime);
                    break label1;
                case 2:
                    task = new DailyTask(taskName, taskDescription, taskDateTime);
                    break label1;
                case 3:
                    task = new WeeklyTask(taskName, taskDescription, taskDateTime);
                    break label1;
                case 4:
                    task = new MonthlyTask(taskName, taskDescription, taskDateTime);
                    break label1;
                case 5:
                    task = new AnnualTask(taskName, taskDescription, taskDateTime);
                    break label1;
            }
        }

        while (true) {
            System.out.println("1.Личная\n" +
                    "2.Рабочая\n" +
                    "Выберете тип задачи: ");
            int i= scanner.nextInt();
            if (i == 1) {
                task.setPersonal(true);
                break;
            }else if (i == 2) {
                task.setBusiness(true);
                break;
            }
        }
        dateBook.addTask(task);
    }

    private static void printMenu() {
        System.out.println("1. Добавить задачу\n" +
                "2. Удалить задачу\n" +
                "3. Получить задачу на указанный день\n" +
                "0. Выход");
    }

    private static void printTaskForDay(Scanner scanner) {
        System.out.println("Введите пожалуйста дату в формате dd.MM.yyyy, чтобы узнать задачи на этот день :");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(scanner.next(), formatter);
        System.out.println("Задачи на "+localDate.format(formatter)+":");
        for(Task task : dateBook.getTaskForDay(localDate)){
            System.out.printf("%s %s описание: %s%n",task.getName(),task.getTaskDateTime().toLocalTime(),task.getDescription());
        }
    }

    private static void deleteTaskById(Scanner scanner) {
        System.out.println("Введини id задачи которую надо удалить: ");
        int id = scanner.nextInt();
        dateBook.deleteTask(id);
    }
}
