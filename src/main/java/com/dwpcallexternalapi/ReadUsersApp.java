package com.dwpcallexternalapi;

import com.dwpcallexternalapi.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class ReadUsersApp {

    private static final Logger logger = LoggerFactory.getLogger(ReadUsersApp.class);

    private static final String USER_LIST = "data/users.json";

    public static void main( String[] args ) throws Exception {
        logger.info("Starting");
        InputStream is = ReadUsersApp.class.getClassLoader().getResourceAsStream(USER_LIST);
        if ( is != null ) {
            logger.info("Successfully read file");
            ObjectMapper mapper = new ObjectMapper();
            List<User> users = mapper.readValue(is, new TypeReference<List<User>>(){});
            logger.info(String.format("Loaded %d user%s", users.size(), (users.size() == 1 ? "" : "s")));
            logger.info("How many with a city specified: " + users.stream().filter(user -> user.getCity() != null).collect(Collectors.toList()).size());
        }
        logger.info("Finishing");
    }

}
