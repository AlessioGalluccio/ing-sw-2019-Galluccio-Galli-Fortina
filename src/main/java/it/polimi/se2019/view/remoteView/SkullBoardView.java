package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.Observable;
import it.polimi.se2019.model.handler.Death;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.view.ModelViewMess.HandlerSkullViewMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class SkullBoardView extends Observable implements Observer {

    private int numSkullCopy = 8;
    private ArrayList<Death> deathCopy = new ArrayList<>();

    public ArrayList<Death> getDeathCopy() {
        return deathCopy;
    }

    public int getNumSkullCopy() {
        return numSkullCopy;
    }

    @Override
    public void update(java.util.Observable o /*Will be always null*/, Object arg) {
        HandlerSkullViewMessage message = (HandlerSkullViewMessage) arg;
        message.handleMessage(this);
        notifyObservers(message); //Forward message to client
    }

    public void handleSkullMessage(int numSkullCopy, List<Death> deathCopy) {
        this.numSkullCopy = numSkullCopy;
        this.deathCopy = (ArrayList<Death>) deathCopy;
    }

    @Override
    public String toString() {
        int skull = numSkullCopy;
        String s ="8 6 4 2 1 1 \t";
        for(Death death : deathCopy) {
            skull--;
            for(int i=0; i<death.getPoints(); i++) {
                s+= ConsoleColor.colorByColor(death.getWhoKilled().getCharacter().getColor()) + "◉" + ConsoleColor.RESET;
            }
            s+=" ";
        }
        for(int i=0; i<skull; i++) {
            s += ConsoleColor.RED + "☠ " + ConsoleColor.RESET;
        }
        return s;
    }
}
