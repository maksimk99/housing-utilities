package com.epam.maksim.katuranau.housingutilities.service;

import com.epam.maksim.katuranau.housingutilities.model.UserInfo;
import com.epam.maksim.katuranau.housingutilities.model.UserRegistrationDto;
import com.epam.maksim.katuranau.housingutilities.model.UserRole;
import com.epam.maksim.katuranau.housingutilities.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserInfoService {

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserInfoService(final UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public UserInfo getUserInfoByUserName(String userName) {
        return userDetailsRepository.findByUserName(userName);
    }

    public List<Integer> getUsersIdList() {
        return userDetailsRepository.getUsersIdList();
    }

    public List<UserInfo> getAllActiveUserInfo() {
        return userDetailsRepository.findAll();
    }

    public UserInfo getUserInfoById(Integer id) {
        return userDetailsRepository.findByUserId(id);
    }

    public UserInfo addUser(UserRegistrationDto userRegistrationDto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userRegistrationDto.getLogin());
        userInfo.setPassword(new BCryptPasswordEncoder().encode(userRegistrationDto.getPassword()));
        userInfo.setUserRoles(getUserRole());
        return userDetailsRepository.save(userInfo);
    }

    private Set<UserRole> getUserRole() {
        HashSet<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setRoleId(1);
        userRole.setRole("USER");
        userRoles.add(userRole);
        return userRoles;
    }
}
