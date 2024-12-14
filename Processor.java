import java.util.ArrayList;
import java.util.List;

public class Processor {
    private Scheduler scheduler;
    private int quantum;
    private int contextSwitching;
    private List<String> executionOrder = new ArrayList<>();
    private List<Process> allProcesses = new ArrayList<>(); // Keep track of all processes

    public Processor(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.contextSwitching = 0;
    }

    public int executeNextProcess(int currentTime) throws InterruptedException {
        Process process = scheduler.getNextProcess(currentTime);
        if (process == null) {
            return currentTime;
        }

        if (!allProcesses.contains(process)) {
            allProcesses.add(process); // Add process to the list if not already there
        }

        executionOrder.add(process.getName());
        System.out.println("Time " + currentTime + ": Executing " + process.getName());

        // Execute for 40% of the quantum or until remaining burst time is depleted
        int quantum40 = (int) Math.ceil(0.4 * process.getQuantum());
        int executionTime = Math.min(quantum40, process.getRemainingBurstTime());

        // Simulate execution
        if (executionTime > contextSwitching) {
            Thread.sleep(contextSwitching * 100); // Simulate context switch
        } else {
            Thread.sleep(executionTime * 100); // Simulate actual execution
        }

        process.setRemainingBurstTime(process.getRemainingBurstTime() - executionTime);
        currentTime += executionTime;

        // Context switching
        if (process.getRemainingBurstTime() > 0) {
            System.out.println("Time " + currentTime + ": Preempting " + process.getName());
            process.updateQuantum(true, 0); // Update quantum
            scheduler.addProcess(process); // Re-add to the queue
        } else {
            System.out.println("Time " + currentTime + ": Completed " + process.getName());
            process.setCompletionTime(currentTime);
        }

        currentTime += contextSwitching;
        return currentTime;
    }

    public void printStatistics() {
        // Print execution order
        System.out.println("\nExecution Order: " + String.join(" -> ", executionOrder));

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        System.out.println("\nProcess Details:");
        for (Process process : allProcesses) {
            int turnaroundTime = process.getCompletionTime() - process.getArrivalTime();
            int waitingTime = turnaroundTime - process.getBurstTime();

            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;

            System.out.println(process.getName() + " - Waiting Time: " + waitingTime
                    + ", Turnaround Time: " + turnaroundTime);
            System.out.println("Quantum Updates for " + process.getName() + ": " + process.getQuantumHistory());
        }

        // Print average waiting and turnaround times
        System.out.println("\nAverage Waiting Time: " + (totalWaitingTime / allProcesses.size()));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / allProcesses.size()));
    }
}
