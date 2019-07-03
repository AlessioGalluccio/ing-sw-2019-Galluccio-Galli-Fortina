package it.polimi.se2019.model.handler;

public class WrongIdRuntimeException extends RuntimeException {
    /**
     * consturctor
     */
    public WrongIdRuntimeException(){
        super();
    }

    /**
     * constructor
     * @param s the message
     */
    public WrongIdRuntimeException(String s){
        super(s);
    }

}
