package it.polimi.se2019.model.player;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.CellSpawn;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Player extends java.util.Observable implements Target {
    private String nickname;
    private ArrayList<Player> damage;
    private int skull;
    private Points points;
    private Mark mark;
    private Character character;
    private AmmoBag ammoBag;
    private ArrayList<PowerupCard> powerupCardList;
    private ArrayList<WeaponCard> weaponCardList;
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
    private static final int MAX_AMMO = 3;

    public Player(String nickname, Character character, int ID) {
        this.nickname = nickname;
        this.damage = new ArrayList<Player>();  //empty
        this.skull = STARTING_SKULLS;
        this.points = new Points(STARTING_POINTS);
        this.mark = new Mark();
        this.character = character;
        this.ammoBag = new AmmoBag(NUM_START_RED, NUM_START_YELLOW, NUM_START_BLUE);
        this.powerupCardList = new ArrayList<>();
        this.weaponCardList = new ArrayList<>();
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
    public List<Player> getDamage() {
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
     * @return Deep copy of player's weapon card list
     */
    public List<WeaponCard> getWeaponCardList() {
        GsonBuilder g = new GsonBuilder()
                .registerTypeAdapter(WeaponCard.class, new JsonAdapter<WeaponCard>())
                .registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>());
        Gson gson = g.create();

        Type TYPE = new TypeToken<List<WeaponCard>>() {
        }.getType();

        return gson.fromJson(gson.toJson(weaponCardList, TYPE), TYPE);
    }

    /**
     *
     * @return Deep copy of player's powerup card list
     */
    public List<PowerupCard> getPowerupCardList() {
        GsonBuilder g = new GsonBuilder()
                .registerTypeAdapter(PowerupCard.class, new JsonAdapter<PowerupCard>());
        Gson gson = g.create();

        Type TYPE = new TypeToken<List<PowerupCard>>() {
        }.getType();

        return gson.fromJson(gson.toJson(powerupCardList, TYPE), TYPE);
    }

    /**
     *
     * @return ArrayList of PointCard of player
     */
    public List<PointCard> getPointCardList() {
        return points.getPointCard();
    }

    public Mark getMark() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(mark), Mark.class);
    }

    /**
     * Set player's ammo to value indicated by params
     * If some param is grater than three the relative ammo is set to 3
     * @param numRed new value of red ammo
     * @param numYellow new value of yellow ammo
     * @param numBlue new value of blue ammo
     * @throws TooManyException if some of param are grater then 3
     */
    public void setAmmoBag(int numRed, int numYellow, int numBlue) throws TooManyException {
        boolean exception = false;
        if(numRed > MAX_AMMO) {
            numRed = MAX_AMMO;
            exception = true;
        }
        if(numBlue > MAX_AMMO) {
            numBlue = MAX_AMMO;
            exception = true;
        }
        if(numYellow > 3) {
            numYellow = 3;
            exception = true;
        }

        ammoBag = new AmmoBag(numRed, numYellow, numBlue);
        if(exception) throw new TooManyException("You have too many ammo of some color, they have been set to the maximum (3)");
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
     * @throws TooManyException if the player has already three powerups
     */
    public void addPowerupCard(PowerupCard powerupToAdd) throws TooManyException {
        //if is dead the player can has 4 powerup in order to respawn
        if (powerupCardList.size() < MAX_CARD || isDead()) powerupCardList.add(powerupToAdd);
        else throw new TooManyException("You, " + getNickname() + ", have already three powerups");
        //TODO add notify()
    }

    /**
     * Add a weapon to the player list
     * @param weaponToAdd weapon to add
     * @throws TooManyException if the player has already three weapon
     */
    public void addWeaponCard(WeaponCard weaponToAdd) throws TooManyException {
        //if is dead the player can has 4 powerup in order to respawn
        if (weaponCardList.size() < MAX_CARD) weaponCardList.add(weaponToAdd);
        else throw new TooManyException("You, " + getNickname() + ", have already three weapons");
        //TODO add notify()
    }

    /**
     * Mark this player with a enemy's mark
     * @param enemy who had marked you
     * @throws TooManyException if enemy had already marked you three times
     */
    public void receiveMarkBy(Player enemy) throws TooManyException {
        //Add a mark to the enemy's list of done marks
        enemy.addMarkDoneTo(this); //Throws TooManyException
        this.mark.addMarkReceivedBy(enemy);
    }

    /**
     * HELPER
     * Add a mark to my list of markDone
     * @param enemyToMark the enemy I have marked
     * @throws TooManyException if you have already marked this enemy three times
     */
    //Called by receiveMarkBy()
    private void addMarkDoneTo(Player enemyToMark) throws TooManyException {
        this.mark.addMarkDoneTo(enemyToMark); //Throws TooManyException
    }

    /**
     * Remove all marks made by enemy
     * @param enemy who made marks I want to remove
     */
    public void removeMarkReceivedBy(Player enemy) {
        this.mark.removeMarkReceivedBy(enemy);
        enemy.removeMarkDoneTo(this);
    }

    /**
     * Remove all marks by my list of markDone
     * @param enemyMarked The enemy I have marked
     */
    //Called by removeMarkReceivedBy()
    private void removeMarkDoneTo(Player enemyMarked) {
        this.mark.removeMarkDoneTo(enemyMarked);
    }
}