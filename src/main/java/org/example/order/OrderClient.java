package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends org.example.Client{
    public static final String ROOT = "/api/orders";

    @Step("Create order")
    public ValidatableResponse createOrder(String accessToken, Order order){
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Receiving user orders")
    public ValidatableResponse receivingUserOrders(String accessToken){
        return spec()
                .header("Authorization", accessToken)
                .when()
                .get(ROOT)
                .then().log().all();
    }
}
