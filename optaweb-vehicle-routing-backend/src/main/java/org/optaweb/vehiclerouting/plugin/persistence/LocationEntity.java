package org.optaweb.vehiclerouting.plugin.persistence;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.*;

/**
 * Persistable location.
 */
@Entity
@Table(name = "locationentity")
class LocationEntity {

    @Id
    private long id;

    // https://wiki.openstreetmap.org/wiki/Node#Structure
    @Column(precision = 9, scale = 7)
    private BigDecimal latitude;
    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    private String description;
    private long readyTime;
    private long dueTime;
    private long serviceDuration;
    private int demand;
    private long transitTime;
    private long breakStartTime;
    private long breakEndTime;
    private String jobGroup;
    private Integer forcedSequenceNo;

    protected LocationEntity() {
        // for JPA
    }

    public LocationEntity(long id, BigDecimal latitude, BigDecimal longitude, String description, long readyTime, long dueTime, long serviceDuration, int demand, long transitTime, long breakStartTime, long breakEndTime, String jobGroup, Integer forcedSequenceNo) {
        this.id = id;
        this.latitude = Objects.requireNonNull(latitude);
        this.longitude = Objects.requireNonNull(longitude);
        this.description = Objects.requireNonNull(description);
        this.readyTime = readyTime;
        this.dueTime = dueTime;
        this.serviceDuration = serviceDuration;
        this.demand = demand;
        this.transitTime = transitTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.jobGroup = jobGroup;
        this.forcedSequenceNo = forcedSequenceNo;
    }

    LocationEntity(long id, BigDecimal latitude, BigDecimal longitude, String description) {
        this.id = id;
        this.latitude = Objects.requireNonNull(latitude);
        this.longitude = Objects.requireNonNull(longitude);
        this.description = Objects.requireNonNull(description);
    }

    long getId() {
        return id;
    }

    BigDecimal getLatitude() {
        return latitude;
    }

    BigDecimal getLongitude() {
        return longitude;
    }

    String getDescription() {
        return description;
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

    public int getDemand() {
        return demand;
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

    @Override
    public String toString() {
        return "LocationEntity{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description + '\'' +
                '}';
    }
}
