package org.optaweb.vehiclerouting.plugin.planner.domain.solver;

import org.optaplanner.core.api.domain.variable.VariableListener;
import org.optaplanner.core.api.score.director.ScoreDirector;
import org.optaweb.vehiclerouting.plugin.planner.domain.*;

import java.util.Objects;

public class ArrivalTimeUpdatingVariableListener implements VariableListener<VehicleRoutingSolution, PlanningVisit> {

    @Override
    public void beforeEntityAdded(ScoreDirector<VehicleRoutingSolution> scoreDirector, PlanningVisit planningVisit) {

    }

    @Override
    public void afterEntityAdded(ScoreDirector<VehicleRoutingSolution> scoreDirector, PlanningVisit planningVisit) {

    }

    @Override
    public void beforeVariableChanged(ScoreDirector<VehicleRoutingSolution> scoreDirector, PlanningVisit planningVisit) {

    }

    @Override
    public void afterVariableChanged(ScoreDirector<VehicleRoutingSolution> scoreDirector, PlanningVisit planningVisit) {

    }

    @Override
    public void beforeEntityRemoved(ScoreDirector<VehicleRoutingSolution> scoreDirector, PlanningVisit planningVisit) {

    }

    @Override
    public void afterEntityRemoved(ScoreDirector<VehicleRoutingSolution> scoreDirector, PlanningVisit planningVisit) {

    }

    protected void updateArrivalTime(ScoreDirector<VehicleRoutingSolution> scoreDirector, PlanningVisit sourceCustomer) {
        Standstill previousStandstill = sourceCustomer.getPreviousStandstill();
        Long departureTime = previousStandstill == null ? null
                : (previousStandstill instanceof PlanningVisit)
                ? ((PlanningVisit) previousStandstill).getDepartureTime()
                : ((PlanningDepot) ((PlanningVehicle) previousStandstill).getDepot()).getLocation().getReadyTime();
        PlanningVisit shadowCustomer = sourceCustomer;
        Long arrivalTime = calculateArrivalTime(shadowCustomer, departureTime);
        while (shadowCustomer != null && !Objects.equals(shadowCustomer.getArrivalTime(), arrivalTime)) {
            scoreDirector.beforeVariableChanged(shadowCustomer, "arrivalTime");
            shadowCustomer.setArrivalTime(arrivalTime);
            scoreDirector.afterVariableChanged(shadowCustomer, "arrivalTime");
            departureTime = shadowCustomer.getDepartureTime();
            shadowCustomer = shadowCustomer.getNextVisit();
            arrivalTime = calculateArrivalTime(shadowCustomer, departureTime);
        }
    }

    private Long calculateArrivalTime(PlanningVisit customer, Long previousDepartureTime) {
        if (customer == null || customer.getPreviousStandstill() == null) {
            return null;
        }
        if (customer.getPreviousStandstill() instanceof PlanningVehicle) {
            // PreviousStandstill is the Vehicle, so we leave from the Depot at the best suitable time
            return Math.max(customer.getLocation().getReadyTime(),
                    previousDepartureTime + customer.distanceFromPreviousStandstill());
        }
        return previousDepartureTime + customer.distanceFromPreviousStandstill();
    }

}
