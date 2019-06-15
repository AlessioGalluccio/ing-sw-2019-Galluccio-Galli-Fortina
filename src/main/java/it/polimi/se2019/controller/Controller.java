package it.polimi.se2019.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;


public class Controller implements Observer {
    private Player playerAuthor;
    private PlayerView playerView;
    private ArrayList<StringAndMessage> messageListExpected;
    private int indexExpected = 0;
    private final GameHandler gameHandler;
    private StateController state;
    private int numOfActionTaken;
    public int numOfMaxActions = 2;

    private String stringToPlayerView;

    public Controller(GameHandler gameHandler, Player playerAuthor, PlayerView playerView) {
        this.gameHandler = gameHandler;
        this.messageListExpected = new ArrayList<>();
        this.numOfActionTaken = 0;
        this.playerAuthor = playerAuthor;
        this.state = new NotYourTurnState(this, gameHandler);
    }

    /////////////////GETTERS

    /**
     * set the author of the messages received
     * @param author the Player author
     */
    public void setAuthor(Player author){
        //TODO fai in modo che Controller lo setti davvero!
        this.playerAuthor = author;
    }

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


    public ArrayList<StringAndMessage> getCopyMessageListExpected() {
        Gson gson = new Gson();

        Type TYPE = new TypeToken<ArrayList<StringAndMessage>>() {
        }.getType();

        return gson.fromJson(gson.toJson(this.messageListExpected, TYPE), TYPE);
    }

    public int getIndexExpected() {
        return indexExpected;
    }




    /////////////////////SETTERS



    public StateController getState() {
        return state;
    }

    public int getNumOfActionTaken() {
        return numOfActionTaken;
    }

    public void setPlayerView(PlayerView playerView) {
        this.playerView = playerView;
    }






    public void setMessageListExpected(ArrayList<StringAndMessage> messageListExpected) {
        this.messageListExpected = messageListExpected;
    }


    public void addMessageListExpected(List<StringAndMessage> arg) {
        this.messageListExpected.addAll(arg);
    }

    public void addMessageListExpected(StringAndMessage arg) {
        this.messageListExpected.add(arg);
    }

    public void addMessageListImmediateNext(List<StringAndMessage> messageListExpected){
        if(indexExpected != this.messageListExpected.size() - 1){
            this.messageListExpected.addAll(indexExpected , messageListExpected);
        }
        else{
            this.messageListExpected.addAll(messageListExpected);
        }

    }

    public void addMessageListImmediateNext(StringAndMessage messageExpected){
        if(indexExpected != this.messageListExpected.size() - 1){
            this.messageListExpected.add(indexExpected , messageExpected);
        }
        else{
            this.messageListExpected.add(messageExpected);
        }
    }

    public void addMessageListBeforeLastOne(List<StringAndMessage> messageList){
        this.messageListExpected.addAll(this.messageListExpected.size()- 1, messageList);
    }

    public void addMessageListBeforeLastOne(StringAndMessage messageList){
        this.messageListExpected.add(this.messageListExpected.size()- 1, messageList);
    }

    public void addReceived() {
        this.indexExpected++;
    }

    /*
    public void removeReceived(){
        if(indexExpected > 0){
            this.indexExpected--;
        }
    }
     */

    public void setIndexExpected(int indexExpected) {
        this.indexExpected = indexExpected;
    }

    public void setState(StateController state) {
        this.state = state;
    }

    public void setNumOfActionTaken(int numOfActionTaken) {
        this.numOfActionTaken = numOfActionTaken;
    }

    /**
     * change the number of max actions per turn. The default is 2
     * @param numOfMaxActions
     */
    public void setNumOfMaxActions(int numOfMaxActions) {
        this.numOfMaxActions = numOfMaxActions;
    }

    //methods

    @Override
    public void update(java.util.Observable o /*will be always NULL*/, Object arg) {
        try {
            stringToPlayerView = state.handle((ViewControllerMessage) arg);
            playerView.printFromController(stringToPlayerView);
        }catch (Exception e) {
            System.err.println("Controller exception: " + e.toString());
            e.printStackTrace();
        }


    }



    /**
     * It verifies the list of messages and, if it is correct, it modifies the model accordingly
     * @param actionMessage the first message in the list. It defines what type of action
     */
    public synchronized void modifyModel(ActionMessage actionMessage) {
        //TODO verify()
        //gameHandler.getActionByID(actionMessage.getActionID(), gameHandler.getPlayerByID(actionMessage.getAuthorID()))
        //       .executeAction(gameHandler.getPlayerByID(actionMessage.getAuthorID()), messageListReceived);
        //Commentato perchè non compilava
    }

    public synchronized void modifyModel(PowerupCard powerupCard) {
        //TODO
    }

    /**
     * general case, should not be used
     * @param message
     */
    public synchronized void modifyModel(ViewControllerMessage message) {
        //TODO scrivi eccezione!
    }


    public PlayerView getPlayerView() {
        return playerView;
    }

    /**
     * it empties the messages after Model is modified
     */
    public void resetMessages() {
        this.messageListExpected = new ArrayList<>();
        indexExpected = 0;
    }

}
