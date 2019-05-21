package it.polimi.se2019.model.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.cloneable.SkinnyObjectExclusionStrategy;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Room;
import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ModelViewMess.SkullBoardMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.lang.reflect.Type;
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
    private int skull;

    //Implementato SOLO PER TESTING
    //TODO Da fare decenetemente
    //TODO sistemare gestione degli ID
    public GameHandler(List<Player> list, int skull) {
        this.orderPlayerList = (ArrayList) list;
        this.turn = 0;
        this.skull = skull;
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
        incrementTurn();  //I had to separate this method in order to improve efficiency test
        //TODO notify();
    }

    /**
     * Increment the turn parameter
     */
    protected void incrementTurn() { //I had to separate this method in order to improve efficiency test
        turn =  (turn == orderPlayerList.size()) ? 0 : turn+1;
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
     * @return all the players in order
     */
    public List<Player> getOrderPlayerList() {
        return new ArrayList<>(orderPlayerList);
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
                skull--;
                if(skull>=0) {
                    arrayDeath.add(new Death(orderPlayerList.get(turn), p));
                    notifyObservers(new SkullBoardMessage(skull, cloneDeath()));
                }
                cashPoint(p, doubleKill, false);
                death++;
                p.resetDamage();
                if(skull<=0) p.setFrenzyDeath(); //if skull <= is going to start frenzy mode, so who is dead now has to be flipped
                doubleKill=true;
            }
        }
        return death;
    }

    /**
     * HELPER
     * Sort list of layer according to their frequency in that list
     * @param listToOrdinate list of player which you want to ordinate by damage
     * @return A list with the same element of the @param but sorted
     */
    private List<Player> sortListByFrequency(List<Player> listToOrdinate) {
        HashMap<Integer, List<Player>> hm = new HashMap<>();

        //List of player who made damage to whoDied SORTED BY TIME OF DAMAGE!
        //(the first one is the first who made damage)
        List<Player> playerInDamage = new ArrayList<>();
        for (Player p : listToOrdinate) {
            if (!playerInDamage.contains(p)) {
                playerInDamage.add(p);
            }
        }

        for (Player p : playerInDamage) {
            int key = Collections.frequency(listToOrdinate, p);
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
        List<Integer> key = new ArrayList<>(hm.keySet());
        key.sort((a, b) -> b - a);

        //List of player who made damage SORTED BY NUMBER OF DAMAGE
        List<Player> playerByDamage = new ArrayList<>();
        for (Integer k : key) {
            playerByDamage.addAll(hm.get(k));
        }

        return playerByDamage;
    }

    /**
     * If some players have died this method calculate the point and give it to each player
     * @param whoDied the player who has died and has to be cash
     * @param doubleKill true if a player has killed more then one enemy in this turn
     * @param lastCash true if we are at the end of frenzy mode
     */
    protected void cashPoint(Player whoDied, boolean doubleKill, boolean lastCash) {
        List<Player> playerByDamage  = sortListByFrequency(whoDied.getDamage());

        if (whoDied.isFrenzyDeath()) {
            int point = 2;
            int howManyOne = 0;
            for (Player p : playerByDamage) {
                if(howManyOne > 3) break;
                p.addPoints(point + bonusPoint(p, whoDied, doubleKill, lastCash));
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
                p.addPoints(point + bonusPoint(p, whoDied, doubleKill, lastCash));
                point -= 2;
            }
        }
    }

    /**
     * Calculate haw many point go the player if he made some special damage
     * @param p who has to receive the point
     * @param whoDied who has died
     * @param doubleKill true if p has killed more then one enemy in this turn
     * @param lastCash true if we are at the end of frenzy mode
     * @return 3 if p is the first who shoot and overkill, 2 is p overkill or is the first who shoot and the killer, 1 if p is the first who shoot or the killer, 0 other way
     */
    private int bonusPoint(Player p, Player whoDied, boolean doubleKill, boolean lastCash) {
        List<Player> damage = whoDied.getDamage();
        Death lastDeath = arrayDeath.get(arrayDeath.size() - 1);

        //If is the second kill there is one extra point
        int secondKill = doubleKill ? 1 : 0;

        //If is frenzy death there's no point for the first blood
        int isFrenzyDeath = whoDied.isFrenzyDeath() ? -1 : 0;

        Player killer = lastCash ? null : lastDeath.getWhoKilled();

        int deadPoint = 0;
        if(skull>=0) deadPoint = lastDeath.getPoints(); //If is not after at the end of frenzy
        else if(whoDied.isDead()) deadPoint =  whoDied.isOverKilled() ? 2 : 1; //If is at the end of frenzy, is this case some one can be not dead

        //if p is the killer and the first
        if (damage.get(0).equals(p) && p.equals(killer)) return 1 + deadPoint + secondKill + isFrenzyDeath;
        //if p is the killer
        if (p.equals(killer)) return deadPoint + secondKill;
        //if p is the first
        if (damage.get(0).equals(p)) return 1 + isFrenzyDeath;
        return 0;
    }

    /**
     * At the end of the game each player receive points according to the killing he has done
     * This method calculate the points
     */
    public void cashSkullBoardPoint() {
        List<Player> skullBoardPlayer = new ArrayList<>();

        for(Death d : arrayDeath) {
            skullBoardPlayer.add(d.getWhoKilled());
            if(d.getPoints()==2) {
                skullBoardPlayer.add(d.getWhoKilled());
            }
        }

        List<Player> playerSkullBoard = sortListByFrequency(skullBoardPlayer);

        int point = 8;
        for (Player p : playerSkullBoard) {
            if (point < 1) point = 1;
            p.addPoints(point);
            point -= 2;
        }
    }

    /**
     * Return the list of player in order by point, the first one is the winner
     * If two player have the same point, win who had killed an enemy first
     * @return list of player in order by points
     */
    public List<Player> getRanking() {
        List<Player> ranking = new ArrayList<>(orderPlayerList);
        List<Player> killer = new ArrayList<>();

        if(!arrayDeath.isEmpty()) {
            for (Death d : arrayDeath) {
                killer.add(d.getWhoKilled());
            }
            ranking.sort((a,b) -> {
                if(a.getNumPoints() == b.getNumPoints()) {
                    return killer.indexOf(a) - killer.indexOf(b);
                } else return b.getNumPoints() - a.getNumPoints();
            }); //sort player by point
        } else {
            ranking.sort((a,b) -> b.getNumPoints()-a.getNumPoints());
        }

        return ranking;
    }

    private List<Death> cloneDeath() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new SkinnyObjectExclusionStrategy())
                .create();

        Type TYPE = new TypeToken<List<Death>>() {
        }.getType();

        return gson.fromJson(gson.toJson(this, TYPE), TYPE);
    }
}


