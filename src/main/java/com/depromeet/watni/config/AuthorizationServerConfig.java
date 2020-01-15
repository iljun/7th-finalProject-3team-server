package com.depromeet.watni.config;

import com.depromeet.watni.domain.member.service.MemberService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@Profile("!local")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final RedisConnectionFactory redisConnectionFactory;
    private final DataSource dataSource;
    private final MemberService memberService;
    public AuthorizationServerConfig(AuthenticationManager authenticationManager,
                                     RedisConnectionFactory redisConnectionFactory,
                                     DataSource dataSource,
                                     MemberService memberService) {
        this.authenticationManager = authenticationManager;
        this.redisConnectionFactory = redisConnectionFactory;
        this.dataSource = dataSource;
        this.memberService = memberService;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(this.dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(new RedisTokenStore(this.redisConnectionFactory))
                .userDetailsService(memberService);
    }

}
