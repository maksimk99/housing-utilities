package com.epam.maksim.katuranau.housing_utilities;

import com.epam.maksim.katuranau.housing_utilities.service.CustomSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableBinding(CustomSource.class)
public class HousingUtilitiesCounter {
    public static void main(String[] args) {
        SpringApplication.run(HousingUtilitiesCounter.class);
    }
}
