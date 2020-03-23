package com.epam.maksim.katuranau.housingutilities.service;

import com.epam.maksim.katuranau.housingutilities.model.CustomUserDetailsFactory;
import com.epam.maksim.katuranau.housingutilities.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserInfoService userInfoDAO;

    @Autowired
    public UserDetailsServiceImpl(final UserInfoService userInfoDAO) {
        this.userInfoDAO = userInfoDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoDAO.getUserInfoByUserName(userName);
        return CustomUserDetailsFactory.createCustomUserDetails(userInfo);
    }
}
