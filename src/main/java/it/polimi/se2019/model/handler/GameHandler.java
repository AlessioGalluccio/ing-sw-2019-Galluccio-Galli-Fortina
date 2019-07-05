package it.polimi.se2019.model.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.cloneable.SkinnyObjectExclusionStrategy;
import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.EmptyControllerState;
import it.polimi.se2019.controller.FirstTurnState;
import it.polimi.se2019.controller.MustRespawnControllerState;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.Observable;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.controller.actions.firemodes.FireMode;
import it.polimi.se2019.model.map.*;
import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;
import it.polimi.se2019.network.WaitingRoom;
import it.polimi.se2019.view.ModelViewMess.*;
import it.polimi.se2019.view.configureMessage.StartGameMessage;
import it.polimi.se2019.view.remoteView.EnemyView;
import it.polimi.se2019.view.remoteView.MapView;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.remoteView.SkullBoardView;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Galli
 */
public class GameHandler extends Observable {

    private int matchID;
    private AmmoDeck ammoDeck;
    private PowerupDeck powerupDeck;
    private WeaponDeck weaponDeck;
    private Map map;
    private List<Player> orderPlayerList = new ArrayList<>();
    private List<PlayerView> playerViews = new ArrayList<>();
    private int turn;
    private List<Death> arrayDeath = new ArrayList<>();
    private Stack<Player> justDied = new Stack<>();
    private transient Modality modality;
    private int skull;
    private int originalSkull;
    private Player firstFrenzyPlayer;
    private boolean suddenDeath;
    private boolean firstTurn = true;
    private transient List<Controller> controllers = new ArrayList<>();

    //Used only for testing
    public GameHandler(List<Player> list, int skull) {
        this.orderPlayerList = list;
        this.turn = 0;
        this.skull = skull;
        this.weaponDeck = new WeaponDeck();
        this.powerupDeck = new PowerupDeck();
        this.ammoDeck = new AmmoDeck(powerupDeck);
        this.modality = new Normal();
    }

    public GameHandler(int matchID) {
        this.weaponDeck = new WeaponDeck();
        this.powerupDeck = new PowerupDeck();
        this.ammoDeck = new AmmoDeck(powerupDeck);
        this.matchID = matchID;
        this.modality = new Normal();
    }

    /**
     * Called when a player has finished his turn
     */
    public void nextTurn() {
        Player player = orderPlayerList.get(turn);
        player.endTurnSetting();
        if(player.isConnected()) getViewByPlayer(player).setTimer(false);
        checkDeath(); //If someone is dead cash his point, add revenge mark and add him to the stack of just dead
        if(justDied.isEmpty()) {
            do {
                incrementTurn();  //I had to separate this method in order to improve efficiency test
            }while(!orderPlayerList.get(turn).isConnected()); //If is disconnected, increment turn
            setNewTurn();
            Controller controller = getControllerByPlayer(orderPlayerList.get(turn));

            //it's the first turn, or the player has disconnected during the spawn, so he doesn't have a position
            //but he is not dead
            if(firstTurn ||controller.getAuthor().getCell() == null) {
                controller.setState(new FirstTurnState(controller, this));
            } else controller.setState(new EmptyControllerState(controller, this));
        }
        else {
            //only the first one will get the modified state. When he will finish, he will do nextTurn, and the function
            //will come back here
            if(justDied.get(0).isConnected()){
                Controller controller = getControllerByPlayer(justDied.get(0));
                controller.setState(new MustRespawnControllerState(controller, this));
            }
            else{
                randomRespawnNotConnectedPlayer(justDied.get(0));
            }
        }
    }

    /**
     * Set everything necessary for a new turn:
     * <li> Reload of cell of the map;</>
     * <li> Notify the user of the change of the turn</>
     * <li> If is the case, set the frenzy</>
     * <li> If is the case, end the game</>
     */
    private void setNewTurn() {
        map.reloadAllCell();
        if(skull<=0 && !modality.isFrenzyEnable()) setFrenzy();
        else if(orderPlayerList.get(turn).equals(firstFrenzyPlayer)) endGame();

        if(matchID!=-1) { //If the match isn't ended
            forwardAllViews(new NewTurnMessage(orderPlayerList.get(turn).getNickname()));
            getViewByPlayer(orderPlayerList.get(turn)).setTimer(true);
        }
    }

