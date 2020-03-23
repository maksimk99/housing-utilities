package com.epam.maksim.katuranau.housingutilities.service.impl;

import com.epam.maksim.katuranau.housingutilities.model.MaintenanceServiceCost;
import com.epam.maksim.katuranau.housingutilities.model.PricePerUnit;
import com.epam.maksim.katuranau.housingutilities.repository.MaintenanceServiceRepository;
import com.epam.maksim.katuranau.housingutilities.repository.PriceRepository;
import com.epam.maksim.katuranau.housingutilities.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

@Service
public class MessageServiceImpl implements MessageService {

    @Value("${count.of.months}")
    private Integer COUNT_OF_MONTHS;
    @Value("${price.per.square.meter}")
    private BigDecimal DEFAULT_PRICE_PER_SQUARE_METER;

    private MaintenanceServiceRepository maintenanceServiceRepository;
    private PriceRepository priceRepository;

    public MessageServiceImpl(final MaintenanceServiceRepository maintenanceServiceRepository,
                              final PriceRepository priceRepository) {
        this.maintenanceServiceRepository = maintenanceServiceRepository;
        this.priceRepository = priceRepository;
    }

    /**
     * Check price per one unit for this month
     *
     * @param maintenanceServiceCost message received from counter service
     */
    @Override
    public void saveUserCost(final MaintenanceServiceCost maintenanceServiceCost) {
        maintenanceServiceCost.setAmountOfResourcesSpent(
                maintenanceServiceCost.getAmountOfResourcesSpent().setScale(0, RoundingMode.UP));
        maintenanceServiceCost.setDate(getStartOfTheMonth(maintenanceServiceCost.getDate()));
        maintenanceServiceRepository.save(maintenanceServiceCost);
        checkCountOfMonths(maintenanceServiceCost);
        checkPrice(maintenanceServiceCost.getDate());
    }

    /**
     * Get min and current values of date
     * If min value bigger than current (min value in previous year) than add 12 month to the current value
     * Calculate difference between min and current value
     * If difference bigger than 4 then remove user costs and price per one unit for min date
     *
     * @param maintenanceServiceCost received message
     */
    private void checkCountOfMonths(final MaintenanceServiceCost maintenanceServiceCost) {
        LocalDate minMonth = getStartOfTheMonth(maintenanceServiceRepository
                .getMinMonth(maintenanceServiceCost.getUserId()));
        int currentMonthValue = maintenanceServiceCost.getDate().getMonthValue();
        if (minMonth.getMonthValue() > currentMonthValue) {
            currentMonthValue += COUNT_OF_MONTHS;
        }
        int monthDifference = currentMonthValue - minMonth.getMonthValue();
        if (monthDifference > 4) {
            maintenanceServiceRepository.deleteByUserIdAndDateBetween(maintenanceServiceCost.getUserId(), minMonth,
                    minMonth.plusMonths(1).minusDays(1));
            priceRepository.deleteByDateBetween(minMonth, minMonth.plusMonths(1).minusDays(1));
        }
    }

    /**
     * Get price for the current month from database
     * If there is not price for current month than get price for the previous month and set this price to the current
     * If previous month don't have price than set default price for the current month
     *
     * @param date date to check price
     */
    private void checkPrice(final LocalDate date) {
        BigDecimal price = getPricePerOneUnitByMonth(date);
        if (Objects.isNull(price)) {
            BigDecimal lastPrice = getPricePerOneUnitByMonth(date.minusMonths(1));
            priceRepository.save(createPriceObject(date, Objects.nonNull(lastPrice)
                    ? lastPrice : DEFAULT_PRICE_PER_SQUARE_METER));
        }
    }

    private BigDecimal getPricePerOneUnitByMonth(final LocalDate date) {
        LocalDate startOfTheMonth = getStartOfTheMonth(date);
        PricePerUnit pricePerUnitObject = priceRepository.findByDateBetween(startOfTheMonth,
                getEndOfTheMonth(startOfTheMonth));
        return Objects.nonNull(pricePerUnitObject) ? pricePerUnitObject.getPrice() : null;
    }

    private PricePerUnit createPriceObject(final LocalDate date, final BigDecimal price) {
        PricePerUnit pricePerUnit = new PricePerUnit();
        pricePerUnit.setDate(date);
        pricePerUnit.setPrice(price);
        return pricePerUnit;
    }

    private LocalDate getStartOfTheMonth(final LocalDate localDate) {
        return localDate != null ? LocalDate.of(localDate.getYear(), localDate.getMonth(), 1) : null;
    }

    private LocalDate getEndOfTheMonth(final LocalDate localDate) {
        return localDate != null ? localDate.plusMonths(1).minusDays(1) : null;
    }
}
