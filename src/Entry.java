public class Entry {
    public String status;
    public double upChance;
    public int bufferLevel, bufferCapacity;
    public double cycleTime, meanRepairTime;
    public int timeStep;

    public Entry(String status, double upChance, int bufferLevel, int bufferCapacity, double cycleTime, double meanRepairTime, int timeStep) {
        this.status = status;
        this.upChance = upChance;
        this.bufferLevel = bufferLevel;
        this.bufferCapacity = bufferCapacity;
        this.cycleTime = cycleTime;
        this.meanRepairTime = meanRepairTime;
        this.timeStep = timeStep;
    }
}
