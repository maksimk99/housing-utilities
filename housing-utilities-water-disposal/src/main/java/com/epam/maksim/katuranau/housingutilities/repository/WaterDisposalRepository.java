package com.epam.maksim.katuranau.housingutilities.repository;

import com.epam.maksim.katuranau.housingutilities.model.WaterDisposalCost;
import com.epam.maksim.katuranau.housingutilities.model.WaterDisposalCostId;
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
public interface WaterDisposalRepository extends CrudRepository<WaterDisposalCost, WaterDisposalCostId> {

    void deleteByUserIdAndDateBetween(@Param("userId") Integer userId, @Param("dateFrom") LocalDate dateFrom,
                                      @Param("dateTo") LocalDate dateTo);

    @Query("SELECT MIN(wd.date) FROM WaterDisposalCost wd WHERE wd.userId = :userId")
    LocalDate getMinMonth(@Param("userId") Integer userId);

    @Query("SELECT MAX(wd.date) FROM WaterDisposalCost wd WHERE wd.userId = :userId")
    LocalDate getMaxMonth(@Param("userId") Integer userId);

    @Query("SELECT wd.amountOfResourcesSpent FROM WaterDisposalCost wd WHERE wd.userId = :userId AND wd.date >= :dateFrom AND wd.date < :dateTo")
    List<BigDecimal> getAmountOfResourcesByMonthAndUserId(@Param("userId") Integer userId,
                                                          @Param("dateFrom") LocalDate dateFrom,
                                                          @Param("dateTo") LocalDate dateTo);
}
