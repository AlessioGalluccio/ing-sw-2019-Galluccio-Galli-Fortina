package it.polimi.se2019.controller.actions;

public class WrongInputException extends Exception{
    public WrongInputException(){
        super();
    }
    public WrongInputException(String s){
        super(s);
    }
}
