package it.polimi.se2019.ui;

public enum ConsoleSymbol {
    WALL("■"),
    PLAYER("⊚"),
    AMMO("✚"),
    SPAWN("↺"),
    SKULL("☠");


    private final String code;

    ConsoleSymbol(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
