package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.List;

public class ElectroScythe_1 extends FireMode {

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        return null;
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        //TODO controlla, non dovrebbe mandare nessun target
        //do nothing
    }

    @Override
    public void fire() throws WrongInputException{
        Cell commonCell = author.getCell();
        for(Player target : gameHandler.getOrderPlayerList()){
            if(target.getCell().equals(commonCell)){
                addDamageAndMarks(target, 1,0);
            }
        }
        super.fire();
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        throw new WrongInputException();
    }


    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        throw new WrongInputException();
    }

}