    /**
     * If sudden death is not active, it switches the modality of the game in Frenzy mode.
     * Otherwise call endGame()
     */
    private void setFrenzy() {
        for(Player player : orderPlayerList) {
            if(orderPlayerList.indexOf(player) >= turn) {
                player.setFirstGroupFrenzy(true);
                getControllerByPlayer(player).setNumOfMaxActions(2);
            } else {
                player.setFirstGroupFrenzy(false);
                getControllerByPlayer(player).setNumOfMaxActions(1);
            }
        }

        if(!suddenDeath && firstFrenzyPlayer==null) {
            modality = new Frenzy();
            firstFrenzyPlayer = orderPlayerList.get(turn);
        }
        else endGame();
    }

    /**
     * Increment the turn parameter
     */
    void incrementTurn() { //I had to separate this method in order to improve efficiency test
        if(turn == orderPlayerList.size()-1) {
            turn = 0;
            firstTurn = false;
        } else turn = turn+1;
    }

    /**
     * To call at the end of the game.
     *  Cash the skull board, send to all user connected the ranking and delete the match
     */
    private void endGame() {
        cashSkullBoardPoint();
        forwardAllViews(new RankingMessage(getRanking()));
        WaitingRoom.deleteMatch(this);
        matchID = -1;
    }

    /**
     * @return The id of this match
     */
    public int getMatchID() {
        return matchID;
    }

    /**
     * @return The number of skull left
     */
    public int getSkull() {
        return skull;
    }

    /**
     * @return ID of the Player of the current turn
     */
    public int getTurnPlayerID() {
        Player playerOfTurn = orderPlayerList.get(turn);
        return playerOfTurn.getID();
    }

