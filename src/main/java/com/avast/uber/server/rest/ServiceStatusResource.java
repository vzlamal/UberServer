package com.avast.uber.server.rest;

import com.avast.uber.server.UberServerApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class ServiceStatusResource {
    private static final Map<String, String> OK_RESULT = Collections.singletonMap("status", "OK");

    @GetMapping(value = UberServerApplication.CONTEXT_PATH + "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getStatus() {
        return OK_RESULT;
    }

}
