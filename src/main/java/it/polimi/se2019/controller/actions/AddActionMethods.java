package it.polimi.se2019.controller.actions;

import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.WeaponIsLoadedException;

public interface AddActionMethods {
    void addCell(int x, int y) throws WrongInputException;

    void addPlayerTarget(int playerID) throws WrongInputException;

    void addTargetingScope(int targetingCardID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException;

    void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException;

    void addWeapon(int weaponID) throws WrongInputException;

    void addFireMode(int fireModeID) throws WrongInputException;

    void addWeapon(WeaponCard weaponCard) throws WrongInputException;

    void addOptional(int numOptional) throws WrongInputException;

    void addNope() throws WrongInputException;
}
