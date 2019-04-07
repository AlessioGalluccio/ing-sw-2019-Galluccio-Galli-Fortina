package it.polimi.se2019.model.player;


import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.deck.WeaponCard;

import java.util.ArrayList;

public class Player extends java.util.Observable implements Target {
    private String nickname;
    private ArrayList<Player> damage;
    private int skull;
    private Points points;
    private Mark mark;
    private Character character;
    private AmmoBag ammoBag;
    private Action actionChosen;
    private ArrayList<PowerupCard> powerupCard;
    private ArrayList<WeaponCard> weaponCard;
    private int ID;

    public Player(String nickname) {
        this.nickname = nickname;
        this.skull = 0;
        //TODO complete the constructor
    }

    public int getID() {

    }

    /**
     *
     * @return nickname of user
     */
    public String getNickname() {

    }

    /**
     *
     * @return list of players who damaged this player in correct order
     */
    public ArrayList<Player> getDamage() {

    }

    /**
     *
     * @return numer of skull of this player (numer of deaths)
     */
    public int getSkull() {

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

    }

    /**
     *
     * @return 1 if the player has been over killed, 0 if not
     */
    public boolean isOverKilled() {

    }

    /**
     *
     * @return the action choosen b y the user
     */
    public Action chooseAction() {

    }

    /**
     * it executes the action chosen
      */
    public void doAction() {
        actionChosen.executeAction(this);

    }

    /**
     * it respawns the player after death
     */
    public void resurrection() {

    }








}
