package it.polimi.se2019.model.handler;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.Room;
import it.polimi.se2019.model.player.Action;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;

public class GameHandler extends java.util.Observable {

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

    /**
     * controlls if the game is finished
     * @return  1 if the game is finished
     *          0 if not
     */
    public boolean isFinished(){

        return false; //TODO implementare
    }

    /**
     * Called when a player has finished his turn
     */
    public void nextTurn(){
        //TODO notify();
    }

    /**
     * Called at the end of the game
     */
    public void finishGame(){

    }

    /**
     *
     * @return ID of the Player of the current turn
     */
    public int getTurnPlayerID() {

        return 0; //TODO implementare
    }

    /**
     *
     * @param id ID of a Player
     * @return the Player object who has this ID
     */
    public Player getPlayerByID(int id) {

        return null; //TODO implementare
    }

    /**
     *
     * @param x coordinate x of the Cell
     * @param y coordinate x of the Cell
     * @return the Cell which has x and y coordinates
     */
    public Cell getCellByCoordinate(int x, int y) {

        return null; //TODO implementare
    }

    /**
     *
     * @param colorRoom color of the Room
     * @return the Room object that has that color
     */
    public Room getRoomByID(String colorRoom) {

        return null; //TODO implementare
    }

    /**
     * It generates an Action object with reference to this GameHandler
     * @param actionID the int ID of the action
     * @return
     */
    public Action getActionByID(int actionID) {
        //return action = Grab(this)

        return null; //TODO implementare
    }

    public FireMode getFireModeByID(int fireModeID){
        return null; //TODO implementare
    }
}
