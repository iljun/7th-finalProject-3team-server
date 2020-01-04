package com.depromeet.watni.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Profile("local")
@Configuration
public class EmbeddedRedisConfig {

    private RedisProperties redisProperties;
    private RedisServer redisServer;
    public EmbeddedRedisConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @PostConstruct
    public void initRedisServer() throws IOException {
        this.redisServer = new RedisServer(redisProperties.getPort());
        this.redisServer.start();
    }

    @PreDestroy
    public void destroyRedisServer() {
        if (this.redisServer != null) {
            this.redisServer.stop();
        }
    }
}
