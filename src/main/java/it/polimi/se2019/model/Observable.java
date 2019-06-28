package it.polimi.se2019.model;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import java.util.Observer;

public class Observable implements Serializable {
    private static final long serialVersionUID = -4790956875928753691L;
    private transient List<Observer> observers = new LinkedList<>();

    /**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the set and it is not null.
     * The order in which notifications will be delivered to multiple
     * observers is not specified.
     *
     * @param   obv   an observer to be added.
     */
    public void attach(Observer obv) {
        if(obv == null) throw new NullPointerException();
        if(!observers.contains(obv)) observers.add(obv);
    }

    /**
     * Deletes an observer from the set of observers of this object.
     * @param   obv   the observer to be deleted.
     */
    public void detach(Observer obv) {
        observers.remove(obv);
    }

    /**
     * Notify all the observers.
     * Each observer has its <code>update</code> method called with two
     * arguments: null and the <code>arg</code> argument.
     * @param   arg   any object.
     */
    public void notifyObservers(Object arg){
        for(Observer o : observers) {
            //update want java.util.Observer, we don't have it
            //DON'T CALL IT!!
            o.update(null, arg);
        }
    }

    /**
     *
     * @return All the observable of this object
     */
    protected List<Observer> getObservers() {
        return new LinkedList<>(observers);
    }

}