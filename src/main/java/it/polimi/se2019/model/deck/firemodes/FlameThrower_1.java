package it.polimi.se2019.model.deck.firemodes;


import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;
import java.util.ArrayList;
import java.util.List;

public class FlameThrower_1 extends FireMode {

    //firstly, we choose a direction of fire with CellMessage, then we select the targets inside

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        //TODO da fare!
        return null;
    }

    @Override
    public void sendPossibleTargetsAtStart() {

        //send the complete list
        sendPossibleTargetsCells(createListCellTargetsFlameThrower());
    }

    @Override
    public void fire() throws WrongInputException{
        if(!shoot.getTargetsPlayer().isEmpty()){
            for(Player target : shoot.getTargetsPlayer()){
                addDamageAndMarks(target,1,0, true);
            }
        }
        super.fire();
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        try{
            Cell cell = gameHandler.getCellByCoordinate(x,y);
            if(shoot.getTargetsCells() == null && createListCellTargetsFlameThrower().contains(cell)){
                shoot.addCellFromFireMode(cell);
            }
            else{
                throw new WrongInputException();
            }
        }catch (NotPresentException e){
            throw new WrongInputException();
        }

    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        //it's the first target and direction is the same of the cell choosen
        if(!shoot.getTargetsCells().isEmpty() && (shoot.getTargetsPlayer().isEmpty()) &&
                getDirectionMax2(target.getCell()) == getDirectionMax2(shoot.getTargetsCells().get(0))){
            shoot.addPlayerTargetFromFireMode(target);
        }
        //it's the second target. He is not in the same cell of the precedent
        else if(!shoot.getTargetsCells().isEmpty() && (!shoot.getTargetsCells().isEmpty()) &&
                shoot.getTargetsCells().size() == 1 &&
                getDirectionMax2(target.getCell()) == getDirectionMax2(shoot.getTargetsCells().get(0)) &&
                !(target.getCell().equals(shoot.getTargetsPlayer().get(0).getCell()))){
            shoot.addPlayerTargetFromFireMode(target);
        }
        else{
            throw new WrongInputException();
        }
    }



    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        throw new WrongInputException();
    }


    private ArrayList<Cell> createListCellTargetsFlameThrower(){
        Cell authorCell = author.getCell();
        ArrayList<Cell> cellTargets = new ArrayList<>();

        List<Cell> cellsAtDistance1And2 = gameHandler.getMap().getCellAtDistance(authorCell,1);
        cellsAtDistance1And2.addAll(gameHandler.getMap().getCellAtDistance(authorCell,2));

        List<Cell> cellsInDirection = gameHandler.getMap().getCellInDirection(authorCell,'N');
        cellsInDirection.addAll(gameHandler.getMap().getCellInDirection(authorCell,'E'));
        cellsInDirection.addAll(gameHandler.getMap().getCellInDirection(authorCell,'S'));
        cellsInDirection.addAll(gameHandler.getMap().getCellInDirection(authorCell,'W'));

        //intersection between cells at distance 1 and 2 and cells in directions
        for(Cell firstCell : cellsAtDistance1And2){
            for(Cell secondCell : cellsInDirection){
                if(firstCell.equals(secondCell)){
                    cellTargets.add(firstCell);
                }
            }
        }

        return cellTargets;
    }

    protected char getDirectionMax2(Cell cellTarget) throws WrongInputException{
        int x = cellTarget.getCoordinateX();
        int y = cellTarget.getCoordinateY();

        if(x == author.getCell().getCoordinateX() && (y == author.getCell().getCoordinateY() + 1) ||
                (y == author.getCell().getCoordinateY() + 2)){
            return 'N';
        }
        else if((x == author.getCell().getCoordinateX() + 1)||(x == author.getCell().getCoordinateX() + 2) &&
                (y == author.getCell().getCoordinateY())){
            return 'E';
        }
        else if(x == author.getCell().getCoordinateX() && (y == author.getCell().getCoordinateY() - 1) ||
                (y == author.getCell().getCoordinateY() - 2)){
            return 'S';
        }
        else if((x == author.getCell().getCoordinateX() - 1)||(x == author.getCell().getCoordinateX() - 2) &&
                (y == author.getCell().getCoordinateY())){
            return 'W';
        }
        else{
            throw new WrongInputException();
        }
    }
}
