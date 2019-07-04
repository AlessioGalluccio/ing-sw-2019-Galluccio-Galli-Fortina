package it.polimi.se2019.model.player;

import it.polimi.se2019.ui.ConsoleColor;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Galli
 */
public class Character implements Serializable {

    private static final long serialVersionUID = -6097552840845310529L;
    private final String nameCharacter;
    private final String color;
    private final int id;

    /**
     * constructor
     * @param nameCharacter the String name of the character
     * @param color the color of the Character
     */
    public Character(String nameCharacter, String color) {
        this.nameCharacter = nameCharacter;
        this.color = color;
        this.id = 0;
    }

    /**
     * constructor
     * @param id the ID of the character
     */
    public Character(int id) {
        switch (id) {
            case 1:
                this.nameCharacter = "Sprog";
                this.color = "GREEN";
                break;
            case 2:
                this.nameCharacter = "Violet";
                this.color = "VIOLET";
                break;
            case 3:
                this.nameCharacter = ":D-Struct-or";
                this.color = "YELLOW";
                break;
            case 4:
                this.nameCharacter = "Dozer";
                this.color = "GREY";
                break;
            case 5:
                this.nameCharacter = "Banshee";
                this.color = "CYAN";
                break;
            default: throw new IllegalArgumentException();
        }
        this.id = id;
    }

    /**
     * String the full character, representing each attributes with color and name
     * Work with ANSI code
     * @return The representation of the character
     */
    @Override
    public String toString() {
        return ConsoleColor.colorByColor(color) + nameCharacter + ConsoleColor.RESET;
    }

    /**
     * @return Character's name
     */
    public String getNameCharacter() {
        return nameCharacter;
    }

    /**
     * @return Character's color
     */
    public String getColor() {
        return color;
    }

    /**
     * Compare the character based on the name and the color
     * @param o The other character to compare
     * @return True if the two characters have the same name and color. False otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return nameCharacter.equals(character.nameCharacter) &&
                color.equals(character.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameCharacter, color);
    }

    /**
     * get the ID of the character
     * @return the integer ID of the character
     */
    public int getId() {
        return id;
    }
}
