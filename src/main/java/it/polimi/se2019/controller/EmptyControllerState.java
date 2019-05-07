package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.*;

import java.util.ArrayList;


public class EmptyControllerState implements  StateController {

    private Controller controller;
    private GameHandler gameHandler;
    private final String SELECT_ACTION_REQUEST = "Please, select an action";

    EmptyControllerState(Controller controller, GameHandler gameHandler) {
        this.controller = controller;
        this.gameHandler = gameHandler;
    }

    @Override
    public void handle(ActionMessage arg) {

        //messageListReceived
        ArrayList<ViewControllerMessage> messageListReceived = new ArrayList<>();
        messageListReceived.add(arg);
        controller.setMessageListReceived(messageListReceived);

        //messageListExpected
        int messageID = arg.getMessageID();
        Action action = gameHandler.getActionByID(messageID);
        ArrayList<StringAndMessage> stringAndMessage = action.getStringAndMessageExpected();
        controller.setMessageListExpected(stringAndMessage);

        //Change State
        controller.setState(new NotEmptyControllerState(controller, gameHandler));

    }

    @Override
    public void handle(CardSpawnChooseMessage arg) {
        //TODO ??? cosa è?
        arg.getAuthorView().printFromController(SELECT_ACTION_REQUEST);

    }

    @Override
    public void handle(CellMessage arg) {
        arg.getAuthorView().printFromController(SELECT_ACTION_REQUEST);
    }

    @Override
    public void handle(FireModeMessage arg) {
        arg.getAuthorView().printFromController(SELECT_ACTION_REQUEST);
    }

    @Override
    public void handle(LoginMessage arg) {
        //TODO
    }

    @Override
    public void handle(NewtonMessage arg) {
        //TODO

    }

    @Override
    public void handle(NopeMessage arg) {
        arg.getAuthorView().printFromController(SELECT_ACTION_REQUEST);
    }

    @Override
    public void handle(PlayerMessage arg) {
        arg.getAuthorView().printFromController(SELECT_ACTION_REQUEST);
    }

    @Override
    public void handle(ReloadMessage arg) {
        //TODO
        Player player = gameHandler.getPlayerByID(arg.getAuthorID());
        int weaponID = arg.getWeaponID();

        try{
            player.loadWeapon(weaponID);
        }catch(NotPresentException e){
            arg.getAuthorView().printFromController("Error: Player doesn't have this Weapon");
            arg.getAuthorView().printFromController(SELECT_ACTION_REQUEST);
        }catch(WeaponIsLoadedException e){
            arg.getAuthorView().printFromController("This weapon is already loaded");
            arg.getAuthorView().printFromController(SELECT_ACTION_REQUEST);
        }catch(NotEnoughAmmoException e){
            arg.getAuthorView().printFromController("To reload this weapon, you need more ammo. Discard correct PowerUp cards and try again");
        }

    }

    @Override
    public void handle(TagbackGranateMessage arg) {
        arg.getAuthorView().printFromController(SELECT_ACTION_REQUEST);
    }

    @Override
    public void handle(TargetingScopeMessage arg) {
        arg.getAuthorView().printFromController(SELECT_ACTION_REQUEST);

    }

    @Override
    public void handle(TeleporterMessage arg) {
        //TODO
    }

    @Override
    public void handle(Object arg) {
        //TODO scrivi eccezione
        throw new IllegalArgumentException();
    }
}
