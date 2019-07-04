package it.polimi.se2019.controller.actions;

/**
 * @author Galluccio
 */
public class FiremodeOfOnlyMarksException extends Exception {
    private static final long serialVersionUID = -671774939858275894L;

    /**
     * constructor
     */
    public FiremodeOfOnlyMarksException(){
        super();
    }

    /**
     * constructor
     * @param s the string message
     */
    public FiremodeOfOnlyMarksException(String s){
        super(s);
    }
}
