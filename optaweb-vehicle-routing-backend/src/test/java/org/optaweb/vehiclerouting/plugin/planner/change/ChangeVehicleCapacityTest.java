/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaweb.vehiclerouting.plugin.planner.change;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.optaweb.vehiclerouting.plugin.planner.MockSolver;
import org.optaweb.vehiclerouting.plugin.planner.domain.PlanningVehicle;
import org.optaweb.vehiclerouting.plugin.planner.domain.PlanningVehicleFactory;
import org.optaweb.vehiclerouting.plugin.planner.domain.SolutionFactory;
import org.optaweb.vehiclerouting.plugin.planner.domain.VehicleRoutingSolution;

class ChangeVehicleCapacityTest {

    @Test
    void change_vehicle_capacity() {
        int oldCapacity = 100;
        int newCapacity = 50;

        MockSolver<VehicleRoutingSolution> mockSolver = MockSolver.build(SolutionFactory.emptySolution());

        PlanningVehicle workingVehicle = PlanningVehicleFactory.testVehicle(1, oldCapacity);
        PlanningVehicle changeVehicle = PlanningVehicleFactory.testVehicle(2, newCapacity);

        mockSolver.whenLookingUp(changeVehicle).thenReturn(workingVehicle);

        // do change
        mockSolver.addProblemChange(new ChangeVehicleCapacity(changeVehicle));

        assertThat(workingVehicle.getCapacity()).isEqualTo(newCapacity);
        mockSolver.verifyProblemPropertyChanged(changeVehicle);
    }
}
