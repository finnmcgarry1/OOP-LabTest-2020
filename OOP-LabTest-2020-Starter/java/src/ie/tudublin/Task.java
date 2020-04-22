package ie.tudublin;


import processing.data.TableRow;

public class Task
{
    private String task;
    private int start;
    private int end;

    public Task(String task, int start, int end) {
        this.task = task;
        this.start = start;
        this.end = end;
    }

    public Task(TableRow tableRow) {
        this(tableRow.getString("task"), tableRow.getInt("start"), tableRow.getInt("end"));
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Tasks [tasks=" + task + ", start=" + start + ", end=" + end +"]";
    }
 }