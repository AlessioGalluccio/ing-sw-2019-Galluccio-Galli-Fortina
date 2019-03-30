package it.polimi.se2019.model.player;

public class Character {

    private final String nameCharacter;
    private final String color;

    public Character(String nameCharacter, String color) {
        this.nameCharacter = nameCharacter;
        this.color = color;
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
