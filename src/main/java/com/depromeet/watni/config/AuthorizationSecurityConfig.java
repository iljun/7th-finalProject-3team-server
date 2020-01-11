package com.depromeet.watni.config;

import com.depromeet.watni.filter.JsonToUrlEncodedAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@Order(Integer.MIN_VALUE)
public class AuthorizationSecurityConfig
        extends AuthorizationServerSecurityConfiguration {

    @Autowired
    private JsonToUrlEncodedAuthenticationFilter jsonFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterBefore(jsonFilter, ChannelProcessingFilter.class);
        super.configure(httpSecurity);
    }
}
