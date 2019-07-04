package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.view.ModelViewMess.HandlerEnemyViewMessage;

import java.io.Serializable;
import java.util.ArrayList;
import it.polimi.se2019.model.Observable;

import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

import static it.polimi.se2019.ui.ConsoleSymbol.*;

public class EnemyView extends Observable implements Observer, Serializable{
    private static final long serialVersionUID = -3162602651843385228L;

    private int ID = -1;
    private String nickname;
    private Character character;
    private AmmoBag ammo;
    private ArrayList<Player> mark = new ArrayList<>();
    private ArrayList<WeaponCard> unloadedWeapon = new ArrayList<>();
    private int loadedWeapon;
    private int skull;
    private List<Player> damage = new LinkedList<>();
    private int powerup;
    private boolean isFrenzyDeath;

    /**
     * true if enemy is in frenzy mode
     * @return boolean isFrenzyDeath
     */
    public boolean isFrenzyDeath() {
        return isFrenzyDeath;
    }

    /**
     * getter of mark
     * @return arraylist of players
     */
    public ArrayList<Player> getMark() {
        return mark;
    }

    /**
     *  set the enemy nickname
     * @param nickname name of the enemy
     */
    public EnemyView(String nickname) {
        this.nickname = nickname;
    }

    /**
     * getter of ammo
     * @return ammo
     */
    public AmmoBag getAmmo() {
        return ammo;
    }

    /**
     *  get list of player who give damage
     * @return list of player who give damage
     */
    public List<Player> getDamage() {
        return damage;
    }

    /**
     * getter of enemy character
     * @return cìenemy character
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * getter of enemy UnloadedWeapon
     * @return Arrylist of UnloadedWeapon
     */
    public ArrayList<WeaponCard> getUnloadedWeapon() {
        return unloadedWeapon;
    }

    /**
     * getetr of enemy number of loadedWeapon
     * @return loadedWeapon
     */
    public int getLoadedWeapon() {
        return loadedWeapon;
    }

    /**
     * getter number of enemy skull
     * @return number of enemy skull
     */
    public int getSkull() {
        return skull;
    }

    /**
     * getetr of enemy Nickname
     * @return enemy Nickname
     */
    public String getNickname() {

        return nickname;
    }

    /**
     * getter of enemy powerup
     * @return enemy powerup
     */
    public int getPowerup() {
        return powerup;
    }

    /**
     * getter of enemy ID
     * @return enemy ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Receive message from the model and forward it to the networkHandler
     * @param o null
     * @param arg the message receive to forward
     */
    @Override
    public void update(java.util.Observable o/*Will be always null*/, Object arg) {
        HandlerEnemyViewMessage message = (HandlerEnemyViewMessage) arg;
        message.handleMessage(this);
        notifyObservers(message);
    }

    /**
     * This method is call whenever a enemy is changed.
     * Update all the attribute with the new ones.
     * @param enemy The new enemy with the attributes changed.
     */
    public void handlePlayerMessage(Player enemy) {
        this.nickname = enemy.getNickname();
        this.ID = enemy.getID();
        this.character = enemy.getCharacter();
        this.ammo = enemy.getAmmo();
        this.mark = (ArrayList<Player>) enemy.getMark().getMarkReceived();
        this.damage = enemy.getDamage();
        this.skull = enemy.getSkull();
        this.powerup =  enemy.getPowerupCardList().size();
        this.unloadedWeapon.clear();
        for(WeaponCard w : enemy.getWeaponCardList()) {
            if(!w.isReloaded()) this.unloadedWeapon.add(w);
        }
        this.loadedWeapon = enemy.getWeaponCardList().size() - unloadedWeapon.size();
        this.isFrenzyDeath = enemy.isFrenzyDeath();
    }

    /**
     * String only the main info of the enemy, representing the attributes with symbol and color.
     * Work with UTF-8 and ANSI code
     * @return A short representation of the enemy
     */
    public String toStringShort() {
        String s = nickname;
        if(character!=null) s += " - " + character.toString();
        s+="\n  Marks: ";
        for(Player p : mark) {
            s+= ConsoleColor.colorByColor(p.getCharacter().getColor()) + PLAYER.toString();
        }
        s+=ConsoleColor.RESET+ "\n  Damages: " + damage.size() + "/12 ";
        for(Player p : damage) {
            s+= ConsoleColor.colorByColor(p.getCharacter().getColor()) + PLAYER.toString();
        }
        s+=ConsoleColor.RESET;
        return s;
    }

    /**
     * String the full enemy, representing each attributes with symbol and color.
     * Work with UTF-8 and ANSI code
     * @return The representation of the enemy
     */
    @Override
    public String toString() {
        String s = toStringShort();
        s+=ConsoleColor.RESET+"\n  Skulls: ";
        if(isFrenzyDeath) s+="2 1 1 1 ";
        else s+="8 6 4 2 1 1 \t";
        for(int i=0; i<skull; i++) {
            s+= ConsoleColor.RED + SKULL.toString() +" "+ ConsoleColor.RESET;
        }
        s+="\n  Ammo: ";
        for(int i=0; i< ammo.getBlueAmmo(); i++) {
            s+= ConsoleColor.BLUE_BOLD_BRIGHT + AMMO.toString();
        }
        for(int i=0; i< ammo.getYellowAmmo(); i++) {
            s+= ConsoleColor.YELLOW_BOLD+ AMMO.toString();
        }
        for(int i=0; i< ammo.getRedAmmo(); i++) {
            s+= ConsoleColor.RED_BOLD_BRIGHT+ AMMO.toString();
        }
        s+= ConsoleColor.RESET + "\n  Weapons loaded: " + loadedWeapon;
        s+= "\n  Weapons unloaded: ";
        for(WeaponCard weapon : unloadedWeapon) {
            s+= weapon.getName();
            if(weapon.isReloaded()) s+="* ";
            else s+=" ";
        }
        s+="\n  Power ups: " + powerup;
        return s;
    }
}
