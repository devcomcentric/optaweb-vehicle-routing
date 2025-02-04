package org.optaweb.vehiclerouting.plugin.planner.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.*;
import org.optaweb.vehiclerouting.plugin.planner.domain.solver.ArrivalTimeUpdatingVariableListener;
import org.optaweb.vehiclerouting.plugin.planner.weight.DepotAngleVisitDifficultyWeightFactory;

@PlanningEntity(difficultyWeightFactoryClass = DepotAngleVisitDifficultyWeightFactory.class)
public class PlanningVisit implements Standstill {

    @PlanningId
    private long id;
    private PlanningLocation location;
    private int demand;

    // Shadow variable
    private Long arrivalTime;

    // Planning variable: changes during planning, between score calculations.
    @PlanningVariable(valueRangeProviderRefs = { "vehicleRange", "visitRange" },
            graphType = PlanningVariableGraphType.CHAINED)
    private Standstill previousStandstill;

    // Shadow variables
    private PlanningVisit nextVisit;
    @AnchorShadowVariable(sourceVariableName = "previousStandstill")
    private PlanningVehicle vehicle;

    PlanningVisit() {
        // Hide public constructor in favor of the factory.
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public PlanningLocation getLocation() {
        return location;
    }

    public void setLocation(PlanningLocation location) {
        this.location = location;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public Standstill getPreviousStandstill() {
        return previousStandstill;
    }

    public void setPreviousStandstill(Standstill previousStandstill) {
        this.previousStandstill = previousStandstill;
    }

    @Override
    public PlanningVisit getNextVisit() {
        return nextVisit;
    }

    @Override
    public void setNextVisit(PlanningVisit nextVisit) {
        this.nextVisit = nextVisit;
    }

    public PlanningVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(PlanningVehicle vehicle) {
        this.vehicle = vehicle;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    /**
     * Distance from the previous standstill to this visit. This is used to calculate the travel cost of a chain
     * beginning with a vehicle (at a depot) and ending with the {@link #isLast() last} visit.
     * The chain ends with a visit, not a depot so the cost of returning from the last visit back to the depot
     * has to be added in a separate step using {@link #distanceToDepot()}.
     *
     * @return distance from previous standstill to this visit
     */
    public long distanceFromPreviousStandstill() {
        if (previousStandstill == null) {
            throw new IllegalStateException(
                    "This method must not be called when the previousStandstill (null) is not initialized yet.");
        }
        return previousStandstill.getLocation().distanceTo(location);
    }

    /**
     * Distance from this visit back to the depot.
     *
     * @return distance from this visit back its vehicle's depot
     */
    public long distanceToDepot() {
        return location.distanceTo(vehicle.getLocation());
    }

    /**
     * Whether this visit is the last in a chain.
     *
     * @return true, if this visit has no {@link #getNextVisit() next} visit
     */
    public boolean isLast() {
        return nextVisit == null;
    }


    /**
     * @return a positive number, the time multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    @ShadowVariable(variableListenerClass = ArrivalTimeUpdatingVariableListener.class,
            // Arguable, to adhere to API specs (although this works), nextVisit should also be a source,
            // because this shadow must be triggered after nextVisit (but there is no need to be triggered by nextVisit)
            sourceVariableName = "vehicle")
    @ShadowVariable(variableListenerClass = ArrivalTimeUpdatingVariableListener.class, sourceVariableName = "previousCustomer")
    public Long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    /**
     * @return a positive number, the time multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public Long getDepartureTime() {
        if (arrivalTime == null) {
            return null;
        }
        return Math.max(arrivalTime, location.getReadyTime()) + location.getServiceDuration();
    }

    public boolean isArrivalBeforeReadyTime() {
        return arrivalTime != null
                && arrivalTime < location.getReadyTime();
    }

    public boolean isArrivalAfterDueTime() {
        return arrivalTime != null
                && location.getDueTime() < arrivalTime;
    }

    @Override
    public String toString() {
        return "PlanningVisit{" +
                (location == null ? "" : "location=" + location.getId()) +
                ",demand=" + demand +
                (previousStandstill == null ? "" : ",previousStandstill='" + previousStandstill.getLocation().getId()) +
                (nextVisit == null ? "" : ",nextVisit=" + nextVisit.getId()) +
                (vehicle == null ? "" : ",vehicle=" + vehicle.getId()) +
                ",id=" + id +
                '}';
    }
}
