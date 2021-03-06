package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.CellSpawn;
import it.polimi.se2019.model.map.NotCardException;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Galluccio
 */
public class Grab extends Action{
    private Cell cellObjective;
    private WeaponCard weaponToDiscard;
    private final int DISTANCE_MAX = 1;
    private boolean flagMustChooseWeapon;


    //messages
    public static final String CHOOSE_CELL = "Please, select a cell. ";

    //errors
    public static final String CHOOSE_WEAPON = "Choose a weapon to grab. ";
    public static final String DISCARD_WEAPON = "Discard a weapon. ";
    public static final String WEAPON_NOT_PRESENT_IN_PLAYER_GRAB = "You don't have this weapon. ";
    public static final String WEAPON_NOT_PRESENT_IN_CELL_GRAB = "This weapon is not present in the cell. ";
    public static final String CARD_NOT_PRESENT_IN_CELL_GRAB = "This card is not present in the cell. ";
    public static final String TOO_MUCH_DISTANCE = "This cell is too distant. ";
    public static final String CANT_PAY_THIS_WEAPON = "You can't pay this weapon. ";


    /**
     * constructor
     * @param gameHandler the gamehandler of the match
     * @param controller the controller of the player
     */
    public Grab(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
    }

    @Override
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        ArrayList<StringAndMessage> list = new ArrayList<>();
        StringAndMessage firstMessage = new StringAndMessage(Identificator.CELL_MESSAGE, CHOOSE_CELL);
        list.add(firstMessage);
        return list;
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        List<Cell> arrayCellsAtDistance = gameHandler.getMap().getCellAtDistance(playerAuthor.getCell(), getMaxDistance());
        try{
            if(arrayCellsAtDistance.contains(gameHandler.getCellByCoordinate(x,y))) {
                cellObjective = gameHandler.getCellByCoordinate(x, y);

                if(cellObjective.getCardID().isEmpty()) {
                    throw new WrongInputException(CARD_NOT_PRESENT_IN_CELL_GRAB);
                }
                else if (cellObjective.getCardID().size() == 1) {
                    //there's only one card to grab
                    grabForAmmoCell();

                }
                else {
                    //handling of WeaponCard
                    if (playerAuthor.getWeaponCardList().size() < 3) {
                        playerAuthor.setPosition(cellObjective);
                        controller.addMessageListExpected(new StringAndMessage(Identificator.WEAPON_MESSAGE, CHOOSE_WEAPON));
                        flagMustChooseWeapon = true;
                    } else {
                        //player must discard a weapon
                        playerAuthor.setPosition(cellObjective);
                        controller.addMessageListExpected(new StringAndMessage(Identificator.DISCARD_WEAPON_MESSAGE, DISCARD_WEAPON));
                        controller.addMessageListExpected(new StringAndMessage(Identificator.WEAPON_MESSAGE, CHOOSE_WEAPON));
                        flagMustChooseWeapon = true;
                    }
                }
            }
            else{
                throw new WrongInputException(TOO_MUCH_DISTANCE);
            }
        }catch(NotPresentException e){
            //should not happen
            throw new WrongInputException();
        }


    }

    @Override
    public void addWeapon(WeaponCard weaponCard) throws WrongInputException {

        if(flagMustChooseWeapon){
            try{
                playerAuthor.payAmmoCost(AmmoBag.createAmmoFromList(weaponCard.getBuyCost()));
                Card weapon = cellObjective.grabCard(weaponCard.getID(), weaponToDiscard);
                if(weaponToDiscard != null){
                    playerAuthor.discardCard(weaponToDiscard);
                }
                playerAuthor.addWeaponCard((WeaponCard) weapon);
            }
            catch (NotCardException e){
                throw new WrongInputException(WEAPON_NOT_PRESENT_IN_CELL_GRAB);
            }catch (NotEnoughAmmoException e){
                throw new WrongInputException(CANT_PAY_THIS_WEAPON);
            }catch (TooManyException e){
                //should never happen
            }catch (NotPresentException e){
                //should never happen
            }
        }
    }

    @Override
    public void addDiscardWeapon(WeaponCard weaponCard) throws WrongInputException {
        boolean itContains = false;
        for(WeaponCard weapon : playerAuthor.getWeaponCardList()){
            if(weapon.getID() == weaponCard.getID()){
                itContains = true;
            }
        }
        if(itContains){
            this.weaponToDiscard = weaponCard;
        }
        else{
            throw new WrongInputException(WEAPON_NOT_PRESENT_IN_PLAYER_GRAB);
        }
    }


    @Override
    public AmmoBag getCost() {
        return new AmmoBag(0,0,0);
    }

    /**
     * get the max distance of moving in this action
     * @return the integer of the max distance of moving
     */
    protected int getMaxDistance(){
        return DISTANCE_MAX;
    }

    /**
     * handles the grab action if the target cell is an AmmoCell
     */
    private void grabForAmmoCell(){
        playerAuthor.setPosition(cellObjective);
        try{
            AmmoCard cardObjective = (AmmoCard) cellObjective.grabCard(cellObjective.getCardID().get(0));
            cardObjective.useCard(playerAuthor);

        }catch(NotCardException e){
            //do nothing
        }catch (TooManyException e) {
            //do nothing
        }
    }
}
