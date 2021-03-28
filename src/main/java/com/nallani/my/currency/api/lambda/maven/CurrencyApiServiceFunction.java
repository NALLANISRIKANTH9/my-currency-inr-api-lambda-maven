package com.nallani.my.currency.api.lambda.maven;

import com.nallani.my.currency.api.lambda.maven.service.CurrencyApiService;
import com.nallani.my.currency.api.lambda.maven.service.impl.CurrencyApiServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Function;

@Component("CustomerVerificationFunctionHandler")
public class CurrencyApiServiceFunction implements Function<String, String> {

    private static Logger LOG = LoggerFactory.getLogger(CurrencyApiServiceFunction.class);
    @Autowired private CurrencyApiService currencyApiService;

    @Override
    public String apply(String s) {
        try {
            LOG.info("Calling service" + s);
            if(s.equals("get-INR")){
                return currencyApiService.getInr();
            }
            return currencyApiService.getAll();
        } catch (Exception e) {
            e.getStackTrace();
            LOG.info("Exception....." + e);
            return String.valueOf(e);
        }
    }
}