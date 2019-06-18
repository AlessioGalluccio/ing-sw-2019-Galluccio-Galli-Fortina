package it.polimi.se2019.ui;

import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.PowerupDeck;
import it.polimi.se2019.model.deck.WeaponDeck;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Map2;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ModelViewMess.MapMessage;
import it.polimi.se2019.view.ModelViewMess.PlayerModelMessage;
import it.polimi.se2019.view.clientView.ClientView;
import it.polimi.se2019.view.remoteView.EnemyView;
import it.polimi.se2019.view.remoteView.MapView;
import it.polimi.se2019.view.remoteView.SkullBoardView;
import org.junit.Before;
import org.junit.Test;

public class CLITest {
    private CLI ui;

    @Before
    public void setUp() throws Exception {
        ClientView clientView = new ClientView();
        ui = new CLI(clientView);
        ui.setSkullBoard(new SkullBoardView());
        Player player1 = new Player("Peter Parker", 0);
        Player player2 = new Player("Bruce Banner", 1);
        Player player3 = new Player("Tony Stark", 2);
        Player player4 = new Player("Steve Rogers", 3);
        player1.setCharacter(new Character("Spider-Man", "RED"));
        player2.setCharacter(new Character("Hulk", "GREEN"));
        player3.setCharacter(new Character("Iron Man", "Yellow"));
        player4.setCharacter(new Character("Cap America", "CYAN"));

        player1.receiveDamageBy(player2);
        player1.receiveDamageBy(player2);
        player1.receiveDamageBy(player2);
        player1.receiveDamageBy(player3);
        player1.receiveDamageBy(player3);
        player1.receiveMarkBy(player4);

        player2.receiveDamageBy(player4);
        player2.receiveDamageBy(player4);
        player2.receiveDamageBy(player2);
        player2.receiveDamageBy(player1);
        player2.receiveDamageBy(player1);
        player2.receiveDamageBy(player1);
        player2.receiveMarkBy(player4);
        player2.receiveMarkBy(player4);

        player4.receiveDamageBy(player1);
        player4.receiveDamageBy(player1);
        player4.receiveDamageBy(player1);

        EnemyView enemyView2 = new EnemyView("Bruce Banner");
        EnemyView enemyView3 = new EnemyView("Tony Stark");
        EnemyView enemyView4 = new EnemyView("Steve Rogers");

        clientView.update(null, new PlayerModelMessage(player1.clone()));
        enemyView2.update(null, new PlayerModelMessage(player2.clone()));
        enemyView3.update(null, new PlayerModelMessage(player3.clone()));
        enemyView4.update(null, new PlayerModelMessage(player4.clone()));
        ui.setEnemyView(enemyView2);
        ui.setEnemyView(enemyView3);
        ui.setEnemyView(enemyView4);

        MapView mapView = new MapView();
        Map map = new Map2(new WeaponDeck(), new AmmoDeck(new PowerupDeck()));
        map.reloadAllCell();
        mapView.update(null, new MapMessage(map.clone()));
        ui.setMapView(mapView);
    }

    @Test
    public void printAll() {
        ui.printAll();
    }
}