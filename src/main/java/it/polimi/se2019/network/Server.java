package it.polimi.se2019.network;

import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.remoteView.EnemyView;

public interface Server {

    /**
     * Send any kind of text to the user
     * @param string text to send
     */
    void send(String string);  // used by controller

    /**
     * Forward a map from virtual view to the client
     * @param map map copy to forward
     */
    void send(Map map);

    /**
     * Forward a cell from virtual view to the client
     * @param cell cell copy to forward
     */
    void send(Cell cell);

    /**
     * Forward a enemy copy from virtual view to the client
     * @param enemy enemy to send
     */
    void send(EnemyView enemy);

    /**
     * Forward a player copy from virtual view to the client
     * @param player player to send
     */
    void send(Player player);
}
