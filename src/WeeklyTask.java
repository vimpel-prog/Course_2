import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeeklyTask extends Task{
    public WeeklyTask(String name, String description, LocalDateTime taskDateTime) {
        super(name, description, taskDateTime);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        LocalDate taskCreationDate = getTaskDateTime().toLocalDate();
        return taskCreationDate.equals(localDate) ||
                taskCreationDate.isBefore(localDate) &&
                        taskCreationDate.getDayOfWeek().equals(localDate.getDayOfWeek());
    }
}
