package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.List;

public class PlasmaGun_1 extends FireMode {

    //COSTS
    private static final AmmoBag COST_FIRST_OPTIONAL = new AmmoBag(0,0,0);
    private static final AmmoBag COST_SECOND_OPTIONAL = new AmmoBag(0,0,1);

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        return null;
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        //TODO
    }

    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        list.add(COST_FIRST_OPTIONAL);
        list.add(COST_SECOND_OPTIONAL);
        return list;
    }

    @Override
    public void fire() throws WrongInputException{

    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {

    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {

    }

    @Override
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {

    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {

    }

    @Override
    public void addNope() throws WrongInputException {

    }
}
