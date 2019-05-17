package it.polimi.se2019.model.handler;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Room;
import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;

public class GameHandler extends java.util.Observable {

    private AmmoDeck ammoDeck;
    private PowerupDeck powerupDeck;
    private PointDeck pointDeck;
    private WeaponDeck weaponDeck;
    private Map map;
    private ArrayList<Player> orderPlayerList;
    private ArrayList<PlayerView> playerViews;
    private int turn;
    private ArrayList<Death> arrayDeath = new ArrayList<>();
    private Modality modality;

    //Implementato SOLO PER TESTARE getPlayerByID()
    //Usato in TestPlayer -> testReceiveMark() per getMarkReveived e getMarkDone della classe Mark
    //TODO Da fare decenetemente
    //TODO sistemare gestione degli ID
    public GameHandler(ArrayList<Player> list) {
        this.orderPlayerList = list;
        this.turn = 0;
        this.weaponDeck = new WeaponDeck();
        this.powerupDeck = new PowerupDeck();
        this.ammoDeck = new AmmoDeck(powerupDeck);
    }

    /**
     * controlls if the game is finished
     *
     * @return 1 if the game is finished
     * 0 if not
     */
    public boolean isFinished() {

        return false; //TODO implementare
    }

    /**
     * Called when a player has finished his turn
     */
    public void nextTurn() {
        //TODO gestione delle morti(checkDeath + check respawn ??)
        Player player = orderPlayerList.get(turn);
        player.endTurnSetting();
        if (turn == orderPlayerList.size()) {
            turn = 0;
        } else {
            turn++;
        }
        //TODO notify();
    }

    /**
     * Called at the end of the game
     */
    public void finishGame() {

    }

    /**
     * @return ID of the Player of the current turn
     */
    public int getTurnPlayerID() {
        Player playerOfTurn = orderPlayerList.get(turn);
        return playerOfTurn.getID();
    }

    /**
     * @param id ID of a Player
     * @return the Player object who has this ID, null if there's not
     */
    public Player getPlayerByID(int id) {
        for (Player p : orderPlayerList) {
            if (p.getID() == id) return p;
        }
        return null;
    }

    /**
     * @param x coordinate x of the Cell
     * @param y coordinate x of the Cell
     * @return the Cell which has x and y coordinates, null if there's not
     */
    public Cell getCellByCoordinate(int x, int y) {
        return map.getCellByCoo(x, y);
    }

    /**
     * @param colorRoom color of the Room
     * @return the Room object that has that color
     */
    public Room getRoomByID(String colorRoom) {

        return null; //TODO implementare
    }

    /**
     * It generates an Action object with reference to this GameHandler
     *
     * @param actionID the int ID of the action
     * @return
     */
    public Action getActionByID(int actionID, Player author) {
        return modality.getActionByID(actionID, author, this);

    }

    /**
     * return all the players of the game
     *
     * @return all the players in order
     */
    public ArrayList<Player> getOrderPlayerList() {
        //TODO fai copia
        return orderPlayerList;
    }

    /**
     * Return the firemode corresponding to the id
     * @param fireModeID FireMode's id
     * @return the firemode with that id, null if there's not
     */
    public FireMode getFireModeByID(int fireModeID) {
            //FireMode id are written as WeaponID + id
            //So if weaponID is 17, his fireModeID is 171 or 172
            //172 / 10 = 17, the weaponID
            WeaponCard weapon = weaponDeck.getCardById(fireModeID / 10);
            if(weapon!=null) {
                for (FireMode fm : weapon.getFireMode())
                    if (fm.getID() == fireModeID) return fm;
            }
        return null;
    }

    /**
     * Return the card with that id
     *
     * @param cardId card's id
     * @return the card with that id
     */
    public AmmoCard getAmmoCardByID(int cardId) {
        return ammoDeck.getCardById(cardId);
    }

    /**
     * Return the card with that id
     *
     * @param cardId card's id
     * @return the card with that id
     */
    public PowerupCard getPowrupCardByID(int cardId) {
        return powerupDeck.getCardById(cardId);
    }

    /**
     * Return the card with that id
     *
     * @param cardId card's id
     * @return the card with that id
     */
    public WeaponCard getWeaponCardByID(int cardId) {
        return weaponDeck.getCardById(cardId);
    }

