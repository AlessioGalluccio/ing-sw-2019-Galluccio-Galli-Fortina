package it.polimi.se2019.model.handler;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.NoSuchCellException;
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
        this.weaponDeck = new WeaponDeck();
        this.powerupDeck = new PowerupDeck();
        this.ammoDeck = new AmmoDeck(powerupDeck);
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
     * @throws NoSuchPlayerException if there is no player with such id
     */
    public Player getPlayerByID(int id) {
        for(Player p : orderPlayerList) {
            if (p.getID() == id) return p;
        }
        //throw new NoSuchPlayerException();
        return null;
    }

    /**
     *
     * @param x coordinate x of the Cell
     * @param y coordinate x of the Cell
     * @return the Cell which has x and y coordinates
     */
    public Cell getCellByCoordinate(int x, int y){
        try {
            return map.getCellByCoo(x, y);
        } catch (NoSuchCellException e) {
            //TODO
        }
        return null;
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

    /**
     * Return the firemode corresponding to the id
     * @param fireModeID FireMode's id
     * @return the firemode with that id
     * @throws NoSuchFireModeException if there's not a fireMode with that id
     */
    public FireMode getFireModeByID(int fireModeID) {
        try {
            //FireMode id are written as WeaponID + id
            //So if weaponID is 17, his fireModeID is 171 or 172
            //172 / 10 = 17, the weaponID
            WeaponCard weapon = weaponDeck.getCardById(fireModeID/10);
            for(FireMode fm : weapon.getFireMode())
                if(fm.getID()==fireModeID) return fm;
        } catch (NoSuchCardException e) {
            //TODO
        } finally {
            //throw new NoSuchFireModeException();
        }
        return null;
    }

    /**
     * Return the card with that id
     * @param cardId card's id
     * @return the card with that id
     * @throws NoSuchCardException if there's not a card with that id
     */
    public AmmoConvertibleCard getAmmoCardByID(int cardId) throws NoSuchCardException {
        return ammoDeck.getCardById(cardId);
    }

    /**
     * Return the card with that id
     * @param cardId card's id
     * @return the card with that id
     * @throws NoSuchCardException if there's not a card with that id
     */
    public PowerupCard getPowrupCardByID(int cardId) throws NoSuchCardException {
        return powerupDeck.getCardById(cardId);
    }

    /**
     * Return the card with that id
     * @param cardId card's id
     * @return the card with that id
     * @throws NoSuchCardException if there's not a card with that id
     */
    public WeaponCard getWeaponCardByID(int cardId) throws NoSuchCardException {
        return weaponDeck.getCardById(cardId);
    }

    public Map getMap() {return map;}
}
