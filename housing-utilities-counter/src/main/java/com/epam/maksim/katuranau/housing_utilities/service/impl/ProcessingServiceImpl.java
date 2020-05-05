package com.epam.maksim.katuranau.housing_utilities.service.impl;

import com.epam.maksim.katuranau.housing_utilities.client.AuthServerClient;
import com.epam.maksim.katuranau.housing_utilities.service.ElectricityService;
import com.epam.maksim.katuranau.housing_utilities.service.ElevatorMaintenanceService;
import com.epam.maksim.katuranau.housing_utilities.service.MaintenanceAndOverhaulService;
import com.epam.maksim.katuranau.housing_utilities.service.ProcessingService;
import com.epam.maksim.katuranau.housing_utilities.service.WaterDisposalService;
import com.epam.maksim.katuranau.housing_utilities.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessingServiceImpl implements ProcessingService {

    private WaterService waterService;
    private AuthServerClient authServerClient;
    private ElectricityService electricityService;
    private WaterDisposalService waterDisposalService;
    private ElevatorMaintenanceService elevatorMaintenanceService;
    private MaintenanceAndOverhaulService maintenanceAndOverhaulService;

    @Autowired
    public ProcessingServiceImpl(final WaterDisposalService waterDisposalService, final WaterService waterService,
                                 final ElectricityService electricityService, final AuthServerClient authServerClient,
                                 final MaintenanceAndOverhaulService maintenanceAndOverhaulService,
                                 final ElevatorMaintenanceService elevatorMaintenanceService) {
        this.waterService = waterService;
        this.authServerClient = authServerClient;
        this.maintenanceAndOverhaulService = maintenanceAndOverhaulService;
        this.electricityService = electricityService;
        this.waterDisposalService = waterDisposalService;
        this.elevatorMaintenanceService = elevatorMaintenanceService;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void processEveryDay() {
        List<Integer> userIds = authServerClient.getUsersIdList();
        electricityService.process(userIds);
        waterService.process(userIds);
        waterDisposalService.process(userIds);
    }

    @Scheduled(cron = "0 0 12 1 * ?")
    public void processEveryMonth() {
        List<Integer> userIds = authServerClient.getUsersIdList();
        elevatorMaintenanceService.process(userIds);
        maintenanceAndOverhaulService.process(userIds);
    }

    public void populateData() {
        List<Integer> userIds = authServerClient.getUsersIdList();
        electricityService.populateData(userIds);
        waterService.populateData(userIds);
        waterDisposalService.populateData(userIds);
        elevatorMaintenanceService.populateData(userIds);
        maintenanceAndOverhaulService.populateData(userIds);
    }
}
