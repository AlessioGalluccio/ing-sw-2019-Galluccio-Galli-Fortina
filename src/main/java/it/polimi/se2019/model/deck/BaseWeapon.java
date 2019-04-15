package it.polimi.se2019.model.deck;

import java.util.ArrayList;
import java.util.List;

public class BaseWeapon extends WeaponCard {

    private ArrayList<? extends FireMode> fireModeList;

    @Override
    public List<FireMode> getFireMode() {
        List<FireMode> fireModeReturned = new ArrayList(fireModeList);

        return duplicateFireMode(fireModeReturned);
    }
}
