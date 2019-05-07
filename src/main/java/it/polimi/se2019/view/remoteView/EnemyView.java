package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class EnemyView implements Observer {

    @Override
    public void update(Observable o, Object arg) {

    }

    private String nickname;
    private Character character;
    private AmmoBag ammo;
    private ArrayList<WeaponCard> unloadedWeapon;
    private int loadedWeapon;
    private int skull;
    private ArrayList<Character> damage;
    private int powerup;

    public EnemyView(String nickname, Character character, AmmoBag ammo, ArrayList<WeaponCard> unloadedWeapon,
                     int loadedWeapon, int skull, ArrayList<Character> damage, int powerup) {
        this.nickname = nickname;
        this.character = character;
        this.ammo = ammo;
        this.unloadedWeapon = unloadedWeapon;
        this.loadedWeapon = loadedWeapon;
        this.skull = skull;
        this.damage = damage;
        this.powerup = powerup;
    }

    public AmmoBag getAmmo() {
        return ammo;
    }

    public ArrayList<Character> getDamage() {
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
}
