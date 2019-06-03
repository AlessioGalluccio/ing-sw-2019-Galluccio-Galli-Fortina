package it.polimi.se2019.controller.actions;

import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.deck.firemodes.AddFireModeMethods;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.WeaponIsLoadedException;

public interface AddActionMethods extends AddFireModeMethods{
    //THE COMMENTENTED lines are already implemented in AddFireModeMethods

    //void addCell(int x, int y) throws WrongInputException;

    //void addPlayerTarget(int playerID) throws WrongInputException;

    //void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException;

    void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException;

    void addFireMode(int fireModeID) throws WrongInputException;

    void addWeapon(WeaponCard weaponCard) throws WrongInputException;

    //void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException;

    //void addNope() throws WrongInputException;
}
