package com.epam.maksim.katuranau.housingutilities.repository;

import com.epam.maksim.katuranau.housingutilities.model.MaintenanceServiceCost;
import com.epam.maksim.katuranau.housingutilities.model.OverhaulServiceCostId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
@Transactional
public interface MaintenanceServiceRepository extends CrudRepository<MaintenanceServiceCost, OverhaulServiceCostId> {

    void deleteByUserIdAndDateBetween(@Param("userId") Integer userId, @Param("dateFrom") LocalDate dateFrom,
                                      @Param("dateTo") LocalDate dateTo);

    @Query("SELECT MIN(es.date) FROM MaintenanceServiceCost es WHERE es.userId = :userId")
    LocalDate getMinMonth(@Param("userId") Integer userId);

    @Query("SELECT MAX(es.date) FROM MaintenanceServiceCost es WHERE es.userId = :userId")
    LocalDate getMaxMonth(@Param("userId") Integer userId);

    @Query("SELECT es.amountOfResourcesSpent FROM MaintenanceServiceCost es WHERE es.userId = :userId AND es.date >= :dateFrom AND es.date < :dateTo")
    BigDecimal getTotalSpaceByMonthAndUserId(@Param("userId") Integer userId,
                                             @Param("dateFrom") LocalDate dateFrom,
                                             @Param("dateTo") LocalDate dateTo);
}
