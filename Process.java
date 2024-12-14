import java.util.ArrayList;
import java.util.List;


public class Process {
    private String name;
    private String color; // Graphical representation
    private int priority;
    private int arrivalTime;
    private int burstTime;
    private int remainingBurstTime;
    private int quantum;
    private int completionTime;

    // Other fields remain unchanged
    private int waitingTime;
    private int turnaroundTime;
    private List<Integer> quantumHistory; // To store quantum updates

    // Static fields for FCAI factor calculation
    private static int lastArrivalTime;
    private static int maxBurstTime;

    public Process(String name, String color, int priority, int arrivalTime, int burstTime, int quantum) {
        this.name = name;
        this.color = color;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
        this.quantumHistory = new ArrayList<>();
        this.quantum = quantum;
    }

    // Static setters for lastArrivalTime and maxBurstTime
    public static void setLastArrivalTime(int lastArrivalTime) {
        Process.lastArrivalTime = lastArrivalTime;
    }

    public static void setMaxBurstTime(int maxBurstTime) {
        Process.maxBurstTime = maxBurstTime;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getPriority() {
        return priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    // Updated calculateFCAIFactor method
    public double calculateFCAIFactor() {
        double V1 = lastArrivalTime / 10.0;
        double V2 = maxBurstTime / 10.0;
        return (10 - priority) + (arrivalTime / V1) + (remainingBurstTime / V2);
    }

    public void updateQuantum(boolean increment, int unusedQuantum) {
        if (increment) {
            quantum += 2;
        } else if (unusedQuantum > 0) {
            quantum += unusedQuantum;
        }
    }

    @Override
    public String toString() {
        return "Process {" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", priority=" + priority +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", remainingBurstTime=" + remainingBurstTime +
                ", quantum=" + quantum +
                '}';
    }

    // Track each quantum update
    public void addQuantumHistory(int quantum) {
        quantumHistory.add(quantum);
    }

    public List<Integer> getQuantumHistory() {
        return quantumHistory;
    }

    public void calculateTimes() {
        turnaroundTime = completionTime - arrivalTime;
        waitingTime = turnaroundTime - burstTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }
}