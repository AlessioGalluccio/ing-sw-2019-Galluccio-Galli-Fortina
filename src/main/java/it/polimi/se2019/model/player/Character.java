package it.polimi.se2019.model.player;

import java.io.Serializable;

public class Character implements Serializable {

    private final String nameCharacter;
    private final String color;

    public Character(String nameCharacter, String color) {
        this.nameCharacter = nameCharacter;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Character{" +
                nameCharacter + '\'' +
                '}';
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
}
