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

public class FlameThrower_2 extends FlameThrower_1 {

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        //TODO da fare!
        return null;
    }


    @Override
    public void fire() throws WrongInputException{
        if(!shoot.getTargetsCells().isEmpty()){
            switch(getDirection(shoot.getTargetsCells().get(0))){
                //TODO
                case NORTH:
                    //Cell northCell = author.getCell().getCoordinateX()
                    break;
                case EAST:

                    break;
                case SOUTH:

                    break;
                case WEST:

                    break;
            }
        }


    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        //we don't need to specify the targets. We shoot to them all
        throw new WrongInputException();
    }
}
