package org.optaweb.vehiclerouting.plugin.persistence;

import javax.persistence.*;

/**
 * Persistable vehicle.
 */
@Entity
@Table(name = "vehicleentity")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int capacity;

    @Column(name = "start_time")
    private long startTime;

    @Column(name = "end_time")
    private long endTime;

    @Column(name = "break_start")
    private long breakStartTime;

    @Column(name = "break_end")
    private long breakEndTime;
    protected VehicleEntity() {
        // for JPA
    }

    public VehicleEntity(long id, String name, int capacity, long startTime, long endTime, long breakStartTime, long breakEndTime) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }

    public VehicleEntity(long id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getBreakStartTime() {
        return breakStartTime;
    }

    public void setBreakStartTime(long breakStartTime) {
        this.breakStartTime = breakStartTime;
    }

    public long getBreakEndTime() {
        return breakEndTime;
    }

    public void setBreakEndTime(long breakEndTime) {
        this.breakEndTime = breakEndTime;
    }

    @Override
    public String toString() {
        return "VehicleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
