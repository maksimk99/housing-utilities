package com.epam.maksim.katuranau.housingutilities.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "maintenance_service_costs")
@IdClass(OverhaulServiceCostId.class)
public class MaintenanceServiceCost {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "total_space")
    private BigDecimal amountOfResourcesSpent;
}
