package it.polimi.se2019.model.player;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.cloneable.SkinnyObject;
import it.polimi.se2019.cloneable.SkinnyObjectExclusionStrategy;
import it.polimi.se2019.cloneable.NotForPlayer;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.Observable;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.CellSpawn;
import it.polimi.se2019.model.map.Room;
import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.view.ModelViewMess.PlayerModelMessage;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player extends Observable implements Target, Serializable {
    private String nickname;
    @SkinnyObject
    @NotForPlayer
    private ArrayList<Player> damage;
    private int skull;
    @SkinnyObject
    private Points points;
    @SkinnyObject
    @NotForPlayer
    private Mark mark;
    private Character character;
    @SkinnyObject
    private AmmoBag ammoBag;
    private transient AmmoBag tempAmmo;   //used for discarded cards
    @SkinnyObject
    private List<PowerupCard> powerupCardList = new ArrayList<>();
    @SkinnyObject
    private List<WeaponCard> weaponCardList = new ArrayList<>();
    private transient Cell cellPosition;
    private boolean isFrenzyDeath = false;
    private boolean isFirstGroupFrenzy = false;
    private transient boolean bonusPowerup = false; //is the forth powerup used for respawn
    private int ID;
    private boolean isConnected = true;


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
        this.damage = new ArrayList<>();  //empty
        this.skull = STARTING_SKULLS;
        this.points = new Points(STARTING_POINTS);
        this.mark = new Mark();
        this.character = character;
        this.ammoBag = new AmmoBag(NUM_START_RED, NUM_START_YELLOW, NUM_START_BLUE);
        this.tempAmmo = new AmmoBag(0,0,0);
        this.ID = ID;
    }

    public Player(String nickname, int ID) {
        this.nickname = nickname;
        this.ID = ID;
        this.damage = new ArrayList<>();  //empty
        this.skull = STARTING_SKULLS;
        this.points = new Points(STARTING_POINTS);
        this.mark = new Mark();
        this.ammoBag = new AmmoBag(NUM_START_RED, NUM_START_YELLOW, NUM_START_BLUE);
        this.tempAmmo = new AmmoBag(0,0,0);
        this.powerupCardList = new ArrayList<>();
        this.weaponCardList = new ArrayList<>();
    }

    /**
     *
     * @return ID of player
     */
    public int getID() {
        return ID;
    }

    public boolean isFrenzyDeath() {
        return isFrenzyDeath;
    }

    /**
     *
     * @return if the player in frenzy mode has first gruop action or second
     */
    public boolean isFirstGroupFrenzy() {
        return isFirstGroupFrenzy;
    }

    /**
     * set isFirstGroup boolean variable
     * @param isFirstGroup
     */
    public void setFirstGroupFrenzy(Boolean isFirstGroup){
        this.isFirstGroupFrenzy= isFirstGroup;
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
        return new ArrayList<>(damage);
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

    public Character getCharacter() {
        return character;
    }

    /**
     *
     * @return Deep copy of player's weapon card list
     */
    public List<WeaponCard> getWeaponCardList() {
        return new ArrayList<>(weaponCardList);
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
        return mark.clone();
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
        notifyObservers(this.clone());
        if(exception) throw new TooManyException("You have too many ammo of some color, they have been set to the maximum (3)");
    }

    /**
     *
     * @return True if the player is dead, false if not
     */
    public boolean isDead() {
        return damage.size() >= NUM_DAMAGE_DEATH;
    }

    /**
     *
     * @return 1 if the player has been over killed, 0 if not
     */
    public boolean isOverKilled() {
        return damage.size() == NUM_DAMAGE_OVERKILL;
    }

    /**
     * Add points to the player
     * @param num number of points added to the player
     */
    public void addPoints(int num) {
        points.addNewPoints(num);
        notifyObservers(new PlayerModelMessage(this.clone()));
    }

    /**
     * it respawns the player after death
     */
    public void resurrection(CellSpawn cellSpawn) {
        //TODO exception if cell is not CellSpawn
        skull += 1;
        bonusPowerup = false;
        setPosition(cellSpawn);
        notifyObservers(new PlayerModelMessage(this.clone()));
    }

    /**
     * returns true if the player is connected, false if not
     * @return bolean true if connected, false if not
     */
    public boolean isConnected(){
        return isConnected;
    }

    /**
     * Add a powerup to the player list
     * @param powerupToAdd powerup to add
     * @throws TooManyException if the player has already three powerups
     */
    public void addPowerupCard(PowerupCard powerupToAdd) throws TooManyException {
        //if is dead the player can has 4 powerup in order to respawn
        if (powerupCardList.size() < MAX_CARD || bonusPowerup) {
            powerupCardList.add(powerupToAdd);
            notifyObservers(new PlayerModelMessage(this.clone()));
        }
        else throw new TooManyException("You, " + getNickname() + ", have already three powerups");
    }

    /**
     * return true if player has the card, otherwise false
     * @param powerupCard the card you want to control
     * @return true if player has the card, otherwise false
     */
    public boolean containsPowerup(PowerupCard powerupCard){
       boolean flag = false;
        for(PowerupCard card : powerupCardList){
            if(card.getID() == powerupCard.getID()){
                flag = true;
            }
        }
        return(flag);
    }

    /**
     * Add a weapon to the player list
     * @param weaponToAdd weapon to add
     * @throws TooManyException if the player has already three + 1 weapon
     * the one in exception is due to the fact the player can discard any weapon instead of the one just picked
     */
    public void addWeaponCard(WeaponCard weaponToAdd) throws TooManyException {
        //the +1 is due to the fact he can discard any weapon instead of the one just picked
        if (weaponCardList.size() < MAX_CARD + 1) {
            weaponCardList.add(weaponToAdd);
            notifyObservers(new PlayerModelMessage(this.clone()));
        }
        else throw new TooManyException("You, " + getNickname() + ", have already three weapons");
    }

    /**
     * Add damage to this player
     * @param enemy who had damaged you
     * @throws NotPresentException If player has been already killed
     * @throws YouDeadException If player dies in this turn
     * @throws YouOverkilledException If player is over killed in this turn
     */
    public void receiveDamageBy(Player enemy) throws NotPresentException, YouDeadException, YouOverkilledException {
        //If player is already kill throw exception, he cannot receive damage!
        if(isOverKilled()) throw new NotPresentException(toString() + " has been already over killed");
        damage.add(enemy);
        notifyObservers(new PlayerModelMessage(this.clone()));
        if(isOverKilled()) throw new YouOverkilledException(toString() + " has been over killed by " + enemy.toString());
        if(isDead()) {
            bonusPowerup=true;
            throw new YouDeadException(toString() + " has been killed by " + enemy.toString());
        }
        //notify done by FireMode when finish to fire
    }

    public void resetDamage() {
        damage.clear();
        //notify done by resurrection
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
        notifyObservers(new PlayerModelMessage(this.clone()));
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
     * When someone shoot me, all my mark made by the shooter has to be eliminated and converted in damage
     * Remove all marks made by enemy
     * @param enemy who made marks I want to remove
     */
    public void removeMarkReceivedBy(Player enemy) {
        this.mark.removeMarkReceivedBy(enemy);
        enemy.removeMarkDoneTo(this);
    }

    /**
     * Load the weapon selected, using ordinary ammo and ammo gained from discarded Powerups
     * @param weaponID the ID of the weapon
     * @throws NotPresentException if the Player doesn't have this weapon
     * @throws WeaponIsLoadedException if the weapon is already loaded
     * @throws NotEnoughAmmoException if there's not enough ammo from ordinary ammo and discarded Powerups
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
                        notifyObservers(new PlayerModelMessage(this.clone()));
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
                notifyObservers(new PlayerModelMessage(this.clone()));
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
                //weapon.discard()  WeaponCard can't be discarded! They have to be replace on the map
                weaponCardList.remove(weapon);
                notifyObservers(new PlayerModelMessage(this.clone()));
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
            notifyObservers(new PlayerModelMessage(this.clone()));
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
        else if(enemyRoom.equals(roomOfThis)){
            return true;
        }
        //
        /*else if(enemyCell.getNorthBorder().isCrossable() && roomOfThis.equals(enemyCell.getNorthCell().getRoom())){
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
        }*/
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

    /**
     * Move player to a new cell
     * It change also the player list on the cell!
     * @param position the new cell of the player
     */
    public void setPosition(Cell position) {
        //if cellPosition==null is the first spawn, can't be removed anywhere
        if(this.cellPosition != null) this.cellPosition.removePlayer(this);
        cellPosition = position;
        position.addPlayer(this);
    }

    /**
     * set status of the connection of the player
     * @param isConnected true if connected, false if disconnected
     */
    public void setConnected(boolean isConnected){
        this.isConnected = isConnected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return ID == player.ID &&
                nickname.equals(player.nickname) &&
                Objects.equals(character, player.character);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, character, ID);
    }


    /**
     * HELPER
     * Used by clone to set the copy of mark
     * @param markClone
     */
    private void addMark(Mark markClone) {
        this.mark = markClone;
    }

    /**
     * HELPER
     * Used by clone to set the copy damage
     * To add a damage by a enemy use receiveDamageBy()
     * @param damageCopy
     */
    private void addDamage(ArrayList<Player> damageCopy) {
        this.damage = damageCopy;
    }

    /**
     * If player die during the last turn or in frenzy mode set isFrenzyDeath as true
     * In this case the player can be cash only for 2-1-1-1 points and not for 8-6-4-2-1-1
     */
    public void setFrenzyDeath() {
        isFrenzyDeath = true;
        //notify done by resurrection
    }

    /**
     * Deep clone of player for the view
     * Not all the attributes are copied, only the NOT transient!
     * @return Deep copy of player
     */
    public Player clone() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PowerupCard.class, new JsonAdapter<PowerupCard>())
                .registerTypeAdapter(AmmoCard.class, new JsonAdapter<AmmoCard>())
                .registerTypeAdapter(WeaponCard.class, new JsonAdapter<WeaponCard>())
                .registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>())
                .setExclusionStrategies(new NotForPlayerExclusionStrategy())
                .create();

        Player clone = gson.fromJson(gson.toJson(this), Player.class);

        clone.addMark(mark.clone());

        gson = new GsonBuilder()
                .setExclusionStrategies(new SkinnyObjectExclusionStrategy())
                .create();

        Type TYPE = new TypeToken<List<Player>>() {
        }.getType();

        clone.addDamage(gson.fromJson(gson.toJson(damage, TYPE), TYPE));

        return clone;
    }

    @Override
    public String toString() {
        String s = nickname;
        if(character!=null) s += " - " + character.toString();
        s+="\n  Marks: ";
        for(Player p : mark.getMarkReceived()) {
            s+= ConsoleColor.colorByColor(p.getCharacter().getColor()) + "◉";
        }
        s+=ConsoleColor.RESET+ "\n  Damages: " + damage.size() + "/12 ";
        for(Player p : damage) {
            s+= ConsoleColor.colorByColor(p.getCharacter().getColor()) + "◉";
        }
        s+="\033[3m (remember adrenaline actions)";
        s+=ConsoleColor.RESET+"\n  Skulls: ";
        if(!isFrenzyDeath) s+="8 6 4 2 1 1 \t";
        else s+="2 1 1 1 ";
        for(int i=0; i<skull; i++) {
            s+= ConsoleColor.RED + " ☠" + ConsoleColor.RESET;
        }
        s+="\n  Points: " + points.getSum();
        s+="\n  Ammo: ";
        for(int i=0; i< ammoBag.getBlueAmmo(); i++) {
            s+= ConsoleColor.BLUE_BOLD_BRIGHT + "✚";
        }
        for(int i=0; i< ammoBag.getRedAmmo(); i++) {
            s+= ConsoleColor.RED_BOLD_BRIGHT+ "✚";
        }
        for(int i=0; i< ammoBag.getYellowAmmo(); i++) {
            s+= ConsoleColor.YELLOW_BOLD+ "✚";
        }
        s+= ConsoleColor.RESET + "\n  Weapons: ";
        for(WeaponCard weapon : weaponCardList) {
            s+= weapon.getName();
            if(weapon.isReloaded()) s+="* ";
            else s+=" ";
        }
        s+="\033[3m(* means loaded)" + ConsoleColor.RESET;
        s+="\n  Power ups: ";
        for(PowerupCard powerup : powerupCardList) {
            s+= powerup.toString() + " " + ConsoleColor.RESET;
        }
        return s;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    private static class NotForPlayerExclusionStrategy implements ExclusionStrategy {
        public boolean shouldSkipClass(Class<?> clazz) {
            return clazz.getAnnotation(NotForPlayer.class) != null;
        }

        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(NotForPlayer.class) != null;
        }
    }

}