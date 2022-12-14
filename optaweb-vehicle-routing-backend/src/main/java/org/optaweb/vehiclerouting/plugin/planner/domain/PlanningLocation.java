package org.optaweb.vehiclerouting.plugin.planner.domain;

import java.util.Objects;

public class PlanningLocation {

    private final long id;
    // Only used to calculate angle.
    private final double latitude;
    private final double longitude;
    private final DistanceMap travelDistanceMap;
    private long readyTime;
    private long dueTime;
    private long serviceDuration;
    private long transitTime;
    private long breakStartTime;
    private long breakEndTime;
    private String jobGroup;
    private Integer forcedSequenceNo;

    public PlanningLocation(long id, double latitude, double longitude, DistanceMap travelDistanceMap, long readyTime, long dueTime, long serviceDuration, long transitTime, long breakStartTime, long breakEndTime, String jobGroup, Integer forcedSequenceNo) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.travelDistanceMap = Objects.requireNonNull(travelDistanceMap);
        this.readyTime = readyTime;
        this.dueTime = dueTime;
        this.serviceDuration = serviceDuration;
        this.transitTime = transitTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.jobGroup = jobGroup;
        this.forcedSequenceNo = forcedSequenceNo;
    }

    PlanningLocation(long id, double latitude, double longitude, DistanceMap travelDistanceMap) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.travelDistanceMap = Objects.requireNonNull(travelDistanceMap);
    }

    /**
     * ID of the corresponding domain location.
     *
     * @return domain location ID
     */
    public long getId() {
        return id;
    }

    /**
     * Distance to the given location.
     *
     * @param location other location
     * @return distance to the other location
     */
    public long distanceTo(PlanningLocation location) {
        if (this == location) {
            return 0L;
        }
        return travelDistanceMap.distanceTo(location);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public DistanceMap getTravelDistanceMap() {
        return travelDistanceMap;
    }

    public long getReadyTime() {
        return readyTime;
    }

    public long getDueTime() {
        return dueTime;
    }

    public long getServiceDuration() {
        return serviceDuration;
    }

    public long getTransitTime() {
        return transitTime;
    }

    public long getBreakStartTime() {
        return breakStartTime;
    }

    public long getBreakEndTime() {
        return breakEndTime;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public Integer getForcedSequenceNo() {
        return forcedSequenceNo;
    }

    /**
     * Angle between the given location and the direction EAST with {@code this} location being the vertex.
     *
     * @param location location that forms one side of the angle (not {@code null})
     * @return angle in radians in the range of -π to π
     */
    public double angleTo(PlanningLocation location) {
        // Euclidean distance (Pythagorean theorem) - not correct when the surface is a sphere
        double latitudeDifference = location.latitude - latitude;
        double longitudeDifference = location.longitude - longitude;
        return Math.atan2(latitudeDifference, longitudeDifference);
    }

    @Override
    public String toString() {
        return "PlanningLocation{" +
                "latitude=" + latitude +
                ",longitude=" + longitude +
                ",travelDistanceMap=" + travelDistanceMap +
                ",id=" + id +
                '}';
    }
}
