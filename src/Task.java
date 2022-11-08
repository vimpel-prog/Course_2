import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Task {
    private static int counter;
    private String name;
    private String description;
    private boolean isPersonal;
    private boolean isBusiness;
    private final LocalDateTime taskDateTime;
    private final int id;

    public void setPersonal(boolean personal) {
        isPersonal = personal;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public Task(String name, String description, LocalDateTime taskDateTime) {
        this.name = name;
        this.description = description;
        this.taskDateTime = taskDateTime;
        counter++;
        id = counter;
    }

    public abstract boolean appearsIn(LocalDate localDate);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTaskDateTime() {
        return taskDateTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskDateTime=" + taskDateTime +
                ", id=" + id +
                '}' + '\n';
    }
}
