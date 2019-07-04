package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

/**
 * @author Galluccio
 */
public class TeleporterSelectedControllerState extends StateController{
    private Player playerAuthor;
    private String message;
    private TeleporterCard teleporterCard;
    private boolean skipSelected = false;

    //messages
    public static final String SELECT_CELL_TELEPORTER = "Select a cell for Teleporting. ";

    //errors
    public static final String CELL_NOT_PRESENT = "This cell is not present on the map. ";

    /**
     *
     * @param controller the controller of the player
     * @param gameHandler the gamehandler of the match
     * @param teleporterCard the TeleporterCard selected
     */
    public TeleporterSelectedControllerState(Controller controller, GameHandler gameHandler, TeleporterCard teleporterCard) {
        super(controller, gameHandler);
        this.playerAuthor = controller.getAuthor();
        this.teleporterCard = teleporterCard;
        controller.getPlayerView().printFromController(SELECT_CELL_TELEPORTER);
    }

    @Override
    public void handleAction(int actionID) {
        message = CANT_DO_THIS;
    }

    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        try{
            Cell cell = gameHandler.getCellByCoordinate(coordinateX,coordinateY);
            playerAuthor.setPosition(cell);
            playerAuthor.discardCard(gameHandler.getPowerupCardByID(teleporterCard.getID()), true);
        }catch (NotPresentException e){
            message = CELL_NOT_PRESENT;
        }
    }

    @Override
    public void handleFiremode(int firemodeID) {
        message = CANT_DO_THIS;
    }

    @Override
    public void handleNewton(NewtonCard usedCard) {
        message = CANT_DO_THIS;
    }

    @Override
    public void handleNope() {
        skipSelected = true;
    }

    @Override
    public void handleOptional(int numOptional) {
        message = CANT_DO_THIS;
    }

    @Override
    public void handlePlayer(int playerID) {
        message = CANT_DO_THIS;
    }

    @Override
    public void handleReload(int weaponID) {
        message = CANT_DO_THIS;
    }

    @Override
    public void handleTagback(TagbackGrenadeCard usedCard) {
        message = CANT_DO_THIS;
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
        message = CANT_DO_THIS;
    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        message = CANT_DO_THIS;
    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        message = CANT_DO_THIS;
    }

    @Override
    public void handleFire() {
        message = CANT_DO_THIS;
    }

    @Override
    public void handleDiscardPowerup(int powerupID) {
        message = CANT_DO_THIS;
    }

    @Override
    public void handleDiscardWeapon(int weaponID) {
        message = CANT_DO_THIS;
    }

    @Override
    public String handle(ViewControllerMessage arg) {
        message = null;
        arg.handle(this);
        if(message == null || skipSelected){
            controller.setState(new EmptyControllerState(controller, gameHandler));
            return null;
        }
        else {
            return message + SELECT_CELL_TELEPORTER;
        }
    }
}
