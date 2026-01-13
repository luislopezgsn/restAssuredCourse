package org.example.endpoints;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class VideoGameEndpoints {

    public Response getVideoGame(String videoGameId) {
        return given()
                .pathParam("videoGameId", videoGameId)
                .when()
                .get("https://videogamedb.uk/api/videogame/{videoGameId}");
    }
}
