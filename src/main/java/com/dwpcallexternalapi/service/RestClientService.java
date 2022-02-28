package com.dwpcallexternalapi.service;

import com.dwpcallexternalapi.model.Coordinates;
import com.dwpcallexternalapi.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class acts as a proxy for the call to the external api.
 * A two-phase call to this api is made
 * - first: to extract users by the city property
 * - second: to extract by coordinates
 */
@Service
public class RestClientService {

    private static final Logger logger = LoggerFactory.getLogger( RestClientService.class );

    private static final String EXTERNAL_API_PREFIX = "https://bpdts-test-app.herokuapp.com";

    @Autowired
    private RestTemplate restTemplate;

    public List<User> getLondonUsers() {
        // First we get those users with the city property of London
        List<User> londonUsers = getUsersBasedInLondon(restTemplate);
        logger.info(String.format("Users in London: %d", londonUsers.size()));
        // Secondly, we get all users and then filter by latitude and longitude
        List<User> usersWithin50Miles = getUsersBasedNearLondon(restTemplate, 50D);
        logger.info(String.format("Users within 50 miles of London: %d", usersWithin50Miles.size()));
        // The we merge the lists
        londonUsers.addAll(usersWithin50Miles);
        logger.info("Total number returned: " + londonUsers.size());
        // Now remove duplicates by creating a map (where a duplicate does not override the existing one)
        Map<Long, User> userMap = londonUsers.stream().collect(Collectors.toMap(User::getId, Function.identity(), (e, r) -> e));
        return userMap.values().stream().collect(Collectors.toList());
    }

    /**
     * Retrieves those users who are located near to London using their their city.
     * @param restTemplate the reference to the external API call
     * @return list of User objects
     */
    private List<User> getUsersBasedInLondon(RestTemplate restTemplate) {
        String usersByCityLondonUrl = String.format("%s%s", EXTERNAL_API_PREFIX, "/city/London/users");
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(usersByCityLondonUrl, User[].class);
        User[] userArray = responseEntity.getBody();
        List<User> result = Arrays.stream(userArray).collect(Collectors.toList());
        return result;
    }

    /**
     * Retrieves those users who are located near to London using their coordinates.
     * @param restTemplate the reference to the external API call
     * @param numberOfMilesDistant number of miles
     * @return list of User objects
     */
    private List<User> getUsersBasedNearLondon(RestTemplate restTemplate, Double numberOfMilesDistant) {
        // We need to define our coordinates of London
        Coordinates londonCoordinates = new Coordinates();
        londonCoordinates.setLongitude(Double.valueOf(0.0));
        londonCoordinates.setLatitude(Double.valueOf(51.5));
        String allUsersUrl = String.format("%s%s", EXTERNAL_API_PREFIX, "/users");
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(allUsersUrl, User[].class);
        User[] allUsersArray = responseEntity.getBody();
        List<User> result = Arrays.stream(allUsersArray).filter( u -> (u.getCoordinates().isWithinDistanceOf(londonCoordinates, numberOfMilesDistant)) ).collect(Collectors.toList());
        return result;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
