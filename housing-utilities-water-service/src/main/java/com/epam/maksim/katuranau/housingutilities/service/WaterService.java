package com.epam.maksim.katuranau.housingutilities.service;

import com.epam.maksim.katuranau.housingutilities.model.CalculatedCostResponse;

import java.time.LocalDate;

public interface WaterService {

    CalculatedCostResponse getUserCostsByUserIdAndMonth(Integer userId, LocalDate dateTime);
}
