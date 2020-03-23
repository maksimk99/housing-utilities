package com.epam.maksim.katuranau.housingutilities.service;

import com.epam.maksim.katuranau.housingutilities.model.CalculatedCostResponse;

import java.time.LocalDate;

public interface MaintenanceService {

    CalculatedCostResponse getUserCostsByUserIdAndMonth(Integer userId, LocalDate dateTime);
}
