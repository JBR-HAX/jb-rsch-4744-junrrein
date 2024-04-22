package org.jetbrains.assignment.service;

import org.jetbrains.assignment.dto.LocationDto;
import org.jetbrains.assignment.dto.MoveDto;

import java.util.List;

public interface IRobotService {
    List<LocationDto> moveRobotFromCurrentLocation(List<MoveDto> moves);
    List<MoveDto> moveRobotFromFirstLocationInList(List<LocationDto> locations);
}
