package com.avast.uber.server.configuration;

import com.avast.uber.server.service.VipCoordsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestServiceConfiguration {

    @Bean
    public VipCoordsService vipCoordsService() {
        return new VipCoordsService(restTemplate());
    }

    @Bean
    public RestTemplate restTemplate() {

        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(4900);
        factory.setReadTimeout(4900);
        return new RestTemplate(factory);
    }
}
