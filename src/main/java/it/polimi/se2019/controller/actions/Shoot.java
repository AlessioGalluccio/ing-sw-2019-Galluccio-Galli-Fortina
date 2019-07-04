package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.firemodes.FireMode;
import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;

/**
 * @author Galluccio
 */
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

    //errors
    static final String NEEDED_TARGET_FOR_TARGETING_BEFORE_OPTIONAL = "Finish Targeting before Optional, please. ";
    static final String SELECT_FIREMODE_BEFORE_OPTIONAL = "Select a a base firemode before an optional one. ";
    public static final String CANT_SELECT_FIREMODE = "You can't select this firemode. ";

    /**
     * constructor
     * @param gameHandler the gamehandler of the match
     * @param controller the controller of the player
     */
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
    public void addFireMode(int fireModeID) throws WrongInputException {
        fireModeID = Identificator.createFiremode(this.weapon.getID(), fireModeID);
        FireMode fireModeSelected = gameHandler.getFireModeByID(fireModeID);
        if(fireModeSelected != null){
            fireModeSelected.addShoot(this);

            AmmoBag newCost = AmmoBag.sumAmmoBag(this.cost, AmmoBag.createAmmoFromList(fireModeSelected.getCost()));
            if(this.fireMode == null && weapon != null && playerAuthor.canPayAmmo(newCost)){
                if(Identificator.containsFiremode(weapon, fireModeSelected)){
                    this.fireMode = fireModeSelected;
                    this.cost = newCost;
                    //adding expected messages of firemode before fire message
                    controller.addMessageListBeforeLastOne(fireMode.getMessageListExpected());

                }
                else{
                    throw new WrongInputException(CANT_SELECT_FIREMODE);
                }
            }
            else{
                throw new WrongInputException(CANT_SELECT_FIREMODE);
            }
        }
        else{
            throw new WrongInputException(CANT_SELECT_FIREMODE);
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
        if(neededTargetForTargeting){
            throw new WrongInputException(NEEDED_TARGET_FOR_TARGETING_BEFORE_OPTIONAL);
        }
        if(fireMode == null){
            throw new WrongInputException(SELECT_FIREMODE_BEFORE_OPTIONAL);
        }
        fireMode.addOptional(numOptional);
    }


    @Override
    public void fire() throws WrongInputException{
        if(fireMode != null){
            fireMode.fire();
        }
        else {
            throw new WrongInputException();
        }
    }

    //METHODS ONLY FOR FIREMODES

    /**
     * get the targets player added to shoot
     * @return an ArrayList of players already selected
     */
    public ArrayList<Player> getTargetsPlayer() {
        return targets;
    }

    /**
     * get the target cells added to shoot
     * @return an ArrayList of cells already selected
     */
    public ArrayList<Cell> getTargetsCells() {
        return cells;
    }

    /**
     * get the possible targets for a targeting scope for this action shoot
     * @return an ArrayList of possible targets for targeting scope in this action shoot
     */
    public ArrayList<Player> getCanBeTargetedPlayers() {
        return canBeTargetedPlayers;
    }

    /**
     * get the Optional firemodes selected
     * @return an ArrayList of the optional firemodes selected
     */
    public ArrayList<Integer> getOptionalSelected() {
        return optionalSelected;
    }

    /**
     * get the targeting Scope cards used inthis shoot action
     * @return an ArrayList of the targeting scope cards used
     */
    public ArrayList<PowerupCard> getTargetingScopeCards() {
        return targetingScopeCards;
    }

    /**
     * returns the weapon of the action
     * @return the weapon selected in this shoot action
     */
    public WeaponCard getWeapon(){return weapon;}

    /**
     * get the ordered list of players targeted by targeting scopes
     * @return ArrayList of Player
     */
    public ArrayList<Player> getTargetsOfTargetings() {
        return targetsOfTargetings;
    }

    @Override
    public AmmoBag getCost() {
        return cost;
    }

    /**
     * add a player to the targets
     * @param player the target
     * @param canBeTargeted true if the player can be targeted by a Targeting Scope, false if not
     */
    public void addPlayerTargetFromFireMode(Player player, boolean canBeTargeted){
        if(player == null){
            return;
        }
        targets.add(player);
        if(canBeTargeted){
            canBeTargetedPlayers.add(player);
        }
    }

    /**
     * add a selected cell from a firemode
     * @param cell the selected Cell from the firemode
     */
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

    /**
     * add an optional selected from firemode
     * @param numOptional the number of the optional firemode
     */
    public void addOptionalFromFiremode(int numOptional){
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
     * set this flag, if true, next PlayerMessage is for the Targeting, not the weapon
     * @param neededTargetForTargeting true PlayerMessage is for the Targeting, not the weapon, false if not
     */
    public void setNeededTargetForTargeting(boolean neededTargetForTargeting) {
        this.neededTargetForTargeting = neededTargetForTargeting;
    }
}
