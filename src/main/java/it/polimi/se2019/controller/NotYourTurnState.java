package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;
import it.polimi.se2019.view.ViewControllerMess.*;

/**
 * @author Galluccio
 */
public class NotYourTurnState extends StateController {

    private Player playerAuthor;
    private String errorString;
    boolean neededTargetForTagBack = false;
    private int tagbackGrenadeCardID;

    static final String NOT_YOUR_TURN_RESPONSE = "Please, wait your turn. ";
    static final String SELECT_TARGET_FOR_TAGBACK = "Select a target player for TagBack. ";
    static final String NO_TARGETS_FOR_TAGBACK= "There are no targets for TagBack. ";
    static final String IS_NOT_TARGET_FOR_TARGETING = "This can't be a target for TagBack. ";
    static final String DONT_HAVE_THIS_CARD = "You don't have this TagBack. ";


    /**
     * constructor
     * @param controller the controller of the player
     * @param gameHandler the gamehandler of the match
     */
    public NotYourTurnState(Controller controller, GameHandler gameHandler) {
        super(controller, gameHandler);
        //we do a next turn when we create this state if passTurn is true
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
                    playerAuthor.discardCard(gameHandler.getPowerupCardByID(tagbackGrenadeCardID), true);
                }catch (TooManyException e){
                    //do nothing, you wasted the powerup bacause target can't have more marks
                } catch (NotPresentException e) {
                    //do nothing, shouldn't happen becuase it's a card you have
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
                this.tagbackGrenadeCardID = usedCard.getID();
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
