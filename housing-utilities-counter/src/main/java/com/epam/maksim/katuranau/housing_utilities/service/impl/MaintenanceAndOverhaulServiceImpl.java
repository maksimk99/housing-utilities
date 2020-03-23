package com.epam.maksim.katuranau.housing_utilities.service.impl;

import com.epam.maksim.katuranau.housing_utilities.model.Message;
import com.epam.maksim.katuranau.housing_utilities.service.CustomSource;
import com.epam.maksim.katuranau.housing_utilities.service.MaintenanceAndOverhaulService;
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
public class MaintenanceAndOverhaulServiceImpl implements MaintenanceAndOverhaulService {

    private static final Double LEFT_LIMIT_OF_TOTAL_LIVING_SPACE = 30.0;
    private static final Double RIGHT_LIMIT_OF_TOTAL_LIVING_SPACE = 50.0;

    private CustomSource source;

    @Autowired
    public MaintenanceAndOverhaulServiceImpl(final CustomSource source) {
        this.source = source;
    }

    @Async
    public void process(final List<Integer> userIds) {
//        LocalDate startDate = LocalDate.of(2019, 9, 1);
//        LocalDate endDate = LocalDate.now();
//        while (!startDate.isAfter(endDate)) {
//            final LocalDate currentDate = startDate;
//            userIds.forEach(userId -> {
//                Message message = new Message()
//                        .setDate(currentDate)
//                        .setUserId(userId)
//                        .setAmountOfResourcesSpent(generateAmountOfResourcesSpent());
//                sendMessage(message);
//            });
//            startDate = startDate.plusMonths(1);
//        }
        userIds.forEach(userId -> {
            Message message = new Message()
                    .setDate(LocalDate.now())
                    .setUserId(userId)
                    .setAmountOfResourcesSpent(generateAmountOfResourcesSpent());
            sendMessage(message);
        });
    }

    private BigDecimal generateAmountOfResourcesSpent() {
        return BigDecimal.valueOf(LEFT_LIMIT_OF_TOTAL_LIVING_SPACE + Math.random()
                * (RIGHT_LIMIT_OF_TOTAL_LIVING_SPACE - LEFT_LIMIT_OF_TOTAL_LIVING_SPACE));
    }

    private void sendMessage(final Message message) {
        source.maintenanceServiceOutput().send(MessageBuilder
                .withPayload(message)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
        source.overhaulServiceOutput().send(MessageBuilder
                .withPayload(message)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }
}
