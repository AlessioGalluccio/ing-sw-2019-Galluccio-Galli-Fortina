package it.polimi.se2019.model.player;


import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.deck.WeaponCard;

import java.util.ArrayList;
import java.util.Observable;

public class Player extends java.util.Observable implements Target {
    private String nickname;
    private ArrayList<Player> damage;
    private int skull;
    private Points points;
    private Mark mark;
    private Character character;
    private AmmoBag ammoBag;
    private ArrayList<PowerupCard> powerupCard;
    private ArrayList<WeaponCard> weaponCard;
    private int ID;

    //starting ammo
    private static final int NUM_START_RED = 2;
    private static final int NUM_START_YELLOW = 2;
    private static final int NUM_START_BLUE = 2;
    private static final int STARTING_POINTS = 0;

    public Player(String nickname, Character character, int ID) {
        this.nickname = nickname;
        this.damage = new ArrayList<Player>();  //empty
        this.skull = 0;
        this.points = new Points(STARTING_POINTS);
        this.mark = new Mark();
        this.character = character;
        this.ammoBag = new AmmoBag(NUM_START_RED, NUM_START_YELLOW, NUM_START_BLUE);
        this.powerupCard = new ArrayList<PowerupCard>();
        this.weaponCard = new ArrayList<WeaponCard>();
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    /**
     *
     * @return nickname of user
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @return list of players who damaged this player in correct order
     */
    public ArrayList<Player> getDamage() {
        return null; //TODO implementare la clone di damage
    }

    /**
     *
     * @return numer of skull of this player (numer of deaths)
     */
    public int getSkull() {
        return skull;
    }

    /**
     *
     * @return ammo of player
     */
    public AmmoBag getAmmo() {
        return this.ammoBag;
    }

    /**
     *
     * @param numRed new value of red ammo
     * @param numYellow new value of yellow ammo
     * @param numBlue new value of blue ammo
     */
    public void setAmmoBag(int numRed, int numYellow, int numBlue ) {
        //TODO control on the values
        ammoBag = new AmmoBag(numRed, numYellow, numBlue);
    }

    /**
     *
     * @return 1 if the player is dead, 0 if not
     */
    public boolean isDead() {

        return false; //TODO implementare
    }

    /**
     *
     * @return 1 if the player has been over killed, 0 if not
     */
    public boolean isOverKilled() {
        return false; //TODO implementare
    }

    /**
     * it respawns the player after death
     */
    public void resurrection() {

    }








}