package com.epam.maksim.katuranau.housingutilities.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CalculatedCostResponse {
    private String tariff;
    private BigDecimal calculatedPrice;
    private List<LocalDate> listOfAvailableDates;
    private LocalDate currentDate;
}
