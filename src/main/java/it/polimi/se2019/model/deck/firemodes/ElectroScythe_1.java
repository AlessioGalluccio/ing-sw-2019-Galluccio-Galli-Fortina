package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.PowerupCard;
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

public class ElectroScythe_1 extends FireMode {
    private static final String SEND_FIRE = "Press fire to complete the action";

    private static final String NO_VISIBLE_FOR_TARGETING = "No visible target for Targeting. ";
    private static final String INVALID_TARGET_FOR_TARGETING = "Invalid target for targeting scope. ";
    private static final long serialVersionUID = 5053678759660132677L;

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage fireMessage = new StringAndMessage(Identificator.FIRE_MESSAGE, SEND_FIRE);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(fireMessage);

        for(Player target : gameHandler.getOrderPlayerList()){
            if(target.getCell().equals(author.getCell()) && target.getID() != author.getID()){
                shoot.addPlayerTargetFromFireMode(target, true);
            }
        }
        return list;
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        //TODO controlla, non dovrebbe mandare nessun target
        //do nothing
    }

    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        for(Player target : shoot.getTargetsPlayer()){
            addDamageAndMarks(target, 1,0, true);
        }
        super.fire();
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        throw new WrongInputException();
    }




    /*
    //COULD BE USEFUL FOR OTHER FIREMODES THAT HAVE TO SELECT A CELL TARGET

    @Override
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        PowerupCard card = gameHandler.getPowerupCardByID(targetingCardID);
        if(shoot.getTargetingScopeCards().contains(card)){
            throw new WrongInputException();
        }
        else if(!author.containsPowerup(card)){
            throw new NotPresentException();
        }
        else if(!author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), cost))){
            throw new NotEnoughAmmoException();
        }
        else if(shoot.getTargetsCells().isEmpty()){
            throw new WrongInputException();
        }
        else{
            boolean canTargeting = false;
            List<Player> list = shoot.getTargetsCells().get(0).getPlayerHere();
            if(list.isEmpty()){
                throw new WrongInputException(NO_VISIBLE_FOR_TARGETING);
            }
            for(Player target: list){
                if(target.isVisibleBy(author)){
                    canTargeting = true;
                }
            }
            if(!canTargeting){
                throw new WrongInputException(NO_VISIBLE_FOR_TARGETING);
            }
            else{
                shoot.addTargetingScopeFromFireMode((PowerupCard)card);
                shoot.addCost(cost);
            }
        }
    }

    @Override
    public void addTargetForTargeting(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(!shoot.getTargetsCells().isEmpty() && !shoot.getTargetsCells().get(0).getPlayerHere().isEmpty()
                &&shoot.getTargetsCells().get(0).getPlayerHere().contains(target)){
            shoot.addTargetForTargetingFromFiremode(target);
        }
        else{
            throw new WrongInputException(INVALID_TARGET_FOR_TARGETING);
        }
    }

    */

    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        throw new WrongInputException();
    }

}
