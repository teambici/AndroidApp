package co.edu.eci.ieti.android.network.data;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import androidx.room.Entity;

import androidx.room.PrimaryKey;
@Entity(tableName = "task_table")
public class Task
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "priority")
    private int priority;

    @ColumnInfo(name = "assignedTo")
    private User assignedTo;
    @ColumnInfo(name = "dueDate")
    private Date dueDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
