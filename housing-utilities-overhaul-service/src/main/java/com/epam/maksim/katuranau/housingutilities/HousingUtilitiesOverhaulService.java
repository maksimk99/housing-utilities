package com.epam.maksim.katuranau.housingutilities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableOAuth2Sso
@EnableFeignClients
@EnableEurekaClient
public class HousingUtilitiesOverhaulService {
    public static void main(String[] args) {
        SpringApplication.run(HousingUtilitiesOverhaulService.class, args);
    }
}
