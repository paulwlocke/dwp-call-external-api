package com.dwpcallexternalapi.controller;

import com.dwpcallexternalapi.model.User;
import com.dwpcallexternalapi.service.RestClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class MyRestController {

    private static final Logger logger = LoggerFactory.getLogger(MyRestController.class);

    @Autowired
    private RestClientService restClientService;

    @RequestMapping("/london-users")
    public List<User> getLondonUsers() {
        logger.info("Calling getLondonUsers");
        List<User> result = restClientService.getLondonUsers();
        logger.info("Service layer returns " + result.size() + " object(s)");
        return  result;
    }

    public RestClientService getRestClientService() {
        return restClientService;
    }

    public void setRestClientService(RestClientService restClientService) {
        this.restClientService = restClientService;
    }
}
