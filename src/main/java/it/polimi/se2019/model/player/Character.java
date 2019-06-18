package it.polimi.se2019.model.player;

import it.polimi.se2019.ui.ConsoleColor;

import java.io.Serializable;
import java.util.Objects;

public class Character implements Serializable {

    private final String nameCharacter;
    private final String color;
    private final int id;

    public Character(String nameCharacter, String color) {
        this.nameCharacter = nameCharacter;
        this.color = color;
        this.id = 0;
    }

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

    public int getId() {
        return id;
    }
}
