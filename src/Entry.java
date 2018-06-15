public class Entry {
    public String status;
    public double upChance;
    public double bufferLevel;
    public double cycleTime;

    public Entry(String status, double upChance, double bufferLevel, double cycleTime) {
        this.status = status;
        this.upChance = upChance;
        this.bufferLevel = bufferLevel;
        this.cycleTime = cycleTime;
    }

}
