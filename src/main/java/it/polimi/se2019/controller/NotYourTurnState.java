package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;
import it.polimi.se2019.view.ViewControllerMess.*;

public class NotYourTurnState extends StateController {

    private Player playerAuthor;
    private String errorString;
    private String stringToPlayerView;
    boolean neededTargetForTagBack = false;
    private TagbackGrenadeCard tagbackGrenadeCard;

    public final static String NOT_YOUR_TURN_RESPONSE = "Please, wait your turn. ";
    public final static String SELECT_TARGET_FOR_TAGBACK = "Select a target player for TagBack. ";
    public final static String NO_TARGETS_FOR_TAGBACK= "There are no targets for TagBack. ";
    public final static String IS_NOT_TARGET_FOR_TARGETING = "This can't be a target for TagBack. ";
    public final static String DONT_HAVE_THIS_CARD = "You don't have this TagBack. ";


    /**
     * constructor
     * @param controller the controller of the player
     * @param gameHandler the gamehandler of the match
     * @param passTurn true if the turn must be passed, false if not
     */
    public NotYourTurnState(Controller controller, GameHandler gameHandler, boolean passTurn) {
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
                    && playerAuthor.getTargetsForTagBack().contains(target.getID())){
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
        boolean isCardPresent = false;
        for(PowerupCard card :playerAuthor.getPowerupCardList()){
            if(card.getID() == usedCard.getID()){
                isCardPresent = true;
            }
        }
        if(!isCardPresent){
            errorString = DONT_HAVE_THIS_CARD;
        }
        if(!playerAuthor.getTargetsForTagBack().isEmpty()){
            boolean canUse = false;
            for(Integer targetID : playerAuthor.getTargetsForTagBack()){
                Player target = gameHandler.getPlayerByID(targetID);
                if(target != null && target.isVisibleBy(gameHandler.getMap(), playerAuthor)){
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
        arg.handle(this);

        if(errorString != null){
            return errorString;
        }
        else if(neededTargetForTagBack){
            return SELECT_TARGET_FOR_TAGBACK;
        }
        else {
            return NOT_YOUR_TURN_RESPONSE;
        }

    }

    @Override
    public void handleReconnection(boolean isConnected) {
        connectionDontPassTurn(isConnected);
    }

    /**
     * handles the invalid messages for this State
     */
    private void cantDoThisHandler(){
        //controller.removeReceived();
    }

}
