package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
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


public class Whisper_1 extends FireMode {
    private static final long serialVersionUID = 2287199755551717645L;

    static final String SELECT_PLAYER = "Select a player you can see far enough from you. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
        return list;
    }


    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(!shoot.getTargetsPlayer().isEmpty()){
            Player target = shoot.getTargetsPlayer().get(0);
            addDamageAndMarks(target, 3,1, true);
            super.fire();
        }
        else{
            throw new WrongInputException();
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        List<Cell> arrayCell = gameHandler.getMap().getCellAtDistance(author.getCell(), 1);
        if(shoot.getTargetsPlayer().isEmpty() &&
                !arrayCell.contains(target.getCell()) &&
                target.isVisibleBy(gameHandler.getMap(), author)){
            shoot.addPlayerTargetFromFireMode(target, true);
        }
        else{
            throw new WrongInputException("This player is on an invalid cell. ");
        }
    }
}
