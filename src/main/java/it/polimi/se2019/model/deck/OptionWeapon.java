package it.polimi.se2019.model.deck;

import java.util.*;


public class OptionWeapon extends WeaponCard {

    private ArrayList<? extends FireMode> optionalFireModeList;
    private FireMode primaryFireMode;

    public void setFireMode(ArrayList<FireMode> fire) {

    }

    @Override
    public List<FireMode> getFireMode() {
        List<FireMode> fireModeReturned = new ArrayList<>(optionalFireModeList);
        fireModeReturned.add(primaryFireMode);

        return duplicateFireMode(fireModeReturned);
    }
}
