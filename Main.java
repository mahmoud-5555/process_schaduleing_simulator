import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

// Step 1: Create a Scheduler instance
    Scheduler scheduler = new Scheduler();

    // Step 2: Manually create processes with unique quantums
    Process process1 = new Process("P1", "R",4, 0, 4,4); // Name, Arrival Time, Burst Time, Quantum
    Process process2 = new Process("P2", "B",2, 3, 5,3);
    Process process3 = new Process("P3", "Y",4, 4, 3,5);
    Process process4 = new Process("P4", "G",4, 29, 3,2);


    int currentTime = 0;
    PriorityQueue<Process> arrivalQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getArrivalTime));
    Processor processor = new Processor(scheduler);
    arrivalQueue.add(process1);
    arrivalQueue.add(process2);
    arrivalQueue.add(process3);
    arrivalQueue.add(process4);

        while (!arrivalQueue.isEmpty() || scheduler.hasPendingProcesses()) {
            // Add new processes to the scheduler if their arrival time has passed
            while (!arrivalQueue.isEmpty() && arrivalQueue.peek().getArrivalTime() <= currentTime) {
                scheduler.addProcess(arrivalQueue.poll());
            }

            // Execute the next process
            if (scheduler.hasPendingProcesses()) {
                currentTime = processor.executeNextProcess(currentTime);
            } else {
                System.out.println("Time " + currentTime + ": Idle");
                currentTime++; // Increment time if no processes are ready
           }
        }

        // Print Final Statistics
        processor.printStatistics();
    }
}