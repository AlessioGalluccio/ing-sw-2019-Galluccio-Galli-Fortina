package it.polimi.se2019.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.se2019.model.handler.GameHandler.getPlayerByID;

public class Controller implements Observer {

    private ArrayList<ViewControllerMessage> messageListReceived;
    private ArrayList<StringAndMessage> messageListExpected;
    private int indexExpected = 0;
    private final GameHandler gameHandler;
    private StateController state;
    private int numOfActionTaken;
    public final int MAX_NUM_OF_ACTION = 2;

    public Controller(GameHandler gameHandler) {
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
    public void update(Observable o, Object arg) {
        try {
            state.handle(arg);
        }catch (Exception e) {
            System.err.println("Controller exception: " + e.toString());
            e.printStackTrace();
        }


    }

    /*

    public void handleMessage(Object arg) {
        //TODO lancia eccezione, è un tipo di messaggio non aspettato
    }

    public void handleMessage(PlayerViewMessage arg) {
        //TODO

        //new Message
        if(messageListExpected.isEmpty() && arg.getMessageID() == Identificator.ACTION_MESSAGE) {
            messageListReceived.add(arg);
            messageListExpected = (gameHandler.getActionByID(arg.getMessageID()).getStringAndMessageExpected());
        }


        //middle cases
        else if(arg.getMessageID() == messageListExpected.get(indexExpected).getMessage().getMessageID()) {
            if(arg.getMessageID() == Identificator.FIRE_MODE_MESSAGE) {
                sendTargetsToView(arg);
            }
            if(arg.getMessageID() == Identificator.NEWTON_MESSAGE) {
                sendTargetsToView(arg);
            }
            if(arg.getMessageID() == Identificator.TAGBACK_GRANADE_MESSAGE) {
                sendTargetsToView(arg);
            }

            messageListReceived.add(arg);
            indexExpected++;
        }

        //NOPE message
        else if(arg.getMessageID() == Identificator.NOPE_MESSAGE) {
            if (messageListExpected.get(indexExpected).isOptional()) {
                indexExpected++;
            }
            else {
                //TODO messaggio da stampare alla view
                arg.getAuthorView().printFromController("Warning, this action is required");
            }
        }

        //UNDO message
        //else if(arg.getMessageID() == Identificator.UNDO_MESSAGE) {
            //TODO da vedere se ha senso di esistere!
            //indexExpected--;
            //messageListReceived.remove(indexExpected);
        //}

        //End of series of messages
        else if(messageListExpected.size() == indexExpected) {
            //TODO inserire modifyModel
            //modifyModel();

            flushMessages();
        }

        //Message is wrong, an error massage is printed in the view of the player
        else {
            arg.getAuthorView().printFromController(messageListExpected.get(indexExpected).getString());
        }

    }

    /*

    /**
     * command the model to send to the view the possible targets of this firemode
     * @param arg the message of this firemode
     */
    public synchronized void sendTargetsToView(FireModeMessage arg) {
        Player playerTemp = getPlayerByID(arg.getAuthorID());
        AmmoBag ammoTemp = playerTemp.getAmmo();
        FireMode fireModeTemp = gameHandler.getFireModeByID(arg.getAuthorID());
        //TODO metodo che compara fireModeTemp.getCost() con ammoTemp. Se non bastano, chiedono a giocatore di scartare un potenziamento se ce l'ha

        //TODO fireModeTemp.sendPossibleTargetsToView(arg.getAuthorView);
    }

    /**
     * command the model to send to the view the possible targets of this tagbackGranate card
     * @param arg the message of this tagbackGranade card
     */
    public synchronized void sendTargetsToView(TagbackGranateMessage arg) {
        Player author = getPlayerByID(arg.getAuthorID());
        TagbackGranedCard card = arg.getUsedCard();
        card.sendPossibleTarget(author, arg.getAuthorView());
        //TODO
    }

    /**
     * command the model to send to the view the possible targets of this Newton card
     * @param arg the message of this Newton card
     */
    public synchronized void sendTargetsToView(NewtonMessage arg) {
        Player author = getPlayerByID(arg.getAuthorID());
        NewtonCard card = arg.getUsedCard();
        card.sendPossibleTarget(author, arg.getAuthorView());
        //TODO
    }

    /**
     * overloading has failed, send message error
     * @param arg the wrong type of message
     */
    public synchronized void sendTargetsToView(PlayerViewMessage arg) {
        //TODO eccezione messaggio inaspettato
    }


    /**
     * It verififies the list of messages and, if it is correct, it modifies the model accordingly
     * @param actionMessage the first message in the list. It defines what type of action
     */
    public synchronized void modifyModel(ActionMessage actionMessage) {
        //TODO verify()
        gameHandler.getActionByID(actionMessage.getActionID()).executeAction(getPlayerByID(actionMessage.getAuthorID()), messageListReceived);

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
