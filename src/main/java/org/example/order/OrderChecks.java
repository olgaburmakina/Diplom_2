package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderChecks {

    @Step("Ingredient must be provided response")
    public void ingredientMustBeProvided(ValidatableResponse response){
        response.assertThat()
                .body("success", equalTo(false))
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Step("Authorized user orders response")
    public void authorizedUserOrders(ValidatableResponse response){
        response.assertThat()
                .body("orders", notNullValue())
                .statusCode(HTTP_OK);
    }

    @Step("Invalid ingredient hash response")
    public void invalidIngredientHash(ValidatableResponse response){
        response.assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }
}
