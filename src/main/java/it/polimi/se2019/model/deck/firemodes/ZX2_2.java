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

public class ZX2_2 extends FireMode {

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
        if(!shoot.getTargetsPlayer().isEmpty()){
            for(Player target : shoot.getTargetsPlayer()){
                addDamageAndMarks(target,0,1);
            }
        }
        //TODO pagamento costo ed eccezione
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(!target.isVisibleBy(author) || target.getID() == author.getID()){
            throw new WrongInputException();
        }

        if(shoot.getTargetsPlayer().size() < 3){
            shoot.addPlayerTargetFromFireMode(target);
            sendAllVisiblePlayers(shoot.getTargetsPlayer());
        }
        else {
            throw new WrongInputException();
        }

    }

    @Override
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        //TODO
    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        throw new WrongInputException();
    }

    @Override
    public void addNope() throws WrongInputException {
        //TODO è possibile in questa modalità per il 2 e il 3 targetPlayer
    }

}
