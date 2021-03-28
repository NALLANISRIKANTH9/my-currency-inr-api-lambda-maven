package com.nallani.my.currency.api.lambda.maven.handler.aws;

import com.nallani.my.currency.api.lambda.maven.service.CurrencyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class RestTest {
    @Autowired
    private CurrencyApiService currecyClass;

    @RequestMapping(
            value = "/get-inr",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String getINR() throws Exception {
        return currecyClass.getInr();
    }

    @RequestMapping(
            value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String getAll() throws Exception {
        return currecyClass.getAll();
    }

    @GetMapping("/health")
    public String healthcheck() {
        return "OK";
    }
}
