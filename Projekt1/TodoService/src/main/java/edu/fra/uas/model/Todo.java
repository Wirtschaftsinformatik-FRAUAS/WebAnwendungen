package edu.fra.uas.model;

public class Todo {

    private long userId;
    private long todoId;
    private String description;
    private boolean isCompleted;

    public Todo() {
    }



    public Todo(long userId, long todoId, String description) {
        this.userId = userId;
        this.todoId = todoId;
        this.description = description;
        this.isCompleted = false;
    }



    public long getTodoId() {
        return todoId;
    }

    public void setTodoId(long todoId) {
        this.todoId = todoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }



    public long getUserId() {
        return userId;
    }



    public void setUserId(long userId) {
        this.userId = userId;
    }

}
