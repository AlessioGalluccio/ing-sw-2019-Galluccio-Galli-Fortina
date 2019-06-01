package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class HeatSeeker_1 extends FireMode {

    private static String FIRST_MSG_STR = "Select a player target";
    private static boolean FIRST_MSH_BOOL = false;

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMsg = new StringAndMessage(Identificator.PLAYER_MESSAGE, FIRST_MSG_STR, FIRST_MSH_BOOL);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(firstMsg);
        return list;
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        //opposto di sendAllVisible
        ArrayList<Player> listTarget = new ArrayList<>();
        for(Player playerOfGame : gameHandler.getOrderPlayerList()){
            if(playerOfGame.getID() != this.author.getID() && !playerOfGame.isVisibleBy(this.author)){
                listTarget.add(playerOfGame);
            }
        }
        sendPossibleTargetsPlayers(listTarget);
    }

    @Override
    public void fire() throws WrongInputException{
        //TODO fare targetingScope e costo
        if(shoot.getTargetsPlayer().size() != 1){
            throw new WrongInputException();
        }
        else{
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 3,0);
        }
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player targetPlayer = gameHandler.getPlayerByID(playerID);
        if(shoot.getTargetsPlayer().isEmpty() && !targetPlayer.isVisibleBy(author)){
            shoot.addPlayerTargetFromFireMode(targetPlayer);
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
        throw new WrongInputException();
    }
}
