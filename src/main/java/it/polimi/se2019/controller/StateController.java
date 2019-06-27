package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.Room;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.*;

public abstract class StateController {
    //they are public for tests

    public static final String SELECT_ACTION_REQUEST = "Please, select an action, a card or pass. ";
    public static final String CANT_SHOOT = "You don't have any weapon loaded, you can't choose this action. ";
    public static final String NOT_PRESENT_WEAPON_RELOAD = "Error: Player doesn't have this Weapon. ";
    public static final String WEAPON_LOADED_RELOAD = "This weapon is already loaded. ";
    public static final String NOT_ENOUGH_AMMO_RELOAD = "To reload this weapon, you need more ammo. Discard correct PowerUp cards and try again. ";
    public static final String TOO_MANY_ACTIONS = "You have already done the max num of actions. ";

    public static final String PLAYER_WRONG = "You can't select this target. ";
    public static final String CELL_WRONG = "You can't select this cell. ";
    public static final String OPTIONAL_WRONG = "You can't select this optional effect. ";

    public static final String NOT_ENOUGH = "You don't have enough Ammo for this. ";
    public static final String ONLY_MARKS = "The firemode selected gives only marks, you can't use targeting. ";
    public static final String CARD_NOT_PRESENT = "The player doesn't have this card. ";
    public static final String ALREADY_SELECTED = "You have already selected this, you can't use again. ";
    public static final String WEAPON_NOT_PRESENT = "This weapon is not present. ";
    public static final String CANT_DO_THIS = "You can't do this now. ";
    public static final String POWERUP_NOT_PRESENT_DISCARD = "You can't discard a card you don't have. ";
    public static final String POWERUP_NOT_PRESENT_USE = "You can't use a card you don't have. ";

    public static final String CANT_DO_ALREADY_RELOADED = "You have already reloaded. ";
    public static final String RELOAD_OR_PASS = "Reload another weapon or pass your turn. ";

    public static final String PLAYER_NOT_PRESENT = "This player is not present on the map. ";


    protected Controller controller;
    protected GameHandler gameHandler;


    public StateController(Controller controller, GameHandler gameHandler) {
        this.controller = controller;
        this.gameHandler = gameHandler;
    }

    /**
     * used by the controller to pass the new message, the other methods are used by messages themselves
     * @param arg
     */
    public abstract String handle(ViewControllerMessage arg);

    public abstract void handleAction(int actionID);

    public abstract void handleCell(int coordinateX, int coordinateY);

    public abstract void handleFiremode(int firemodeID);

    public abstract void handleNewton(NewtonCard usedCard);

    public abstract void handleNope();

    public abstract void handleOptional(int numOptional);

    public abstract void handlePlayer(int playerID);

    public abstract void handleReload(int weaponID);

    public abstract void handleTagback(TagbackGrenadeCard usedCard);

    public abstract void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost);

    public abstract void handleTeleporter(TeleporterCard usedCard);

    public abstract void handleWeaponCard(WeaponCard usedCard);

    /**
     * when called by a PassTurnMessage , it passes the turn
     */
    public void handlePassTurn() {
        controller.setState(new NotYourTurnState(controller,gameHandler));
        gameHandler.nextTurn();
    }

    public abstract void handleFire();

    public void handleReconnection(boolean isConnected){
        if(!isConnected){
            gameHandler.setPlayerConnectionStatus(controller.getAuthor(), false);
            gameHandler.nextTurn();
            controller.setState(new DisconnectedControllerState(controller, gameHandler));
        }
    }

    public abstract void handleDiscardPowerup(int powerupID);

    public abstract void handleDiscardWeapon(int weaponID);

    public void handleCharacter(int characterID){
        //do nothing
    }

    //HELPER METHODS

    /**
     * handle the respwawn of the player. Returns a message string if there's an error
     * @param playerToRespawn the player you want to repawn
     * @param powerupID the powerup discarded by the player
     * @return a string message if there's an error. Otherwise null.
     */
    protected String respawnPlayerWithPowerup(Player playerToRespawn, int powerupID){
        PowerupCard powerupCard = gameHandler.getPowerupCardByID(powerupID);

        if(!playerToRespawn.getPowerupCardList().contains(powerupCard)){
            return CARD_NOT_PRESENT;
        }

        String color = powerupCard.getColor();
        Room room = gameHandler.getRoomByID(color);
        Cell cellSpawn = room.getSpawnCell();
        playerToRespawn.setPosition(cellSpawn);
        try{
            playerToRespawn.discardCard(powerupCard, true);
        }catch (NotPresentException e){
            //should not happen
        }

        return null;
    }

    /**
     * it disconnettes the player withou passing the turn. Use it for states where it's not the player turn
     * @param isConnected false if the player is disconnected, true if not
     */
    protected void connectionDontPassTurn(boolean isConnected){
        //TODO controlla da sistemare sicuramente
        if(!isConnected){
            gameHandler.setPlayerConnectionStatus(controller.getAuthor(), false);
            controller.setState(new DisconnectedControllerState(controller, gameHandler));
        }
    }

}
