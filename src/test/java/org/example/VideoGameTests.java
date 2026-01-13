package org.example;

import org.example.endpoints.VideoGameEndpoints;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

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
}
