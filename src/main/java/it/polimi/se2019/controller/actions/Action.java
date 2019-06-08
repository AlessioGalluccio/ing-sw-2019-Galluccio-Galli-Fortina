package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.EmptyControllerState;
import it.polimi.se2019.controller.NotYourTurnState;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.WeaponIsLoadedException;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public abstract class Action implements AddActionMethods {
    protected GameHandler gameHandler;
    protected ArrayList<StringAndMessage> correctMessages;
    protected Player playerAuthor;
    protected PlayerView playerView;
    protected Controller controller;

    public Action(GameHandler gameHandler, Controller controller) {
        this.gameHandler = gameHandler;
        this.controller = controller;
        this.playerAuthor = controller.getAuthor();
        this.playerView = controller.getPlayerView();
    }

    public void executeAction() throws WrongInputException{

    }
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        return correctMessages;
    }

    public boolean verifyCorrectMessages(Player author, ArrayList<ViewControllerMessage> msg) {

        return false; //TODO implementare
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public Player getPlayerAuthor() {
        return playerAuthor;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public void addNope() throws WrongInputException {
        endAction();
    }

    /**
     * call it at the end of the action. It will change the state of the controller
     */
    public void endAction(){
        //TODO da controllare pesantemente
        controller.setNumOfActionTaken(controller.getNumOfActionTaken() + 1);
        if(controller.getNumOfActionTaken() == controller.getNumOfMaxActions()){
            controller.setState(new NotYourTurnState(controller, gameHandler));
            gameHandler.nextTurn();
        }
        else{
            controller.setState(new EmptyControllerState(controller, gameHandler));
        }

    }
}
