package it.polimi.se2019.model.player;

import java.util.List;

public class AmmoBag {
    private final int redAmmo;
    private final int yellowAmmo;
    private final int blueAmmo;

    /**
     * Create a new ammo bag for the player
     * (Accessible only in model.player package)
     * @param redAmmo the new red ammo to set
     * @param yellowAmmo the new yellow ammo to set
     * @param blueAmmo the new blue ammo to set
     */
    AmmoBag(int redAmmo, int yellowAmmo, int blueAmmo) {
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

    public static AmmoBag createAmmoFromList(List<ColorRYB> list){
        if(list == null){
            return new AmmoBag(0,0,0);
        }
        int red = 0;
        int yellow = 0;
        int blue = 0;
        for(ColorRYB color : list){
            switch (color){
                case RED:
                    red++;
                    break;
                case YELLOW:
                    yellow++;
                    break;
                case BLUE:
                    blue++;
                    break;
            }
        }
        return new AmmoBag(red, yellow, blue);
    }
}
