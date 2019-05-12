package it.polimi.se2019.view;

import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import javafx.beans.Observable;

public interface View extends Observable {

    public void notifyObservers(ViewControllerMessage message);

}
