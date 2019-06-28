package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.player.*;

import java.util.ArrayList;
import java.util.List;

public class Hellion_2 extends Hellion_1 {

    private static final long serialVersionUID = -3964732490391201864L;

    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(1,0,0)); //cost of shooting base firemode
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        fireHellion(1,2,0,2);
    }

}
