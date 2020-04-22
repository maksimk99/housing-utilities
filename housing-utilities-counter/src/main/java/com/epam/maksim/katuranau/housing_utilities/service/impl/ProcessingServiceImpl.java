package com.epam.maksim.katuranau.housing_utilities.service.impl;

import com.epam.maksim.katuranau.housing_utilities.dao.UserDao;
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

    private UserDao userDao;
    private WaterService waterService;
    private ElectricityService electricityService;
    private WaterDisposalService waterDisposalService;
    private ElevatorMaintenanceService elevatorMaintenanceService;
    private MaintenanceAndOverhaulService maintenanceAndOverhaulService;

    @Autowired
    public ProcessingServiceImpl(final WaterDisposalService waterDisposalService, final UserDao userDao,
                                 final ElectricityService electricityService, final WaterService waterService,
                                 final ElevatorMaintenanceService elevatorMaintenanceService,
                                 final MaintenanceAndOverhaulService maintenanceAndOverhaulService) {
        this.userDao = userDao;
        this.waterService = waterService;
        this.maintenanceAndOverhaulService = maintenanceAndOverhaulService;
        this.electricityService = electricityService;
        this.waterDisposalService = waterDisposalService;
        this.elevatorMaintenanceService = elevatorMaintenanceService;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void processEveryDay() {
        List<Integer> userIds = userDao.getUsersIdList();
        electricityService.process(userIds);
        waterService.process(userIds);
        waterDisposalService.process(userIds);
    }

    @Scheduled(cron = "0 0 12 1 * ?")
    public void processEveryMonth() {
        List<Integer> userIds = userDao.getUsersIdList();
        elevatorMaintenanceService.process(userIds);
        maintenanceAndOverhaulService.process(userIds);
    }

    public void populateData() {
        List<Integer> userIds = userDao.getUsersIdList();
        electricityService.populateData(userIds);
        waterService.populateData(userIds);
        waterDisposalService.populateData(userIds);
        elevatorMaintenanceService.populateData(userIds);
        maintenanceAndOverhaulService.populateData(userIds);
    }
}
