package se.lexicon.model;

import java.time.LocalDate;

public class ToDo {
    private int toDoId;
    private String taskTittle;
    private String taskDescripton;
    private LocalDate taskDeadline;
    private boolean done;
    private Person taskAssignee;

    public ToDo() {}

    public ToDo(int toDoId, String taskTittle, String taskDescripton, boolean done, Person taskAssignee, LocalDate taskDeadline) {
        this.toDoId = toDoId;
        this.taskTittle = taskTittle;
        this.taskDescripton = taskDescripton;
        this.done = done;
        this.taskAssignee = taskAssignee;
        this.taskDeadline = taskDeadline;
    }

    /**
     * Constructs a new {@code ToDo} instance that has not yet been persisted to the database.
     * <p>
     * This constructor is intended to be used when creating a new todo item before it is
     * stored in the database. The {@code todoId} is initialized to {@code 0} and will be
     * replaced with a generated value when the object is persisted via JDBC.
     * </p>
     *
     * <p>
     * By default, a newly created todo item is marked as not done, has no assignee and no deadline.
     * </p>
     *
     * @param title the title of the todo item
     * @param description an optional description of the todo item
     * @param deadline the deadline for completing the todo item
     */
    public ToDo(String title, String description, LocalDate deadline) {
        this(0, title, description,  false,  null, deadline);
    }

    public int getToDoId() { return toDoId;}

    public void setToDoId(int toDoId) { this.toDoId = toDoId;}

    public String getTaskTittle() { return taskTittle;}

    public void setTaskTittle(String taskTittle) { this.taskTittle = taskTittle;}

    public String getTaskDescripton() { return taskDescripton;}

    public void setTaskDescripton(String taskDescripton) { this.taskDescripton = taskDescripton;}

    public LocalDate getTaskDeadline() { return taskDeadline;}

    public void setTaskDeadline(LocalDate taskDeadline) { this.taskDeadline = taskDeadline;}

    public boolean isDone() { return done;}

    public void setDone(boolean done) { this.done = done;}

    public Person getTaskAssignee() { return taskAssignee;}

    public void setTaskAssignee(Person taskAssignee) { this.taskAssignee = taskAssignee;}

}
