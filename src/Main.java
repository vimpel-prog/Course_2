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
                            System.out.println();
                            inputTask(scanner);
                            System.out.println();
                            break;
                        case 2:
                            System.out.println();
                            deleteTaskById(scanner);
                            System.out.println();
                            break;
                        case 3:
                            System.out.println();
                            printTaskForDay(scanner);
                            System.out.println();
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println();
                    System.out.println("Выберите пункт меню из списка!");
                    System.out.println();
                }
            }
        }
    }

    private static void inputTask(Scanner scanner) {
        Task task;
        String taskName;
        String taskDescription;
        LocalDateTime taskDateTime;

        do {
            System.out.println("Введите название задачи: ");
            taskName = scanner.useDelimiter("\n").next();
        } while (taskName.isBlank());

        do {
            System.out.println("Введите описание задачи: ");
            taskDescription = scanner.useDelimiter("\n").next();
        } while (taskDescription.isBlank());

        while (true) {
            System.out.println("Введите время выполнения задачи в формате dd.MM.yyyy HH:mm ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            try {
                taskDateTime = LocalDateTime.parse(scanner.useDelimiter("\n").next(), formatter);
                break;
            } catch (Exception e) {
                System.out.println("Введите время в правильном формате!!!\n");
            }
        }

        label1:
        while (true) {
            System.out.println();
            System.out.print("1.Одноразовая\n" +
                    "2.Ежедневная\n" +
                    "3.Еженедельная\n" +
                    "4.Ежемесячнаяя\n" +
                    "5.Ежегодная\n" + "" +
                    "Введите повторяемость задачи: ");
            int taskRepeat;
            try {
                taskRepeat = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Выберете пункт меню из списка!!!\n");
                continue;
            }

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
            System.out.println();
            System.out.print("1.Личная\n" +
                    "2.Рабочая\n" +
                    "Выберете тип задачи: ");
            if (scanner.hasNextInt()) {
                int i = scanner.nextInt();
                if (i == 1) {
                    task.setPersonal(true);
                    break;
                } else if (i == 2) {
                    task.setBusiness(true);
                    break;
                }
            } else {
                scanner.next();
                System.out.println("Выберете пункт меню из списка!");
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
        while (true) {
            System.out.println("Введите пожалуйста дату в формате дд.мм.гггг : ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate;
            try {
                localDate = LocalDate.parse(scanner.next(), formatter);
            } catch (Exception e) {
                System.out.println("Введите дату в правильном формате!!!\n");
                continue;
            }
            System.out.println("Задачи на " + localDate.format(formatter) + ":\n");
            for (Task task : dateBook.getTaskForDay(localDate)) {
                System.out.printf("%d. %s %s описание: %s%n", task.getId(), task.getName(), task.getTaskDateTime().toLocalTime(), task.getDescription());
            }
            break;

        }
    }

    private static void deleteTaskById(Scanner scanner) {
        while (true) {
            System.out.print("Введини id задачи которую надо удалить: ");
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                try {
                    dateBook.deleteTask(id);
                    break;
                } catch (TaskNotFoundException e) {
                    System.out.println("Такой задачи в списке нет!\n");
                    break;
                }
            } else {
                scanner.next();
                System.out.println("Ввводите номер только цифрами!");
            }
        }
    }
}
