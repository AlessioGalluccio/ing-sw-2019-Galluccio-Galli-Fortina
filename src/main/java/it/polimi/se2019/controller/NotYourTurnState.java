package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;
import it.polimi.se2019.view.ViewControllerMess.*;

public class NotYourTurnState extends StateController {

    private Player playerAuthor;
    private String errorString;
    private String stringToPlayerView;
    boolean neededTargetForTagBack = false;
    private TagbackGrenadeCard tagbackGrenadeCard;

    public final String NOT_YOUR_TURN_RESPONSE = "Please, wait your turn";
    public final String SELECT_TARGET_FOR_TAGBACK = "Select a target player for TagBack. ";
    public final String NO_TARGETS_FOR_TAGBACK= "There are no targets for TagBack. ";
    public final String IS_NOT_TARGET_FOR_TARGETING = "This can't be a target for TagBack. ";


    public NotYourTurnState(Controller controller, GameHandler gameHandler, boolean passTurn) {
        //TODO aggiungere playerAuthor e playerView (anche a tutti gli stati!)
        super(controller, gameHandler);
        //we do a next turn when we create this state if passTurn is true
        if(passTurn){
            gameHandler.nextTurn();
        }
        controller.setNumOfActionTaken(0);
        controller.resetMessages();
        this.playerAuthor = controller.getAuthor();
    }

    @Override
    public void handleAction(int actionID) {
        cantDoThisHandler();
    }

    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        cantDoThisHandler();

    }

    @Override
    public void handleFiremode(int firemodeID) {
        cantDoThisHandler();

    }


    @Override
    public void handleNewton(NewtonCard usedCard) {
        cantDoThisHandler();

    }

    @Override
    public void handleNope() {
        cantDoThisHandler();

    }

    @Override
    public void handleOptional(int numOptional) {
        cantDoThisHandler();
    }

    @Override
    public void handlePlayer(int playerID) {
        Player target = gameHandler.getPlayerByID(playerID);

        if(neededTargetForTagBack){
            if(target.isVisibleBy(gameHandler.getMap(),playerAuthor)
                    && playerAuthor.getTargetsForTagBack().contains(playerAuthor)){
                try{
                    target.receiveMarkBy(playerAuthor);
                }catch (TooManyException e){
                    //do nothing, you wasted the powerup
                }
                neededTargetForTagBack = false;
            }
            else{
                errorString = IS_NOT_TARGET_FOR_TARGETING;
            }
        }
        else{
            cantDoThisHandler();
        }

    }

    @Override
    public void handleReload(int weaponID) {
        cantDoThisHandler();
    }

    @Override
    public void handleTagback(TagbackGrenadeCard usedCard) {
        if(!playerAuthor.getTargetsForTagBack().isEmpty()){
            boolean canUse = false;
            for(Player target : playerAuthor.getTargetsForTagBack()){
                if(target.isVisibleBy(gameHandler.getMap(), playerAuthor)){
                    canUse = true;
                }
            }
            if(canUse){
                neededTargetForTagBack = true;
                this.tagbackGrenadeCard = usedCard;
            }
            else{
                errorString = NO_TARGETS_FOR_TAGBACK;
            }
        }
        else{
            errorString = NO_TARGETS_FOR_TAGBACK;
        }

    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
        cantDoThisHandler();

    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        cantDoThisHandler();
    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        cantDoThisHandler();
    }

    @Override
    public void handlePassTurn() {
        cantDoThisHandler();
    }

    @Override
    public void handleFire() {
        cantDoThisHandler();
    }
    

    @Override
    public void handleDiscardPowerup(int powerupID) {
        cantDoThisHandler();
    }

    @Override
    public void handleDiscardWeapon(int weaponID) {
        cantDoThisHandler();
    }


    @Override
    public String handle(ViewControllerMessage arg) {
        errorString = null;
        //controlls if it's the turn of the playerAuthor. If it is, it changes the state and it passes the message to the new state
        int IDPlayer = arg.getAuthorID();
        if(IDPlayer == gameHandler.getTurnPlayerID()) {
            StateController nextState = new EmptyControllerState(controller, gameHandler);
            nextState.handle(arg);
            controller.setState(nextState);
        }

        else {
            controller.addReceived();
            arg.handle(this);
        }

        if(errorString != null){
            stringToPlayerView = errorString;
        }
        else if(neededTargetForTagBack){
            stringToPlayerView = SELECT_TARGET_FOR_TAGBACK;
        }
        else {
            stringToPlayerView = NOT_YOUR_TURN_RESPONSE;
        }
        return stringToPlayerView;

    }

    @Override
    public void handleReconnection(boolean isConnected) {
        connectionDontPassTurn(isConnected);
    }

    @Override
    public void endAction() {
        //do nothing, shouldn't be called in this state
    }

    private void cantDoThisHandler(){
        //controller.removeReceived();
    }

}
