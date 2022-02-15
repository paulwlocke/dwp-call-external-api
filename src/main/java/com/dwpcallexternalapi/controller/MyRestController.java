package com.dwpcallexternalapi.controller;

import com.dwpcallexternalapi.ReadUsersApp;
import com.dwpcallexternalapi.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class MyRestController {

    private static final Logger logger = LoggerFactory.getLogger(MyRestController.class);

    @RequestMapping("/london-users")
    public List<User> getLondonUsers() {
        List<User> users = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String externalApiPrefix = "https://bpdts-test-app.herokuapp.com";
        // First we get those users with the city property of London
        String usersByCityLondonUrl = String.format("%s%s", externalApiPrefix, "/city/London/users");
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(usersByCityLondonUrl, User[].class);
        User[] userArray = responseEntity.getBody();
        logger.info(String.format("Users in London: %d", userArray.length));
        List<User> londonUsers = Arrays.stream(userArray).collect(Collectors.toList());
        // Secondly, we get all users and then filter by latitude and longitude
        Double londonLatitude = Double.valueOf(51.5);
        Double londonLongitude = Double.valueOf(0.0);
        // One degree of latitude or longitude is approximately 66 miles (assuming a spherical Earth 24000 miles in circumference).
        // So my margin of one degree is actually larger than that requested.
        Double withinFiftyMileMargin = Double.valueOf(1.0);
        String allUsersUrl = String.format("%s%s", externalApiPrefix, "/users");
        responseEntity = restTemplate.getForEntity(allUsersUrl, User[].class);
        User[] allUsersArray = responseEntity.getBody();
        logger.info("All users: %d", allUsersArray.length);
        List<User> within50Miles = Arrays.stream(allUsersArray).filter( u -> (Math.abs(u.getLatitude() - londonLatitude) <= withinFiftyMileMargin) && (Math.abs(u.getLongitude() - londonLongitude) <= withinFiftyMileMargin) ).collect(Collectors.toList());
        logger.info("Number within 50 miles of London based on coordinates: " + within50Miles.size());
        // The we merge the lists
        londonUsers.addAll(within50Miles);
        // Now remove duplicates by creating a map (where a duplicate does not override the existing one)
        Map<Long, User> userMap = londonUsers.stream().collect(Collectors.toMap(User::getId, Function.identity(), (e, r) -> e));
        return userMap.values().stream().collect(Collectors.toList());
    }

}
