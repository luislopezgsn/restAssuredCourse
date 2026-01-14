import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import config.VideoGameConfig;
import config.VideoGameEndpoints;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class MyFirstTest extends VideoGameConfig {

    @Test
    @Description("Verify that the API returns the list of video games")
    public void myFirstTest() {
        given()
                .filter(new AllureRestAssured())
                .log().all()
        .when()
                .get("/videogame")
        .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void myFirstTestWithEndpoint() {
        get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then().log().all();
    }
}
