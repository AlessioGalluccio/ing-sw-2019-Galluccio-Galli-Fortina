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
import java.util.List;


public class EmptyControllerState implements  StateController {

    private Player player;
    private PlayerView playerView;
    private Controller controller;
    private GameHandler gameHandler;
    private final String SELECT_ACTION_REQUEST = "Please, select an action";
    private final String CANT_SHOOT = "You don't have any weapon loaded, you can't choose this action";
    private final String NOT_PRESENT_WEAPON_RELOAD = "Error: Player doesn't have this Weapon";
    private final String WEAPON_LOADED_RELOAD = "This weapon is already loaded";
    private final String NOT_ENOUGH_AMMO_RELOAD = "To reload this weapon, you need more ammo. Discard correct PowerUp cards and try again";


    public EmptyControllerState(Controller controller, GameHandler gameHandler) {
        //TODO aggiungere player e playerView (anche a tutti gli stati!)
        this.controller = controller;
        this.gameHandler = gameHandler;
        this.player = controller.getPlayer();
        this.playerView = controller.getPlayerView();
    }

    @Override
    public void handleAction(int actionID) {
        //if there is no weapon loaded, you can't select shoot
        if(actionID == Identificator.SHOOT){
            boolean canShoot = false;
            for(WeaponCard weaponCard : player.getWeaponCardList()){
                if(weaponCard.isReloaded()){
                    canShoot = true;
                }
            }
            if(!canShoot){
                playerView.printFromController(CANT_SHOOT);
                controller.getLastReceivedMessage().getAuthorView().printFromController(SELECT_ACTION_REQUEST);
                controller.removeLastReceivedMessage();
            }
        }

        //messageListExpected
        Action action = gameHandler.getActionByID(actionID, player);
        ArrayList<StringAndMessage> stringAndMessages = action.getStringAndMessageExpected();
        controller.setMessageListExpected(stringAndMessages);

        //Change State
        controller.setState(new ActionSelectedControllerState(controller, gameHandler));

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
            controller.getLastReceivedMessage().getAuthorView().printFromController(NOT_PRESENT_WEAPON_RELOAD);
            youCantDoThis();
        }catch(WeaponIsLoadedException e){
            controller.getLastReceivedMessage().getAuthorView().printFromController(WEAPON_LOADED_RELOAD);
            youCantDoThis();
        }catch(NotEnoughAmmoException e){
            controller.getLastReceivedMessage().getAuthorView().printFromController(NOT_ENOUGH_AMMO_RELOAD);
            youCantDoThis();
        }

        //after reloading, you pass your turn
        controller.setState(new NotYourTurnState(controller, gameHandler));
    }

    @Override
    public void handleTagback(TagbackGranedCard usedCard) {
        youCantDoThis();
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, ColorRYB colorAmmo) {
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
    public void handle(ViewControllerMessage arg) {
        controller.addMessageListReceived(arg);
        arg.handle(this);
    }

    private void youCantDoThis(){
        controller.getLastReceivedMessage().getAuthorView().printFromController(SELECT_ACTION_REQUEST);
        controller.removeLastReceivedMessage();
    }
}
