package it.polimi.se2019.controller.actions;

public class WrongInputException extends Exception{
    WrongInputException(){
        super();
    }
    WrongInputException(String s){
        super(s);
    }
}
