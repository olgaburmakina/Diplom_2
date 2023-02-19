package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.User;
import org.example.user.UserChecks;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.Test;

public class LoginUserTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserChecks checks = new UserChecks();

    @Test
    @DisplayName("Login User")
    @Description("Логин под существующим пользователем")

    public void loginUser(){
        User user = generator.userLoginData();
        ValidatableResponse response = client.loginUser(user);
        checks.successfullyResponse(response);
    }

    @Test
    @DisplayName("Wrong email when logging in")
    @Description("Логин с неверным email")

    public void wrongEmailLoginUser(){
        User user = generator.userLoginData();
        user.setEmail("ol.guseva@yandex.ru");
        ValidatableResponse response = client.loginUser(user);
        checks.loginFailed(response);
    }

    @Test
    @DisplayName("Wrong password when logging in")
    @Description("Логин с неверным паролем")

    public void wrongPasswordLoginUser(){
        User user = generator.userLoginData();
        user.setPassword("efirav6530");
        ValidatableResponse response = client.loginUser(user);
        checks.loginFailed(response);
    }
}
