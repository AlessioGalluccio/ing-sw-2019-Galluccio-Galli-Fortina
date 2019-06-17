package it.polimi.se2019.model.player;

public enum ColorRYB {
    RED,
    YELLOW,
    BLUE;

    public static ColorRYB colorByString(String color) throws IllegalArgumentException {
        color = color.toUpperCase();
        switch (color) {
            case "RED": return RED;
            case "BLUE": return BLUE;
            case "YELLOW": return YELLOW;
        }
        throw new IllegalArgumentException("Invalid color, use only RED, BLUE and YELLOW");
    }
}
