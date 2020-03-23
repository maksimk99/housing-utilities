package com.epam.maksim.katuranau.housingutilities.repository;

import com.epam.maksim.katuranau.housingutilities.model.ElectricityServiceCost;
import com.epam.maksim.katuranau.housingutilities.model.ElectricityServiceCostId;
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
public interface ElectricityServiceRepository extends CrudRepository<ElectricityServiceCost, ElectricityServiceCostId> {

    void deleteByUserIdAndDateBetween(@Param("userId") Integer userId, @Param("dateFrom") LocalDate dateFrom,
                                      @Param("dateTo") LocalDate dateTo);

    @Query("SELECT MIN(es.date) FROM ElectricityServiceCost es WHERE es.userId = :userId")
    LocalDate getMinMonth(@Param("userId") Integer userId);

    @Query("SELECT MAX(es.date) FROM ElectricityServiceCost es WHERE es.userId = :userId")
    LocalDate getMaxMonth(@Param("userId") Integer userId);

    @Query("SELECT es.amountOfResourcesSpent FROM ElectricityServiceCost es WHERE es.userId = :userId AND es.date >= :dateFrom AND es.date < :dateTo")
    List<BigDecimal> getAmountOfResourcesByMonthAndUserId(@Param("userId") Integer userId,
                                                          @Param("dateFrom") LocalDate dateFrom,
                                                          @Param("dateTo") LocalDate dateTo);
}
