package com.example.newsapi.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.time.Duration;

@Configuration
@EnableConfigurationProperties(WorldNewsProperties.class)
public class WebClientConfig {

    @Bean
    public WebClient worldNewsWebClient(WorldNewsProperties properties) throws SSLException {
        int timeoutMs = properties.getTimeoutMs();
        
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeoutMs)
                .responseTimeout(Duration.ofMillis(timeoutMs));
        
        // Configure SSL based on properties
        if (properties.isSkipSslValidation()) {
            // Create SSL context that trusts all certificates (for development/testing)
            SslContext sslContext = SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
            httpClient = httpClient.secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
        }
        
        httpClient = httpClient.doOnConnected(conn -> conn
                .addHandlerLast(new ReadTimeoutHandler(timeoutMs / 1000))
                .addHandlerLast(new WriteTimeoutHandler(timeoutMs / 1000))
        );

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(properties.getBaseUrl())
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(4 * 1024 * 1024))
                        .build())
                .build();
    }
}

