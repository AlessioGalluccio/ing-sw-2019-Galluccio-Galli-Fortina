package it.polimi.se2019.ui;

import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.clientView.ClientEnemyView;
import it.polimi.se2019.view.remoteView.EnemyView;
import it.polimi.se2019.view.remoteView.MapView;
import it.polimi.se2019.view.remoteView.SkullBoardView;

import java.util.List;

/**
 * @author Galli
 * @author Fortina
 */
public interface UiInterface {

    /**
     * Notify the users about the success of the login
     * @param success true if the login works correctly, false otherwise
     * @param isFirst true if the user is the first of the match, false otherwise
     */
    void login(boolean success, boolean isFirst);

    /**
     * Notify the users about the starting of the game
     */
    void startGame();

    /**
     * Notify the users they were be disconnected from the match
     * @param matchID tha ID of the match they were playing
     */
    void disconnect(int matchID);

    /**
     * A usual setter for the skullBoardView attribute
     * @param skullBoardView the skullBoardView to set
     */
    void setSkullBoard(SkullBoardView skullBoardView);

    /**
     * A usual setter for the mapView attribute
     * @param mapView the mapView to set
     */
    void setMapView(MapView mapView);

    /**
     * A usual setter for the enemyView attribute
     * @param enemyView the enemyView to set
     */
    void setEnemyView(EnemyView enemyView);

    /**
     * Print a string from the controller
     * @param message The message to print to the users
     */
    void printFromController(String message);

    /**
     * Update a specific cell of the map
     * @param cell the cell which has changed and has to be updated
     */
    void updateCell(Cell cell);

    /**
     * Update the player
     */
    void updatePlayer();

    /**
     * Update a specific enemy
     * @param enemyView the enemy to updte
     */
    void updateEnemy(ClientEnemyView enemyView);

    /**
     * Update the skull board
     */
    void updateSkullBoard();

    /**
     * Notify the users about the ending of the game
     * This method prints the ranking of the game
     * @param players The list of player on order of ranking
     * @throws Exception If something goes wrong which JavaFX
     */
    void printRanking(List<Player> players) throws Exception;

    /**
     * Notify the users a new turn as begin
     * @param nickname The nickname of the player whose turn it is
     * @param yourTurn True if is your turn
     */
    void turn(String nickname, boolean yourTurn);

    /**
     * Notify the users they has to choose a character
     * @param characters a list of available character from which to choose
     * @throws Exception If something goes wrong which JavaFX
     */
    void chooseCharacter(List<Character> characters) throws Exception;
}
