package com.dwpcallexternalapi.service;

import com.dwpcallexternalapi.model.Coordinates;
import com.dwpcallexternalapi.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class RestClientServiceIntegrationTest {

    @TestConfiguration
    static class MyConfiguration {
        @Bean
        public RestClientService restClientService() {
            return new RestClientService();
        }

    }

    @Autowired
    private RestClientService restClientService;

    @MockBean
    private RestTemplate restTemplate;

    /**
     * This test verifies that, when the getLondonUsers method of the service class is invoked,
     * the external api is invoked twice.
     * The data returned from the mocked restTemplate is stub data.
     * @throws Exception
     */
    @Test
    public void testCallsToExternalApi() throws Exception {
        assertNotNull(restClientService.getRestTemplate());
        User user1 = new User();
        {
            user1.setId(1L);
            user1.setFirstName("Paul");
            user1.setLastName("Locke");
            user1.setCity("Bern");
            Coordinates user1Coords = new Coordinates();
            user1Coords.setLatitude(44.3);
            user1Coords.setLongitude(2.4);
            user1.setCoordinates(user1Coords);
        }
        User user2 = new User();
        {
            user2.setId(2L);
            user2.setFirstName("Fred");
            user2.setLastName("Bloggs");
            user2.setCity("Edinburgh");
            Coordinates user2Coords = new Coordinates();
            user2Coords.setLatitude(44.3);
            user2Coords.setLongitude(2.4);
            user2.setCoordinates(user2Coords);
        }
        User[] userArray = new User[] {user1, user2};
        ResponseEntity<User[]> response = new ResponseEntity(userArray, HttpStatus.OK );
        when(restTemplate.getForEntity(anyString(), eq(User[].class))).thenReturn(response);
        List<User> londonUsers = restClientService.getLondonUsers();
        Mockito.verify(restTemplate, Mockito.times(2)).getForEntity(anyString(), eq(User[].class));
        assertEquals(2, londonUsers.size());
    }

}
