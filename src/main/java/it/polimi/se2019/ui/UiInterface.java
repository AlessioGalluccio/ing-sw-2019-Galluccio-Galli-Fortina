package it.polimi.se2019.ui;

public interface UiInterface {

    void login(boolean success, boolean isFirst);

    void selectedMap(int choosMap);

    void startGame();

    void disconnect();

    //TODO aggiungere metodo per gestire printFromController

    //TODO aggiungere metodi per stampare mappa, player, enemy etc...

}
