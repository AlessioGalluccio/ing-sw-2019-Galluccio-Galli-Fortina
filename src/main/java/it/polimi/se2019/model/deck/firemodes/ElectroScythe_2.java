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

public class ElectroScythe_2 extends ElectroScythe_1 {

    @Override
    public void fire() throws WrongInputException{
        Cell commonCell = author.getCell();
        for(Player target : gameHandler.getOrderPlayerList()){
            if(target.getCell().equals(commonCell)){
                addDamageAndMarks(target, 2,0);
            }
        }
        super.fire();
    }

}
