package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.PlayerView;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.List;

public class LockRifle_1 extends FireMode {

    @Override
    public List<Target> sendPossibleTarget(Player player, PlayerView playerView) {
        return null;
    }

    @Override
    public void fire(List<ViewControllerMessage> stack, GameHandler gameHandler) {

    }

    @Override
    public boolean controlMessage(List<ViewControllerMessage> stack, GameHandler gameHandler) throws NotEnoughAmmoException {
        //TODO
        return false;

    }
}
