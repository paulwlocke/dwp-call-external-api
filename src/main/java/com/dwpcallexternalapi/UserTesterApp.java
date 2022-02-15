package com.dwpcallexternalapi;

import com.dwpcallexternalapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTesterApp {

    private static final Logger logger = LoggerFactory.getLogger(UserTesterApp.class);

    public static void main( String[] args ) throws Exception {
        logger.info("Starting");
        ObjectMapper mapper = new ObjectMapper();
        User user = User.builder()
                .id(Long.valueOf(1))
                .firstName("Paul")
                .lastName("Locke")
                .email("paul.locke3@virginmedia.com")
                .city("Bern")
                .build();
        logger.info("User: " + mapper.writeValueAsString(user));
        logger.info("Finishing");
    }

}
