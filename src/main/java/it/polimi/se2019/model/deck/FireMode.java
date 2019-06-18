package it.polimi.se2019.model.deck;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.firemodes.AddFireModeMethods;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;

import java.io.Serializable;
import java.util.*;

public abstract class FireMode implements AddFireModeMethods, Serializable {
    private ArrayList<ColorRYB> cost;
    private transient ArrayList<? extends Target> target;
    private int ID;
    private boolean used;
    private boolean primary;
    //added from addShoot
    protected transient Shoot shoot;
    protected transient GameHandler gameHandler;
    protected transient Controller controller;
    protected transient Player author;
    protected transient PlayerView playerView;

    private static final String NOT_PRESENT = "Can't do more damage to this player";
    private static final String TOO_MANY = "You have already three marks on this Player, you will not add more marks";
    private static final String OVERKILLED = "You have overkilled this player, you can't do more damage";
    private static final String KILLED = "You killed ths Player";

    private static final String NO_TARGET_TARGETING = "Select a target for your firemode before";
    private static final String CANT_PAY = "You don't have enough Ammo for this. ";

    protected static final String NO_TARGET_NO_ACTION = "No target available, action is aborted. ";

    //common used in firemodes
    protected static final String CANT_DO = "You can't do this.  ";
    protected static final String CELL_NOT_PRESENT = "This cell is not present on the map. ";
    protected static final String SELECTED_YOURSELF = "Error,you have selected yourself. ";
    protected static final String NOT_VISIBLE = "Error,this player is not visible. ";
    protected static final String CANT_DO_FIRE = "You can't do fire now. ";
    private static final String NO_VISIBLE_FOR_TARGETING = "No visible target for Targeting. ";
    private static final String INVALID_TARGET_FOR_TARGETING = "Invalid target for targeting scope. ";


    public GameHandler getGameHandler() {
        //TODO Eliminala, serve SOLO PER TESTING
        return gameHandler;
    }

    /**
     * Create a deep copy of cost
     * @return If the FireMode is free an empty list, else a deep copy of cost
     */
    public List<ColorRYB> getCost() {
        return cost==null ? new ArrayList<>() : new ArrayList<>(cost);     //basta una copia dell'array perchè ColorTYB è enum
    }

    /**
     *
     * @return Fire mode's ID
     */
    public int getID() {
        return ID;
    }

    /**
     * returns a list of AmmoBag. The first is the additional cost of the firemode (during shooting), the others are the optionals in sequence
     * @return
     */
    public abstract List<AmmoBag> costOfFiremodeNotReloading();

    /**
     *
     * @return true if it has been already used in this turn, else false
     */
    public boolean isUsed() {
        return used;
    }

    /**
     *
     * @return true if it is a primary fire mode (compulsory to use), else false
     */
    public boolean isPrimary() {
        return primary;
    }

    /**
     * Call it after adding shoot. It sends the first targets. The other will be send automatically
     * @reurns the targets
     */
    public abstract void sendPossibleTargetsAtStart();

    /**
     * Create and send message containing the possible targets (Players) to the view of player
     */
    protected void sendPossibleTargetsPlayers(ArrayList<Player> players){
        if(players != null){
            playerView.setPossibleTargets(players);
        }
    }

    /**
     * Create and send message containing the possible targets (Cells) to the view of player
     */
    protected void sendPossibleTargetsCells(ArrayList<Cell> cells){
        if(cells != null){
            playerView.setPossibleTargets(cells);
        }
    }



    /**
     * Fires to the target. Here we have the implementation for Targeting and pay Ammo cost, so use super.fire() in
     * subclasses!
     *
     */
    public void fire() throws WrongInputException{
        commonEndingFire();
    }

    @Override
    public void addNope() throws WrongInputException {
        shoot.endAction();
    }

    /**
     * Set targets in order to fire it
     * @param target the targets
     */
    public void setTarget(ArrayList<Target> target) {
        this.target = target;
    }

    public abstract List<StringAndMessage> getMessageListExpected();

    /**
     * MANDATORY after the firemode is created in action
     * @param shoot the action shoot
     */
    public void addShoot(Shoot shoot){
        this.shoot = shoot;
        this.controller = shoot.getController();
        this.gameHandler = shoot.getGameHandler();
        this.author = shoot.getPlayerAuthor();
        this.playerView = shoot.getPlayerView();
    }

