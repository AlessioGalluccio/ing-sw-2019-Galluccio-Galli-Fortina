package it.polimi.se2019.model.deck;

import java.util.*;


public class OptionWeapon extends WeaponCard {

    private static final long serialVersionUID = 4350546617698640742L;
    private ArrayList<? extends FireMode> optionalFireModeList = new ArrayList<>();
    private FireMode primaryFireMode;

    public void setFireMode(ArrayList<FireMode> fire) {

    }

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
