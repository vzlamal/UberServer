package com.avast.uber.server.service;

import com.avast.uber.server.UberServerApplication;
import com.avast.uber.server.domain.Vip;
import org.springframework.web.client.RestTemplate;



public class VipCoordsService {
    private final RestTemplate restTemplate;

    public VipCoordsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Vip getVipCoords(int time) {
        return restTemplate.getForObject(UberServerApplication.VIP_API_BASE_URI + time, Vip.class);
    }

}
