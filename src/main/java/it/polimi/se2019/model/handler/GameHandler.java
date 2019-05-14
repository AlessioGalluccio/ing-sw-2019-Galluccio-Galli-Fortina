package it.polimi.se2019.model.handler;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Room;
import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public class GameHandler extends java.util.Observable {

    private AmmoDeck ammoDeck;
    private PowerupDeck powerupDeck;
    private PointDeck pointDeck;
    private WeaponDeck weaponDeck;
    private Map map;
    private ArrayList<Player> orderPlayerList;
    private ArrayList<PlayerView> playerViews;
    private int turn;
    private ArrayList<Death> arrayDeath;
    private Modality modality;

    //Implementato SOLO PER TESTARE getPlayerByID()
    //Usato in TestPlayer -> testReceiveMark() per getMarkReveived e getMarkDone della classe Mark
    //TODO Da fare decenetemente
    //TODO sistemare gestione degli ID
    public GameHandler(ArrayList<Player> list) {
        this.orderPlayerList = list;
        this.turn = 0;
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
        //TODO gestione delle morti
        Player player = orderPlayerList.get(turn);
        player.endTurnSetting();
        if(turn == orderPlayerList.size()){
            turn = 0;
        }
        else{
            turn++;
        }
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
        Player playerOfTurn = orderPlayerList.get(turn);
        return playerOfTurn.getID();
    }

    /**
     *
     * @param id ID of a Player
     * @return the Player object who has this ID
     */
    public Player getPlayerByID(int id) {
        for(Player p : orderPlayerList) {
            if (p.getID() == id) return p;
        }

        //If there's not a player whit that id: return null
        //TODO lanciare eccezione se non c'è il player!
        return null;
    }

    /**
     *
     * @param x coordinate x of the Cell
     * @param y coordinate x of the Cell
     * @return the Cell which has x and y coordinates
     */
    public Cell getCellByCoordinate(int x, int y) {

        return map.getCellByCoo(x, y);
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
    public Action getActionByID(int actionID, Player author) {
        return modality.getActionByID(actionID, author, this);

    }

    /**
     * return all the players of the game
     * @return all the players in order
     */
    public  ArrayList<Player> getOrderPlayerList(){
        //TODO fai copia
        return orderPlayerList;
    }

    public static FireMode getFireModeByID(int fireModeID){
        return null; //TODO implementare
    }

    public Map getMap() {return map;}
}
