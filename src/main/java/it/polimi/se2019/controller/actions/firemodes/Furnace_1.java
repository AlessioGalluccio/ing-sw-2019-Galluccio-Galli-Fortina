package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class Furnace_1 extends FireMode {
    private static final long serialVersionUID = 7284647777673664015L;
    //messages
    static final String PLAYER_OF_ROOM_REQUEST = "Select a player of a room. ";

    //errors
    static final String INVALID_ROOM = "You can't see this room. ";


    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE, PLAYER_OF_ROOM_REQUEST);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
        return list;
    }


    @Override
    public void fire() throws WrongInputException{
        fireFurnace(1,0);
    }


    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(!target.isVisibleBy(gameHandler.getMap(), author) ||
                target.getCell().getRoom().equals(author.getCell().getRoom())){
            throw new WrongInputException(INVALID_ROOM);
        }
        else {
            List<Player> players = target.getCell().getRoom().getPlayerHere();
            for(Player player : players){
                shoot.addPlayerTargetFromFireMode(player,true);
            }

        }
    }

    /**
     * method of fire for Furnace firemodes
     * @param damage the damage the targets will take
     * @param marks the marks the tagets will take
     * @throws WrongInputException if it can't do fire
     */
    protected void fireFurnace(int damage, int marks) throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            for(Player target : shoot.getTargetsPlayer()){
                addDamageAndMarks(target,damage,marks,true);
            }
            super.fire();
        }
    }

}
