package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.ui.Printable;
import it.polimi.se2019.view.ModelViewMess.HandlerEnemyViewMessage;

import java.io.Serializable;
import java.util.ArrayList;
import it.polimi.se2019.model.Observable;
import java.util.Observer;

public class EnemyView extends Observable implements Observer, Serializable, Printable {

    private int ID;
    private String nickname;
    private Character character;
    private AmmoBag ammo;
    private ArrayList<Player> mark = new ArrayList<>();
    private ArrayList<WeaponCard> unloadedWeapon = new ArrayList<>();
    private int loadedWeapon;
    private int skull;
    private ArrayList<Player> damage = new ArrayList<>();
    private int powerup;
    private boolean isFrenzyDeath;
    private boolean first = true;

    public EnemyView(String nickname) {
        this.nickname = nickname;
    }

    public AmmoBag getAmmo() {
        return ammo;
    }

    public ArrayList<Player> getDamage() {
        return damage;
    }

    public Character getCharacter() {
        return character;
    }

    public ArrayList<WeaponCard> getUnloadedWeapon() {
        return unloadedWeapon;
    }

    public int getLoadedWeapon() {
        return loadedWeapon;
    }

    public int getSkull() {
        return skull;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPowerup() {
        return powerup;
    }

    @Override
    public void update(java.util.Observable o/*Will be always null*/, Object arg) {
        HandlerEnemyViewMessage message = (HandlerEnemyViewMessage) arg;
        message.handleMessage(this);
        notifyObservers(message);
    }

    public void handlePlayerMessage(Player enemy) {
        if(first) {
            this.nickname = enemy.getNickname();
            this.ID = enemy.getID();
            this.character = enemy.getCharacter();
            first=false;
        }
        this.ammo = enemy.getAmmo();
        this.mark = (ArrayList) enemy.getMark().getMarkReceived();
        this.damage = (ArrayList<Player>) enemy.getDamage();
        this.skull = enemy.getSkull();
        this.powerup =  enemy.getPowerupCardList().size();
        for(WeaponCard w : enemy.getWeaponCardList()) {
            if(!w.isReloaded()) this.unloadedWeapon.add(w);
        }
        this.loadedWeapon = enemy.getWeaponCardList().size() - unloadedWeapon.size();
        this.isFrenzyDeath = enemy.isFrenzyDeath();
    }

    public String toStringShort() {
        String s = nickname;
        if(character!=null) s += " - " + character.toString();
        s+="\n  Marks: ";
        for(Player p : mark) {
            s+= ConsoleColor.colorByColor(p.getCharacter().getColor()) + "◉";
        }
        s+=ConsoleColor.RESET+ "\n  Damages: " + damage.size() + "/12 ";
        for(Player p : damage) {
            s+= ConsoleColor.colorByColor(p.getCharacter().getColor()) + "◉";
        }
        s+=ConsoleColor.RESET;
        return s;
    }

    @Override
    public String toString() {
        String s = toStringShort();
        s+=ConsoleColor.RESET+"\n  Skulls: ";
        if(isFrenzyDeath) s+="2 1 1 1 ";
        else s+="8 6 4 2 1 1 \t";
        for(int i=0; i<skull; i++) {
            s+= ConsoleColor.RED + " ☠" + ConsoleColor.RESET;
        }
        s+="\n  Ammo: ";
        for(int i=0; i< ammo.getBlueAmmo(); i++) {
            s+= ConsoleColor.BLUE_BOLD_BRIGHT + "✚";
        }
        for(int i=0; i< ammo.getYellowAmmo(); i++) {
            s+= ConsoleColor.YELLOW_BOLD+ "✚";
        }
        for(int i=0; i< ammo.getRedAmmo(); i++) {
            s+= ConsoleColor.RED_BOLD_BRIGHT+ "✚";
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


    public int getID() {
        return ID;
    }

    @Override
    public String printRow(int row) {
        String s = ConsoleColor.RESET.toString();
        switch(row) {
            case 0:
                s+= nickname;
                if(character!=null) s += " - " + character.toString();
                break;
            case 1:
                s+="  Marks: ";
                for(Player p : mark) {
                    s += ConsoleColor.colorByColor(p.getCharacter().getColor()) + "◉";
                }
                break;
            case 2:
                s+="  Damages: " + damage.size() + "/12 ";
                for(Player p : damage) {
                    s+= ConsoleColor.colorByColor(p.getCharacter().getColor()) + "◉";
                }
                break;
            case 3:
                s+="  Skulls: ";
                if(isFrenzyDeath) s+="2 1 1 1 ";
                else s+="8 6 4 2 1 1 \t";
                for(int j=0; j<skull; j++) {
                    s += ConsoleColor.RED + " ☠" + ConsoleColor.RESET;
                }
                break;
        }
        return s;
    }
}
