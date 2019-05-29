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
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Observer;


public class Controller implements Observer {

    private Player player; //TODO aggiungere al costruttore (anche a tutti gli stati!)
    private PlayerView playerView; //TODO aggiungere al costruttore
    private ArrayList<ViewControllerMessage> messageListReceived;
    private ArrayList<StringAndMessage> messageListExpected;
    private int indexExpected = 0;
    private final GameHandler gameHandler;
    private StateController state;
    private int numOfActionTaken;
    public final int MAX_NUM_OF_ACTION = 2;

    public Controller(GameHandler gameHandler) {
        //TODO aggiungere player e playerView (anche a tutti gli stati!)
        this.gameHandler = gameHandler;
        this.messageListReceived = new ArrayList<>();
        this.messageListExpected = new ArrayList<>();
        this.numOfActionTaken = 0;
        this.state = new NotYourTurnState(this, gameHandler);
    }

    //getter and Setters

    public ArrayList<ViewControllerMessage> getCopyMessageListReceived() {
        GsonBuilder g = new GsonBuilder()
                .registerTypeAdapter(ViewControllerMessage.class, new JsonAdapter<ViewControllerMessage>())
                .registerTypeAdapter(Target.class, new JsonAdapter<Target>());
        Gson gson = g.create();

        Type TYPE = new TypeToken<ArrayList<ViewControllerMessage>>() {
        }.getType();

        return gson.fromJson(gson.toJson(messageListReceived, TYPE), TYPE);
    }

    public ArrayList<StringAndMessage> getCopyMessageListExpected() {
        Gson gson = new Gson();

        Type TYPE = new TypeToken<ArrayList<StringAndMessage>>() {
        }.getType();

        return gson.fromJson(gson.toJson(this.messageListExpected, TYPE), TYPE);
    }


    public ViewControllerMessage getLastReceivedMessage(){
        //TODO da controllare in casi particolari
        int last = messageListReceived.size();
        return messageListReceived.get(last);
    }

    public void removeLastReceivedMessage(){
        int last = messageListExpected.size();
        messageListExpected.remove(last);
    }

    public int getIndexExpected() {
        return indexExpected;
    }

    public StateController getState() {
        return state;
    }

    public int getNumOfActionTaken() {
        return numOfActionTaken;
    }

    public void setMessageListReceived(ArrayList<ViewControllerMessage> messageListReceived) {
        this.messageListReceived = messageListReceived;
    }

    public void addMessageListReceived(ViewControllerMessage arg) {
        this.messageListReceived.add(arg);
    }

    public void setMessageListExpected(ArrayList<StringAndMessage> messageListExpected) {
        this.messageListExpected = messageListExpected;
    }

    public void addMessageListExpected(StringAndMessage arg) {
        this.messageListExpected.add(arg);
    }

    public void setIndexExpected(int indexExpected) {
        this.indexExpected = indexExpected;
    }

    public void setState(StateController state) {
        this.state = state;
    }

    public void setNumOfActionTaken(int numOfActionTaken) {
        this.numOfActionTaken = numOfActionTaken;
    }


    //methods

    @Override
    public void update(java.util.Observable o /*will be always NULL*/, Object arg) {
        try {
            state.handle((ViewControllerMessage) arg);
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

    public Player getPlayer() {
        return player;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    /**
     * it empties the messages after Model is modified
     */
    public void flushMessages() {
        messageListReceived = new ArrayList<>();
        this.messageListExpected = new ArrayList<>();
        indexExpected = 0;
    }

    /**
     * it empties only the messages of the optional firemode
     */
    public void flushOptionalMessages() {
        //TODO
    }


}
