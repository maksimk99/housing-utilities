package com.epam.maksim.katuranau.housingutilities.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WaterDisposalCostId implements Serializable {

    private Integer userId;
    private LocalDate date;
}
