package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;

public class Shoot extends Action{
    protected WeaponCard weapon;
    protected FireMode fireMode;
    protected ArrayList<Player> targets;
    protected ArrayList<Player> canBeTargetedPlayers;
    protected ArrayList<Integer> optionalSelected;
    protected ArrayList<Cell> cells;
    protected ArrayList<PowerupCard> targetingScopeCards;
    protected ArrayList<Player> targetsOfTargetings;
    protected AmmoBag cost;

    boolean neededTargetForTargeting;   //if true, next targetPlayer is for targeting, not weapon

    //STRING AND MESSAGE
    public static final String FIRST_MESSAGE = "Please, select a Weapon";
    public static final String SECOND_MESSAGE = "Please, select a Firemode";
    public static final String LAST_MESSAGE = "Please, press Fire";

    private String SELECT_TARGET_FOR_TARGETING = "Select a target for the Targeting Scope";
    public static final String NEEDED_TARGET_FOR_TARGETING_BEFORE_OPTIONAL = "Finish Targeting before Optional, please. ";

    //TODO manca WeaponCardMess per StringAndMessage !!!!!
    private final static StringAndMessage SECOND_STRING_AND_MESS = new StringAndMessage(Identificator.FIRE_MODE_MESSAGE, "Select a Firemode");

    public Shoot(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
        fireMode = null;
        this.cost = new AmmoBag(0,0,0);
        this.targetsOfTargetings = new ArrayList<>();
        this.targets = new ArrayList<>();
        this.cells = new ArrayList<>();
        this.optionalSelected = new ArrayList<>();
        this.targetingScopeCards = new ArrayList<>();
        this.canBeTargetedPlayers = new ArrayList<>();
        this.neededTargetForTargeting = false;
    }

    @Override
    public void executeAction() throws WrongInputException {
        super.executeAction();
    }

