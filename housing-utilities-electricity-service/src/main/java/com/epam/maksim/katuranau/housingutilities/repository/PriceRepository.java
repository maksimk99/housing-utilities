package com.epam.maksim.katuranau.housingutilities.repository;

import com.epam.maksim.katuranau.housingutilities.model.PricePerUnit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Repository
@Transactional
public interface PriceRepository extends CrudRepository<PricePerUnit, Integer> {

    PricePerUnit findByDateBetween(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
    void deleteByDateBetween(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}
