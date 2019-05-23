package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class LockRifle_1 extends FireMode {
    private static final int NUM_DAMAGE = 2;
    private static final int NUM_MARK = 1;

    private static final String FIRST_MSG_STR = "Select a player from possible targets";
    private static final boolean FIRST_MSG_BOOL = false;

    @Override
    public List<Target> sendPossibleTarget() {
        List<Target> listTarget = new ArrayList<>();
        for(Player playerOfGame : gameHandler.getOrderPlayerList()){
            if(playerOfGame.getID() != this.author.getID() && playerOfGame.isVisibleBy(this.author)){
                listTarget.add(playerOfGame);
            }
        }
        //TODO invio dei target
        return listTarget;
    }

    @Override
    public void fire() throws WrongInputException {
        //TODO fare targetingScope
        addDamageAndMarks(shoot.getTargetsPlayer().get(0), 2,1);
        if(shoot.getTargetsPlayer().size() == 2){
            addDamageAndMarks(shoot.getTargetsPlayer().get(1), 0, 1);
        }
        //TODO pagamento costo ed eccezione
    }


    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> messageListExpected = new ArrayList<>();

        StringAndMessage firstTarget = new StringAndMessage(Identificator.PLAYER_MESSAGE, FIRST_MSG_STR, FIRST_MSG_BOOL);
        messageListExpected.add(firstTarget);

        return messageListExpected;
    }

    @Override
    public void addCell(Shoot shoot, int x, int y) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addPlayerTarget(Shoot shoot, int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        int targetID = target.getID();
        if(targetID == author.getID()){
            shoot.getPlayerView().printFromController("Error,you have selected yourself");
            throw new WrongInputException();
        }
        if(target.isVisibleBy(author)){
            if(shoot.getTargetsPlayer().isEmpty()){
                shoot.addPlayerTargetFromFireMode(target);
            }
            else if(shoot.getTargetsPlayer().size() == 1 && shoot.getTargetsPlayer().get(0).getID() != targetID
                    && shoot.getOptionalSelected().contains(Identificator.FIRST_OPTIONAL)){
                shoot.addPlayerTargetFromFireMode(target);
            }
            else{
                throw new WrongInputException();
            }

        }
        else{
            throw new WrongInputException();
        }

    }

    @Override
    public void addTargetingScope(Shoot shoot, int targetingCardID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        Player author = shoot.getPlayerAuthor();
        //TODO manca metodo per ottenere TargetingCard da ID
    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        if(numOptional == Identificator.FIRST_OPTIONAL && shoot.getOptionalSelected().isEmpty()){
            //TODO controlla costo
            shoot.addOptional(numOptional);
        }
        else{
            throw new WrongInputException();
        }
    }

    @Override
    public void addNope() throws WrongInputException {
        throw new WrongInputException();
    }

}


