package it.polimi.se2019.model.player;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.CellSpawn;
import it.polimi.se2019.model.map.Room;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Player extends java.util.Observable implements Target, Serializable {
    private String nickname;
    private ArrayList<Player> damage;
    private int skull;
    private Points points;
    private Mark mark;
    private Character character;
    private AmmoBag ammoBag;
    private AmmoBag tempAmmo;   //used for discarded cards
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
        this.tempAmmo = new AmmoBag(0,0,0);
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
        return mark; //TODO fare una copia
    }

    /**
     * return a copy of the Cell where the player is
     * @return a copy of cellPosition
     */
    public Cell getCell(){
        return cellPosition;
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
     * Add damage to this player
     * @param enemy who had damaged you
     */
    public void receiveDamageBy(Player enemy) throws NotPresentException {
        //TODO sistemare se giocatore non è presente
        if(isOverKilled()){
            throw new NotPresentException();
        }
        //TODO finisci

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
     * load the weapon selected, using ordinary ammo and ammo gained from diascarded Powerups
     * @param weaponID the ID of the weapon
     * @throws NotPresentException  if the Player doesn't have this weapon
     * @throws WeaponIsLoadedException  if the weapon is already loaded
     * @throws NotEnoughAmmoException   if there's not enough ammo from ordinary ammo and discarded Powerups
     */
    public void loadWeapon(int weaponID) throws NotPresentException, WeaponIsLoadedException, NotEnoughAmmoException {
        for(WeaponCard weaponCard : weaponCardList) {
            if(weaponCard.getID() == weaponID) {
                if (weaponCard.isReloaded()) {
                    throw new WeaponIsLoadedException();
                } else {
                    AmmoBag cost = AmmoBag.createAmmoFromList(weaponCard.getReloadCost());
                    if( canPayAmmo(cost)) {

                        weaponCard.reload();
                        this.payAmmoCost(cost);
                    }
                    else{
                        throw new NotEnoughAmmoException();
                    }
                }
            }
        }
        //weapon not found
        throw new NotPresentException();
    }

    /**
     * discard a Powerup card (not during spawn). It adds the ammo in the temporary ammo. If not used for a cost in the turn, the ammo will vanish
     * @param powerupCard the card which has to be discarded
     * @throws NotPresentException if the card is not possessed by the Player
     */
    public void discardCard(PowerupCard powerupCard) throws NotPresentException {
        for(PowerupCard card : powerupCardList){
            if(card == powerupCard){
                ColorRYB colorOfCard = card.getAmmo();
                int tempRed = tempAmmo.getRedAmmo();
                int tempYellow = tempAmmo.getYellowAmmo();
                int tempBlue = tempAmmo.getBlueAmmo();

                switch(colorOfCard){
                    case RED:
                        tempRed++;
                        break;
                    case YELLOW:
                        tempYellow++;
                        break;
                    case BLUE:
                        tempBlue++;
                        break;
                }
                tempAmmo = new AmmoBag(tempRed,tempYellow, tempBlue);
                card.discard();
                powerupCardList.remove(card);
                //TODO notify
                return;
            }
        }
        throw new NotPresentException();
    }

    /**
     * discard a weapon
     * @param weaponToDiscard the weapon to discard
     * @throws NotPresentException if the player doesn't have the card
     */
    public void discardCard(WeaponCard weaponToDiscard) throws NotPresentException {
        for(WeaponCard weapon : weaponCardList){
            if(weapon.getID() == weaponToDiscard.getID()){
                weapon.discard();
                weaponCardList.remove(weapon);
                //TODO notify
                return;
            }
        }
        throw new NotPresentException();
    }

    /**
     * controll if the player can pay the cost (powerup discarded are counted in the calculation)
     * @param cost AmmoBag cost
     * @return true if the Player can pay it, false if not
     */
    public boolean canPayAmmo(AmmoBag cost){
        if(cost == null){
            return true;
        }

        return( cost.getRedAmmo() <= (ammoBag.getRedAmmo() + tempAmmo.getRedAmmo())
                && cost.getYellowAmmo() <= (ammoBag.getYellowAmmo() + tempAmmo.getYellowAmmo())
                && cost.getBlueAmmo() <= (ammoBag.getBlueAmmo() + tempAmmo.getBlueAmmo()));
    }

    /**
     * the Player pays the ammo cost
     * @param cost the AmmoBag cost
     * @throws NotEnoughAmmoException if the Player can't pay
     */
    public void payAmmoCost(AmmoBag cost) throws NotEnoughAmmoException {
        if (!canPayAmmo(cost)) {
            throw new NotEnoughAmmoException();
        } else {
            int newRed, newYellow, newBlue, newTempRed, newTempYellow, newTempBlue;
            //RED
            if (tempAmmo.getRedAmmo() >= cost.getRedAmmo()) {
                newTempRed = tempAmmo.getRedAmmo() - cost.getRedAmmo();
                newRed = ammoBag.getRedAmmo();
            } else {
                newTempRed = 0;
                newRed = tempAmmo.getRedAmmo() + ammoBag.getRedAmmo() - cost.getRedAmmo();
            }

            //YELLOW
            if (tempAmmo.getYellowAmmo() >= cost.getYellowAmmo()) {
                newTempYellow = tempAmmo.getYellowAmmo() - cost.getYellowAmmo();
                newYellow = ammoBag.getYellowAmmo();
            } else {
                newTempYellow = 0;
                newYellow = tempAmmo.getYellowAmmo() + ammoBag.getYellowAmmo() - cost.getYellowAmmo();
            }

            //BLUE
            if (tempAmmo.getBlueAmmo() >= cost.getBlueAmmo()) {
                newTempBlue = tempAmmo.getBlueAmmo() - cost.getBlueAmmo();
                newBlue = ammoBag.getBlueAmmo();
            } else {
                newTempBlue = 0;
                newBlue = tempAmmo.getBlueAmmo() + ammoBag.getBlueAmmo() - cost.getBlueAmmo();
            }

            ammoBag = new AmmoBag(newRed, newYellow, newBlue);
            tempAmmo = new AmmoBag(newTempRed, newTempYellow, newTempBlue);

        }
    }


    public boolean isVisibleBy(Player shooter){
        Cell enemyCell = shooter.getCell();
        Room enemyRoom = enemyCell.getRoom();
        Room roomOfThis = this.cellPosition.getRoom();
        //it's the same player
        if(shooter.getID() == ID){
            return false;
        }
        //in the same room
        else if(enemyRoom.equals(cellPosition.getRoom())){
            return true;
        }
        //
        else if(enemyCell.getNorthBorder().isCrossable() && roomOfThis.equals(enemyCell.getNorthCell().getRoom())){
            return true;
        }
        else if(enemyCell.getEastBorder().isCrossable() && roomOfThis.equals(enemyCell.getEastCell().getRoom())){
            return true;
        }
        else if(enemyCell.getSouthBorder().isCrossable() && roomOfThis.equals(enemyCell.getSouthCell().getRoom())){
            return true;
        }
        else if(enemyCell.getWestBorder().isCrossable() && roomOfThis.equals(enemyCell.getWestCell().getRoom())){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * must be called after the turn of the player to set parameters
     */
    public void endTurnSetting() {
        tempAmmo = new AmmoBag(0, 0, 0);
    }

    /**
     * Remove all marks by my list of markDone
     * @param enemyMarked The enemy I have marked
     */
    //Called by removeMarkReceivedBy()
    private void removeMarkDoneTo(Player enemyMarked) {
        this.mark.removeMarkDoneTo(enemyMarked);
    }

    public void setPosition(Cell position) {
        //if cellPosition==null is the first spawn, can't be removed anywhere
        if(this.cellPosition != null) this.cellPosition.removePlayer(this);
        cellPosition = position;
        position.addPlayer(this);
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", character=" + character +
                ", ID=" + ID +
                '}';
    }
}