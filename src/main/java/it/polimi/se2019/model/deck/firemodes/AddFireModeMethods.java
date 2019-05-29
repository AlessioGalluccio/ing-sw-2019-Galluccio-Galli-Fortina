package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.WeaponIsLoadedException;

public interface AddFireModeMethods {

    void addCell(int x, int y) throws WrongInputException;

    void addPlayerTarget(int playerID) throws WrongInputException;

    void addTargetingScope(int targetingCardID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException;

    void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException;

    void addNope() throws WrongInputException;

}
