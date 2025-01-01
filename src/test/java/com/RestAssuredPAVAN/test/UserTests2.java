package com.RestAssuredPAVAN.test;

import com.RestAssuredPAVAN.endpoints.UserEndpoints2;
import com.RestAssuredPAVAN.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests2
{
    Faker faker;
    User userPayload;
    public Logger logger;
    @BeforeClass
    public void setup(){
       faker = new Faker();
       userPayload = new User();

       userPayload.setId(faker.idNumber().hashCode());
       userPayload.setUsername(faker.name().username());
       userPayload.setFirstName(faker.name().firstName());
       userPayload.setLastName(faker.name().lastName());
       userPayload.setEmail(faker.internet().safeEmailAddress());
       userPayload.setPassword(faker.internet().password());
       userPayload.setPhone(faker.phoneNumber().cellPhone());

       // logs
        logger = LogManager.getLogger(this.getClass());
    }
    @Test(priority = 1)
    public void testPostUser()
    {
        logger.info("********** Creating User *************");
        Response response = UserEndpoints2.createUser(userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("********** User Created *************");
    }
    @Test(priority = 2)
    public void testGetUserByName()
    {
        logger.info("********** Reading User *************");
        Response response = UserEndpoints2.readUser(this.userPayload.getUsername());
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("********** User is Read *************");
    }
    @Test(priority = 3)
    public void testUpdateUserByName()
    {
        logger.info("********** Updating User *************");
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndpoints2.updateUser(this.userPayload.getUsername(),userPayload);
        response.then().log().body().statusCode(200);   // we can validate here also

        Assert.assertEquals(response.getStatusCode(),200);     // testng validation

        logger.info("********** User is updated *************");

        // checking data after update
        Response responseAfterupdate = UserEndpoints2.readUser(this.userPayload.getUsername());
        Assert.assertEquals(responseAfterupdate.getStatusCode(),200);
    }
    @Test(priority = 4)
    public void testDeleteUserByName()
    {
        logger.info("********** Deleting User *************");
        Response response = UserEndpoints2.deleteUser(this.userPayload.getUsername());
        Assert.assertEquals(response.getStatusCode(),200);

        logger.info("********** User Deleted *************");
    }

}
