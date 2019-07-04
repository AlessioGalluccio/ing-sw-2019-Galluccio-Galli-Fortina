package it.polimi.se2019.controller.actions;

import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.controller.actions.firemodes.AddFireModeMethods;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.WeaponIsLoadedException;

/**
 * this methods must be implemented by Action classes to permit a correct comunication. They also include the
 * AddFireMode methods of the firemodes
 * @author Galluccio
 */
public interface AddActionMethods extends AddFireModeMethods{
    //THE COMMENTED lines are already implemented in AddFireModeMethods

    //void addCell(int x, int y) throws WrongInputException;

    //void addPlayerTarget(int playerID) throws WrongInputException;

    //void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException;

    /**
     * add a reloading command input
     * @param weaponID the ID of the weapon which must be reloaded
     * @throws WrongInputException if invalid input
     * @throws NotPresentException if the player doesn't have the weapon
     * @throws NotEnoughAmmoException if the player doesn't have enough ammo
     * @throws WeaponIsLoadedException if the weapon is already loaded
     */
    void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException;

    /**
     * add a firemode input
     * @param fireModeID the ID of the firemode
     * @throws WrongInputException if invalid input
     */
    void addFireMode(int fireModeID) throws WrongInputException;

    /**
     * add a weapon input
     * @param weaponCard the weapon card
     * @throws WrongInputException if invalid input
     */
    void addWeapon(WeaponCard weaponCard) throws WrongInputException;

    /**
     * add a Discard Weapon command input
     * @param weaponCard the weapon card to discard
     * @throws WrongInputException if invalid input
     */
    void addDiscardWeapon(WeaponCard weaponCard) throws WrongInputException;

    //void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException;

    //void fire() throws WrongInputException;
}
