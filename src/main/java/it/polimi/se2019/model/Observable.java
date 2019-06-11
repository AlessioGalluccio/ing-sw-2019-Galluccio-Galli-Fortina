package it.polimi.se2019.model;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import java.util.Observer;

public class Observable implements Serializable {
    private transient List<Observer> observers = new LinkedList<>();

    public void attach(Observer obv) {
        if(obv == null) throw new NullPointerException();
        if(!observers.contains(obv)) observers.add(obv);
    }

    public void detach(Observer obv) {
        observers.remove(obv);
    }

    public void clearObserver(){
        observers.clear();
    }

    public void notifyObservers(Object arg){
        for(Observer o : observers) {
            //update want java.util.Observer, we don't have it
            //DON'T CALL IT!!
            o.update(null, arg);
        }
    }

    public List<Observer> getObservers() {
        return new LinkedList<>(observers);
    }

}