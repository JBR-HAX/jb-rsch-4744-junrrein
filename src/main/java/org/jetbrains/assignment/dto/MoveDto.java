package org.jetbrains.assignment.dto;

public record MoveDto(
    Direction direction,
    int steps
) {

    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}
