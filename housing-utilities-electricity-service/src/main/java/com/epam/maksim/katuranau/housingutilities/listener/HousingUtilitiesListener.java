package com.epam.maksim.katuranau.housingutilities.listener;

import com.epam.maksim.katuranau.housingutilities.model.ElectricityServiceCost;
import com.epam.maksim.katuranau.housingutilities.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
public class HousingUtilitiesListener {

    private MessageService messageService;

    @Autowired
    public HousingUtilitiesListener(final MessageService messageService) {
        this.messageService = messageService;
    }

    @StreamListener(target = Sink.INPUT)
    public void handleMessage(@Payload ElectricityServiceCost electricityServiceCost) {
        messageService.saveUserCost(electricityServiceCost);
    }
}
