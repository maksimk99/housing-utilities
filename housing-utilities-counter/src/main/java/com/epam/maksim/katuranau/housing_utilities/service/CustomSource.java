package com.epam.maksim.katuranau.housing_utilities.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomSource {
    String ELECTRICITY_SERVICE = "electricity-service";
    String WATER_SERVICE = "water-service";
    String WATER_DISPOSAL = "water-disposal";
    String MAINTENANCE_SERVICE = "maintenance-service";
    String ELEVATOR_MAINTENANCE_SERVICE = "elevator-maintenance";
    String OVERHAUL_SERVICE = "overhaul-service";

    @Output(ELECTRICITY_SERVICE)
    MessageChannel electricityServiceOutput();

    @Output(WATER_SERVICE)
    MessageChannel waterServiceOutput();

    @Output(WATER_DISPOSAL)
    MessageChannel waterDisposalOutput();

    @Output(MAINTENANCE_SERVICE)
    MessageChannel maintenanceServiceOutput();

    @Output(ELEVATOR_MAINTENANCE_SERVICE)
    MessageChannel elevatorMaintenanceOutput();

    @Output(OVERHAUL_SERVICE)
    MessageChannel overhaulServiceOutput();
}
