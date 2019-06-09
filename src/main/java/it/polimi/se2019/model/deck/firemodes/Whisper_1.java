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


public class Whisper_1 extends FireMode {

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        return null;
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        //TODO
    }

    @Override
    public void fire() throws WrongInputException{
        if(!shoot.getTargetsPlayer().isEmpty()){
            Player target = shoot.getTargetsPlayer().get(0);
            addDamageAndMarks(target, 3,1);
            super.fire();
        }
        else{
            throw new WrongInputException();
        }
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        //TODO controlla probabile errore in contains
        Player target = gameHandler.getPlayerByID(playerID);
        List<Cell> arrayCell = gameHandler.getMap().getCellAtDistance(author.getCell(), 2);
        if(!shoot.getTargetsPlayer().isEmpty() && arrayCell.contains(target.getCell())){
            shoot.addPlayerTargetFromFireMode(target);
        }
        else{
            throw new WrongInputException();
        }
    }


    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        throw new WrongInputException();
    }
}
