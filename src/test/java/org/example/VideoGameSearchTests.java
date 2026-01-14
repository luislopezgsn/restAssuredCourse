package org.example;

import org.example.endpoints.VideoGameEndpoints;
import io.qameta.allure.*;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

@Epic("Video Game API Tests")
@Feature("Video Game Search and Retrieval")
public class VideoGameSearchTests {

    VideoGameEndpoints endpoints = new VideoGameEndpoints();

    @Test
    @Story("GET operations")
    @Description("Verify that a single video game can be retrieved by its ID")
    @Severity(SeverityLevel.BLOCKER)
    public void getSingleVideoGame() {
        endpoints.getVideoGame("5")
                .then()
                .statusCode(200)
                .body("name", equalTo("The Legend of Zelda: Ocarina of Time"));
    }

    @Test
    @Story("GET operations")
    @Description("Verify that video games can be filtered by category")
    @Severity(SeverityLevel.CRITICAL)
    public void getVideoGamesByCategory() {
        endpoints.getVideoGamesByCategory("Driving")
                .then()
                .statusCode(200)
                .body("name", hasItem("Gran Turismo 3"));
    }

    @Test
    @Story("Data extraction")
    @Description("Demonstrate data extraction from an API response")
    public void extractVideoGameData() {
        String gameName = endpoints.getAllVideoGames()
                .then()
                .extract()
                .path("name[0]");

        System.out.println("Extracted Game Name: " + gameName);
    }
}
