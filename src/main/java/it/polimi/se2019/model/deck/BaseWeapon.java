package it.polimi.se2019.model.deck;

import it.polimi.se2019.controller.actions.firemodes.FireMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Galli
 */
public class BaseWeapon extends WeaponCard {

    private static final long serialVersionUID = 8502911012102121335L;
    private ArrayList<? extends FireMode> fireModeList;

    /**
     *
     * @return Deep copy of the fire mode list available with this weapon
     */
    @Override
    public List<FireMode> getFireMode() {
        List<FireMode> fireModeReturned = new ArrayList<>(fireModeList);

        return duplicateFireMode(fireModeReturned);
    }
}
