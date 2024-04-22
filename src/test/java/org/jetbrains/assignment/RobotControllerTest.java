package org.jetbrains.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.assignment.dto.LocationDto;
import org.jetbrains.assignment.dto.MoveDto;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobotControllerTest {

    @Test
    public void testPostMoves() throws IOException, InterruptedException {

        List<MoveDto> inputMoves = Arrays.asList(
            new MoveDto(MoveDto.Direction.EAST, 1),
            new MoveDto(MoveDto.Direction.NORTH, 3),
            new MoveDto(MoveDto.Direction.EAST, 3),
            new MoveDto(MoveDto.Direction.SOUTH, 5),
            new MoveDto(MoveDto.Direction.WEST, 2)
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/locations"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(inputMoves)))
            .build();

        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> responseString = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<LocationDto> expectedLocations = List.of(
            new LocationDto(0, 0),
            new LocationDto(1, 0),
            new LocationDto(1, 3),
            new LocationDto(4, 3),
            new LocationDto(4, -2),
            new LocationDto(2, -2)
        );

        assertEquals(mapper.writeValueAsString(expectedLocations), responseString.body());
    }
}