    public Map getMap() {
        return map;
    }

    /**
     * When a player pass his turn, this method check if someone has died
     * If his the case create a Death object, cash point, reset damage of the death and ask player to re-spawn
     * @return the number of death
     */
    protected int checkDeath() {
        int death = 0;
        boolean doubleKill = false;
        for (Player p : orderPlayerList) {
            if (p.isDead()) {
                //If someone has died, create a death object
                //The player of this turn is the killer
                arrayDeath.add(new Death(orderPlayerList.get(turn), p));
                cashPoint(p, doubleKill);
                death++;
                p.resetDamage();
                doubleKill=true;
            }
        }
        return death;
    }

    /**
     * HELPER
     * Create a HashMap to help cashPoint
     * @param whoDied the player who has died
     * @return HashMap with number of damage as key, list of player who made that damage as value
     */
    private HashMap<Integer, List<Player>> createHashMap(Player whoDied) {
        List<Player> damage = whoDied.getDamage();
        HashMap<Integer, List<Player>> hm = new HashMap<>();

        //List of player who made damage to whoDied SORTED BY TIME OF DAMAGE!
        //(the first one is the first who made damage)
        List<Player> playerInDamage = new ArrayList<>();
        for (Player p : damage) {
            if (!playerInDamage.contains(p)) {
                playerInDamage.add(p);
            }
        }

        for (Player p : playerInDamage) {
            int key = Collections.frequency(damage, p);
            if (hm.containsKey(key)) {
                List<Player> newValue = hm.get(key);
                newValue.add(p);
                hm.put(key, newValue);
            } else {
                List<Player> newValue = new ArrayList<>();
                newValue.add(p);
                hm.put(key, newValue);
            }
        }
        return hm;
    }

    /**
     * If some players have died this method calculate the point and give it to each player
     *
     * @param whoDied the player who has died and has to be cash
     */
    protected void cashPoint(Player whoDied, boolean doubleKill) {
        HashMap<Integer, List<Player>> hm = createHashMap(whoDied);

        List<Integer> key = new ArrayList<>(hm.keySet());
        key.sort((a, b) -> b - a);

        //List of player who made damage SORTED BY NUMBER OF DAMAGE
        List<Player> playerByDamage = new ArrayList<>();
        for (Integer k : key) {
            playerByDamage.addAll(hm.get(k));
        }

        if (whoDied.isFrenzyDeath()) {
            int point = 2;
            int howManyOne = 0;
            for (Player p : playerByDamage) {
                if(howManyOne > 3) break;
                p.addPoints(point + bonusPoint(p, whoDied.getDamage(), doubleKill));
                point = 1;
                howManyOne++;
            }
        } else {
            int point = 8 - whoDied.getSkull() * 2;
            int howManyOne = 0;
            for (Player p : playerByDamage) {
                if(howManyOne == 2) break;
                if (point < 1) {
                    point = 1;
                    howManyOne++;
                }
                p.addPoints(point + bonusPoint(p, whoDied.getDamage(), doubleKill));
                point -= 2;
            }
        }
    }

    /**
     * Calculate haw many point go the player if he made some special damage
     * @param p who has to receive the point
     * @param damage players in order to time of damage
     * @return 3 if p is the first who shoot and overkill, 2 is p overkill or is the first who shoot and the killer, 1 if p is the first who shoot or the killer, 0 other way
     *
     */
    private int bonusPoint(Player p, List<Player> damage, boolean doubleKill) {
        int secondKill = 0;
        if(doubleKill)  secondKill = 1;
        Death lastDeath = arrayDeath.get(arrayDeath.size() - 1);
        //if p is the killer and the first
        if (damage.get(0).equals(p) && lastDeath.getWhoKilled().equals(p))
            return 1 + lastDeath.getPoints() + secondKill;
        //if p is the killer
        if (lastDeath.getWhoKilled().equals(p)) return lastDeath.getPoints() + secondKill;
        //if p is the first
        if (damage.get(0).equals(p)) return 1;
        return 0;

        //TODO gestire se è l'ultimo incasso (in questo caso non è morto nessuno!)
    }
}

