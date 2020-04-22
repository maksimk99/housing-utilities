package com.epam.maksim.katuranau.housing_utilities.service.impl;

import com.epam.maksim.katuranau.housing_utilities.model.Message;
import com.epam.maksim.katuranau.housing_utilities.service.CustomSource;
import com.epam.maksim.katuranau.housing_utilities.service.ElectricityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ElectricityServiceImpl implements ElectricityService {

    private static final Double LEFT_ELECTRICITY_SERVICE_RESOURCES_AMOUNT_LIMIT = 4.0;
    private static final Double RIGHT_ELECTRICITY_SERVICE_RESOURCES_AMOUNT_LIMIT = 5.0;

    private CustomSource source;

    @Autowired
    public ElectricityServiceImpl(final CustomSource source) {
        this.source = source;
    }

    @Async
    public void process(final List<Integer> userIds) {
        userIds.forEach(userId -> {
            Message message = new Message()
                    .setDate(LocalDate.now())
                    .setUserId(userId)
                    .setAmountOfResourcesSpent(generateAmountOfResourcesSpent());
            sendMessage(message);
        });
    }

    @Async
    public void populateData(final List<Integer> userIds) {
        LocalDate startDate = LocalDate.of(2019, 11, 1);
        LocalDate endDate = LocalDate.now();
        while (!startDate.isAfter(endDate)) {
            final LocalDate currentDate = startDate;
            userIds.forEach(userId -> {
                Message message = new Message()
                        .setDate(currentDate)
                        .setUserId(userId)
                        .setAmountOfResourcesSpent(generateAmountOfResourcesSpent());
                sendMessage(message);
            });
            startDate = startDate.plusDays(1);
        }
    }

    private BigDecimal generateAmountOfResourcesSpent() {
        return BigDecimal.valueOf(LEFT_ELECTRICITY_SERVICE_RESOURCES_AMOUNT_LIMIT + Math.random()
                * (RIGHT_ELECTRICITY_SERVICE_RESOURCES_AMOUNT_LIMIT - LEFT_ELECTRICITY_SERVICE_RESOURCES_AMOUNT_LIMIT));
    }

    private void sendMessage(final Message message) {
        source.electricityServiceOutput().send(MessageBuilder
                .withPayload(message)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }
}
