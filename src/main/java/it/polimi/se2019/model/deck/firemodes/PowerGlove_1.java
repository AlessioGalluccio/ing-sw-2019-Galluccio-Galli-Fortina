package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class PowerGlove_1 extends FireMode {
    static final String SELECT_PLAYER = "Select a player on any cell near you. ";
    static final String WRONG_PLAYER = "You can't select that player. ";

    private static final long serialVersionUID = -3228158856128568861L;

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER));
        return list;
    }

    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        return list;
    }

    @Override
    public void fire() throws WrongInputException {
        if(shoot.getTargetsPlayer().isEmpty()) throw new WrongInputException(CANT_DO_FIRE);

        for(Player target : shoot.getTargetsPlayer()) {
            addDamageAndMarks(target, 1,2,true);
        }

        //Move author on the cell of the target
        author.setPosition(shoot.getTargetsPlayer().get(0).getCell());
        super.fire();
    }


    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Map map = gameHandler.getMap();

        if(playerID == author.getID()) throw new WrongInputException(FireMode.SELECTED_YOURSELF);

        Player targetPlayer = gameHandler.getPlayerByID(playerID);

        //Too distant or too near
        if(map.getDistance(author.getCell(), targetPlayer.getCell())!=1 ||
            !shoot.getTargetsPlayer().isEmpty())
            throw new WrongInputException(WRONG_PLAYER);

        shoot.addPlayerTargetFromFireMode(targetPlayer, true);
    }

}
