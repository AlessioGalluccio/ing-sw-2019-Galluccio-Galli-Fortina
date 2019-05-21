package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.map.Cell;

public class CellModelMessage implements ModelViewMessage {
    Cell cellCopy;

    public CellModelMessage(Cell cellCopy) {
        this.cellCopy = cellCopy;
    }

    public Cell getCellCopy() {
        return cellCopy;
    }
}
