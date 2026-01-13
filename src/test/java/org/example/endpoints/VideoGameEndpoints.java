package org.example.endpoints;

import io.restassured.response.Response;
import io.qameta.allure.restassured.AllureRestAssured;
import static io.restassured.RestAssured.given;

public class VideoGameEndpoints {

    public Response getVideoGame(String videoGameId) {
        return given()
                .filter(new AllureRestAssured())
                .pathParam("videoGameId", videoGameId)
                .when()
                .get("https://videogamedb.uk/api/videogame/{videoGameId}");
    }

    public Response getVideoGamesByCategory(String category) {
        return given()
                .filter(new AllureRestAssured())
                .queryParam("category", category)
                .when()
                .get("https://videogamedb.uk/api/videogame");
    }
}
