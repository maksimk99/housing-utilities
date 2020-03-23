package com.epam.maksim.katuranau.housingutilities.custom;

import com.epam.maksim.katuranau.housingutilities.model.CustomUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Collections;

public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        ((DefaultOAuth2AccessToken) accessToken)
                .setAdditionalInformation(Collections.singletonMap("userId", user.getUserId()));
        return accessToken;
    }
}
