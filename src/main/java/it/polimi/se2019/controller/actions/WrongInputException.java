package it.polimi.se2019.controller.actions;

/**
 * @author Galluccio
 */
public class WrongInputException extends Exception{
    private static final long serialVersionUID = -71383657358367058L;

    /**
     * constructor
     */
    public WrongInputException(){
        super();
    }

    /**
     * constructor
     * @param s the message
     */
    public WrongInputException(String s){
        super(s);
    }
}
