package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.player.*;

import java.util.ArrayList;
import java.util.List;

public class ElectroScythe_2 extends ElectroScythe_1 {

    private static final long serialVersionUID = 6200012478870176300L;

    @Override
    public void fire() throws WrongInputException{
        for(Player target : shoot.getTargetsPlayer()){
            addDamageAndMarks(target, 2,0, true);
        }
        commonEndingFire();
    }

    @Override
    public List<AmmoBag> costAdditionalForFiremodeDuringShoot() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(1,0,1)); //cost of shooting base firemode
        return list;
    }




}
