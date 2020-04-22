package ie.tudublin;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.data.Table;

public class Gantt extends PApplet {
    private int squareSize = 35;
    private int selectRight = -1;
    private int selectLeft = -1;
    private float leftGap = 80f;
    private float dragSpace = 20f;
    private float distance = width / 4f;

	private ArrayList<Task> tasks;

    public void settings() {
        size(800, 600);
    }

    public void loadTasks() {
		Table t = loadTable("tasks.csv", "header");

		t.rows().forEach(tableRow -> tasks.add(new Task(tableRow)));
    }

    public void printTasks() {
		tasks.forEach(System.out::println);

        System.out.println();
    }

    public void displayTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
             float yStart = map(i, 0, tasks.size(), distance * 3,height - distance);

            fill(255);
            text(task.getTask(), distance, yStart);

            noStroke();
            fill(map(i, 0, tasks.size(), 0, 255), 255, 255);

            float start = map(task.getStart(), 1, 30, leftGap, width - distance);
            float end = map(task.getEnd(), 1, 30, leftGap, width - distance);
            rect(start, yStart - (squareSize / 2), end - start, squareSize, 5, 5, 5, 5);
        }
    }

    private void drawGrid() {
        stroke(255);

        for (int i = 1; i <= 10; i++) {
            float pos = map(i, 1, 10, leftGap, width - distance);

            line(pos, distance, pos, height - distance );
            fill(255);
            text(i, pos - 2, distance / 2);
        }
    }

    public void mousePressed() {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            float frontStart = map(task.getStart(), 1, 30, leftGap, width - distance);
            float frontEnd = map(task.getEnd(), 1, 30, leftGap, width - distance);

            float bottomStart = map(i, 0, tasks.size(), distance * 3,height - distance) - (squareSize / 2);
            float bottomEnd = bottomStart + frontEnd;

            if (mouseY >= bottomStart && mouseY <= bottomEnd) {
                if (Hovering(frontStart)) {
                    selectLeft = i;
                    selectRight = -1;
                } else if (Hovering(frontEnd)) {
                    selectRight = i;
                    selectLeft = -1;
                } else {
                    selectRight = -1;
                    selectLeft = -1;
                }
            }
        }
        }
    
    private boolean Hovering(float dragPosition) {
         return mouseX > dragPosition - dragSpace && mouseX < dragPosition + dragSpace;
    }   


    public void mouseDragged() {
        int position = (int) (1 + 30 * ((mouseX - (leftGap - dragSpace)) / (width - (leftGap - dragSpace))));

        if (selectLeft >= 0) {
            Task task = tasks.get(selectLeft);

            if (position > 0 && position < task.getEnd()) {
                task.setStart(position);
            }
        }else if (selectRight >= 0) {
            Task task = tasks.get(selectRight);

            if (position <= 30 && position > task.getStart()) {
                task.setEnd(position);
            }
        }
    }

    public void setup() {
    	tasks = new ArrayList<>();

    	loadTasks();
    	printTasks();
    }

    public void draw() {
        colorMode(HSB);
        background(0);

        drawGrid();
        displayTasks();
    }
}