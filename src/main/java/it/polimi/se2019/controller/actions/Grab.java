package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.deck.Card;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.NotCardException;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class Grab extends Action{
    private Cell cellObjective;
    private Card cardObjective;
    private WeaponCard weaponToDiscard;
    private final int DISTANCE_MAX = 1;
    private boolean flagMustChooseWeapon;

    public static final String CHOOSE_WEAPON = "Choose a weapon to grab. ";
    public static final String DISCARD_WEAPON = "Discard a weapon. ";
    public static final String WEAPON_NOT_PRESENT_IN_PLAYER_GRAB = "You don't have this weapon. ";
    public static final String WEAPON_NOT_PRESENT_IN_CELL_GRAB = "This weapon is not present in the cell. ";
    public static final String CARD_NOT_PRESENT_IN_CELL_GRAB = "This card is not present in the cell. ";



    public Grab(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
    }

    @Override
    public void executeAction() throws WrongInputException {
        playerAuthor.setPosition(cellObjective);
        //TODO gestire prendere carte e munizioni
        try{
            cellObjective.grabCard(cardObjective.getID());
        }catch(NotCardException e){
            //it should be never launched here, because we already have cardObjective
            throw new WrongInputException(CARD_NOT_PRESENT_IN_CELL_GRAB);
        }
    }

    @Override
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        return super.getStringAndMessageExpected();
    }

    @Override
    public boolean verifyCorrectMessages(Player author, ArrayList<ViewControllerMessage> msg) {
        return super.verifyCorrectMessages(author, msg);
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        //TODO discutere sull'executeAction()
        List<Cell> arrayCellsAtDistance = gameHandler.getMap().getCellAtDistance(playerAuthor.getCell(), DISTANCE_MAX);
        try{
            if(arrayCellsAtDistance.contains(gameHandler.getCellByCoordinate(x,y))) {
                cellObjective = gameHandler.getCellByCoordinate(x, y);
                //there's only one card to grab
                if (cellObjective.getCardID().size() == 1) {
                    //TODO cardObjective = gameHandler.getCardByID(cellObjective.getCardID().get(0));
                    executeAction();
                } else {
                    //handling of WeaponCard
                    if (playerAuthor.getWeaponCardList().size() < 3) {
                        playerAuthor.setPosition(cellObjective);
                        controller.addMessageListImmediateNext(new StringAndMessage(Identificator.WEAPON_MESSAGE, CHOOSE_WEAPON));
                        flagMustChooseWeapon = true;
                    } else {
                        //player must discard a weapon
                        playerAuthor.setPosition(cellObjective);
                        controller.addMessageListImmediateNext(new StringAndMessage(Identificator.DISCARD_WEAPON_MESSAGE, DISCARD_WEAPON));
                        controller.addMessageListImmediateNext(new StringAndMessage(Identificator.WEAPON_MESSAGE, CHOOSE_WEAPON));
                        flagMustChooseWeapon = true;
                    }
                }
            }
            else{
                throw new WrongInputException();
            }
        }catch(NotPresentException e){
            //should not happen
            throw new WrongInputException();
        }


    }


    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        throw new WrongInputException();

    }

    @Override
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        throw new WrongInputException();
    }

    @Override
    public void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {
        throw new WrongInputException();
    }

    @Override
    public void addFireMode(int fireModeID) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addWeapon(WeaponCard weaponCard) throws WrongInputException {
        if(flagMustChooseWeapon){
            try{
                cellObjective.grabCard(weaponCard.getID(), weaponToDiscard);
            }
            catch (NotCardException e){
                throw new WrongInputException(WEAPON_NOT_PRESENT_IN_CELL_GRAB);
            }
        }
    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addNope() throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addDiscardWeapon(WeaponCard weaponCard) throws WrongInputException {
        if(playerAuthor.getWeaponCardList().contains(weaponCard)){
            this.weaponToDiscard = weaponCard;
        }
        else{
            throw new WrongInputException(WEAPON_NOT_PRESENT_IN_PLAYER_GRAB);
        }
    }

    @Override
    public void fire() throws WrongInputException {
        throw new WrongInputException();
    }
}
