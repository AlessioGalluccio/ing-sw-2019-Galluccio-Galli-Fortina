package it.polimi.se2019.model.player;

import java.io.Serializable;
import java.util.Objects;

@Deprecated
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
}
