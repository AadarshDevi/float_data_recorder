package com.miramar_water_jets;

public class DataPoint {
    private int id;
    private double time;
    private double depth;
    private String packetId;

    public DataPoint(int id, String packetId, double time, double depth) {
        this.packetId = packetId;
        this.id = id;
        this.time = time;
        this.depth = depth;
    }

    public double getDepth() {
        return depth;
    }

    public double getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public String getPacketId() {
        return packetId;
    }

    @Override
    public String toString() {
        return String.format("%fs, %fm", time, depth);
    }
}
