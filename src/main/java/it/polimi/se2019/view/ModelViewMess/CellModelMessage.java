package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.remoteView.MapView;

public class CellModelMessage implements ModelViewMessage, HandlerMapViewMessage  {
    Cell cellCopy;

    public CellModelMessage(Cell cellCopy) {
        this.cellCopy = cellCopy;
    }

    public Cell getCellCopy() {
        return cellCopy;
    }

    @Override
    public void handleMessage(MapView view) {
        view.handleCellMessage(cellCopy);
    }

    @Override
    public void handleMessage(Client client) {

    }
}
