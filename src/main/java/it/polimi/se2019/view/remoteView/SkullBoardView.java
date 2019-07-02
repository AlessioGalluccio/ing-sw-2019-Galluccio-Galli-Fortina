package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.Observable;
import it.polimi.se2019.model.handler.Death;
import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.view.ModelViewMess.HandlerSkullViewMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class SkullBoardView extends Observable implements Observer {

    private int originalSkull;
    private int numSkullCopy = 8;
    private ArrayList<Death> deathCopy = new ArrayList<>();

    /**
     * Getter of deathCopy
     * @return deathCopy
     */
    public ArrayList<Death> getDeathCopy() {
        return deathCopy;
    }

    /**
     * Getter of numSkullCopy
     * @return numSkullCopy
     */
    public int getNumSkullCopy() {
        return numSkullCopy;
    }

    /**
     * Getter of originalSkull
     * @return originalSkull
     */
    public int getOriginalSkull() {
        return originalSkull;
    }

    /**
     * This method is called whenever the array of Death object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param   o     Always null
     * @param   arg   The message with which update the skull board
     */
    @Override
    public void update(java.util.Observable o /*Will be always null*/, Object arg) {
        HandlerSkullViewMessage message = (HandlerSkullViewMessage) arg;
        message.handleMessage(this);
        notifyObservers(message); //Forward message to client
    }

    /**
     * This method is call whenever the skull board changed.
     * @param originalSkull the nueber of skull at the beginning of the match
     * @param numSkullCopy the number of skull
     * @param deathCopy the new array of death who has changed
     */
    public void handleSkullMessage(int originalSkull, int numSkullCopy, List<Death> deathCopy) {
        this.originalSkull = originalSkull;
        this.numSkullCopy = numSkullCopy;
        this.deathCopy = (ArrayList<Death>) deathCopy;
    }

    /**
     * String the full skull board, representing each attributes with symbol and color
     * Work with UTF-8 and ANSI code
     * @return The representation of the skull board
     */
    @Override
    public String toString() {
        String s ="8 6 4 2 1 1 \t";
        for(Death death : deathCopy) {
            for(int i=0; i<death.getPoints(); i++) {
                s+= ConsoleColor.colorByColor(death.getWhoKilled().getCharacter().getColor()) + "○" + ConsoleColor.RESET;
            }
            s+=" ";
        }
        for(int i=0; i<numSkullCopy; i++) {
            s += ConsoleColor.RED + "† " + ConsoleColor.RESET;
        }
        return s;
    }
}
