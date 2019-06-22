package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.ViewControllerMess.*;


public class EmptyControllerState extends StateController {

    private Player player;
    private String errorString;
    private String stringToPlayerView;




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
            //if there is no weapon loaded, you can't select shoot
            if(actionID == Identificator.SHOOT){
                boolean canShoot = false;
                for(WeaponCard weaponCard : player.getWeaponCardList()){
                    if(weaponCard.isReloaded()){
                        canShoot = true;
                    }
                }
                if(!canShoot){
                    errorString = CANT_SHOOT;
                    youCantDoThis();
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
        youCantDoThis();
    }

    @Override
    public void handleFiremode(int firemodeID) {
        youCantDoThis();
    }

    @Override
    public void handleNewton(NewtonCard usedCard) {
        //TODO

    }

    @Override
    public void handleNope() {
        youCantDoThis();
    }

    @Override
    public void handleOptional(int numOptional) {
        youCantDoThis();
    }

    @Override
    public void handlePlayer(int playerID) {
        youCantDoThis();
    }

    @Override
    public void handleReload(int weaponID) {
        //TODO
        try{
            player.loadWeapon(weaponID);
        }catch(NotPresentException e){
            errorString = NOT_PRESENT_WEAPON_RELOAD;
            youCantDoThis();
        }catch(WeaponIsLoadedException e){
            errorString = WEAPON_LOADED_RELOAD;
            youCantDoThis();
        }catch(NotEnoughAmmoException e){
            errorString = NOT_ENOUGH_AMMO_RELOAD;
            youCantDoThis();
        }

        //after reloading, you go to HasReloadedState
        controller.setState(new HasReloadedControllerState(controller, gameHandler));
    }

    @Override
    public void handleTagback(TagbackGrenadeCard usedCard) {
        youCantDoThis();
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
        youCantDoThis();

    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        //TODO

    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        youCantDoThis();
    }

    @Override
    public void handlePassTurn() {
        controller.setState(new NotYourTurnState(controller,gameHandler, true));
    }

    @Override
    public void handleFire() {
        youCantDoThis();
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
        //TODO
    }


    @Override
    public String handle(ViewControllerMessage arg) {
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

    @Override
    public void endAction() {
        //do nothing, shouldn't be called in this state
    }

    private void youCantDoThis(){
        //controller.removeReceived();
    }
}