    /**
     * add a targeting scope. Use this method only if weapon shhots only visible targets, otherwise override it
     * @param targetingCardID
     * @param cost
     * @throws WrongInputException
     * @throws NotPresentException
     * @throws NotEnoughAmmoException
     * @throws FiremodeOfOnlyMarksException
     */
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException,
            NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        //Use Override if the firemode can't use Targeting scopes because it only adds marks
        PowerupCard card = gameHandler.getPowerupCardByID(targetingCardID);
        if(shoot.getTargetingScopeCards().contains(card)){
            throw new WrongInputException();
        }
        else if(!author.containsPowerup(card)){
            throw new NotPresentException();
        }
        else if(!author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), cost))){
            throw new NotEnoughAmmoException(CANT_PAY);
        }
        else{
            boolean canTargeting = false;
            List<Player> list = shoot.getCanBeTargetedPlayers();
            if(list.isEmpty()){
                throw new WrongInputException(NO_TARGET_TARGETING);
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

    /**
     * add damage and marks to a player
     * @param targetPlayer the player shooted
     * @param numDamage number of Damage to apply
     * @param numMarks number of Marks to apply
     * @param useMarks true if you want to apply to damage the old marks, false if not (a targeting scope)
     */
    protected void addDamageAndMarks(Player targetPlayer, int numDamage, int numMarks, boolean useMarks){
        //TODO I must add also the damege of the previous marks!
        int numMakrsToConvert = 0;
        if(useMarks){
            numMakrsToConvert = author.getMark().getNumMarkDoneTo(targetPlayer);
            targetPlayer.removeMarkReceivedBy(author);
        }
        for(int i = 0; i < numDamage + numMakrsToConvert; i++){
            try{
                targetPlayer.receiveDamageBy(author);
            }catch (NotPresentException e){
                playerView.printFromController(NOT_PRESENT);
                break;
            }catch (YouOverkilledException e) {
                playerView.printFromController(OVERKILLED);
                break;
            }catch (YouDeadException e) {
                playerView.printFromController(KILLED);
                //We don't use break here. We can do one more damage to overkill the target
            }
        }
        for(int i = 0; i < numMarks; i++){
            try{
                targetPlayer.receiveMarkBy(author);
            }catch(TooManyException e){
                playerView.printFromController(TOO_MANY);
                break;
            }
        }
    }

    //DEFAULT METHODS THROW EXCEPTIONS TO LIMIT CODE DUPLICATION. USE OVERRIDE

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        throw new WrongInputException();
    }

    //USED ONLY IN SHOOT AND NOT IN OTHER ACTIONS

    public void addTargetForTargeting(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(shoot.getCanBeTargetedPlayers().contains(target) && target.isVisibleBy(author)){
            shoot.addTargetForTargetingFromFiremode(target);
        }
        else{
            throw new WrongInputException(INVALID_TARGET_FOR_TARGETING);
        }
    }


    //COMMON METHODS

    /**
     * send all visible players to the PlayerView only if there is at least one
     * @param alreadySelected ArrayList of all targets already selected
     * @return false if there is no target (and it doesn't send them), otherwise true and it sends them
     */
    protected boolean sendAllVisiblePlayers(ArrayList<Player> alreadySelected){
        ArrayList<Player> listTarget = new ArrayList<>();
        for(Player playerOfGame : gameHandler.getOrderPlayerList()){
            if(playerOfGame.getID() != this.author.getID() && playerOfGame.isVisibleBy(this.author)
                    && (alreadySelected == null || !alreadySelected.contains(playerOfGame))){

                listTarget.add(playerOfGame);

            }
        }
        if(listTarget.isEmpty()){
            return false;
        }
        else{
            sendPossibleTargetsPlayers(listTarget);
            return true;
        }

    }

    /**
     * send all players who are in the same cell of the author to the PlayerView only if there is at least one
     * @param alreadySelected ArrayList of all targets already selected
     * @return false if there is no target (and it doesn't send them), otherwise true and it sends them
     */
    protected boolean sendPlayersInYourCell(ArrayList<Player> alreadySelected){
        Cell commonCell = author.getCell();
        ArrayList<Player> listTarget = new ArrayList<>();
        for(Player playerOfGame : gameHandler.getOrderPlayerList()){
            if(playerOfGame.getID() != this.author.getID() && playerOfGame.getCell().equals(commonCell)
                    && (alreadySelected == null || !alreadySelected.contains(playerOfGame))){

                listTarget.add(playerOfGame);

            }
        }
        if(listTarget.isEmpty()){
            return false;
        }
        else{
            sendPossibleTargetsPlayers(listTarget);
            return true;
        }
    }


    /**
     * call it when you abort the firemode
     */
    public void endFiremode(){
        shoot.endAction();
    }

    /**
     * end of fire, call it if you can't use super.fire() in the firemode
     */
    public void commonEndingFire() throws WrongInputException{
        //damage of targetings
        if(!shoot.getTargetsOfTargetings().isEmpty()){
            for(Player targetTargeting: shoot.getTargetsOfTargetings()){
                addDamageAndMarks(targetTargeting, 1,0, false);
            }
        }

        //payment of the total cost of this action
        try{
            author.payAmmoCost(shoot.getCost());
            endFiremode();
        }catch (NotEnoughAmmoException e){
            //it should never happen, because cost must always be controlled before
            throw new WrongInputException();
        }
    }

    public void helperTargetingFiremodeNotVisibleTarget(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
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
        else{
            boolean canTargeting = false;
            List<Player> list = author.getCell().getPlayerHere();
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




}
