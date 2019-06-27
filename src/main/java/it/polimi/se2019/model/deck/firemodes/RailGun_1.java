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

public class RailGun_1 extends FireMode {

    private static final long serialVersionUID = 4684255276743354888L;

    //messages
    public static final String SELECT_PLAYER_RAILGUN = "Select a target player. ";

    //errors
    public static final String INVALID_TARGET = "Invalid target. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER_RAILGUN));
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
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 3, 0, true);
        }
        super.fire();
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        if(playerID == author.getID()){
            throw new WrongInputException(SELECTED_YOURSELF);
        }
        else {
            Player target = gameHandler.getPlayerByID(playerID);
            if(isInValidPosition(target.getCell())){
                shoot.addPlayerTargetFromFireMode(target, true);
            }
            else{
                throw new WrongInputException(INVALID_TARGET);
            }
        }
    }

    protected boolean isInValidPosition(Cell cellOfTarget){
        return(gameHandler.getMap().getCellInDirection(author.getCell(),'N').contains(cellOfTarget) ||
            gameHandler.getMap().getCellInDirection(author.getCell(),'E').contains(cellOfTarget) ||
            gameHandler.getMap().getCellInDirection(author.getCell(),'S').contains(cellOfTarget) ||
            gameHandler.getMap().getCellInDirection(author.getCell(),'W').contains(cellOfTarget));
    }

}
