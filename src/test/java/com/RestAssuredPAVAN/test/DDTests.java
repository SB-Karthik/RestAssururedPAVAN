package com.RestAssuredPAVAN.test;

import com.RestAssuredPAVAN.endpoints.UserEndpoints;
import com.RestAssuredPAVAN.payload.User;
import com.RestAssuredPAVAN.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DDTests
{
    @Test(priority = 1, dataProvider = "Data",dataProviderClass = DataProviders.class)
    public void testPostUser(String id, String userName, String firstName, String lastname, String email, String password, String phone, String userStatus)
    {
        User userPayload = new User();

        userPayload.setId((int) Double.parseDouble(id));
        userPayload.setUsername(userName);
        userPayload.setFirstName(firstName);
        userPayload.setLastName(lastname);
        userPayload.setEmail(email);
        userPayload.setPassword(password);
        userPayload.setPhone(phone);
        userPayload.setUserStatus((int) Double.parseDouble(userStatus));

        Response response = UserEndpoints.createUser(userPayload);
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testGetUserByName(String userName){
        Response response = UserEndpoints.readUser(userName);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);

    }
    @Test(priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteUserByName(String userName) {

        Response response = UserEndpoints.deleteUser(userName);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
