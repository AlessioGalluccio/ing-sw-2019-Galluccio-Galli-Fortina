package it.polimi.se2019.ui;

public interface UiInterface {

    void login(boolean success, boolean isFirst);

    void selectedMap(int choosenMap);


    void startGame();

    //TODO aggiungere metodo per gestire printFromController

    //TODO aggiungere metodi per stampare mappa, player, enemy etc...

}
