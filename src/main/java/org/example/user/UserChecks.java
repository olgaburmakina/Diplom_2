package org.example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserChecks {

    @Step("Successfully response")
    public String successfullyResponse(ValidatableResponse response) {
        return response.assertThat()
                .body("success", equalTo(true))
                .statusCode(HTTP_OK)
                .extract()
                .body()
                .path("accessToken");
    }

    @Step("User already exists response")
    public void userAlreadyExists(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", equalTo("User already exists"));
    }

    @Step("One of the fields is missing response")
    public void oneOfTheFieldsIsMissing(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Login failed response")
    public void loginFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Need for authorization response")
    public void needForAuthorization(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
    }

    @Step("User successfully deleted response")
    public void userDeleted(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_ACCEPTED)
                .body("message", equalTo("User successfully removed"));
    }
}
