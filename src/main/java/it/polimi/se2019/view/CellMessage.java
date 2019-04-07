package it.polimi.se2019.view;

public class CellMessage extends ViewControllerMessage {

    private int x;
    private int y;

    public CellMessage(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
