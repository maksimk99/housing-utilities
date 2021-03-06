package com.epam.maksim.katuranau.housingutilities.controller;

import com.epam.maksim.katuranau.housingutilities.model.CalculatedCostResponse;
import com.epam.maksim.katuranau.housingutilities.model.CustomPrincipal;
import com.epam.maksim.katuranau.housingutilities.service.WaterDisposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/water/disposal")
public class WaterDisposalController {

    private WaterDisposalService waterDisposalService;

    @Autowired
    public WaterDisposalController(final WaterDisposalService maintenanceService) {
        this.waterDisposalService = maintenanceService;
    }

    @GetMapping("/user/calculate")
    public CalculatedCostResponse calculate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                            final Principal principal) {
        Authentication authentication = ((OAuth2Authentication) principal).getUserAuthentication();
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return waterDisposalService.getUserCostsByUserIdAndMonth(customPrincipal.getUserId(), date);
    }
}
