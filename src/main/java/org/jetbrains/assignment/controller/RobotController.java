package org.jetbrains.assignment.controller;

import org.jetbrains.assignment.dto.LocationDto;
import org.jetbrains.assignment.dto.MoveDto;
import org.jetbrains.assignment.service.IRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RobotController {

    @Autowired
    IRobotService robotService;


    @PostMapping("/locations")
    public ResponseEntity<List<LocationDto>> postMoves(@RequestBody List<MoveDto> moves) {

        return ResponseEntity.ok(robotService.moveRobotFromCurrentLocation(moves));
    }

    @PostMapping("/moves")
    public ResponseEntity<List<MoveDto>> postLocations(@RequestBody List<LocationDto> locations) {

        return ResponseEntity.ok(robotService.moveRobotFromFirstLocationInList(locations));
    }
}
