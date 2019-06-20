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

    private static final long serialVersionUID = 4760119110368705474L;
    private static String FIRST_MSG_STR = "Select a player target";


    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMsg = new StringAndMessage(Identificator.PLAYER_MESSAGE, FIRST_MSG_STR);
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
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().size() == 1){
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 3,0, true);
            super.fire();
        }
        else{
            throw new WrongInputException();
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player targetPlayer = gameHandler.getPlayerByID(playerID);
        if(shoot.getTargetsPlayer().isEmpty() && !targetPlayer.isVisibleBy(author)){
            shoot.addPlayerTargetFromFireMode(targetPlayer, true);
        }
        else {
            throw new WrongInputException();
        }

    }
}
