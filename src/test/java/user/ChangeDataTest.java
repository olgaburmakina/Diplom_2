package user;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.User;
import org.example.user.UserChecks;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Test;

public class ChangeDataTest {
    private final UserClient client = new UserClient();
    private final UserChecks checks = new UserChecks();
    private final UserGenerator generator = new UserGenerator();
    private final Faker faker = new Faker();
    private String accessToken = "zeroToken";

    @After
    public void deleteUser() {
        if (!accessToken.equals("zeroToken")) {
            ValidatableResponse deleteResponse = client.deleteUser(accessToken);
            checks.userDeleted(deleteResponse);
        }
    }

    @Test
    @DisplayName("Change email")
    @Description("Изменение данных пользователя с авторизацией")

    public void changeEmail() {
        User user = generator.random();
        ValidatableResponse response = client.createUser(user);
        accessToken = checks.successfullyResponse(response);
        user.setEmail(faker.internet().emailAddress());
        ValidatableResponse changeResponse = client.changeDataUser(user, accessToken);
        checks.successfullyResponse(changeResponse);
    }

    @Test
    @DisplayName("Change name")
    @Description("Изменение данных пользователя с авторизацией")

    public void changeName() {
        User user = generator.random();
        ValidatableResponse response = client.createUser(user);
        accessToken = checks.successfullyResponse(response);
        user.setName(faker.name().firstName());
        ValidatableResponse changeResponse = client.changeDataUser(user, accessToken);
        checks.successfullyResponse(changeResponse);
    }

    @Test
    @DisplayName("Change password")
    @Description("Изменение данных пользователя с авторизацией")

    public void changePassword() {
        User user = generator.random();
        ValidatableResponse response = client.createUser(user);
        accessToken = checks.successfullyResponse(response);
        user.setPassword(faker.internet().password(6, 10));
        ValidatableResponse changeResponse = client.changeDataUser(user, accessToken);
        checks.successfullyResponse(changeResponse);
    }

    @Test
    @DisplayName("Change email without authorization")
    @Description("Изменение данных пользователя без авторизации")

    public void changeEmailWithoutAuthorization() {
        User user = generator.random();
        user.setEmail(faker.internet().emailAddress());
        String wrongAccessToken = (faker.random().hex(20));
        ValidatableResponse changeResponse = client.changeDataUser(user, wrongAccessToken);
        checks.needForAuthorization(changeResponse);
    }


    @Test
    @DisplayName("Change name without authorization")
    @Description("Изменение данных пользователя без авторизации")

    public void changeNameWithoutAuthorization() {
        User user = generator.random();
        user.setName(faker.name().firstName());
        String wrongAccessToken = (faker.random().hex(20));
        ValidatableResponse changeResponse = client.changeDataUser(user, wrongAccessToken);
        checks.needForAuthorization(changeResponse);
    }

    @Test
    @DisplayName("Change password without authorization")
    @Description("Изменение данных пользователя без авторизации")

    public void changePasswordWithoutAuthorization() {
        User user = generator.random();
        user.setPassword(faker.internet().password(6, 10));
        String wrongAccessToken = (faker.random().hex(20));
        ValidatableResponse changeResponse = client.changeDataUser(user, wrongAccessToken);
        checks.needForAuthorization(changeResponse);
    }
}
