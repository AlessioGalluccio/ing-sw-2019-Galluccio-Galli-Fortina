package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.Room;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public class FirstTurnState extends StateController {

    private Player playerAuthor;
    private String errorString;
    private String stringToPlayerView;
    private boolean isCharacterSelected = false;

    public static final String CHARACTER_REQUEST = "Please, select a Character";
    public static final String POWERUP_DISCARD_REQUEST = "Please, discard a Powerup to spawn";

    private static StringAndMessage firstMessage =
            new StringAndMessage(Identificator.CHARACTER_MESSAGE, CHARACTER_REQUEST);
    private static StringAndMessage secondMessage =
            new StringAndMessage(Identificator.DISCARD_POWERUP_MESSAGE, POWERUP_DISCARD_REQUEST);

    public FirstTurnState(Controller controller, GameHandler gameHandler) {
        super(controller, gameHandler);
        ArrayList<StringAndMessage> listExpectedMessages = new ArrayList<>();
        listExpectedMessages.add(firstMessage);
        listExpectedMessages.add(secondMessage);
        controller.resetMessages();
        controller.addMessageListExpected(listExpectedMessages);
        this.playerAuthor = controller.getAuthor();

        //TODO al giocatore vengono dati due powerup
        //we immediately send a message to the player
        controller.getPlayerView().printFromController(CHARACTER_REQUEST);
    }

    @Override
    public void handleAction(int actionID) {
        errorString = CANT_DO_THIS;
    }
    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleFiremode(int firemodeID) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleNewton(NewtonCard usedCard) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleNope() {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleOptional(int numOptional) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handlePlayer(int playerID) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleReload(int weaponID) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleTagback(TagbackGranedCard usedCard) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handlePassTurn() {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleFire() {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void endAction() {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleDiscardPowerup(int powerupID) {
        if(!isCharacterSelected){
            errorString = CANT_DO_THIS;
        }
        else{
            PowerupCard powerupCard = gameHandler.getPowerupCardByID(powerupID);
            String color = powerupCard.getColor();
            Room room = gameHandler.getRoomByID(color);
            Cell cellSpawn = room.getSpawnCell();
            playerAuthor.setPosition(cellSpawn);

            //spawn process is finished. we go to the next state
            controller.setState(new EmptyControllerState(controller, gameHandler));
        }
    }

    @Override
    public void handleDiscardWeapon(int weaponID) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleCharacter(int characterID) {
        //TODO
        //playerAuthor.setCharacter(new Character());
    }

    @Override
    public String handle(ViewControllerMessage arg) {
        if(arg.getMessageID() ==
                controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getMessageID()){
            arg.handle(this);
        }
        else{
            errorString = CANT_DO_THIS;
        }

        if(errorString != null){
            stringToPlayerView = errorString +
                    controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
            errorString = null;
        }
        else{
            stringToPlayerView = controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
        }
        return stringToPlayerView;
    }
}
