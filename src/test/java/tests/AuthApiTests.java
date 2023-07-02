package tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import model.LoginPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.Endpoints.REGISTER;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.Specs.baseRequestSpec;
import static specs.Specs.baseResponseSpecCode200;

public class AuthApiTests {

    @Test
    @Severity(SeverityLevel.BLOCKER)
    void checkRegistrationToken() {

        String userEmail = "eve.holt@reqres.in";
        String userPassword = "cityslicka";

        LoginPayload data = new LoginPayload();
        data.setEmail(userEmail);
        data.setPassword(userPassword);

        step("Check registration token", () -> {
                LoginPayload.LoginResponse response = given()
                .spec(baseRequestSpec)
                .body(data)
                .when()
                .post(REGISTER)
                .then()
                .spec(baseResponseSpecCode200)
                .extract().as(LoginPayload.LoginResponse.class);

        assertThat(response.getToken()).isNotNull();
        });
    }
}
