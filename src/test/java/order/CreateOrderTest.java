package order;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.Order;
import org.example.order.OrderChecks;
import org.example.order.OrderClient;
import org.example.user.User;
import org.example.user.UserChecks;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateOrderTest {

    private final OrderClient orderClient = new OrderClient();
    private final OrderChecks orderChecks = new OrderChecks();
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserChecks checks = new UserChecks();
    private final Order order = new Order(new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa76", "61c0c5a71d1f82001bdaaa6e")));
    private String accessToken;

    private final Faker faker = new Faker();

    @Test
    @DisplayName("Create an order with authorization with ingredients")
    @Description("Создание заказа с авторизацией с ингредиентами")

    public void createOrderWithAuthorizationWithIngredients() {
        User user = generator.userLoginData();
        ValidatableResponse response = client.loginUser(user);
        accessToken = checks.successfullyResponse(response);
        ValidatableResponse createResponse = orderClient.createOrder(accessToken, order);
        checks.successfullyResponse(createResponse);
    }

    @Test
    @DisplayName("Create an order with authorization without ingredients")
    @Description("Создание заказа с авторизацией без ингредиентов")

    public void createOrderWithAuthorizationWithoutIngredients() {
        User user = generator.userLoginData();
        ValidatableResponse response = client.loginUser(user);
        accessToken = checks.successfullyResponse(response);
        checks.successfullyResponse(response);
        order.setIngredients(null);
        ValidatableResponse createResponse = orderClient.createOrder(accessToken, order);
        orderChecks.ingredientMustBeProvided(createResponse);
    }

    @Test
    @DisplayName("Creating an order without authorization with ingredients")
    @Description("Создание заказа без авторизации c ингредиентами")

    public void createOrderWithoutAuthorizationWithIngredients() {
        String wrongAccessToken = (faker.random().hex(20));
        ValidatableResponse createResponse = orderClient.createOrder(wrongAccessToken, order);
        checks.successfullyResponse(createResponse);//почему-то заказ проходит
    }

    @Test
    @DisplayName("Creating an order without authorization without ingredients")
    @Description("Создание заказа без авторизации без ингредиентов")

    public void createOrderWithoutAuthorizationWithoutIngredients() {
        String wrongAccessToken = (faker.random().hex(20));
        order.setIngredients(null);
        ValidatableResponse createResponse = orderClient.createOrder(wrongAccessToken, order);
        orderChecks.ingredientMustBeProvided(createResponse);
    }

    @Test
    @DisplayName("Creating an order with an invalid ingredient hash")
    @Description("Создание заказа с неверным хешем ингредиентов.")

    public void createOrderWithInvalidHash() {
        User user = generator.userLoginData();
        ValidatableResponse response = client.loginUser(user);
        accessToken = checks.successfullyResponse(response);
        order.addIngredient(faker.random().hex(20));
        ValidatableResponse createResponse = orderClient.createOrder(accessToken, order);
        orderChecks.invalidIngredientHash(createResponse);
    }
}
