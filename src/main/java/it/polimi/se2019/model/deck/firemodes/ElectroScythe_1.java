package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.List;

public class ElectroScythe_1 extends FireMode {
    private static final String SEND_FIRE = "Press fire to complete the action";

    private static final String NO_VISIBLE_FOR_TARGETING = "No visible target for Targeting. ";
    private static final String INVALID_TARGET_FOR_TARGETING = "Invalid target for targeting scope. ";
    private static final long serialVersionUID = 5053678759660132677L;

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        for(Player target : author.getCell().getPlayerHere()){
            if(target.getID() != author.getID()){
                shoot.addPlayerTargetFromFireMode(target, true);
            }
        }
        return new ArrayList<>(); //empty list
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        //TODO controlla, non dovrebbe mandare nessun target
        //do nothing
    }

    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            for(Player target : shoot.getTargetsPlayer()){
                addDamageAndMarks(target, 1,0, true);
            }
            super.fire();
        }
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        throw new WrongInputException();
    }

}
