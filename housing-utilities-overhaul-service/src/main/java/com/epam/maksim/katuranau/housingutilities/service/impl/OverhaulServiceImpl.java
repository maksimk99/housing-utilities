package com.epam.maksim.katuranau.housingutilities.service.impl;

import com.epam.maksim.katuranau.housingutilities.model.CalculatedCostResponse;
import com.epam.maksim.katuranau.housingutilities.model.PricePerUnit;
import com.epam.maksim.katuranau.housingutilities.repository.OverhaulServiceRepository;
import com.epam.maksim.katuranau.housingutilities.repository.PriceRepository;
import com.epam.maksim.katuranau.housingutilities.service.OverhaulService;
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
public class OverhaulServiceImpl implements OverhaulService {

    @Value("${price.per.square.meter}")
    private BigDecimal DEFAULT_PRICE_PER_SQUARE_METER;

    private OverhaulServiceRepository overhaulServiceRepository;
    private PriceRepository priceRepository;

    @Autowired
    public OverhaulServiceImpl(final OverhaulServiceRepository overhaulServiceRepository,
                               final PriceRepository priceRepository) {
        this.overhaulServiceRepository = overhaulServiceRepository;
        this.priceRepository = priceRepository;
    }

    /**
     * Get cost for the selected month and user
     *
     * @param userId   user id to get costs
     * @param dateTime the first day of the month for which user wants to get cost
     * @return CalculatedCostResponse object
     */
    @Override
    public CalculatedCostResponse getUserCostsByUserIdAndMonth(final Integer userId, final LocalDate dateTime) {
        LocalDate startOfTheMonth = getStartOfTheMonth(dateTime);
        BigDecimal totalSpace = overhaulServiceRepository.getTotalSpaceByMonthAndUserId(userId,
                startOfTheMonth, getEndOfTheMonth(startOfTheMonth));
        return createElectricityObject(Objects.nonNull(totalSpace) ? totalSpace : BigDecimal.ZERO,
                startOfTheMonth, userId);
    }

    /**
     * Get tariff for current date
     * If it is not exist set default value
     *
     * @param totalSpace  total space of the apartment
     * @param currentDate current date
     * @param userId      user id to create electricity object
     * @return calculated response
     */
    private CalculatedCostResponse createElectricityObject(final BigDecimal totalSpace,
                                                           final LocalDate currentDate, final Integer userId) {
        BigDecimal pricePerKilowatt = getPricePerOneUnitByMonth(currentDate);
        CalculatedCostResponse calculatedCostResponse = new CalculatedCostResponse();
        calculatedCostResponse.setCurrentDate(currentDate);
        calculatedCostResponse.setCalculatedPrice(calculateTotalPrice(totalSpace, currentDate));
        calculatedCostResponse.setTariff("1 m2 - " + (Objects.nonNull(pricePerKilowatt)
                ? pricePerKilowatt : DEFAULT_PRICE_PER_SQUARE_METER) + "p.");
        calculatedCostResponse.setListOfAvailableDates(createListOfAvailableDates(userId));
        return calculatedCostResponse;
    }

    /**
     * Get price per one unit of resources for current month from database
     * If it is not exist set default price for current moth and use default value to calculate cost
     *
     * @param totalSpace total space of the apartment
     * @param date       current date
     * @return calculated price
     */
    private BigDecimal calculateTotalPrice(final BigDecimal totalSpace, final LocalDate date) {
        BigDecimal pricePerKilowatt = getPricePerOneUnitByMonth(date);
        BigDecimal calculatedPrice = totalSpace.multiply(Objects.nonNull(pricePerKilowatt)
                ? pricePerKilowatt : DEFAULT_PRICE_PER_SQUARE_METER);
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
        LocalDate minMonth = getStartOfTheMonth(overhaulServiceRepository.getMinMonth(userId));
        LocalDate maxMonth = getStartOfTheMonth(overhaulServiceRepository.getMaxMonth(userId));
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
