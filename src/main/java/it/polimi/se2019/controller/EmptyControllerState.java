package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;


public class EmptyControllerState implements  StateController {

    private Player player;
    private PlayerView playerView;
    private Controller controller;
    private GameHandler gameHandler;
    private final String SELECT_ACTION_REQUEST = "Please, select an action";

    EmptyControllerState(Controller controller, GameHandler gameHandler) {
        //TODO aggiungere player e playerView (anche a tutti gli stati!)
        this.controller = controller;
        this.gameHandler = gameHandler;
    }

    @Override
    public void handleAction(int actionID) {

        //messageListExpected
        Action action = gameHandler.getActionByID(actionID, player);
        ArrayList<StringAndMessage> stringAndMessage = action.getStringAndMessageExpected();
        controller.setMessageListExpected(stringAndMessage);

        //Change State
        controller.setState(new NotEmptyControllerState(controller, gameHandler));

    }

    @Override
    public void handleCardSpawn(PowerupCard cardChoosen, PowerupCard cardDiscarded) {
        //TODO ??? cosa è?
        controller.getLastReceivedMessage().getAuthorView().printFromController(SELECT_ACTION_REQUEST);
        controller.removeLastReceivedMessage();
    }

    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        controller.getLastReceivedMessage().getAuthorView().printFromController(SELECT_ACTION_REQUEST);
        controller.removeLastReceivedMessage();
    }

    @Override
    public void handleFiremode(int firemodeID) {
        controller.getLastReceivedMessage().getAuthorView().printFromController(SELECT_ACTION_REQUEST);
        controller.removeLastReceivedMessage();
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
        controller.getLastReceivedMessage().getAuthorView().printFromController(SELECT_ACTION_REQUEST);
        controller.removeLastReceivedMessage();
    }

    @Override
    public void handlePlayer(int playerID) {
        controller.getLastReceivedMessage().getAuthorView().printFromController(SELECT_ACTION_REQUEST);
        controller.removeLastReceivedMessage();
    }

    @Override
    public void handleReload(int weaponID) {
        //TODO
        Player player = gameHandler.getPlayerByID(controller.getLastReceivedMessage().getAuthorID());

        try{
            player.loadWeapon(weaponID);
        }catch(NotPresentException e){
            controller.getLastReceivedMessage().getAuthorView().printFromController("Error: Player doesn't have this Weapon");
            controller.getLastReceivedMessage().getAuthorView().printFromController(SELECT_ACTION_REQUEST);
        }catch(WeaponIsLoadedException e){
            controller.getLastReceivedMessage().getAuthorView().printFromController("This weapon is already loaded");
            controller.getLastReceivedMessage().getAuthorView().printFromController(SELECT_ACTION_REQUEST);
        }catch(NotEnoughAmmoException e){
            controller.getLastReceivedMessage().getAuthorView().printFromController("To reload this weapon, you need more ammo. Discard correct PowerUp cards and try again");
        }

    }

    @Override
    public void handleTagback(TagbackGranedCard usedCard) {
        controller.getLastReceivedMessage().getAuthorView().printFromController(SELECT_ACTION_REQUEST);
        controller.removeLastReceivedMessage();
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, ColorRYB colorAmmo) {
        controller.getLastReceivedMessage().getAuthorView().printFromController(SELECT_ACTION_REQUEST);
        controller.removeLastReceivedMessage();

    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        //TODO
    }

    @Override
    public void handle(ViewControllerMessage arg) {
        controller.addMessageListReceived(arg);
        arg.handle(this);
    }
}
