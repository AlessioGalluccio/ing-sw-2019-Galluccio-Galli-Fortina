package it.polimi.se2019.model.handler;

import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.PointDeck;
import it.polimi.se2019.model.deck.PowerupDeck;
import it.polimi.se2019.model.deck.WeaponDeck;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;

public class GameHandler {

    private AmmoDeck ammoDeck;
    private PowerupDeck powerupDeck;
    private PointDeck pointDeck;
    private WeaponDeck weaponDeck;
    private ArrayList<Player> orderPlayerList;
    private int turn;
    private Death death;
    private Modality mode;


    public GameHandler() {
    }

    public boolean isFinished(){

    }

    public void nextTurn(){

    }

    public void finishGame(){

    }
}
