package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.ViewControllerMess.*;

/**
 * @author Galluccio
 */
public class EmptyControllerState extends StateController {

    private Player player;
    private String errorString;

    /**
     * constructor
     * @param controller the controller of the player
     * @param gameHandler the gamehnalder of the match
     */
    public EmptyControllerState(Controller controller, GameHandler gameHandler) {
        super(controller, gameHandler);
        this.player = controller.getAuthor();
        this.player.resetTargetsForTagBack(); //for tagBack grenade
        controller.resetMessages();
        controller.getPlayerView().printFromController(SELECT_ACTION_REQUEST);
    }

    @Override
    public void handleAction(int actionID) {
        if(controller.getNumOfActionTaken() < controller.getNumOfMaxActions()){
            //if there is no weapon loaded and it's not frenzy modality, you can't select shoot
            if(actionID == Identificator.SHOOT && !gameHandler.getModality().isFrenzyEnable()){
                boolean canShoot = false;
                for(WeaponCard weaponCard : player.getWeaponCardList()){
                    if(weaponCard.isReloaded()){
                        canShoot = true;
                    }
                }
                if(!canShoot){
                    errorString = CANT_SHOOT;
                    notPossible();
                    return;
                }
            }
            controller.addReceived();
            //messageListExpected
            try {
                Action action = gameHandler.getActionByID(actionID, controller);
                //Change State, we don't add the new messages beacuse it's already handled by the constructor of ActionSelected
                controller.setState(new ActionSelectedControllerState(controller, gameHandler, action));
            }catch (WrongInputException e){
                errorString = e.getMessage();
            }

        }
        else{
            errorString = TOO_MANY_ACTIONS;
        }

    }


    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        notPossible();
    }

    @Override
    public void handleFiremode(int firemodeID) {
        notPossible();
    }

    @Override
    public void handleNewton(NewtonCard usedCard) {
        if(!player.containsPowerup(usedCard)){
            errorString = POWERUP_NOT_PRESENT_USE;
        }
        else{
            controller.setState(new NewtonSelectedControllerState(controller, gameHandler, usedCard));
        }

    }

    @Override
    public void handleNope() {
        notPossible();
    }

    @Override
    public void handleOptional(int numOptional) {
        notPossible();
    }

    @Override
    public void handlePlayer(int playerID) {
        notPossible();
    }

    @Override
    public void handleReload(int weaponID) {

        try{
            player.loadWeapon(weaponID);
            //after reloading (if successful), you go to HasReloadedState
            controller.setState(new HasReloadedControllerState(controller, gameHandler));
        }catch(NotPresentException e){
            errorString = NOT_PRESENT_WEAPON_RELOAD;
            notPossible();
        }catch(WeaponIsLoadedException e){
            errorString = WEAPON_LOADED_RELOAD;
            notPossible();
        }catch(NotEnoughAmmoException e){
            errorString = NOT_ENOUGH_AMMO_RELOAD;
            notPossible();
        }
    }

    @Override
    public void handleTagback(TagbackGrenadeCard usedCard) {
        notPossible();
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
        notPossible();

    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        if(!player.containsPowerup(usedCard)){
            errorString = POWERUP_NOT_PRESENT_USE;
        }
        else{
            controller.setState(new TeleporterSelectedControllerState(controller, gameHandler, usedCard));
        }

    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        notPossible();
    }

    @Override
    public void handleFire() {
        notPossible();
    }


    @Override
    public void handleDiscardPowerup(int powerupID) {
        try{
            controller.getAuthor().discardCard(gameHandler.getPowerupCardByID(powerupID), false);
        }catch (NotPresentException e){
            errorString = POWERUP_NOT_PRESENT_DISCARD;
        }
    }

    @Override
    public void handleDiscardWeapon(int weaponID) {
        try {
            player.discardCard(gameHandler.getWeaponCardByID(weaponID));
        } catch (NotPresentException e) {
            errorString = WEAPON_NOT_PRESENT;
        }
    }


    @Override
    public String handle(ViewControllerMessage arg) {
        String stringToPlayerView;
        errorString = null;
        controller.addReceived();
        arg.handle(this);
        if(errorString != null){
            stringToPlayerView = errorString + SELECT_ACTION_REQUEST;
        }
        else {
            if(controller.getState() == this){ //only if the controller has this EXACT state
                stringToPlayerView = SELECT_ACTION_REQUEST;
            }
            else{
                stringToPlayerView = null;
            }

        }
        return stringToPlayerView;
    }

    /**
     * handles the invalid messages
     */
    private void notPossible(){
        //do nothing
    }
}
