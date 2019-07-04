package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.CellSpawn;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.view.ModelViewMess.HandlerMapViewMessage;
import it.polimi.se2019.model.Observable;

import java.util.*;

/**
 * @author Galli
 * @author Fortina
 */
public class MapView extends Observable implements Observer {

    private Map mapCopy;
    private Cell[][] cells;

    /**
     * mapCopy getter
     * @return mapCopy
     */
    public Map getMapCopy() {
        return mapCopy;
    }

    /**
     * Return a single cell by its coordinate
     * @param x coordinate x
     * @param y coordinate y
     * @return The cell with X,Y as coordinate
     */
    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    /**
     * cells getter
     * @return cells
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Return only the spawn cells of the map
     * @return A list with all the spawn cell
     */
    public List<CellSpawn> getCellSpawn() {
        List<CellSpawn> cellSpawnList = new LinkedList<>();
        cellSpawnList.add((CellSpawn)cells[0][1]);
        cellSpawnList.add((CellSpawn)cells[2][2]);
        cellSpawnList.add((CellSpawn)cells[3][0]);
        return cellSpawnList;
    }

    /**
     * Receive message from the model and forward it to the networkHandler
     * @param o null
     * @param arg the message receive to forward
     */
    @Override
    public void update(java.util.Observable o /*Will be always null*/, Object arg) {
        HandlerMapViewMessage message = (HandlerMapViewMessage) arg;
        message.handleMessage(this);
        notifyObservers(message); //Forward message to client
    }

    /**
     * This method is call at the beginning of the game to set the map of the view.
     * @param m The map to set.
     */
    public void handleMapMessage(Map m) {
        mapCopy = m;
        cells = m.getCell();
    }

    /**
     * This method is call whenever a cell is changed.
     * Update the corresponding cell with the new one.
     * @param c The new cell changed.
     */
    public void handleCellMessage(Cell c) {
        cells[c.getCoordinateX()][c.getCoordinateY()] = c;
    }


    /**
     * Create a string of a specific row of the map.
     * Works with UTF-8 and ANSI code.
     * @param block the bloc of cell to print
     * @param row the line to print
     * @return The string to print
     */
    public String printRow(int block, int row) {
        String space = ConsoleColor.BLACK + "◙" + ConsoleColor.RESET;
        String s="";
        s+= ConsoleColor.RESET;
        if(row==3)s+=block + " "; //print coordinate
        else s+="  ";
        if(block!=-1) {
            s+=cells[0][block].printRow(row);
            s+=ConsoleColor.RESET;
            s+=cells[1][block].printRow(row);
            s+=ConsoleColor.RESET;
            s+=cells[2][block].printRow(row);
            s+=ConsoleColor.RESET;
            s+=cells[3][block].printRow(row);
            s+=ConsoleColor.RESET;
            if(row>1) s+=printWeapon(row, block);
            s+=ConsoleColor.RESET;
        }
        if(block==-1) s+=space +space +space + space +"0"+
                space +space +space +space + space +space +space +space + "1" +
        space +space +space +space + space +space +space +space + "2" +
        space +space +space +space + space +space +space +space +space + "3";
        return s;
    }

    /**
     * Create a string with the weapon on a specific spawn cell.
     * Works with UTF-8 and ANSI code.
     * @param row The row to print
     * @param cell The cell of which you wanna print the weapons
     * @return The string to print
     */
    private String printWeapon(int row, int cell) {
        String s="";
        CellSpawn cellRed = (CellSpawn) this.cells[0][1];
        CellSpawn cellBlue= (CellSpawn) this.cells[2][2];
        CellSpawn cellYellow= (CellSpawn) this.cells[3][0];
        List<WeaponCard> weaponBlue= new ArrayList<>(cellBlue.getWeapon());
        List<WeaponCard> weaponRed= new ArrayList<>(cellRed.getWeapon());
        List<WeaponCard> weaponYellow= new ArrayList<>(cellYellow.getWeapon());
        switch (row) {
            case 2:
                if(cell==2) s+=ConsoleColor.BLUE_BOLD+"\t Weapons on blue spawn cell:";
                if(cell==1) s+=ConsoleColor.RED_BOLD+"\t Weapons on red spawn cell:";
                if(cell==0) s+=ConsoleColor.YELLOW_BOLD+"\t Weapons on yellow spawn cell:";
                break;
            case 3:
                if(cell==2&&weaponBlue.get(0)!=null) s+="\033[22;37m\t\t0. " + weaponBlue.get(0).toStringShort();
                if(cell==1&&weaponRed.get(0)!=null) s+="\t\t0. " + weaponRed.get(0).toStringShort();
                if(cell==0&&weaponYellow.get(0)!=null) s+="\t\t0. " + weaponYellow.get(0).toStringShort();
                break;
            case 4:
                if(cell==2&&weaponBlue.get(1)!=null) {
                    s+="\033[22;37m\t\t1. " + weaponBlue.get(1).toStringShort();
                }
                if(cell==1&&weaponRed.get(1)!=null) s+="\t\t1. " + weaponRed.get(1).toStringShort();
                if(cell==0&&weaponYellow.get(1)!=null) s+="\t\t1. " + weaponYellow.get(1).toStringShort();
                break;
            case 5:
                if(cell==2&&weaponBlue.get(2)!=null) s+="\033[22;37m\t\t2. " + weaponBlue.get(2).toStringShort();
                if(cell==1&&weaponRed.get(2)!=null) s+="\t\t2. " + weaponRed.get(2).toStringShort();
                if(cell==0&&weaponYellow.get(2)!=null) s+="\t\t2. " + weaponYellow.get(2).toStringShort();
                break;
        }
        return s;
    }
}
