package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;


public class EmptyControllerState extends StateController {

    private Player player;
    private PlayerView playerView;
    private Controller controller;
    private GameHandler gameHandler;
    private String errorString;
    private String stringToPlayerView;

    private final String SELECT_ACTION_REQUEST = "Please, select an action, a card or pass. ";
    private final String CANT_SHOOT = "You don't have any weapon loaded, you can't choose this action. ";
    private final String NOT_PRESENT_WEAPON_RELOAD = "Error: Player doesn't have this Weapon. ";
    private final String WEAPON_LOADED_RELOAD = "This weapon is already loaded. ";
    private final String NOT_ENOUGH_AMMO_RELOAD = "To reload this weapon, you need more ammo. Discard correct PowerUp cards and try again. ";
    private static final String TOO_MANY_ACTIONS = "You have already done the max num of actions. ";


    public EmptyControllerState(Controller controller, GameHandler gameHandler) {
        this.controller = controller;
        this.gameHandler = gameHandler;
        this.player = controller.getAuthor();
        this.playerView = controller.getPlayerView();
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
                }
            }

            //messageListExpected
            Action action = gameHandler.getActionByID(actionID, player);
            ArrayList<StringAndMessage> stringAndMessages = action.getStringAndMessageExpected();
            controller.setMessageListExpected(stringAndMessages);

            //Change State
            controller.setState(new ActionSelectedControllerState(controller, gameHandler, action));
        }
        else{
            errorString = TOO_MANY_ACTIONS;
            controller.removeReceived();
        }

    }

    @Override
    public void handleCardSpawn(PowerupCard cardChoosen, PowerupCard cardDiscarded) {
        //TODO ??? cosa è?
        youCantDoThis();
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
    public void handleLogin(String playerNickname, Character chosenCharacter) {
        //TODO
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
    public void handleTagback(TagbackGranedCard usedCard) {
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
        //TODO
    }

    @Override
    public void handleFire() {
        //TODO
    }

    @Override
    public void handleReconnection(boolean isConnected) {
        //TODO controlla
        if(!isConnected){
            gameHandler.setPlayerConnectionStatus(player, false);
            gameHandler.nextTurn();
            controller.setState(new DisconnectedControllerState(controller, gameHandler));
        }
    }

    @Override
    public void handleDiscardPowerup(int powerupID) {
        //TODO
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
            stringToPlayerView = SELECT_ACTION_REQUEST;
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
