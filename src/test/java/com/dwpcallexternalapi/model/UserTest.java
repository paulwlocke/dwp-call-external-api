package com.dwpcallexternalapi.model;

import com.dwpcallexternalapi.ReadUsersApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;


public class UserTest {

    @Test
    @DisplayName("Test JSON mapping - null city value")
    public void testDemarshalling1() throws Exception {
        String filePath = "data/user1.json";
        InputStream is = ReadUsersApp.class.getClassLoader().getResourceAsStream(filePath);
        if ( is != null ) {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(is, User.class);
            assertEquals("Mechelle", user.getFirstName());
            assertEquals("113.71.242.187", user.getIpAddress());
            assertNull(user.getCity());
            assertNotNull(user.getCoordinates());
        }
        is.close();
    }

    @Test
    @DisplayName("Test JSON mapping 2 - non-null city")
    public void testDemarshalling2() throws Exception {
        String filePath = "data/user2.json";
        InputStream is = ReadUsersApp.class.getClassLoader().getResourceAsStream(filePath);
        if ( is != null ) {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(is, User.class);
            assertEquals("Mechelle", user.getFirstName());
            assertEquals("113.71.242.187", user.getIpAddress());
            assertNotNull(user.getCity());
            assertEquals( "Bern", user.getCity() );
        }
        is.close();
    }
}
