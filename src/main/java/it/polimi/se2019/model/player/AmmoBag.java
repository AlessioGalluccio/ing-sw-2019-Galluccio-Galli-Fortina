package it.polimi.se2019.model.player;

import it.polimi.se2019.ui.ConsoleColor;

import java.io.Serializable;
import java.util.List;

import static it.polimi.se2019.ui.ConsoleSymbol.AMMO;

/**
 * @author Galli
 * @author Galluccio
 */
public class AmmoBag implements Serializable {
    private static final long serialVersionUID = -3617983877168060807L;
    private final int redAmmo;
    private final int yellowAmmo;
    private final int blueAmmo;

    /**
     * Create a new ammo bag for the player
     * @param redAmmo the new red ammo to set
     * @param yellowAmmo the new yellow ammo to set
     * @param blueAmmo the new blue ammo to set
     */
    public AmmoBag(int redAmmo, int yellowAmmo, int blueAmmo) {
        this.redAmmo = redAmmo;
        this.yellowAmmo = yellowAmmo;
        this.blueAmmo = blueAmmo;
    }

    /**
     * returns the number of red ammo
     * @return the integer of the red ammo
     */
    public int getRedAmmo() {
        return redAmmo;
    }

    /**
     * returns the number of blue ammo
     * @return the integer of the blue ammo
     */
    public int getBlueAmmo() {
        return blueAmmo;
    }

    /**
     * returns the number of yellow ammo
     * @return the integer of the yellow ammo
     */
    public int getYellowAmmo() {
        return yellowAmmo;
    }

    /**
     * Create a Ammo Bag starting from a list of ColorRYB
     * @param list the list to convert to Ammo Bag
     * @return The ammo bag with the ammo corresponding to the list
     */
    public static AmmoBag createAmmoFromList(List<ColorRYB> list){
        if(list == null || list.isEmpty()){
            return new AmmoBag(0,0,0);
        }
        else{
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

    /**
     * Sum the number of ammo of two ammo bag.
     * Sum the ammo according to their color, red with red, blue with blue etc.
     * @param first The first ammo bag to sum
     * @param second The second ammo bag to sum
     * @return A new ammo bag with the sum of the color
     */
    public static AmmoBag sumAmmoBag(AmmoBag first, AmmoBag second){
        int newRed = first.getRedAmmo() + second.getRedAmmo();
        int newYellow = first.getYellowAmmo() + second.getYellowAmmo();
        int newBlue = first.getBlueAmmo() + second.getBlueAmmo();

        return new AmmoBag(newRed, newYellow, newBlue);
    }

    /**
     * String the full ammo bag, representing each attributes with symbol and color.
     * Work with UTF-8 and ANSI code
     * @return The representation of the ammo bag
     */
    @Override
    public String toString() {
        String s = "";
        for(int i=0; i<redAmmo; i++) {
            s += ConsoleColor.RED + AMMO.toString();
        }
        for(int i=0; i<blueAmmo; i++) {
            s += ConsoleColor.BLUE + AMMO.toString();
        }
        for(int i=0; i<yellowAmmo; i++) {
            s += ConsoleColor.YELLOW + AMMO.toString();
        }
        s+=ConsoleColor.RESET;
        return  s;
    }
}
