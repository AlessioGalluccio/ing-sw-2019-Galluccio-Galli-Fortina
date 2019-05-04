package it.polimi.se2019.model.handler;

public class WrongIdRuntimeException extends RuntimeException {
    public WrongIdRuntimeException(){
        super();
    }
    public WrongIdRuntimeException(String s){
        super(s);
    }

}
