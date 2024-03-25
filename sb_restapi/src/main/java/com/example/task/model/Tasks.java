package com.example.task.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Tasks {

    @Id
    
    private int taskId;

    @NotNull(message = "Description must not be blank")
    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotNull(message = "Status must not be blank")
    private String status;

    @NotNull(message = "Assigned to must not be blank")
    private String assignedto;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedto() {
        return assignedto;
    }

    public void setAssignedto(String assignedto) {
        this.assignedto = assignedto;
    }

    public Tasks(int taskId, String description, String status, String assignedto) {
        super();
        this.taskId = taskId;
        this.description = description;
        this.status = status;
        this.assignedto = assignedto;
    }

    public Tasks() {

    }
}
