package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ModelViewMess.HandlerEnemyViewMessage;

import java.io.Serializable;
import java.util.ArrayList;
import it.polimi.se2019.model.Observable;
import java.util.Observer;

public class EnemyView extends Observable implements Observer, Serializable {

    private String nickname;
    private Character character;
    private AmmoBag ammo;
    private ArrayList<WeaponCard> unloadedWeapon = new ArrayList<>();
    private int loadedWeapon;
    private int skull;
    private ArrayList<Player> damage = new ArrayList<>();
    private int powerup;
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
        notifyObservers(message); //Forward message to client
    }

    public void handlePlayerMessage(Player enemy) {
        if(first) {
            this.nickname = enemy.getNickname();
            this.character = enemy.getCharacter();
            first=false;
        }
        this.ammo = enemy.getAmmo();
        this.damage = (ArrayList<Player>) enemy.getDamage();
        this.skull = enemy.getSkull();
        this.powerup =  enemy.getPowerupCardList().size();
        for(WeaponCard w : enemy.getWeaponCardList()) {
            if(!w.isReloaded()) this.unloadedWeapon.add(w);
        }
        this.loadedWeapon = enemy.getWeaponCardList().size() - unloadedWeapon.size();
    }
}
