package it.polimi.se2019.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.socket.SocketHandler;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Galluccio
 */
public class Controller implements Observer {
    private Player playerAuthor;
    private PlayerView playerView;
    private ArrayList<StringAndMessage> messageListExpected;
    private int indexExpected = 0;
    private final GameHandler gameHandler;
    private StateController state;
    private int numOfActionTaken;
    private int numOfMaxActions = 2;

    /**
     * constructor
     * @param gameHandler the GameHandler of the match
     * @param playerAuthor the author Player of this Controller
     * @param playerView the PlayerView of the author player
     */
    public Controller(GameHandler gameHandler, Player playerAuthor, PlayerView playerView) {
        this.gameHandler = gameHandler;
        this.messageListExpected = new ArrayList<>();
        this.numOfActionTaken = 0;
        this.playerAuthor = playerAuthor;
        this.state = new NotYourTurnState(this, gameHandler);
        this.playerView=playerView;
    }

    /////////////////GETTERS



    /**
     * returns the author of the messages
     * @return the Player author
     */
    public Player getAuthor(){
        return this.playerAuthor;
    }

    /**
     * get the number of max action per turn. It can change in come modalities
     * @return int number of max action per turn
     */
    public int getNumOfMaxActions() {
        return numOfMaxActions;
    }

    /**
     * get a copy of the list of the requests to the user and the messages expected from user
     * @return an ArrayList of StringAndMessage of requests and expected messages
     */
    public ArrayList<StringAndMessage> getCopyMessageListExpected() {
        Gson gson = new Gson();

        Type TYPE = new TypeToken<ArrayList<StringAndMessage>>() {
        }.getType();

        return gson.fromJson(gson.toJson(this.messageListExpected, TYPE), TYPE);
    }

    /**
     * the the index of the message expected
     * @return the int index of the message expected
     */
    public int getIndexExpected() {
        return indexExpected;
    }

    /**
     * get the State of the controller
     * @return the State of the controller
     */
    public StateController getState() {
        return state;
    }

    /**
     * get the number of the actions took by the player in the current turn
     * @return the number of the actions took by the player in this turn
     */
    public int getNumOfActionTaken() {
        return numOfActionTaken;
    }


    /////////////////////SETTERS

    /**
     * set the author of the messages received
     * @param author the Player author
     */
    public void setAuthor(Player author){
        this.playerAuthor = author;
    }

    /**
     * set the PlayerView of the author
     * @param playerView the PlayerView
     */
    public void setPlayerView(PlayerView playerView) {
        this.playerView = playerView;
    }

    /**
     * add a list of messages expected at the end of the list
     * @param arg the Listt of StringAndMessage
     */
    public void addMessageListExpected(List<StringAndMessage> arg) {
        this.messageListExpected.addAll(arg);
    }

    /**
     * add a message expected at the end of the list
     * @param arg the StringAndMessage
     */
    public void addMessageListExpected(StringAndMessage arg) {
        this.messageListExpected.add(arg);
    }

    /**
     * add a List of messages expected that will become the expected ones (in sequence)
     * @param messageListExpected the List of messages expected
     */
    public void addMessageListImmediateNext(List<StringAndMessage> messageListExpected){
        this.messageListExpected.addAll(indexExpected , messageListExpected);
    }

    /**
     * add a message expected that will become the expected one
     * @param messageExpected the message expected
     */
    public void addMessageListImmediateNext(StringAndMessage messageExpected){
        this.messageListExpected.add(indexExpected , messageExpected);
    }

    /**
     * add a List of expected messages before the last one
     * @param messageList the List of expected messages
     */
    public void addMessageListBeforeLastOne(List<StringAndMessage> messageList){
        if(messageList.isEmpty()){
            //do nothing
        }
        if(this.messageListExpected.isEmpty()){
            this.messageListExpected.addAll(messageList);
        }
        else{
            this.messageListExpected.addAll(this.messageListExpected.size()- 1, messageList);
        }

    }

    /**
     * call it when a message expected has arrived and it's valid. It will move the expectedIndex on the next one
     */
    public void addReceived() {
        this.indexExpected++;
    }

    /**
     * set the State of the Controller
     * @param state the new State
     */
    public void setState(StateController state) {
        this.state = state;
    }

    /**
     * set num of actions taken
     * @param numOfActionTaken the nume of actions
     */
    public void setNumOfActionTaken(int numOfActionTaken) {
        this.numOfActionTaken = numOfActionTaken;
    }

    /**
     * add a new action taken. Call it at the end of an action
     */
    public void addNumOfActionTaken() {
        this.numOfActionTaken++;
    }

    /**
     * change the number of max actions per turn. The default is 2
     * @param numOfMaxActions integer of max actions per turn
     */
    public void setNumOfMaxActions(int numOfMaxActions) {
        this.numOfMaxActions = numOfMaxActions;
    }

    @Override
    public synchronized void update(java.util.Observable o /*will be always NULL*/, Object arg) {
        try {
            String stringToPlayerView = state.handle((ViewControllerMessage) arg);
            if(stringToPlayerView != null){ //otherwise states that are killed would print null
                playerView.printFromController(stringToPlayerView);
            }
        }catch (Exception e) {
            Logger.getLogger(Controller.class.getName()).log(Level.WARNING, "Can't handle properly a message", e);
        }
    }

    /**
     * get the playerView linked to the player of this Controller
     * @return the PlayerView of the player of this controller
     */
    public PlayerView getPlayerView() {
        return playerView;
    }

    /**
     * it empties the messages after Model is modified
     */
    void resetMessages() {
        this.messageListExpected = new ArrayList<>();
        indexExpected = 0;
    }

}
