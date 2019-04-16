package it.polimi.se2019.model.player;


import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.CellSpawn;

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
    private Cell cellPosition;
    private int ID;

    //constants
    private static final int NUM_START_RED = 2;
    private static final int NUM_START_YELLOW = 2;
    private static final int NUM_START_BLUE = 2;
    private static final int STARTING_POINTS = 0;
    private static final int STARTING_SKULLS = 0;
    private static final int NUM_DAMAGE_DEATH = 11;
    private static final int NUM_DAMAGE_OVERKILL = 12;
    private static final int MAX_CARD = 3;

    public Player(String nickname, Character character, int ID) {
        this.nickname = nickname;
        this.damage = new ArrayList<Player>();  //empty
        this.skull = STARTING_SKULLS;
        this.points = new Points(STARTING_POINTS);
        this.mark = new Mark();
        this.character = character;
        this.ammoBag = new AmmoBag(NUM_START_RED, NUM_START_YELLOW, NUM_START_BLUE);
        this.powerupCard = new ArrayList<>();
        this.weaponCard = new ArrayList<>();
        this.ID = ID;
    }

    /**
     *
     * @return ID of player
     */
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
     * @return integer of points of the player
     */
    public int getNumPoints() {
        return points.getSum();
    }

    /**
     *
     * @return ArrayList of PointCard of player
     */
    public ArrayList<Points> getListPointCards() {
        return null; //TODO clone of points
    }

    /**
     *
     * @param numRed new value of red ammo
     * @param numYellow new value of yellow ammo
     * @param numBlue new value of blue ammo
     */
    public void setAmmoBag(int numRed, int numYellow, int numBlue ) {
        //TODO control on the values, create the exception for incorrect values of ammo
        ammoBag = new AmmoBag(numRed, numYellow, numBlue);
    }

    /**
     *
     * @return 1 if the player is dead, 0 if not
     */
    public boolean isDead() {
        if (damage.size() >= NUM_DAMAGE_DEATH) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     *
     * @return 1 if the player has been over killed, 0 if not
     */
    public boolean isOverKilled() {
        if(damage.size() == NUM_DAMAGE_OVERKILL) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Add points to the player
     * @param num number of points added to the player
     */
    public void addPoints(int num) {
        points.addNewPoints(num);
    }

    /**
     * it respawns the player after death
     */
    public void resurrection(CellSpawn cellSpawn) {
        //TODO exception if cell is not CellSpawn
        skull += 1;
        cellPosition = cellSpawn;
    }

    /**
     * Add a powerup to the player list
     * @param powerupToAdd powerup to add
     * @throws TooManyCardException if the player has already three powerups
     */
    public void addPowerupCard(PowerupCard powerupToAdd) throws TooManyCardException {
        //if is dead the player can has 4 powerup in order to respawn
        if (powerupCard.size() < MAX_CARD || isDead()) powerupCard.add(powerupToAdd);
        else throw new TooManyCardException("You, " + getNickname() + ", have already three powerups");
    }







}