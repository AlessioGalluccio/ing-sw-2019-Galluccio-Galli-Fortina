package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.WeaponIsLoadedException;

/**
 * this methods must be implemented by both firemodes and actions to permit a correct communication
 * @author Galluccio
 */
public interface AddFireModeMethods {

    /**
     * add a cell input
     * @param x coordinate x of the cell
     * @param y coordinate y of the cell
     * @throws WrongInputException if invalid input
     */
    void addCell(int x, int y) throws WrongInputException;

    /**
     * add a player target input
     * @param playerID the ID of the player
     * @throws WrongInputException if invalid input
     */
    void addPlayerTarget(int playerID) throws WrongInputException;

    /**
     * add a Targeting Scope input
     * @param targetingCardID the ID of the targeting scope
     * @param cost the AmmoBag the player wants to convert to damage
     * @throws WrongInputException if invalid input
     * @throws NotPresentException if card is not present
     * @throws NotEnoughAmmoException if the player doesn't have enough ammo
     * @throws FiremodeOfOnlyMarksException if the firemode can't use targeting scopes because it only does marks to the targets
     */
    void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException;

    /**
     * add an optional Firemode input
     * @param numOptional the number of the optional
     * @throws WrongInputException if invalid input
     * @throws NotEnoughAmmoException if the player doesn't have enough ammo
     */
    void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException;

    /**
     * add a fire input
     * @throws WrongInputException if invalid input
     */
    void fire() throws WrongInputException;

}
