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

    protected static final int NORTH = 0;
    protected static final int EAST = 1;
    protected static final int SOUTH = 2;
    protected static final int WEST = 3;




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
                addDamageAndMarks(target,1,0);
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
                getDirection(target.getCell()) == getDirection(shoot.getTargetsCells().get(0))){
            shoot.addPlayerTargetFromFireMode(target);
        }
        //it's the second target. He is not in the same cell of the precedent
        else if(!shoot.getTargetsCells().isEmpty() && (!shoot.getTargetsCells().isEmpty()) &&
                shoot.getTargetsCells().size() == 1 &&
                getDirection(target.getCell()) == getDirection(shoot.getTargetsCells().get(0)) &&
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

        //NORTH
        try{
            Cell northCell = gameHandler.getCellByCoordinate(authorCell.getCoordinateX(),
                    authorCell.getCoordinateY() + 1);
            if(authorCell.getNorthBorder().isCrossable() && !northCell.getPlayerHere().isEmpty()){
                cellTargets.add(northCell);
            }
            //if I get an exception after this, I can fire anyway to the north target
            Cell moreNorthCell = gameHandler.getCellByCoordinate(authorCell.getCoordinateX(),
                    authorCell.getCoordinateY() + 2);
            if(northCell.getNorthBorder().isCrossable() && !moreNorthCell.getPlayerHere().isEmpty()){
                cellTargets.add(moreNorthCell);
            }

        }catch (NotPresentException e){
            //do nothing
        }

        //EAST
        try{
            Cell eastCell = gameHandler.getCellByCoordinate(authorCell.getCoordinateX() + 1,
                    authorCell.getCoordinateY());
            if(authorCell.getEastBorder().isCrossable() && !eastCell.getPlayerHere().isEmpty()){
                cellTargets.add(eastCell);
            }
            //if I get an exception after this, I can fire anyway to the east target
            Cell moreEastCell = gameHandler.getCellByCoordinate(authorCell.getCoordinateX() + 2,
                    authorCell.getCoordinateY());
            if(eastCell.getEastBorder().isCrossable() && !moreEastCell.getPlayerHere().isEmpty()){
                cellTargets.add(moreEastCell);
            }

        }catch (NotPresentException e){
            //do nothing
        }

        //SOUTH
        try{
            Cell southCell = gameHandler.getCellByCoordinate(authorCell.getCoordinateX(),
                    authorCell.getCoordinateY() - 1);
            if(authorCell.getSouthBorder().isCrossable() && !southCell.getPlayerHere().isEmpty()){
                cellTargets.add(southCell);
            }
            //if I get an exception after this, I can fire anyway to the east target
            Cell moreSouthCell = gameHandler.getCellByCoordinate(authorCell.getCoordinateX(),
                    authorCell.getCoordinateY() -2);
            if(southCell.getSouthBorder().isCrossable() && !moreSouthCell.getPlayerHere().isEmpty()){
                cellTargets.add(moreSouthCell);
            }

        }catch (NotPresentException e){
            //do nothing
        }

        //WEST
        try{
            Cell westCell = gameHandler.getCellByCoordinate(authorCell.getCoordinateX() - 1,
                    authorCell.getCoordinateY());
            if(authorCell.getWestBorder().isCrossable() && !westCell.getPlayerHere().isEmpty()){
                cellTargets.add(westCell);
            }
            //if I get an exception after this, I can fire anyway to the east target
            Cell moreWestCell = gameHandler.getCellByCoordinate(authorCell.getCoordinateX(),
                    authorCell.getCoordinateY() -2);
            if(westCell.getWestBorder().isCrossable() && !moreWestCell.getPlayerHere().isEmpty()){
                cellTargets.add(moreWestCell);
            }

        }catch (NotPresentException e){
            //do nothing
        }

        return cellTargets;
    }

    protected int getDirection(Cell cellTarget) throws WrongInputException{
        int x = cellTarget.getCoordinateX();
        int y = cellTarget.getCoordinateY();

        if(x == author.getCell().getCoordinateX() && (y == author.getCell().getCoordinateY() + 1) ||
                (y == author.getCell().getCoordinateY() + 2)){
            return NORTH;
        }
        else if((x == author.getCell().getCoordinateX() + 1)||(x == author.getCell().getCoordinateX() + 2) &&
                (y == author.getCell().getCoordinateY())){
            return EAST;
        }
        else if(x == author.getCell().getCoordinateX() && (y == author.getCell().getCoordinateY() - 1) ||
                (y == author.getCell().getCoordinateY() - 2)){
            return SOUTH;
        }
        else if((x == author.getCell().getCoordinateX() - 1)||(x == author.getCell().getCoordinateX() - 2) &&
                (y == author.getCell().getCoordinateY())){
            return WEST;
        }
        else{
            throw new WrongInputException();
        }
    }
}
