package com.epam.maksim.katuranau.housing_utilities.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "housing-utilities-auth-server")
public interface AuthServerClient {

    @RequestMapping(method= RequestMethod.GET, value="/user/id/list")
    List<Integer> getUsersIdList();
}
