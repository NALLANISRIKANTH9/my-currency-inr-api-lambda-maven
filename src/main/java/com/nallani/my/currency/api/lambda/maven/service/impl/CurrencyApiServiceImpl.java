package com.nallani.my.currency.api.lambda.maven.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nallani.my.currency.api.lambda.maven.service.CurrencyApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class CurrencyApiServiceImpl implements CurrencyApiService {
    private static Logger LOG = LoggerFactory.getLogger(CurrencyApiServiceImpl.class);
    @Autowired
    private Environment env;

    @Override
    public String getInr() throws Exception {
        ResponseEntity<String> response= null;
        JsonNode rootNode = null;
        String status = "inr";

        RestTemplate r = new RestTemplate();
        response = r.getForEntity("http://api.currencylayer.com/live?access_key=480f4b22850aaa3e955e5d30d8a06c14&currencies=INR", String.class);
        String result =  response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        rootNode = objectMapper.readTree(result);
        status = rootNode.findPath("USDINR").asText();
        getSNS(status);
        return status;
    }

    @Override
    public String getAll() throws Exception {

        RestTemplate response = new RestTemplate();
        ResponseEntity<String> responseEntity = response.getForEntity("http://api.currencylayer.com/live?access_key=480f4b22850aaa3e955e5d30d8a06c14", String.class);
        return responseEntity.getBody();
    }

    public AmazonSNS getSNS(String msg) throws IOException {
        LOG.info("Message: " + msg);
        AmazonSNSClient sns = new AmazonSNSClient();
        final PublishRequest publishRequest = new PublishRequest("arn:aws:sns:us-east-1:140458552010:lambda-trigger", msg);
        final PublishResult publishResponse = sns.publish(publishRequest);
        LOG.info("MessageId: " + publishResponse.getMessageId());
        return sns;
    }
}
