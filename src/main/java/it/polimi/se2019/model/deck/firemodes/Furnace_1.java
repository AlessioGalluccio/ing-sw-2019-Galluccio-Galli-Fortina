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

public class Furnace_1 extends FireMode {
    //messages
    public static final String PLAYER_OF_ROOM_REQUEST = "Select a player of a room. ";

    //errors
    public static final String INVALID_ROOM = "You can't select this room. ";


    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE, PLAYER_OF_ROOM_REQUEST);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
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
        //if I arrive here, shoo.getTargetsPlayer shouldn't be empty
        for(Player target : shoot.getTargetsPlayer()){
            addDamageAndMarks(target,1,0,true);
        }
        super.fire();
    }

    /*
    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(!target.isVisibleBy(author) || target.getCell().getRoom().equals(author.getCell().getRoom())){
            throw new WrongInputException(INVALID_ROOM);
        }
        else {
            for(Cell cell : //TODO tutte le celle della room){
                List<Player> players = cell.getPlayerHere();
                for(Player player : players){
                    shoot.addPlayerTargetFromFireMode(player,true);
                }
            }
        }
    }
    */
}
