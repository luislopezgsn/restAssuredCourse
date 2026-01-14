package org.example.endpoints;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.Step;
import org.example.models.VideoGame;
import static io.restassured.RestAssured.given;

public class VideoGameEndpoints {

    @Step("Get video game with ID: {videoGameId}")
    public Response getVideoGame(String videoGameId) {
        return given()
                .filter(new AllureRestAssured())
                .pathParam("videoGameId", videoGameId)
                .when()
                .get("https://videogamedb.uk/api/videogame/{videoGameId}");
    }

    @Step("Get video games in category: {category}")
    public Response getVideoGamesByCategory(String category) {
        return given()
                .filter(new AllureRestAssured())
                .queryParam("category", category)
                .when()
                .get("https://videogamedb.uk/api/videogame");
    }

    @Step("Get all video games")
    public Response getAllVideoGames() {
        return given()
                .filter(new AllureRestAssured())
                .when()
                .get("https://videogamedb.uk/api/videogame");
    }

    @Step("Authenticate user: {username}")
    public String authenticate(String username, String password) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body("{ \"password\": \"" + password + "\", \"username\": \"" + username + "\" }")
                .when()
                .post("https://videogamedb.uk/api/authenticate")
                .then()
                .extract()
                .path("token");
    }

    @Step("Create a new video game")
    public Response createVideoGame(String token, VideoGame videoGame) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(videoGame)
                .when()
                .post("https://videogamedb.uk/api/videogame");
    }

    @Step("Update video game {videoGameId}")
    public Response updateVideoGame(String token, String videoGameId, VideoGame videoGame) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .pathParam("videoGameId", videoGameId)
                .body(videoGame)
                .when()
                .put("https://videogamedb.uk/api/videogame/{videoGameId}");
    }

    @Step("Delete video game {videoGameId}")
    public Response deleteVideoGame(String token, String videoGameId) {
        return given()
                .filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + token)
                .pathParam("videoGameId", videoGameId)
                .when()
                .delete("https://videogamedb.uk/api/videogame/{videoGameId}");
    }
}
