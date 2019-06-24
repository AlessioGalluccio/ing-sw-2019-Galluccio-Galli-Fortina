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

public class RailGun_2 extends RailGun_1 {

    private static final long serialVersionUID = 3954418059011118447L;

    public static final String PLAYER_ALREADY_SELECTED = "This player is already selected. ";
    public static final String SELECT_SECOND_PLAYER = "Select another target player. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER_RAILGUN));
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_SECOND_PLAYER));
        return list;
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        //TODO
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
            for(Player target : shoot.getTargetsPlayer()){
                addDamageAndMarks(target, 2, 0, true);
            }
        }
        commonEndingFire();
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        if(!shoot.getTargetsPlayer().contains(gameHandler.getPlayerByID(playerID))){
            super.addPlayerTarget(playerID);
        }
        else{
            throw new WrongInputException(PLAYER_ALREADY_SELECTED);
        }
    }

    @Override
    protected boolean isInValidPosition(Cell cellOfTarget){
        int authorX = author.getCell().getCoordinateX();
        int autorY = author.getCell().getCoordinateY();

        int targetX = cellOfTarget.getCoordinateX();
        int targetY = cellOfTarget.getCoordinateY();

        if(authorX == targetX + 1 && autorY == targetY){
            return true;
        }
        else if(authorX == targetX - 1 && autorY == targetY){
            return true;
        }
        else if(authorX == targetX && autorY == targetY + 1){
            return true;
        }
        else if(authorX == targetX && autorY == targetY - 1){
            return true;
        }
        else if(authorX == targetX + 2 && autorY == targetY){
            return true;
        }
        else if(authorX == targetX - 2 && autorY == targetY){
            return true;
        }
        else if(authorX == targetX && autorY == targetY + 2){
            return true;
        }
        else if(authorX == targetX && autorY == targetY - 2){
            return true;
        }
        else{
            return false;
        }
    }

}
