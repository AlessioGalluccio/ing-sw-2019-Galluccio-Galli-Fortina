package it.polimi.se2019.network;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.remoteView.EnemyView;
import it.polimi.se2019.view.remoteView.PlayerView;

public class WaitingPlayer {
    private Player player;
    private PlayerView playerView;
    private Controller controller;
    private Server networkHandler;
    private EnemyView enemyView;

    public WaitingPlayer(Player player, PlayerView playerView, Controller controller, Server networkHandler, EnemyView enemyView) {
        this.player = player;
        this.playerView = playerView;
        this.controller = controller;
        this.networkHandler = networkHandler;
        this.enemyView = enemyView;
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

    public Server getNetworkHandler() {
        return networkHandler;
    }

    public EnemyView getEnemyView() {
        return enemyView;
    }
}
