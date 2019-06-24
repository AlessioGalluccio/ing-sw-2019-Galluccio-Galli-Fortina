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

public class ShockWave_1 extends FireMode {

    private static final long serialVersionUID = -7666019166677851905L;

    //messages
    public static final String SELECT_FIRST_PLAYER = "Select a first target player. ";
    public static final String SELECT_SECOND_PLAYER = "Select a second target player or fire. ";
    public static final String SELECT_THIRD_PLAYER = "Select a third target player or fire. ";

    //errors
    public static final String INVALID_TARGET = "Invalid target. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        ArrayList<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_FIRST_PLAYER));
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_SECOND_PLAYER));
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_THIRD_PLAYER));
        return list;
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        //TODO
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            for(Player target : shoot.getTargetsPlayer()){
                addDamageAndMarks(target,1,0,true);
            }
            super.fire();
        }
    }

    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        return list;
    }


    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        if(playerID == author.getID()){
            throw new WrongInputException(SELECTED_YOURSELF);
        }
        else {
            Player target = gameHandler.getPlayerByID(playerID);
            Cell cellOfTarget = target.getCell();
            if(gameHandler.getMap().getDistance(author.getCell(), cellOfTarget) == 1){
                if(shoot.getTargetsPlayer().isEmpty()){
                    shoot.addPlayerTargetFromFireMode(target,true);
                }
                else{
                    boolean invalidTarget = false;
                    for(Player otherTarget : shoot.getTargetsPlayer()){
                        if(otherTarget.getCell().equals(target.getCell())){
                            //it's in the same room of another player or it's already selected
                            invalidTarget = true;
                        }
                    }
                    if(invalidTarget){
                        //it's in the same room of another player or it's already selected
                        throw new WrongInputException(INVALID_TARGET);
                    }
                    else{
                        shoot.addPlayerTargetFromFireMode(target, true);
                    }
                }
            }
            else {
                throw new WrongInputException(INVALID_TARGET);
            }
        }



    }


}
