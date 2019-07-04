package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.*;
import it.polimi.se2019.model.player.Player;

/**
 * This modality is for the 90% of the game.
 * Before the endgame you can have three 'normal' action.
 * This modality terminate when the number of skull is zero, at this point may begin the frenzy mode.
 * @author Galli
 */
public class Normal implements Modality {

    /**
     * Return the correct action base on modality (normal in this case) and life points of the player
     * @param actionID action which want the user
     * @param controller the controller linked to the user, this method need to know his damage (adrenaline action vs normal)
     * @param gameHandler game handler of the match that require the action
     * @throws WrongInputException if the actionID doesn't have a corresponding action
     * @return the correct action based on the life point of the player and the ID
     */
    //For frenzy action go in Frenzy class!
    @Override
    public Action getActionByID(int actionID, Controller controller, GameHandler gameHandler) throws WrongInputException{
        Player author = controller.getAuthor();
        int damage = author.getDamage().size();
        switch(actionID){
            case Identificator.MOVE:
                return new Move(gameHandler, controller);

            case Identificator.GRAB:
                if(damage > 2){
                    return new GrabAdrenaline(gameHandler, controller);
                }
                else{
                    return new Grab(gameHandler,controller);
                }

            case Identificator.SHOOT:
                if(damage < 6){
                    return new Shoot(gameHandler, controller);
                }
                else{
                    return new ShootAdrenaline(gameHandler, controller);
                }


            default: throw new WrongInputException(CANT_CHOOSE_ACTION);
        }

    }

    /**
     * Return true if is a frenzy modality, false otherwise
     * @return false
     */
    @Override
    public boolean isFrenzyEnable() {
        return false;
    }
}
