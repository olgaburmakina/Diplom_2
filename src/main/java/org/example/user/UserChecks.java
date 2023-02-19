package org.example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserChecks {

    @Step("Successfully response")
    public String successfullyResponse(ValidatableResponse response){
        return response.assertThat()
                .body("success", equalTo(true))
                .statusCode(HTTP_OK)
                .extract()
                .body()
                .path("accessToken");
    }

    @Step("User already exists response")
    public void userAlreadyExists(ValidatableResponse response){
        response.assertThat()
                .body("message", equalTo("User already exists"))
                .statusCode(HTTP_FORBIDDEN);
    }

    @Step("One of the fields is missing response")
    public void oneOfTheFieldsIsMissing(ValidatableResponse response){
        response.assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .statusCode(HTTP_FORBIDDEN);
    }

    @Step("Login failed response")
    public void loginFailed(ValidatableResponse response){
        response.assertThat()
                .body("message", equalTo("email or password are incorrect"))
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Step("Need for authorization response")
    public void needForAuthorization(ValidatableResponse response){
        response.assertThat()
                .body("message", equalTo("You should be authorised"))
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Step("User successfully deleted response")
    public void userDeleted(ValidatableResponse response){
        response.assertThat()
                .body("message", equalTo("User successfully removed"))
                .statusCode(HTTP_ACCEPTED);
    }
}
