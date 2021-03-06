# dwp-call-external-api
The assignment required the creation of an API which under the bonnet calls an external API
and returns a list of User objects. 
The external API was provided, and the data returned by that formed the basis of my data modelling. 

Class User models the user data type. I reworked this to NOT use the Builder design batter with Lombok
as it was causing issues with the JSON deserialisation when working with the JsonUnwrapped annotation. 

Two simple interaction main classes were created to check the serialization and deserialization
of the User type. 

The class that drives the Spring Boot restful service is DwpCallExternalApiApplication.

The rest controller class is called MyRestController and supports one GET request: /london-users

This request results in two calls to the external service:

    1. Get users registered with a city property of London. This calls the
       endpoint /city/London/users  
    2. Get users whose latitude and longitude place them close to London. This calls the
       endpoint /users to get ALL users and them filters them according to their latitude and longitude values. 

The lists returned from these calls are them merged and processed to remove any duplicates.
A "duplicate" here means two User instances with the same value for their id field. 

By calling the spring-boot:run task, the application is launched and is deployed to localhost:8080

Using a browser (or Postman or curl) ou can then try this endpoint: http://localhost:8080/london-users
and see the result.

Test classes have been included to check the demarshalling of the JSON into the User class instances. 
Another test class employing mocking to verify that the service class RestClientService does make the
requisite number of calls to the external api.


