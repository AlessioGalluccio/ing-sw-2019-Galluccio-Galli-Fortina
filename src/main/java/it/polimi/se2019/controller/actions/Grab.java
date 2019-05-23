package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.deck.Card;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.NotCardException;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.WeaponIsLoadedException;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class Grab extends Action{
    private Cell cellObjective;
    private Card cardObjective;
    private final int DISTANCE_MAX = 1;



    public Grab(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
    }

    @Override
    public void executeAction() {
        playerAuthor.setPosition(cellObjective);
        //TODO gestire prendere carte e munizioni
        try{
            cellObjective.grabCard(cardObjective.getID());
        }catch(NotCardException e){
            //it should be never launched here, because we already have cardObjective
            playerView.printFromController("Error in execution of Grab action");
            throw new IllegalArgumentException();
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
        List<Cell> arrayCell = gameHandler.getMap().getCellAtDistance(playerAuthor.getCell(), DISTANCE_MAX);

        if(arrayCell.contains(gameHandler.getCellByCoordinate(x,y))) {
            cellObjective = gameHandler.getCellByCoordinate(x,y);
            //there's only one card to grab
            if(cellObjective.getCardID().size() == 1){
                //TODO cardObjective = gameHandler.getCardByID(cellObjective.getCardID().get(0));
                executeAction();
            }
            else{
                //TODO getsione WeaponCard
                /*
                controller.addMessageListExpected(Identificator.WEAPON_CARD, "Choose a Weapon", false);
                controller.sendTargetsToView();
                */
            }

        }
        else{
            throw new WrongInputException();
        }

    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        throw new WrongInputException();

    }

    @Override
    public void addTargetingScope(int targetingCardID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        throw new WrongInputException();
    }

    @Override
    public void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {
        throw new WrongInputException();
    }

    @Override
    public void addWeapon(int weaponID) throws WrongInputException {
        if(cellObjective == null || cellObjective.getCardID().size() == 1){
            throw new WrongInputException();
        }
        else{
            //TODO prendi carta arma
        }
    }

    @Override
    public void addFireMode(int fireModeID) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addWeapon(WeaponCard weaponCard) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addNope() throws WrongInputException {
        throw new WrongInputException();
    }
}
