package com.epam.maksim.katuranau.housing_utilities.controller;

import com.epam.maksim.katuranau.housing_utilities.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private ProcessingService processingService;

    @Autowired
    public UserController(final ProcessingService processingService) {
        this.processingService = processingService;
    }

    @GetMapping("/populateData")
    public void send() {
        processingService.populateData();
    }
}
