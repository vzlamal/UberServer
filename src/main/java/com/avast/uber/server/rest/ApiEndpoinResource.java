package com.avast.uber.server.rest;

import com.avast.uber.server.UberServerApplication;
import com.avast.uber.server.domain.Vip;
import com.avast.uber.server.rest.utils.UnixTime;
import com.avast.uber.server.service.VipCoordsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;


import java.util.Map;


@RestController
@RequestMapping(UberServerApplication.CONTEXT_PATH)
public class ApiEndpoinResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiEndpoinResource.class);
    @Autowired
    VipCoordsService vipCoordsService;

    @GetMapping(value = "/now", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getTime() {
        return Map.of("now", UnixTime.getTime());
    }

    @GetMapping(value = "/VIP/{time}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCoordsFromTime(@PathVariable("time") int time) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Making request for time: " + time);
        }
        try {
            Vip vip = vipCoordsService.getVipCoords(time);
            if (vip == null) {
                LOGGER.error("Could not reconstruct VIP object from api response.");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            ObjectMapper mapper = new ObjectMapper();

            ObjectNode response = mapper.createObjectNode();
            response.put("source", "vip-db");
            ObjectNode coords = mapper.createObjectNode();
            coords.put("lat", vip.getLatitude());
            coords.put("long", vip.getLongitude());
            response.set("coords", coords);


            return new ResponseEntity<>(mapper.writeValueAsString(response), HttpStatus.OK);

        } catch (ResourceAccessException e) {
            LOGGER.error("Response took to long. Failing fast!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (HttpClientErrorException e) {
            LOGGER.error("Http client error.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (HttpServerErrorException e) {
            LOGGER.error("Http server error.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOGGER.error("Something went wrong: ");
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
