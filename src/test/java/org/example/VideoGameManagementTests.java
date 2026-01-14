package org.example;

import org.example.endpoints.VideoGameEndpoints;
import org.example.models.VideoGame;
import io.qameta.allure.*;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

@Epic("Video Game API Tests")
@Feature("Video Game Management")
public class VideoGameManagementTests {

    VideoGameEndpoints endpoints = new VideoGameEndpoints();

    @Test
    @Story("POST operations")
    @Description("Verify that a new video game can be created successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void createNewVideoGame() {
        String token = endpoints.authenticate("admin", "admin");
        VideoGame videoGame = new VideoGame("Shooter", "Half-Life 2", "Mature", "2004-11-16", 96);

        endpoints.createVideoGame(token, videoGame)
                .then()
                .statusCode(200)
                .body("name", equalTo("Half-Life 2"));
    }

    @Test
    @Story("PUT operations")
    @Description("Verify that an existing video game can be updated")
    @Severity(SeverityLevel.NORMAL)
    public void updateExistingVideoGame() {
        String token = endpoints.authenticate("admin", "admin");
        VideoGame videoGame = new VideoGame("Adventure", "The Legend of Zelda: Ocarina of Time UPDATED", "PG-13", "1998-12-10", 99);

        endpoints.updateVideoGame(token, "5", videoGame)
                .then()
                .statusCode(200)
                .body("name", equalTo("The Legend of Zelda: Ocarina of Time UPDATED"))
                .body("reviewScore", equalTo(99));
    }

    @Test
    @Story("DELETE operations")
    @Description("Verify that a video game can be deleted")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteExistingVideoGame() {
        String token = endpoints.authenticate("admin", "admin");

        endpoints.deleteVideoGame(token, "5")
                .then()
                .statusCode(200)
                .body(equalTo("Video game deleted"));
    }
}
