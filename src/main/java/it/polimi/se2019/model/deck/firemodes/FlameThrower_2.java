package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class FlameThrower_2 extends FlameThrower_1 {

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        //TODO da fare!
        return null;
    }

    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,2,0)); //cost of shooting base firemode
        return list;
    }


    @Override
    public void fire() throws WrongInputException{
        if(!shoot.getTargetsCells().isEmpty()){

            List<Cell> cellAtDistance1 = gameHandler.getMap().getCellAtDistance(author.getCell(), 1);
            List<Cell> cellAtDistance2 = gameHandler.getMap().getCellAtDistance(author.getCell(), 2);

            char direction = getDirectionMax2(shoot.getTargetsCells().get(0));
            List<Cell> cellInDirection = gameHandler.getMap().getCellInDirection(author.getCell(), direction);

            //we add 2 damage to all the players int the adjacent cell in that direction
            for(Cell firstCell : cellAtDistance1){
                for(Cell secondCell : cellInDirection){
                    if(firstCell.equals(secondCell)){
                        for (Player target : firstCell.getPlayerHere()){
                            addDamageAndMarks(target, 2,0, true);
                        }
                    }
                }
            }

            //we add 1 damage to all the target in that direction at distance 2
            for(Cell firstCell : cellAtDistance2){
                for(Cell secondCell : cellInDirection){
                    if(firstCell.equals(secondCell)){
                        for (Player target : firstCell.getPlayerHere()){
                            addDamageAndMarks(target, 1,0, true);
                        }
                    }
                }
            }

            commonEndingFire();

        }
        else{
            throw new WrongInputException();
        }


    }


    //THIS MUST BE OVERRIDED BECAUSE IT'S DIFFERENT FROM FLAAMETHROWER_1 !!!!!
    //YOU CAN'T USE THE DAFUALT METHOD OF FIREMODE BECAUSE IT EXTENDS FLAMETHROWER_1
    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        //we don't need to specify the targets. We shoot to them all
        throw new WrongInputException();
    }
}
