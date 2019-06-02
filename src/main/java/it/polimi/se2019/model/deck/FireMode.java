package it.polimi.se2019.model.deck;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.AddActionMethods;
import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.firemodes.AddFireModeMethods;
import it.polimi.se2019.model.deck.firemodes.AddFireModeMethods;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import java.io.Serializable;
import java.util.*;

public abstract class FireMode implements AddFireModeMethods, Serializable {
    private ArrayList<ColorRYB> cost;
    private transient ArrayList<? extends Target> target;
    private String description;
    private int ID;
    private boolean used;
    private boolean primary;
    //added from addShoot
    protected transient Shoot shoot;
    protected transient GameHandler gameHandler;
    protected transient Controller controller;
    protected transient Player author;
    protected transient PlayerView playerView;

    private String NOT_PRESENT = "Can't do more damage to this player";
    private String TOO_MANY = "You have already three marks on this Player, you will not add more marks";
    private String OVERKILLED = "You have overkilled this player, you can't do more damage";
    private String KILLED = "You killed ths Player";


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
     *
     * @return Fire mode's description
     */
    public String getDescription() {
        return description;
    }

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
        //damage of targetings
        if(!shoot.getTargetsOfTargetings().isEmpty()){
            for(Player targetTargeting: shoot.getTargetsOfTargetings()){
                addDamageAndMarks(targetTargeting, 1,0);
            }
        }

        //payment of the total cost of this action
        try{
            author.payAmmoCost(shoot.getCost());
        }catch (NotEnoughAmmoException e){
            //it should never happen, because cost must always be controlled before
            throw new WrongInputException();
        }
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

    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException,
            NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        //Use Override if the firemode can't use Targeting scopes because it only adds marks
        PowerupCard card = gameHandler.getPowrupCardByID(targetingCardID);
        if(shoot.getTargetingScopeCards().contains(card)){
            throw new WrongInputException();
        }
        else if(!author.getPowerupCardList().contains(card)){
            throw new NotPresentException();
        }
        else if(author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), cost))){
            throw new NotEnoughAmmoException();
        }
        else{
            //I consider the target as the last selected Player
            int size = shoot.getTargetsPlayer().size();
            shoot.addTargetingScopeFromFireMode((PowerupCard)card, shoot.getTargetsPlayer().get(size));
            shoot.addCost(cost);
        }
    }

    /**
     * add damage and marks to a player
     * @param targetPlayer the player shooted
     * @param numDamage number of Damage to apply
     * @param numMarks number of Marks to apply
     */
    protected void addDamageAndMarks(Player targetPlayer, int numDamage, int numMarks){
        for(int i = 0; i < numDamage; i++){
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



    //COMMON METHODS

    protected void sendAllVisiblePlayers(ArrayList<Player> alreadySelected){
        ArrayList<Player> listTarget = new ArrayList<>();
        for(Player playerOfGame : gameHandler.getOrderPlayerList()){
            if(playerOfGame.getID() != this.author.getID() && playerOfGame.isVisibleBy(this.author)){
                if(alreadySelected == null || !alreadySelected.contains(playerOfGame)){
                    listTarget.add(playerOfGame);
                }
            }
        }
        sendPossibleTargetsPlayers(listTarget);
    }


}
