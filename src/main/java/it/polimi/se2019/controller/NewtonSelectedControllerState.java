package it.polimi.se2019.controller;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.EmptyControllerState;
import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Galluccio
 */
public class NewtonSelectedControllerState extends StateController {
    private Player playerAuthor;
    private String errorString;
    private NewtonCard newtonCard;
    private boolean skipSelected = false;
    private boolean operationFinished = false;
    private Player target = null;

    //messages
    static final String SELECT_TARGET_NEWTON = "Select a player target. ";
    static final String SELECT_CELL_NEWTON = "Select a cell. ";

    //errors
    public static final String CELL_NOT_PRESENT = "This cell is not present on the map. ";
    static final String PLAYER_ALREADY_SELECTED_NEWTON = "The target is already selected. ";
    static final String SELECTED_YOURSELF = "You can't select yourself. ";
    static final String NOT_VALID_CELL = "This cell is not valid. ";

    /**
     * constructor
     * @param controller the constroller of the player
     * @param gameHandler the gamehandler of the match
     * @param newtonCard the NewtonCard selected
     */
    public NewtonSelectedControllerState(Controller controller, GameHandler gameHandler, NewtonCard newtonCard) {
        super(controller, gameHandler);
        this.playerAuthor = controller.getAuthor();
        this.newtonCard = newtonCard;
        controller.resetMessages();
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_TARGET_NEWTON));
        list.add(new StringAndMessage(Identificator.CELL_MESSAGE, SELECT_CELL_NEWTON));
        controller.addMessageListExpected(list);
        controller.getPlayerView().printFromController(SELECT_TARGET_NEWTON);
    }

    @Override
    public void handleAction(int actionID) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        try{
            if(target != null){
                Cell cell = gameHandler.getCellByCoordinate(coordinateX,coordinateY);
                if(createListCellForNewton(target).contains(cell)){
                    target.setPosition(cell);
                    playerAuthor.discardCard(gameHandler.getPowerupCardByID(newtonCard.getID()), true);
                    operationFinished = true;
                }
                else{
                    errorString = NOT_VALID_CELL;
                }

            }
            else{
                errorString = CANT_DO_THIS;
            }

        }catch (NotPresentException e){
            errorString = CELL_NOT_PRESENT;
        }
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
        skipSelected = true;
    }

    @Override
    public void handleOptional(int numOptional) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handlePlayer(int playerID) {
        if(target != null){
            errorString = PLAYER_ALREADY_SELECTED_NEWTON;
        }
        else{
            if(playerID != playerAuthor.getID()){
                target = gameHandler.getPlayerByID(playerID);
                controller.addReceived();
            }
            else{
                errorString = SELECTED_YOURSELF;
            }

        }
    }

    @Override
    public void handleReload(int weaponID) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleTagback(TagbackGrenadeCard usedCard) {
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
    public void handleFire() {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleDiscardPowerup(int powerupID) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleDiscardWeapon(int weaponID) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public String handle(ViewControllerMessage arg) {
        errorString = null;
        arg.handle(this);
        if(skipSelected || operationFinished){
            controller.setState(new EmptyControllerState(controller, gameHandler));
            return null;
        }
        else if(errorString != null){
            return errorString + controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
        }
        else {
            return controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
        }
    }

    /**
     * create a list of possible cells where to move the target
     * @param target the Player target of this card
     * @return ArrayList of possible Cell where to move the target
     */
    private ArrayList<Cell> createListCellForNewton(Player target){
        Cell targetCell = target.getCell();
        ArrayList<Cell> cellTargets = new ArrayList<>();

        List<Cell> cellsAtDistance1And2 = gameHandler.getMap().getCellAtDistance(targetCell,2);

        List<Cell> cellsInDirection = gameHandler.getMap().getCellInDirection(targetCell,'N');
        cellsInDirection.addAll(gameHandler.getMap().getCellInDirection(targetCell,'E'));
        cellsInDirection.addAll(gameHandler.getMap().getCellInDirection(targetCell,'S'));
        cellsInDirection.addAll(gameHandler.getMap().getCellInDirection(targetCell,'W'));

        //intersection between cells at distance 1 and 2 and cells in directions
        for(Cell firstCell : cellsAtDistance1And2){
            for(Cell secondCell : cellsInDirection){
                if(firstCell.equals(secondCell)){
                    cellTargets.add(firstCell);
                }
            }
        }

        return cellTargets;
    }
}
