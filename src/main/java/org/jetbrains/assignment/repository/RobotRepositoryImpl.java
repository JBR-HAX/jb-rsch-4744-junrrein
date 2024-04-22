package org.jetbrains.assignment.repository;

import org.jetbrains.assignment.entity.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RobotRepositoryImpl implements IRobotRepository {

    List<Location> robotLocations;

    public RobotRepositoryImpl() {
        robotLocations = new ArrayList<>();
        robotLocations.add(new Location(0, 0));
    }



    @Override
    public Location getLastLocation() {
        return robotLocations.get(robotLocations.size() - 1);
    }

    @Override
    public void addNewLocationsAfterCurrent(List<Location> locations) {
        robotLocations.addAll(locations);
    }

    @Override
    public void replaceLocationsWithNewOnes(List<Location> locations) {
        robotLocations = new ArrayList<>(locations);
    }
}
