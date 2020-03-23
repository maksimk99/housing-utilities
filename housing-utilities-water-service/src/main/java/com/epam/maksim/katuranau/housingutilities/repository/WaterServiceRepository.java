package com.epam.maksim.katuranau.housingutilities.repository;

import com.epam.maksim.katuranau.housingutilities.model.WaterServiceCost;
import com.epam.maksim.katuranau.housingutilities.model.WaterServiceCostId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface WaterServiceRepository extends CrudRepository<WaterServiceCost, WaterServiceCostId> {

    void deleteByUserIdAndDateBetween(@Param("userId") Integer userId, @Param("dateFrom") LocalDate dateFrom,
                                      @Param("dateTo") LocalDate dateTo);

    @Query("SELECT MIN(ws.date) FROM WaterServiceCost ws WHERE ws.userId = :userId")
    LocalDate getMinMonth(@Param("userId") Integer userId);

    @Query("SELECT MAX(ws.date) FROM WaterServiceCost ws WHERE ws.userId = :userId")
    LocalDate getMaxMonth(@Param("userId") Integer userId);

    @Query("SELECT ws.amountOfResourcesSpent FROM WaterServiceCost ws WHERE ws.userId = :userId AND ws.date >= :dateFrom AND ws.date < :dateTo")
    List<BigDecimal> getAmountOfResourcesByMonthAndUserId(@Param("userId") Integer userId,
                                                  @Param("dateFrom") LocalDate dateFrom,
                                                  @Param("dateTo") LocalDate dateTo);
}
