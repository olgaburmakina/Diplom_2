package order;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.OrderChecks;
import org.example.order.OrderClient;
import org.example.user.User;
import org.example.user.UserChecks;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.Test;

public class ReceivingUserOrdersTest {

    private final OrderClient orderClient = new OrderClient();
    private final OrderChecks orderChecks = new OrderChecks();
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserChecks checks = new UserChecks();
    private final Faker faker = new Faker();

    @Test
    @DisplayName("Receiving orders from an authorized user")
    @Description("Получение заказов конкретного пользователя: авторизованный пользователь")

    public void receivingAuthorizedUserOrdersTest(){
        User user = generator.userLoginData();
        ValidatableResponse userCreateResponse = client.loginUser(user);
        String accessToken = checks.successfullyResponse(userCreateResponse);
        ValidatableResponse response = orderClient.receivingUserOrders(accessToken);
        orderChecks.authorizedUserOrders(response);
    }

    @Test
    @DisplayName("Receiving orders from an unauthorized user")
    @Description("Получение заказов конкретного пользователя: неавторизованный пользователь")

    public void receivingOrdersFromAnUnauthorizedUserTest(){
        String wrongAccessToken = (faker.random().hex(20));
        ValidatableResponse response = orderClient.receivingUserOrders(wrongAccessToken);
        checks.needForAuthorization(response);
    }
}
