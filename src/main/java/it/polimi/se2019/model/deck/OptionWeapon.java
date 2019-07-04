package it.polimi.se2019.model.deck;

import it.polimi.se2019.controller.actions.firemodes.FireMode;

import java.util.*;

/**
 * @author Galli
 */
public class OptionWeapon extends WeaponCard {

    private static final long serialVersionUID = 4350546617698640742L;
    private ArrayList<? extends FireMode> optionalFireModeList = new ArrayList<>();
    private FireMode primaryFireMode;

    /**
     *
     * @return Deep copy of the fire mode list available with this weapon
     */
    @Override
    public List<FireMode> getFireMode() {
        List<FireMode> fireModeReturned = new ArrayList<>(optionalFireModeList);
        fireModeReturned.add(primaryFireMode);
        return duplicateFireMode(fireModeReturned);
    }
}
