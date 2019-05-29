package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.WeaponIsLoadedException;
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
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 1,2);
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
        if(target.isVisibleBy(author) && shoot.getTargetsPlayer().isEmpty() && target.getID() != author.getID()){
            shoot.addPlayerTargetFromFireMode(target);
        }
        else{
            throw new WrongInputException();
        }
    }

    @Override
    public void addTargetingScope(int targetingCardID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        //TODO
    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        throw new WrongInputException();
    }

    @Override
    public void addNope() throws WrongInputException {
        throw new WrongInputException();
    }

}
