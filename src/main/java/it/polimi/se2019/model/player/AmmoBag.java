package it.polimi.se2019.model.player;

public class AmmoBag {
    private final int redAmmo;
    private final int yellowAmmo;
    private final int blueAmmo;

    public AmmoBag(int redAmmo, int yellowAmmo, int blueAmmo) {
        this.redAmmo = redAmmo;
        this.yellowAmmo = yellowAmmo;
        this.blueAmmo = blueAmmo;
    }

    public int getRedAmmo() {
        return redAmmo;
    }

    public int getBlueAmmo() {
        return blueAmmo;
    }

    public int getYellowAmmo() {
        return yellowAmmo;
    }
}
