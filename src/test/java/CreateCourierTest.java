import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.CoderResult;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void createCourierPositiveTest() {
        CourierLogin loginJson = new CourierLogin("loginlogin", "passpassword");
        given()
                .header("Content-type", "application/json")
                .body(loginJson)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    public void createCourierWithoutLoginTest() {
        CourierLogin login = new CourierLogin();
        login.setPassword("1234");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void createCourierWithoutPasswordTest() {
        CourierLogin login = new CourierLogin();
        login.setLogin("1231234");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
}
