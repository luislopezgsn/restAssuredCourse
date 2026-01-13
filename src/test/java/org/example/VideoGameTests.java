package org.example;

import org.example.endpoints.VideoGameEndpoints;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class VideoGameTests {

    @Test
    public void getSingleVideoGame() {
        VideoGameEndpoints endpoints = new VideoGameEndpoints();
        
        endpoints.getVideoGame("5")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("The Legend of Zelda: Ocarina of Time"));
    }

    @Test
    public void getVideoGamesByCategory() {
        VideoGameEndpoints endpoints = new VideoGameEndpoints();

        endpoints.getVideoGamesByCategory("Driving")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", hasItem("Gran Turismo 3"));
    }
}
