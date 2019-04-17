package it.polimi.se2019.model.deck;

import java.util.ArrayList;
import java.util.List;

public class BaseWeapon extends WeaponCard {

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
