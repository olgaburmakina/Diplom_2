package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.User;
import org.example.user.UserChecks;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Test;

public class CreateUserTest {
    private final UserClient client = new UserClient();
    private final UserChecks checks = new UserChecks();
    private final UserGenerator generator = new UserGenerator();
    private String accessToken = "zeroToken";

    @After
    public void deleteUser() {
        if (!accessToken.equals("zeroToken")) {
            ValidatableResponse deleteResponse = client.deleteUser(accessToken);
            checks.userDeleted(deleteResponse);
        }
    }

    @Test
    @DisplayName("Verification of user creation")
    @Description("Проверим, что можно создать уникального пользователя")

    public void createUser() {
        User user = generator.random();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = checks.successfullyResponse(createResponse);

    }

    @Test
    @DisplayName("Create user twice")
    @Description("Проверим, что нельзя создать пользователя, который уже зарегистрирован")

    public void createUserTwice() {
        User user = generator.random();
        client.createUser(user);
        ValidatableResponse loginResponse = client.createUser(user);
        checks.userAlreadyExists(loginResponse);
    }

    @Test
    @DisplayName("Create user without name")
    @Description("Проверим, что нельзя создать пользователя и не заполнить одно из обязательных полей")

    public void createUserWithoutName() {
        User user = generator.random();
        user.setName(null);
        ValidatableResponse createResponse = client.createUser(user);
        checks.oneOfTheFieldsIsMissing(createResponse);
    }
}