    @Override
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.WEAPON_MESSAGE, FIRST_MESSAGE);
        StringAndMessage secondMessage = new StringAndMessage(Identificator.FIRE_MODE_MESSAGE, SECOND_MESSAGE);
        StringAndMessage lastMessage = new StringAndMessage(Identificator.FIRE_MESSAGE, LAST_MESSAGE);

        ArrayList<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
        list.add(secondMessage);
        list.add(lastMessage);
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
            fireMode.addCell(x,y);
        }

    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        if(fireMode != null && !neededTargetForTargeting){
            fireMode.addPlayerTarget( playerID);
        }
        else if(fireMode != null && neededTargetForTargeting){
            //for weapons who doesn't shoot to single visible targets, you ask one more message to select the target
            //and you controll it with this method
            fireMode.addTargetForTargeting(playerID);
        }

    }

    @Override
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        if(fireMode != null){
            fireMode.addTargetingScope(targetingCardID, cost);
        }
    }

    @Override
    public void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {
        throw new WrongInputException();
    }

    @Override
    public void addFireMode(int fireModeID) throws WrongInputException {
        FireMode fireModeSelected = gameHandler.getFireModeByID(fireModeID);
        fireModeSelected.addShoot(this);

        AmmoBag newCost = AmmoBag.sumAmmoBag(this.cost, AmmoBag.createAmmoFromList(fireModeSelected.getCost()));
        if(this.fireMode == null && weapon != null && playerAuthor.canPayAmmo(newCost)){
            if(Identificator.containsFiremode(weapon, fireModeSelected)){
                this.fireMode = fireModeSelected;
                this.cost = newCost;
                //adding expected messages of firemode before fire message
                controller.addMessageListBeforeLastOne(fireMode.getMessageListExpected());

                //since firemode doesn't have a constructor, we need to send them here
                this.fireMode.sendPossibleTargetsAtStart();
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
        int weaponID = weaponCard.getID();
        if(this.weapon == null){
            WeaponCard weaponCardOfGame = gameHandler.getWeaponCardByID(weaponID);
            if(playerAuthor.getWeaponCardList().contains(weaponCardOfGame) && weaponCardOfGame.isReloaded()){
                this.weapon = weaponCardOfGame;
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
        //TODO
        if(neededTargetForTargeting){
            throw new WrongInputException(NEEDED_TARGET_FOR_TARGETING_BEFORE_OPTIONAL);
        }
        fireMode.addOptional(numOptional);
    }

    @Override
    public void addNope() throws WrongInputException {
        //TODO
    }

    public void fire() throws WrongInputException{
        if(fireMode != null){
            fireMode.fire();
        }
        else {
            throw new WrongInputException();
        }
    }

    //METHODS ONLY FOR FIREMODES

    public ArrayList<Player> getTargetsPlayer() {
        return targets;
    }

    public ArrayList<Cell> getTargetsCells() {
        return cells;
    }

    public ArrayList<Player> getCanBeTargetedPlayers() {
        return canBeTargetedPlayers;
    }

    public ArrayList<Integer> getOptionalSelected() {
        return optionalSelected;
    }

    public ArrayList<PowerupCard> getTargetingScopeCards() {
        return targetingScopeCards;
    }

    /**
     * get the ordered list of players targeted by targeting scopes
     * @return ArrayList of Player
     */
    public ArrayList<Player> getTargetsOfTargetings() {
        return targetsOfTargetings;
    }

    public AmmoBag getCost() {
        return cost;
    }

    /**
     * add a player to the targets
     * @param player the target
     * @param canBeTargeted true if the player can be targeted by a Targeting Scope, false if not
     */
    public void addPlayerTargetFromFireMode(Player player, boolean canBeTargeted){
        targets.add(player);
        if(canBeTargeted){
            canBeTargetedPlayers.add(player);
        }
    }

    public void addCellFromFireMode(Cell cell){
        cells.add(cell);
    }

    /**
     * Add a new targeting scope and set to true the flag that makes the next PlayerMessage
     * for the firemode, not the weapon. Add a next MessageExpected PlayerMessage
     * @param card  the TargetingScopeCard used
     */
    public void addTargetingScopeFromFireMode(PowerupCard card){
        targetingScopeCards.add(card);
        StringAndMessage stringAndMessage =
                new StringAndMessage(Identificator.PLAYER_MESSAGE, "Select a target for the Targeting Scope");
        ArrayList<StringAndMessage> list = new ArrayList<>();
        list.add(stringAndMessage);
        controller.addMessageListImmediateNext(list);
        neededTargetForTargeting = true;
    }

    /**
     * add a target for a targeting scope from a firemode and set to false the flag that makes the next PlayerMessage
     * for the firemode, not the weapon
     * @param player the target
     */
    public void addTargetForTargetingFromFiremode(Player player){
        targetsOfTargetings.add(player);
        neededTargetForTargeting = false;
    }

    public void addOptionalSelected(int numOptional){
        this.optionalSelected.add(numOptional);
    }

    /**
     * unique method for adding new cost of the action
     * @param ammoAdded amount added
     * @throws NotEnoughAmmoException if author can't pay the new cost
     */
    public void addCost(AmmoBag ammoAdded) throws NotEnoughAmmoException{
        AmmoBag oldCost = getCost();
        AmmoBag newCost = AmmoBag.sumAmmoBag(oldCost, ammoAdded);
        if(playerAuthor.canPayAmmo(newCost)){
            this.cost = newCost;
        }
        else{
            throw new NotEnoughAmmoException();
        }
    }

    /**
     * unique method for paying the AmmoBag cost
     * @throws NotEnoughAmmoException shouldn't be launched, cost is always controlled before beeing added
     */
    public void payActionCost() throws NotEnoughAmmoException{
        playerAuthor.payAmmoCost(this.cost);
    }

    /**
     * set this flag, if true, next PlayerMessage is for the Targeting, not the weapon
     * @param neededTargetForTargeting
     */
    public void setNeededTargetForTargeting(boolean neededTargetForTargeting) {
        this.neededTargetForTargeting = neededTargetForTargeting;
    }
}
