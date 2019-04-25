package it.polimi.se2019.controller;

import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.WeaponIsLoadedException;
import it.polimi.se2019.model.player.WeaponNotPresentException;
import it.polimi.se2019.model.player.WrongColorException;
import it.polimi.se2019.view.ViewControllerMess.*;

public class AmmoNeededFirstReload implements StateController {

    private Controller controller;
    private GameHandler gameHandler;
    private int weaponIdReloading;
    private static String AMMO_NEEDED = "To reload this weapon, you need one more ammo. Discard a correct PowerUp or cancel action";
    private static String WRONG_COLOR = "The color of the ammo of this PowerUp is incorrect. Please, select another one or cancel action";
    private static String WEAPON_ALREADY_LOADED = "Error: Weapon already loaded. Reloading cancelled";
    private static String WEAPON_NOT_PRESENT = "Error: Player doesn't have this weapon. Reloading cancelled";

    AmmoNeededFirstReload(Controller controller, GameHandler gameHandler, int weaponIdReloading, String colorNeeded) {
        this.controller = controller;
        this.gameHandler = gameHandler;
        this.weaponIdReloading = weaponIdReloading;
    }

    @Override
    public void handle(ActionMessage arg) {
        arg.getAuthorView().printFromController(AMMO_NEEDED);

    }

    @Override
    public void handle(CardSpawnChooseMessage arg) {
        arg.getAuthorView().printFromController(AMMO_NEEDED);
    }

    @Override
    public void handle(CellMessage arg) {
        arg.getAuthorView().printFromController(AMMO_NEEDED);
    }

    @Override
    public void handle(FireModeMessage arg) {
        arg.getAuthorView().printFromController(AMMO_NEEDED);
    }

    @Override
    public void handle(NewtonMessage arg) {
        Player player = gameHandler.getPlayerByID(arg.getAuthorID());
        try{
            player.loadWeaponWithPowerUp(weaponIdReloading, arg.getUsedCard());
        }catch(WeaponNotPresentException e){
            arg.getAuthorView().printFromController(WEAPON_ALREADY_LOADED);
            controller.setState(new EmptyControllerState(controller, gameHandler));

        }catch(WeaponIsLoadedException e){
            arg.getAuthorView().printFromController(WEAPON_ALREADY_LOADED);
            controller.setState(new EmptyControllerState(controller, gameHandler));

        }catch(WrongColorException e){
            arg.getAuthorView().printFromController(WRONG_COLOR);
        }

    }

    @Override
    public void handle(NopeMessage arg) {

    }

    @Override
    public void handle(PlayerViewMessage arg) {
        arg.getAuthorView().printFromController(AMMO_NEEDED);
    }

    @Override
    public void handle(ReloadMessage arg) {

    }

    @Override
    public void handle(TagbackGranateMessage arg) {

    }

    @Override
    public void handle(TargetingScopeMessage arg) {

    }

    @Override
    public void handle(TargetMessage arg) {

    }

    @Override
    public void handle(TeleporterMessage arg) {

    }

    @Override
    public void handle(Object arg) {

    }
}
