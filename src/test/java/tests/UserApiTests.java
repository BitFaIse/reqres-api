package tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import model.CreateUpdateUserPayload;
import model.ListUserResponse;
import model.SingleUserResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static helpers.Endpoints.SINGLE_USER;
import static helpers.Endpoints.USERS;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static specs.Specs.*;

public class UserApiTests {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    void checkCreatedUserData() {

        String userName = "John Doe";
        String userJob = "Engineer";

        CreateUpdateUserPayload data = new CreateUpdateUserPayload();
        data.setName(userName);
        data.setJob(userJob);

        step("Verify created user data", () -> {
            CreateUpdateUserPayload.CreateUserResponse response = given()
                    .spec(baseRequestSpec)
                    .body(data)
                    .when()
                    .post(USERS)
                    .then()
                    .spec(baseResponseSpecCode201)
                    .extract().as(CreateUpdateUserPayload.CreateUserResponse.class);

            assertThat(response.getName()).isEqualTo(userName);
            assertThat(response.getJob()).isEqualTo(userJob);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    void checkUpdatedUserData() {

        String userName = "Elon Musk";
        String userJob = "Businessman";

        CreateUpdateUserPayload data = new CreateUpdateUserPayload();
        data.setName(userName);
        data.setJob(userJob);

        step("Verify updated user data", () -> {
            CreateUpdateUserPayload.CreateUserResponse response = given()
                    .spec(baseRequestSpec)
                    .body(data)
                    .when()
                    .put(SINGLE_USER)
                    .then()
                    .spec(baseResponseSpecCode200)
                    .extract().as(CreateUpdateUserPayload.CreateUserResponse.class);

            assertThat(response.getName()).isEqualTo(userName);
            assertThat(response.getJob()).isEqualTo(userJob);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    void checkDeletedUser() {

        step("Verify deleted user", () -> {
            given()
                    .spec(baseRequestSpec)
                    .when()
                    .delete(SINGLE_USER)
                    .then()
                    .spec(baseResponseSpecCode204);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    void checkSingleUserData() {

        step("Verify single user data", () -> {
            SingleUserResponse data = given()
                    .spec(baseRequestSpec)
                    .when()
                    .get(SINGLE_USER)
                    .then()
                    .spec(baseResponseSpecCode200)
                    .extract().as(SingleUserResponse.class);

            assertEquals(2, data.getUser().getId());
            assertEquals("janet.weaver@reqres.in", data.getUser().getEmail());
            assertEquals("Janet", data.getUser().getFirstName());
            assertEquals("Weaver", data.getUser().getLastName());
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    void checkUserEmailsEndWithDomain() {

        step("Verify user emails end with domain", () -> {
            List<ListUserResponse> users = given()
                    .queryParam("page", "2")
                    .spec(baseRequestSpec)
                    .when()
                    .get(USERS)
                    .then()
                    .spec(baseResponseSpecCode200)
                    .extract().body().jsonPath().getList("data", ListUserResponse.class);
            users.forEach(x -> assertTrue(x.getEmail().endsWith("reqres.in")));
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    void checkTotalUsersNumber() {

        step("Verify total users number", () -> {
            given()
                    .queryParam("page", "2")
                    .spec(baseRequestSpec)
                    .when()
                    .get(USERS)
                    .then()
                    .spec(baseResponseSpecCode200)
                    .body("total", equalTo(12));
        });
    }

}
