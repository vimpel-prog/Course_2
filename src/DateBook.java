import java.time.LocalDate;
import java.util.*;

public class DateBook {
    Map<Integer, Task> taskMap = new HashMap<>();

    public void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public Collection<Task> getTaskForDay(LocalDate localDate) {
        Set<Task> taskForDaySet = new TreeSet<>(new TaskTimeComparator());
        for (Task task : taskMap.values()) {
            if (task.appearsIn(localDate)) {
                taskForDaySet.add(task);
            }
        }
        return taskForDaySet;
    }

    public void deleteTask(int id) {
        taskMap.remove(id);
    }

}
