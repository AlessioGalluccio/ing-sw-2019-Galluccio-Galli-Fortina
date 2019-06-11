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

import java.util.List;

public class ZX2_1 extends FireMode {

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        return null;
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        sendAllVisiblePlayers(null);
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().size() == 1){
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 1,2, true);
            super.fire();
        }
        else{
            throw new WrongInputException();
        }

    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(target.isVisibleBy(author) && shoot.getTargetsPlayer().isEmpty() && target.getID() != author.getID()){
            shoot.addPlayerTargetFromFireMode(target);
        }
        else{
            throw new WrongInputException();
        }
    }

    //THIS MUST REMAIN, IT LAUNCHES ANOTHER EXCEPTION
    @Override
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        throw new FiremodeOfOnlyMarksException();
    }


}
