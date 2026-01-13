import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class MyFirstTest {

    @Test
    @Description("Verify that the API returns the list of video games")
    public void myFirstTest() {
        given()
                .filter(new AllureRestAssured())
                .log().all()
        .when()
                .get("https://videogamedb.uk/api/videogame")
        .then()
                .log().all()
                .statusCode(200);

    }
}
