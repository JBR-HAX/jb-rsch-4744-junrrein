package org.jetbrains.assignment.repository;

import org.jetbrains.assignment.entity.Location;

import java.util.List;

public interface IRobotRepository {
    Location getLastLocation();
    void addNewLocationsAfterCurrent(List<Location> locations);
    void replaceLocationsWithNewOnes(List<Location> locations);
}
