package com.epam.maksim.katuranau.housingutilities.service.impl;

import com.epam.maksim.katuranau.housingutilities.model.CalculatedCostResponse;
import com.epam.maksim.katuranau.housingutilities.model.PricePerUnit;
import com.epam.maksim.katuranau.housingutilities.repository.ElectricityServiceRepository;
import com.epam.maksim.katuranau.housingutilities.repository.PriceRepository;
import com.epam.maksim.katuranau.housingutilities.service.ElectricityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ElectricityServiceImpl implements ElectricityService {

    @Value("${price.per.kilowatt.hour}")
    private BigDecimal DEFAULT_PRICE_PER_KILOWATT_HOUR;

    private ElectricityServiceRepository electricityServiceRepository;
    private PriceRepository priceRepository;

    @Autowired
    public ElectricityServiceImpl(final ElectricityServiceRepository electricityServiceRepository,
                                  final PriceRepository priceRepository) {
        this.electricityServiceRepository = electricityServiceRepository;
        this.priceRepository = priceRepository;
    }

    /**
     * Get all costs for the selected month and user and calculate them
     *
     * @param userId   user id to get costs
     * @param dateTime the first day of the month for which user wants to get costs
     * @return CalculatedCostResponse object
     */
    @Override
    public CalculatedCostResponse getUserCostsByUserIdAndMonth(final Integer userId, final LocalDate dateTime) {
        LocalDate startOfTheMonth = getStartOfTheMonth(dateTime);
        List<BigDecimal> listCosts = electricityServiceRepository.getAmountOfResourcesByMonthAndUserId(userId,
                startOfTheMonth, getEndOfTheMonth(startOfTheMonth));
        BigDecimal amountOfResourcesSpent = listCosts.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return createElectricityObject(amountOfResourcesSpent, startOfTheMonth, userId);
    }

    /**
     * Get tariff for current date
     * If it is not exist set default value
     *
     * @param amountOfResourcesSpent amount of resources spent
     * @param currentDate            current date
     * @param userId                 user id to create electricity object
     * @return calculated response
     */
    private CalculatedCostResponse createElectricityObject(final BigDecimal amountOfResourcesSpent,
                                                           final LocalDate currentDate, final Integer userId) {
        BigDecimal pricePerKilowatt = getPricePerOneUnitByMonth(currentDate);
        CalculatedCostResponse calculatedCostResponse = new CalculatedCostResponse();
        calculatedCostResponse.setCurrentDate(currentDate);
        calculatedCostResponse.setCalculatedPrice(calculateTotalPrice(amountOfResourcesSpent, currentDate));
        calculatedCostResponse.setTariff("1 кВт•ч - " + (Objects.nonNull(pricePerKilowatt)
                ? pricePerKilowatt : DEFAULT_PRICE_PER_KILOWATT_HOUR) + "p.");
        calculatedCostResponse.setListOfAvailableDates(createListOfAvailableDates(userId));
        return calculatedCostResponse;
    }

    /**
     * Get price per one unit of resources for current month from database
     * If it is not exist set default price for current moth and use default value to calculate cost
     *
     * @param amountOfResourcesSpent amount of resources spent
     * @param date                   current date
     * @return calculated price
     */
    private BigDecimal calculateTotalPrice(final BigDecimal amountOfResourcesSpent, final LocalDate date) {
        BigDecimal pricePerKilowatt = getPricePerOneUnitByMonth(date);
        BigDecimal calculatedPrice = amountOfResourcesSpent.multiply(Objects.nonNull(pricePerKilowatt)
                ? pricePerKilowatt : DEFAULT_PRICE_PER_KILOWATT_HOUR);
        return calculatedPrice.setScale(2, RoundingMode.UP);
    }

    private BigDecimal getPricePerOneUnitByMonth(final LocalDate date) {
        LocalDate startOfTheMonth = getStartOfTheMonth(date);
        PricePerUnit pricePerUnitObject = priceRepository.findByDateBetween(startOfTheMonth,
                getEndOfTheMonth(startOfTheMonth));
        return Objects.nonNull(pricePerUnitObject) ? pricePerUnitObject.getPrice() : null;
    }

    /**
     * Get min and max values of date in database
     * Create a list of the months that are between max and min values
     *
     * @param userId userId
     * @return list of dates
     */
    private List<LocalDate> createListOfAvailableDates(final Integer userId) {
        LocalDate minMonth = getStartOfTheMonth(electricityServiceRepository.getMinMonth(userId));
        LocalDate maxMonth = getStartOfTheMonth(electricityServiceRepository.getMaxMonth(userId));
        List<LocalDate> listOfAvailableDates = new ArrayList<>();
        if (minMonth != null && maxMonth != null) {
            while (!minMonth.isAfter(maxMonth)) {
                listOfAvailableDates.add(minMonth);
                minMonth = minMonth.plusMonths(1);
            }
        }
        return listOfAvailableDates;
    }

    private LocalDate getStartOfTheMonth(final LocalDate localDate) {
        return localDate != null ? LocalDate.of(localDate.getYear(), localDate.getMonth(), 1) : null;
    }

    private LocalDate getEndOfTheMonth(final LocalDate localDate) {
        return localDate != null ? localDate.plusMonths(1).minusDays(1) : null;
    }
}
