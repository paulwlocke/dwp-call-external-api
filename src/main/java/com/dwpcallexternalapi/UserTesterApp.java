package com.dwpcallexternalapi;

import com.dwpcallexternalapi.model.Coordinates;
import com.dwpcallexternalapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTesterApp {

    private static final Logger logger = LoggerFactory.getLogger(UserTesterApp.class);

    public static void main( String[] args ) throws Exception {
        logger.info("Starting");
        ObjectMapper mapper = new ObjectMapper();
        Coordinates c = new Coordinates();
        c.setLatitude(55.15);
        c.setLongitude(2.13);
        User user =new User();
        {
            user.setId(Long.valueOf(1));
            user.setFirstName("Paul");
            user.setLastName("Locke");
            user.setEmail("paul.locke3@virginmedia.com");
            user.setCity("Edinburgh");
            user.setCoordinates(c);
        }
        String userAsJson = mapper.writeValueAsString(user);
        logger.info("User: " + userAsJson);
        User fromJson = mapper.readValue(userAsJson, User.class);
        logger.info(fromJson.toString());
        logger.info("Finishing");
    }

}
