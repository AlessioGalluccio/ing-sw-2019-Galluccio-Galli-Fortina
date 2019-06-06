package it.polimi.se2019.network;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.remoteView.PlayerView;

public class WaintingPlayer {
    private Player player;
    private PlayerView playerView;
    private Controller controller;

    public WaintingPlayer(Player player, PlayerView playerView, Controller controller) {
        this.player = player;
        this.playerView = playerView;
        this.controller = controller;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public Controller getController() {
        return controller;
    }
}
