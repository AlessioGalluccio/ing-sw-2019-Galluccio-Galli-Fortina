package it.polimi.se2019.model.map;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.io.Serializable;
import java.util.Objects;

@Immutable
public class Coordinate implements Serializable {
    private static final long serialVersionUID = -8917867784167746187L;
    private  final int x;
    private final int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Compare the coordinate based on X and Y
     * @param o The other player to compare
     * @return True if the two coordinates has the same X and Y. False otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}