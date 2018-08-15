public class Entry {
    public String status, stationID;
    public double upChance;
    public int bufferLevel, bufferCapacity;
    public double cycleTime, meanRepairTime;
    public int timeStep;

    public Entry(String stationID, String status, double upChance, int bufferLevel, int bufferCapacity, double cycleTime, double meanRepairTime, int timeStep) {
        this.status = status;
        this.stationID = stationID;
        this.upChance = upChance;
        this.bufferLevel = bufferLevel;
        this.bufferCapacity = bufferCapacity;
        this.cycleTime = cycleTime;
        this.meanRepairTime = meanRepairTime;
        this.timeStep = timeStep;
    }

    public Entry cloneEntryPure() {
        return new Entry(this.stationID, this.status, this.upChance, this.bufferLevel, this.bufferCapacity, this.cycleTime, this.meanRepairTime, this.timeStep);
    }

    public Entry cloneEntryForced() {
        return new Entry(this.stationID, "FORCED", this.upChance, this.bufferLevel, this.bufferCapacity, this.cycleTime, this.meanRepairTime, this.timeStep);
    }

    public void replaceEntry(Entry entry) {
        this.status = entry.status;
        this.bufferLevel = entry.bufferLevel;
        this.timeStep = entry.timeStep;
    }
}
