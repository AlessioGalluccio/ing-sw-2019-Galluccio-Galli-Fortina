package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

public class HasReloadedControllerState extends StateController {
    private Player player;
    private String errorString;
    private String stringToPlayerView;

    /**
     * constructor
     * @param controller the controller of the player
     * @param gameHandler the gamehandler of the match
     */
    public HasReloadedControllerState(Controller controller, GameHandler gameHandler){
        super(controller, gameHandler);
        this.player = controller.getAuthor();
        controller.getPlayerView().printFromController(RELOAD_OR_PASS);
    }

    @Override
    public void handleAction(int actionID) {
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
    public void handleNewton(NewtonCard usedCard) {
        youCantDoThis();
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
        try{
            player.loadWeapon(weaponID);
            controller.addReceived();
        }catch(NotPresentException e){
            errorString = NOT_PRESENT_WEAPON_RELOAD;

        }catch(WeaponIsLoadedException e){
            errorString = WEAPON_LOADED_RELOAD;


        }catch(NotEnoughAmmoException e){
            errorString = NOT_ENOUGH_AMMO_RELOAD;
        }
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
        youCantDoThis();
    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        youCantDoThis();
    }


    @Override
    public void handleFire() {
        youCantDoThis();
    }


    @Override
    public void handleDiscardPowerup(int powerupID) {
        try{
            player.discardCard(gameHandler.getPowerupCardByID(powerupID), false);
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
        errorString = null;

        arg.handle(this);

        if(errorString != null){
            stringToPlayerView = errorString + RELOAD_OR_PASS;
        }
        else{
            stringToPlayerView = RELOAD_OR_PASS;
        }
        return stringToPlayerView;
    }

    /**
     * handles the invalid messages
     */
    private void youCantDoThis(){
        errorString = CANT_DO_ALREADY_RELOADED;
    }

}
