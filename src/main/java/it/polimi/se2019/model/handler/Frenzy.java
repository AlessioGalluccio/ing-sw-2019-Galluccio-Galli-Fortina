package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.*;
import it.polimi.se2019.model.player.Player;

/**
 * This modality is for the last part of the game
 * After the endgame you can have two or three 'frenzy' action
 * This modality terminate at the end of the game
 */
public class Frenzy implements Modality {

    /**
     * Return the correct action base on modality (normal in this case) and life points of the player
     * @param actionID action which want the user
     * @param controller the controller linked to the user, this method need to know his damage (adrenaline action vs normal)
     * @param gameHandler
     * @throws WrongInputException if the actionID doesn't have a corresponding action
     * @return the correct action based on the life point of the player and the ID
     */
    //For frenzy action go in Frenzy class!
    @Override
    public Action getActionByID(int actionID, Controller controller, GameHandler gameHandler) throws WrongInputException{
        Player author = controller.getAuthor();
        if(author.isFirstGroupFrenzy()){
            switch(actionID){
                case Identificator.MOVE:
                    return new MoveFrenzy(gameHandler, controller);

                case Identificator.GRAB:
                    return new GrabAdrenaline(gameHandler, controller); //it's the same of the adrenaline equivalent

                case Identificator.SHOOT:
                    return new ShootFrenzyGroup1(gameHandler, controller);

                default: throw new WrongInputException(CANT_CHOOSE_ACTION);
            }
        }
        else {
            switch(actionID){
                case Identificator.MOVE:
                    throw  new WrongInputException(CANT_CHOOSE_ACTION); //you can't select an action in frenzy group 2

                case Identificator.GRAB:
                    return new GrabFrenzy(gameHandler, controller); //it's the same of the adrenaline equivalent

                case Identificator.SHOOT:
                    return new ShootFrenzyGroup2(gameHandler, controller);

                default: throw new WrongInputException(CANT_CHOOSE_ACTION);
            }
        }
    }

    /**
     * Return true if is a frenzy modality, false otherwise
     * @return true
     */
    @Override
    public boolean isFrenzyEnable() {
        return true;
    }
}

