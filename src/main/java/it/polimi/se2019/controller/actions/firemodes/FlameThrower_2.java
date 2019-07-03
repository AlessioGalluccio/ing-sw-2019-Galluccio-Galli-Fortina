package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class FlameThrower_2 extends FlameThrower_1 {

    private static final long serialVersionUID = -8030079729591996463L;

    private List<Player> targetsDamage1 = new ArrayList<>();
    private List<Player> targetsDamage2 = new ArrayList<>();
    //errors
    static final String TARGET_NOT_NEEDED = "This target is not needed. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.CELL_MESSAGE, CHOOSE_CELL_DIRECTION));
        return list;

    }

    @Override
    public List<AmmoBag> costAdditionalForFiremodeDuringShoot() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,2,0)); //cost of shooting base firemode
        return list;
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        super.addCell(x, y);

        //FOR TARGETING

        //getCellAtDistance give us all the cell in distance equal or minor the number, so we need to subtract them
        List<Cell> cellAtDistance1 = gameHandler.getMap().getCellAtDistance(author.getCell(), 1);
        cellAtDistance1.removeAll(gameHandler.getMap().getCellAtDistance(author.getCell(), 0));
        List<Cell> cellAtDistance2 = gameHandler.getMap().getCellAtDistance(author.getCell(), 2);
        cellAtDistance2.removeAll(gameHandler.getMap().getCellAtDistance(author.getCell(), 1));

        char direction = getDirectionMax2(shoot.getTargetsCells().get(0));
        List<Cell> cellInDirection = gameHandler.getMap().getCellInDirection(author.getCell(), direction);

        //we add 2 damage to all the players int the adjacent cell in that direction
        for(Cell firstCell : cellAtDistance1){
            for(Cell secondCell : cellInDirection){
                if(firstCell.equals(secondCell)){
                    for (Player target : firstCell.getPlayerHere()){
                        shoot.addPlayerTargetFromFireMode(target, true);
                    }
                }
            }
        }

        //we add 1 damage to all the target in that direction at distance 2
        for(Cell firstCell : cellAtDistance2){
            for(Cell secondCell : cellInDirection){
                if(firstCell.equals(secondCell)){
                    for (Player target : firstCell.getPlayerHere()){
                        shoot.addPlayerTargetFromFireMode(target, true);
                    }
                }
            }
        }
    }

    @Override
    public void fire() throws WrongInputException{
        if(!shoot.getTargetsCells().isEmpty()){

            //getCellAtDistance give us all the cell in distance equal or minor the number, so we need to subtract them
            List<Cell> cellAtDistance1 = gameHandler.getMap().getCellAtDistance(author.getCell(), 1);
            cellAtDistance1.removeAll(gameHandler.getMap().getCellAtDistance(author.getCell(), 0));
            List<Cell> cellAtDistance2 = gameHandler.getMap().getCellAtDistance(author.getCell(), 2);
            cellAtDistance2.removeAll(gameHandler.getMap().getCellAtDistance(author.getCell(), 1));

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
            if(!targetsDamage2.isEmpty()){
                for(Player target: targetsDamage2){
                    addDamageAndMarks(target,2,0, true);
                }
            }

            commonEndingFire();

        }
        else{
            throw new WrongInputException(CANT_DO_FIRE);
        }


    }


    //THIS MUST BE OVERRIDED BECAUSE IT'S DIFFERENT FROM FLAAMETHROWER_1 !!!!!
    //YOU CAN'T USE THE DEFAULT METHOD OF FIREMODE BECAUSE IT EXTENDS FLAMETHROWER_1
    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        //we don't need to specify the targets. We shoot to them all
        throw new WrongInputException(TARGET_NOT_NEEDED);
    }
}