    /**
     * get the Modality of the game
     * @return the modality of the game
     */
    public Modality getModality() {
        return modality;
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
     * @return the Cell which has x and y coordinates
     * @throws NotPresentException if there is no cell with those coordinates
     */
    public Cell getCellByCoordinate(int x, int y) throws NotPresentException {

        if(map.getCellByCoo(x, y) == null){
            throw new NotPresentException();
        }
        else{
            return map.getCellByCoo(x, y);
        }

    }

    /**
     * @param colorRoom color of the Room
     * @return the Room object that has that color
     */
    public Room getRoomByID(String colorRoom) {
        for(Room room : map.getRooms()) {
            if(room.getColor().equalsIgnoreCase(colorRoom)) return room;
        }
        return null;
    }

    /**
     * It generates an Action object with reference to this GameHandler
     * @param actionID the int ID of the action
     * @param controller The controller of the player who make the action
     * @throws WrongInputException If there's no action available for that ID anc that player
     * @return An Action object according to the ID, the player and the modality of the game
     */
    public Action getActionByID(int actionID, Controller controller) throws WrongInputException {
        return modality.getActionByID(actionID, controller, this);

    }

    /**
     * Return all the players of the game
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
     * @param cardId card's id
     * @return the card with that id
     */
    public AmmoCard getAmmoCardByID(int cardId) {
        return ammoDeck.getCardById(cardId);
    }

    /**
     * Return the card with that id
     * @param cardId card's id
     * @return the card with that id
     */
    public PowerupCard getPowerupCardByID(int cardId) {
        return powerupDeck.getCardById(cardId);
    }

    /**
     * Return the card with that id
     * @param cardId card's id
     * @return the card with that id
     */
    public WeaponCard getWeaponCardByID(int cardId) {
        return weaponDeck.getCardById(cardId);
    }

    /**
     * Returns the Map of the game
     * @return Map of the game
     */
    public Map getMap() {
        return map;
    }

    /**
     * Returns the powerup deck of the game
     * @return PowerupDeck of the game
     */
    public PowerupDeck getPowerupDeck() {
        return powerupDeck;
    }

    /**
     * Returns the weapon deck of the game
     * @return WeaponDeck of the game
     */
    public WeaponDeck getWeaponDeck() {
        return weaponDeck;
    }

    /**
     * Return the number of skulls at the beginning of the match
     * @return number of skulls at the beginning of the match
     */
    public int getOriginalSkull() {
        return originalSkull;
    }

    /**
     * When a player pass his turn, this method check if someone has died
     * If his the case create a Death object, cash point, reset damage of the death and ask player to re-spawn
     */
    void checkDeath() {
        boolean doubleKill = false;
        for (Player p : orderPlayerList) {
            if (p.isDead()) {
                //If someone has died, create a death object
                //The player of this turn is the killer
                skull--;
                if(skull>=0) {
                    arrayDeath.add(new Death(orderPlayerList.get(turn), p));
                    notifyObservers(new SkullBoardMessage(originalSkull, skull, cloneDeath()));
                }
                if(p.isOverKilled()) {
                    try {
                        orderPlayerList.get(turn).receiveMarkBy(p); //revenge mark
                    } catch (TooManyException e) {
                        getViewByPlayer(p).printFromController("It's not possible to set the revenge mark" +
                                " you have already marked him three times");
                    }
                }
                cashPoint(p, doubleKill, false);
                justDied.add(p);
                p.resetDamage();

                doubleKill=true;
            }
        }
        if(skull<=0 && !justDied.isEmpty()) {
            for (Player dead : justDied) {
                dead.setFrenzyDeath();  //if skull <= 0 is going to start frenzy mode
                                        // so who is dead now has to be flipped
            }
        }
    }

    /**
     * Sort list of player according to their frequency in that list
     * @param listToOrdinate list of player which you want to ordinate by damage
     * @return A list with the element of the @param but sorted and without repetition
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
     * If some players had died this method calculate the point and give it to each player
     * @param whoDied the player who has died and has to be cash
     * @param doubleKill true if a player has killed more then one enemy in this turn
     * @param lastCash true if we are at the end of frenzy mode
     */
    void cashPoint(Player whoDied, boolean doubleKill, boolean lastCash) {
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
     * @return 2 if p is the first who shoot and made a double kill
     *         1 if p is the first who shoot
     *         1 if p made a double kill
     *         0 otherwise
     */
    private int bonusPoint(Player p, Player whoDied, boolean doubleKill, boolean lastCash) {
        List<Player> damage = whoDied.getDamage();
        Death lastDeath = arrayDeath.get(arrayDeath.size() - 1);

        //If is the second kill there is one extra point
        int secondKill = doubleKill ? 1 : 0;

        //If is frenzy death there's no point for the first blood
        int isFrenzyDeath = whoDied.isFrenzyDeath() ? -1 : 0;

        Player killer = lastCash ? null : lastDeath.getWhoKilled();

        //if p is the first and
        if (damage.get(0).equals(p) && p.equals(killer)) return 1 + secondKill + isFrenzyDeath;
        //if p is the killer
        if (p.equals(killer)) return secondKill;
        //if p is the first
        if (damage.get(0).equals(p)) return 1 + isFrenzyDeath;
        return 0;
    }

    /**
     * At the end of the game each player receive points according to the killing he has done
     * This method calculate the points
     */
    void cashSkullBoardPoint() {
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
    List<Player> getRanking() {
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

    /**
     * Create a deep copy the death array
     * @return a deep copy the death array
     */
    public List<Death> cloneDeath() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new SkinnyObjectExclusionStrategy())
                .create();

        Type TYPE = new TypeToken<List<Death>>() {
        }.getType();

        return gson.fromJson(gson.toJson(arrayDeath, TYPE), TYPE);
    }

    /**
     * Return the player view of the player
     * @param p player whose you want the player view
     * @return player view of the player
     */
    private PlayerView getViewByPlayer(Player p) {
        for(PlayerView pw : playerViews) {
            if(p.equals(pw.getPlayerCopy())) return pw;
        }
        throw new IllegalArgumentException("There is no PlayerView linked to " + p.toString());
    }

    /**
     * Return the controller of the player
     * @param p player whose you want the controller
     * @return controller of the player
     */
    public Controller getControllerByPlayer(Player p) {
        for(Controller controller : controllers) {
            if(p.equals(controller.getAuthor())) return controller;
        }
        throw new IllegalArgumentException("There is no Controler linked to " + p.toString());
    }

    /**
     * Set player, playerView and controller of the same user all in one time
     * @param p player to set
     * @param playerView playerView to set
     * @param controller controller to set
     */
    public void setUp(Player p, PlayerView playerView, Controller controller) {
        if(orderPlayerList.size()<=5) {
            orderPlayerList.add(p);
            playerViews.add(playerView);
            controllers.add(controller);
        }
    }

    /**
     * Initialize the map in base the map's ID
     * @param map The ID of the map to set (1-4)
     */
    public void setMap(int map) {
        switch (map) {
            case 1:
                this.map = new Map1(weaponDeck, ammoDeck);
                break;
            case 2:
                this.map = new Map2(weaponDeck, ammoDeck);
                break;
            case 3:
                this.map = new Map3(weaponDeck, ammoDeck);
                break;
            case 4:
                this.map = new Map4(weaponDeck, ammoDeck);
                break;
            default: throw new IllegalArgumentException();
        }
        this.map.reloadAllCell();
    }

    public void setSkull(int skulls) {
        this.skull = skulls;
        this.originalSkull = skulls;
    }

    public void setSuddenDeath(boolean suddenDeath) {
        this.suddenDeath = suddenDeath;
    }

    /**
     * Attach every observer to his observable
     * Cell to mapView, GameHandler to skullBardView
     * @param mapView the mapView of this match
     * @param skullBoardView  the skullBoardView of this match
     * @param enemyViews the enemyViews of this match
     */
    public void attachAll(MapView mapView, SkullBoardView skullBoardView, List<EnemyView> enemyViews) {
        //Cell -> mapView
        map.attach(mapView);
        //GH -> skullBardView
        attach(skullBoardView);
    }

    /**
     * Send to the view the initial setting of each element
     * Notify the player the game is started
     */
    public void start() {
        map.notifyObservers(new MapMessage(map.clone()));
        for(Player p : orderPlayerList) {
            p.notifyObservers(new PlayerModelMessage(p.clone()));
        }
        notifyObservers(new SkullBoardMessage(originalSkull, skull, cloneDeath()));

        if(orderPlayerList.size()>3) {
            try {
                Thread.sleep(750); //Wait all message arrive at the user
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        forwardAllViews(new StartGameMessage(matchID));
        try {
            Thread.sleep(400); //Wait all message arrive at the user
            forwardAllViews(new NewTurnMessage(orderPlayerList.get(turn).getNickname()));

            Thread.sleep(400); //Wait all message arrive at the user
            Controller controller = getControllerByPlayer(orderPlayerList.get(turn));
            controller.setState(new FirstTurnState(controller, this));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        getViewByPlayer(orderPlayerList.get(turn)).setTimer(true);

    }

    /**
     * Set the ConnectionState of the player
     * @param player the player
     * @param isConnected true if it's connected, false if not
     */
    public void setPlayerConnectionStatus(Player player, boolean isConnected){
        player.setConnected(isConnected);
        if(isConnected) {
           forwardAllViews(player.getNickname() + " is back!\n");
        } else {
            forwardAllViews(player.getNickname() + " has been disconnected! \n");
            if(playerConnected() < 3) endGame();
        }
    }

    /**
     * Calculate the number of players connected in this moment
     * @return the number of players connected
     */
    private int playerConnected() {
        int playerConnected = 0;
        for(Player player : orderPlayerList) {
            if(player.isConnected()) playerConnected++;
        }
        return playerConnected;
    }

    /**
     * Check if a player is disconnected
     * @param nickname the player's nickname to check
     * @return True if is disconnected, false if is connected
     */
    public boolean isDisconnected(String nickname) {
        for(Player p : orderPlayerList) {
            if(p.getNickname().equals(nickname)) return !p.isConnected();
        }
        throw new IllegalArgumentException("No player called " + nickname);
    }

    /**
     * Send a common message to all player
     * @param message the message to send
     */
    private void forwardAllViews(ModelViewMessage message) {
        for(PlayerView pw : playerViews) {
            pw.update(null, message);
        }
    }

    /**
     * Send a common message to all player
     * @param message the message to send
     */
    private void forwardAllViews(String message) {
        for(PlayerView playerView : playerViews) {
            playerView.printFromController(message);
        }
    }

    /**
     * Create a list with all characters which hasn't been assigned to some player yet.
     * In oder words, create a list with all character free.
     * @return A list with possible target to choose by the player
     */
    public List<Character> possibleCharacter() {
        List<Integer> charactersTaken = new LinkedList<>();
        for(Player player : orderPlayerList) {
            if(player.getCharacter()!=null) charactersTaken.add(player.getCharacter().getId());
        }

        List<Character> possibleCharacter= new LinkedList<>();

        for(int i=1; i<=5; i++) {
            if(!charactersTaken.contains(i)) possibleCharacter.add(new Character(i));
        }

        return possibleCharacter;
    }

    /**
     * Remove the player from the list of dead players. Call it during the respwan
     * @param player the player that is respawned
     */
    public void removeJustDied(Player player){
        this.justDied.remove(player);
    }

    /**
     * Respawn a disconnected player in a random cellSpawn
     * @param player the player who must respawn, but he is disconnected
     */
    public void randomRespawnNotConnectedPlayer(Player player){
        PowerupCard powerupCard = powerupDeck.pick();
        String color = powerupCard.getColor();
        Room room = getRoomByID(color);
        Cell cellSpawn = room.getSpawnCell();
        player.setPosition(cellSpawn);
        player.resurrection();
        powerupCard.discard();
        removeJustDied(player);
        nextTurn(); // we must return to this function
    }
}