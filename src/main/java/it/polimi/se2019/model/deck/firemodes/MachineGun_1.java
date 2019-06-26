package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class MachineGun_1 extends FireMode {

    //STRING AND MESSAGE
    private static final String FIRST_MSG_STR = "Select a player from possible targets";
    private static final String SECOND_MSG_STR = "Select a second player from possible targets";
    private static final String OPTIONAL_SECOND_MSG = "Select a third Player";

    //COSTS
    private static final AmmoBag COST_FIRST_OPTIONAL = new AmmoBag(0,1,0);
    private static final AmmoBag COST_SECOND_OPTIONAL = new AmmoBag(0,0,1);
    private static final long serialVersionUID = -5685357636737183420L;


    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> messageListExpected = new ArrayList<>();

        StringAndMessage firstTarget = new StringAndMessage(Identificator.PLAYER_MESSAGE,
                FIRST_MSG_STR);
        messageListExpected.add(firstTarget);

        StringAndMessage secondTarget = new StringAndMessage(Identificator.PLAYER_MESSAGE,
                SECOND_MSG_STR);
        messageListExpected.add(secondTarget);

        return messageListExpected;
    }


    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        list.add(COST_FIRST_OPTIONAL);
        list.add(COST_SECOND_OPTIONAL);
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException();
        }

        for(Player target: shoot.getTargetsPlayer()){
            addDamageAndMarks(target,1,0, true);
        }
        if(shoot.getOptionalSelected().contains(Identificator.FIRST_OPTIONAL)){
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 1,0, false);
        }
        if(shoot.getOptionalSelected().contains(Identificator.SECOND_OPTIONAL)){
            addDamageAndMarks(shoot.getTargetsPlayer().get(1), 1,0, false);
        }
        super.fire();
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        int targetID = target.getID();
        if(targetID == author.getID()){
            shoot.getPlayerView().printFromController("Error,you have selected yourself");
            throw new WrongInputException();
        }
        else if(target.isVisibleBy(gameHandler.getMap(), author)){
            if(shoot.getTargetsPlayer().isEmpty()){
                shoot.addPlayerTargetFromFireMode(target, true);
            }
            else if(shoot.getTargetsPlayer().size() < 2 && !shoot.getTargetsPlayer().contains(target)){
                shoot.addPlayerTargetFromFireMode(target, true);
            }
            else if(shoot.getTargetsPlayer().size() == 2 && !shoot.getTargetsPlayer().contains(target)
                        && shoot.getOptionalSelected().contains(Identificator.SECOND_OPTIONAL)){
                shoot.addPlayerTargetFromFireMode(target, true);
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
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        //TODO controlla
        if(numOptional == Identificator.FIRST_OPTIONAL
                && !shoot.getOptionalSelected().contains(Identificator.FIRST_OPTIONAL)){
            shoot.addOptionalSelected(Identificator.FIRST_OPTIONAL);
            shoot.addCost(new AmmoBag(0,1,0));
        }
        else if(numOptional == Identificator.SECOND_OPTIONAL
                && !shoot.getOptionalSelected().contains(Identificator.SECOND_OPTIONAL)
                && shoot.getTargetsPlayer().size() == 2){
                shoot.addOptionalSelected(Identificator.SECOND_OPTIONAL);
                StringAndMessage stringAndMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE,
                        OPTIONAL_SECOND_MSG);
                controller.addMessageListExpected(stringAndMessage);
                shoot.addCost(new AmmoBag(0,0,1));

        }
        else{
            throw new WrongInputException();
        }

    }
}
