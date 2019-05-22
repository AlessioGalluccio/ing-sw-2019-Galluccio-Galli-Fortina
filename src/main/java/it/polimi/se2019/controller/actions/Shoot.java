package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.TargetingScopeCard;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class Shoot extends Action{
    protected WeaponCard weapon;
    protected FireMode fireMode;
    protected List<FireMode> fireModesUsed;
    protected List<Player> targets;
    protected List<Cell> cells;
    protected List<TargetingScopeCard> targetingScopeCards;
    protected AmmoBag cost;

    private String FIRST_MESSAGE = "Please, select a Weapon";
    private String SECOND_MESSAGE = "Please, select a Firemode";

    //TODO manca WeaponCardMess per StringAndMessage !!!!!
    private final static StringAndMessage SECOND_STRING_AND_MESS = new StringAndMessage(Identificator.FIRE_MODE_MESSAGE, "Select a Firemode", false);

    public Shoot(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
        fireMode = null;
    }

    @Override
    public void executeAction() throws WrongInputException {
        super.executeAction();
    }

    @Override
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.WEAPON_MESSAGE, FIRST_MESSAGE, false);
        StringAndMessage secondMessage = new StringAndMessage(Identificator.FIRE_MODE_MESSAGE, SECOND_MESSAGE, false);

        ArrayList<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
        list.add(secondMessage);
        return list;
    }

    @Override
    public boolean verifyCorrectMessages(Player author, ArrayList<ViewControllerMessage> msg) {
        return super.verifyCorrectMessages(author, msg);
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        if(fireMode == null){
            throw new WrongInputException();
        }
        else{
            //TODO
            //fireMode.addCell();
        }

    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        if(fireMode != null){
            fireMode.addPlayerTarget(this, playerID);
        }

    }

    @Override
    public void addTargetingScope(int targetingCardID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        if(fireMode != null){
            fireMode.addTargetingScope(this,targetingCardID);
            //TODO
        }
    }

    @Override
    public void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {
        throw new WrongInputException();
    }

    @Override
    public void addWeapon(int weaponID) throws WrongInputException {
        if(this.weapon == null){
            WeaponCard weaponCard = gameHandler.getWeaponCardByID(weaponID);
            if(playerAuthor.getWeaponCardList().contains(weaponCard) && weaponCard.isReloaded()){
                this.weapon = weaponCard;
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
    public void addFireMode(int fireModeID) throws WrongInputException {
        FireMode fireModeSelected = gameHandler.getFireModeByID(fireModeID);
        if(this.fireMode == null && weapon != null &&!fireModesUsed.contains(fireModeSelected)){
            if(weapon.getFireMode().contains(fireModeSelected)){
                this.fireMode = fireModeSelected;
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
    public void addWeapon(WeaponCard weaponCard) throws WrongInputException {
        //TODO
        if(this.weapon == null){
            this.weapon = weaponCard;
        }
        else{
            throw new WrongInputException();
        }
    }

    public List<Player> getTargets() {
        return targets;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<TargetingScopeCard> getTargetingScopeCards() {
        return targetingScopeCards;
    }

    public List<FireMode> getFireModesUsed() {
        return fireModesUsed;
    }

    public void addPlayerTargetFromFireMode(Player player){
        targets.add(player);
    }

    public void addCellFromFireMode(Cell cell){
        cells.add(cell);
    }

    public void addTargetingScopeFromFireMode(TargetingScopeCard card){
        targetingScopeCards.add(card);
    }

    public void addFireModeUsed(FireMode fireMode){
        fireModesUsed.add(fireMode);
    }
}
