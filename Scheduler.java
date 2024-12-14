import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    // Comparator to sort processes by FCAI factor or other criteria
    private PriorityQueue<Process> readyQueue;

    public Scheduler() {
        // Initialize PriorityQueue with a comparator for the desired factor
        readyQueue = new PriorityQueue<>(Comparator.comparingDouble(
                process -> process.calculateFCAIFactor(/* pass necessary parameters */)
        ));
    }

    public void addProcess(Process process) {
        readyQueue.add(process); // Add process to the priority queue
    }

    public Process getNextProcess(int currentTime) {
        if (readyQueue.isEmpty()) {
            return null;
        }
        return readyQueue.poll(); // Get the process with the highest priority
    }

    public boolean hasPendingProcesses() {
        return !readyQueue.isEmpty();
    }
}