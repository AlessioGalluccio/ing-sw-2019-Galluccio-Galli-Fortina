package it.polimi.se2019.model.player;


import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.CellMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;

public class Move extends Action {

    private int newCoordinateX;
    private int newCoordinateY;

    public Move(GameHandler gameHandler) {
        super(gameHandler);
    }

    @Override
    public void executeAction(Player author, ArrayList<ViewControllerMessage> msg) throws IllegalArgumentException{
        int i = 0;
        for(ViewControllerMessage arg : msg){
            if(arg.getMessageID() == getStringAndMessageExpected().get(i).getMessage().getMessageID()) {
                handle(arg);
            }
            else {
                //TODO controlla eccezione
                throw new IllegalArgumentException("Sequence of messages is incorrect");
            }
        }

        //modify the model
        //TODO quando avrai implementato Cell, sistemami!!

        //Cell newCell = getCellByCoordinate(newCooordinateX, newCoordinateY);
        //author.setCellPosition(newCell);


    }

    @Override
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {

        return super.getStringAndMessageExpected();
    }

    @Override
    public boolean verifyCorrectMessages(Player author, ArrayList<ViewControllerMessage> msg) {
        return super.verifyCorrectMessages(author, msg);
    }

    private void handle(CellMessage cell){
        newCoordinateX = cell.getX();
        newCoordinateY = cell.getY();
    }

    private void handle(ViewControllerMessage arg) {
        //TODO lancia eccezione
        throw new IllegalArgumentException();
    }


}
