package it.polimi.se2019.model.player;

public enum ColorRYB {
    RED,
    YELLOW,
    BLUE;

    /**
     * Convert a string in a ColorRYB element
     * @param color The string representing the color to convert
     * @return The ColorRYB corresponding to the string
     * @throws IllegalArgumentException If there's no color for the param string
     */
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
