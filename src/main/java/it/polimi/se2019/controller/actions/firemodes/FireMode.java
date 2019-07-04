package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.ModelViewMess.PlayerModelMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Galli
 * @author Galluccio
 */
public abstract class FireMode implements AddFireModeMethods, Serializable {
    private static final long serialVersionUID = 9095800883593243992L;
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

     static final String NOT_PRESENT = "Can't do more damage to this player";
    static final String TOO_MANY = "You have already three marks on this Player, you will not add more marks";
    static final String OVERKILLED = "You have overkilled this player, you can't do more damage";
    static final String KILLED = "You killed ths Player";

    static final String NO_TARGET_TARGETING = "Select a target for your firemode before";
    static final String CANT_PAY = "You don't have enough Ammo for this. ";


    //common used in firemodes
    public static final String CANT_DO = "You can't do this.  ";
    public static final String CELL_NOT_PRESENT = "This cell is not present on the map.";
    public static final String SELECTED_YOURSELF = "Error, you have selected yourself. ";
    public static final String NOT_VISIBLE = "Error, this player is not visible. ";
    public static final String CANT_DO_FIRE = "You can't do fire now. ";
    public static final String INVALID_TARGET_FOR_TARGETING = "Invalid target for targeting scope. ";


    /**
     * method used for testing
     * @return the GameHandler saved in the Firemode
     */
    public GameHandler getGameHandler() {
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
     * returns a list of AmmoBag. The first element is the additional cost of the firemode (during shooting),
     * the others are the optionals in sequence
     * This method is for ZERO COST. Override it if the weapon has a cost
     * @return a list with the ammo cost of the firemode
     */
    public List<AmmoBag> costAdditionalForFiremodeDuringShoot(){
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        return list;
    }

    /**
     * Fires to the target. It comprehends the implementation for Targeting and pay Ammo cost, so use super.fire() in
     * subclasses
     *
     */
    public void fire() throws WrongInputException{
        commonEndingFire();
    }


    /**
     * Set targets in order to fire it
     * @param target the targets
     */
    public void setTarget(ArrayList<Target> target) {
        this.target = target;
    }

    /**
     * returns the list of expected messages and requests of the firemode
     * @return a list of StringAndMessages of the expected messages and requests
     */
    public abstract List<StringAndMessage> getMessageListExpected();

    /**
     * MANDATORY after the firemode is created in action
     * it links the firemode to the shoot object
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
     * add a targeting scope. Use this method only if weapon shoots only visible targets, otherwise override it
     * @param targetingCardID the targeting scope card ID
     * @param cost the ammo used for this powerup
     * @throws WrongInputException if the action is invalid
     * @throws NotPresentException if the card is not present in the player
     * @throws NotEnoughAmmoException if the player has not enough ammo
     * @throws FiremodeOfOnlyMarksException if the firemode only gives marks to the target, so targeting can't be used
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
            List<Player> list = shoot.getCanBeTargetedPlayers();
            if(list.isEmpty()){
                throw new WrongInputException(NO_TARGET_TARGETING);
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
        int numMakrsToConvert = 0;
        if(useMarks && numDamage > 0){
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
                //we write in the playerView of the killed player
                gameHandler.getControllerByPlayer(targetPlayer).getPlayerView().printFromController(Identificator.YOU_DEAD);
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

    /**
     * add a target for targeting scope cards
     * @param playerID the target of the targeting
     * @throws WrongInputException if the target is not valid
     */
    public void addTargetForTargeting(int playerID) throws WrongInputException {
        Player targetOfTargeting = gameHandler.getPlayerByID(playerID);
        if(shoot.getCanBeTargetedPlayers().contains(targetOfTargeting)){
            shoot.addTargetForTargetingFromFiremode(targetOfTargeting);
        }
        else{
            throw new WrongInputException(INVALID_TARGET_FOR_TARGETING);
        }
    }


    //COMMON METHODS


    /**
     * end of fire, call it if you can't use super.fire() in the firemode
     * it handles the damage of targeting scope and it discards the targeting scope cards
     */
    protected void commonEndingFire() throws WrongInputException{
        //damage of targetings
        if(!shoot.getTargetsOfTargetings().isEmpty()){
            for(Player targetTargeting: shoot.getTargetsOfTargetings()){
                addDamageAndMarks(targetTargeting, 1,0, false);
            }
            for(PowerupCard card : shoot.getTargetingScopeCards()){
                try {
                    author.discardCard(card, true);
                } catch (NotPresentException e) {
                    Logger.getLogger(FireMode.class.getName()).log(Level.FINE, "This card is not present in the player");
                }
            }

        }

        //payment of the total cost of this action
        try{
            author.payAmmoCost(shoot.getCost());
            shoot.getWeapon().unload();
            author.notifyObservers(new PlayerModelMessage(author.clone()));
        }catch (NotEnoughAmmoException e){
            //it should never happen, because cost must always be controlled before
            throw new WrongInputException();
        }
    }




}
