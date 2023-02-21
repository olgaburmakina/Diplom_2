package org.example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserClient extends org.example.Client {
    public static final String ROOT = "/api/auth/";

    @Step("Create user")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(ROOT + "register")
                .then().log().all();
    }

    @Step("Login user")
    public ValidatableResponse loginUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(ROOT + "login")
                .then().log().all();
    }

    @Step("Change data user")
    public ValidatableResponse changeDataUser(User user, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(ROOT + "user")
                .then().log().all();
    }

    @Step("Delete user")
    public ValidatableResponse deleteUser(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete(ROOT + "user")
                .then().log().all();
    }
}
