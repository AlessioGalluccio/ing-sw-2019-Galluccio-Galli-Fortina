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

public class MachineGun_2 extends FireMode {

    @Override
    public List<Target> sendPossibleTarget(Player player, PlayerView playerView, GameHandler gameHandler) {
        return null;
    }

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        return null;
    }

    @Override
    public boolean giveOnlyMarks() {
        return false;
    }

    @Override
    public void fire(List<ViewControllerMessage> stack, GameHandler gameHandler) {

    }

    @Override
    public boolean controlMessage(List<ViewControllerMessage> stack, GameHandler gameHandler) throws NotEnoughAmmoException {
        //TODO
        return false;

    }

    @Override
    public void addCell(Shoot shoot, int x, int y) throws WrongInputException {

    }

    @Override
    public void addPlayerTarget(Shoot shoot, int playerID) throws WrongInputException {

    }

    @Override
    public void addTargetingScope(Shoot shoot, int targetingCardID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {

    }

}
