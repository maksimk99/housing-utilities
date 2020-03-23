package com.epam.maksim.katuranau.housingutilities.litener;

import com.epam.maksim.katuranau.housingutilities.model.WaterServiceCost;
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
    public void handleMessage(@Payload WaterServiceCost waterServiceCost) {
        messageService.saveUserCost(waterServiceCost);
    }
}
