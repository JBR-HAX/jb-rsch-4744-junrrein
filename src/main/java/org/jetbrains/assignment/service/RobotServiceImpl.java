package org.jetbrains.assignment.service;

import org.jetbrains.assignment.dto.LocationDto;
import org.jetbrains.assignment.dto.MoveDto;
import org.jetbrains.assignment.entity.Location;
import org.jetbrains.assignment.repository.IRobotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RobotServiceImpl implements IRobotService {

    @Autowired
    IRobotRepository robotRepository;


    @Override
    public List<LocationDto> moveRobotFromCurrentLocation(List<MoveDto> moves) {

        Location lastLocation = robotRepository.getLastLocation();
        Location currentLocation = new Location(lastLocation.getX(), lastLocation.getY());

        List<Location> intermediateLocations = new ArrayList<>();

        for (MoveDto move : moves) {
            switch (move.direction()) {
                case NORTH -> currentLocation.setY(currentLocation.getY() + move.steps());
                case SOUTH -> currentLocation.setY(currentLocation.getY() - move.steps());
                case EAST -> currentLocation.setX(currentLocation.getX() + move.steps());
                case WEST -> currentLocation.setX(currentLocation.getX() - move.steps());
            }

            intermediateLocations.add(new Location(currentLocation.getX(), currentLocation.getY()));
        }

        robotRepository.addNewLocationsAfterCurrent(intermediateLocations);

        List<LocationDto> returnedLocations = new ArrayList<>();
        returnedLocations.add(new LocationDto(lastLocation.getX(), lastLocation.getY()));

        intermediateLocations.forEach(location ->
            returnedLocations.add(new LocationDto(location.getX(), location.getY()))
        );

        return returnedLocations;
    }

    @Override
    public List<MoveDto> moveRobotFromFirstLocationInList(List<LocationDto> locations) {

        if (locations.isEmpty())
            throw new RuntimeException("Locations list is empty");

        List<MoveDto> moves = new ArrayList<>();

        for (int i = 1; i < locations.size(); i++) {
            LocationDto lastLocation = locations.get(i - 1);
            LocationDto currentLocation = locations.get(i);

            int xDiff = currentLocation.x() - lastLocation.x();
            int yDiff = currentLocation.y() - lastLocation.y();

            if (xDiff > 0)
                moves.add(new MoveDto(MoveDto.Direction.EAST, xDiff));
            else if (xDiff < 0)
                moves.add(new MoveDto(MoveDto.Direction.WEST, -xDiff));

            if (yDiff > 0)
                moves.add(new MoveDto(MoveDto.Direction.NORTH, yDiff));
            else if (yDiff < 0)
                moves.add(new MoveDto(MoveDto.Direction.SOUTH, -yDiff));
        }

        robotRepository.replaceLocationsWithNewOnes(locations.stream()
            .map(locationDto -> new Location(locationDto.x(), locationDto.y()))
            .toList());

        return moves;
    }
}
