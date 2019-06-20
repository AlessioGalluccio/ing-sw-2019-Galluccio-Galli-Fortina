package it.polimi.se2019.controller.actions;

public class WrongInputException extends Exception{
    private static final long serialVersionUID = -71383657358367058L;

    public WrongInputException(){
        super();
    }
    public WrongInputException(String s){
        super(s);
    }
}
