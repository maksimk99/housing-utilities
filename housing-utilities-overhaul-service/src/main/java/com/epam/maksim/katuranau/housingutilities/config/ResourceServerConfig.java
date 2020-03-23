package com.epam.maksim.katuranau.housingutilities.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private CorsFilter corsFilter;

    @Autowired
    public ResourceServerConfig(final CorsFilter corsFilter) {
        this.corsFilter = corsFilter;
    }

    @Override
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter, SessionManagementFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "OPTIONS", "POST", "PUT", "DELETE");
            }
        };
    }

    @Bean
    @Primary
    public RemoteTokenServices tokenService(final RemoteTokenServices remoteTokenServices,
                                            final AccessTokenConverter accessTokenConverter) {
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
        return remoteTokenServices;
    }

    @Bean
    public AccessTokenConverter accessTokenConverter(final UserAuthenticationConverter userAuthenticationConverter) {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        return accessTokenConverter;
    }
}
