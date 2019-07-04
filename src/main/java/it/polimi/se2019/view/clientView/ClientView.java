package it.polimi.se2019.view.clientView;


import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.messages.SettingMessage;
import it.polimi.se2019.ui.UiInterface;
import it.polimi.se2019.view.ModelViewMess.HandlerPlayerViewMessage;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.network.messages.LoginMessage;

import java.util.List;

/**
 * @author Galli
 * @author Fortina
 */
public class ClientView extends View /*View implement observer/observable*/{

    private Player playerCopy;
    private List<Target> possibleTarget;
    private List<Target> selectedTarget;
    private List<Character> possibleCharacter;
    private Character choosenCharacter;
    private transient UiInterface ui;
    private int lastAck;
    private int matchId = -1;

    /**
     * getter of playerCopy
     * @return playerCopy
     */
    public Player getPlayerCopy() {
        return playerCopy;
    }

    /**
     * getter of possibleCharacter
     * @return possibleCharacter
     */
    public List<Character> getPossibleCharacter() {
        return possibleCharacter;
    }

    /**
     *  getter of possibleTarget
     * @return possibleTarget
     */
    public List<Target> getPossibleTarget() {
        return possibleTarget;
    }

    /**
     * getter of matchId
     * @return matchId
     */
    public int getMatchId() {
        return matchId;
    }

    /**
     * create a CellMessage that the client send to the server
     * @param x coordinate of cell
     * @param y coordinate of cell
     */
    public void createCellMessage(int x, int y){
        CellMessage message = new CellMessage(x,y,playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a NopeMessage that the client send to the server
     */
    public void createNopeMessage(){
        NopeMessage message = new NopeMessage(playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a ActionMessage that the client send to the server
     * @param actionID int id of action
     */
    public void createActionMessage(int actionID){
        ActionMessage message = new ActionMessage(actionID,playerCopy.getID(),this);
        notifyObservers(message);
    }


    /**
     * create a NewtonMessage that the client send to the server
     * @param usedCard newtoncard selected by the player
     */
    public void createNewtonMessage(NewtonCard usedCard){
        NewtonMessage message = new NewtonMessage(usedCard,playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a ReloadMessage that the client send to the server
     * @param weapon card selected by the player
     */
    public void createReloadMessage(WeaponCard weapon){
        ReloadMessage message = new ReloadMessage(weapon, playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a TeleporterMessage that the client send to the server
     * @param usedCard card selected by the player
     */
    public void createTeleporterMessage(TeleporterCard usedCard){
        TeleporterMessage message = new TeleporterMessage(usedCard, playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a TargetingScopeMessage that the client send to the server
     * @param usedCard card selected by the player
     * @param colorRYB color of ammo
     */
    public void createTargetingScopeMessage(TargetingScopeCard usedCard, ColorRYB colorRYB){
        TargetingScopeMessage message = new TargetingScopeMessage(usedCard, colorRYB, playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a TagbackGrenadeMessage that the client send to the server
     * @param usedCard card selected by the player
     */
    public void createTagbackGranadeMessage(TagbackGrenadeCard usedCard){
        TagbackGrenadeMessage message = new TagbackGrenadeMessage(usedCard,playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a FireModeMessage that the client send to the server
     * @param firemodeID id of the firemode selected by the player
     */
    public void createFireModeMessage(int firemodeID) {
        FireModeMessage message = new FireModeMessage(firemodeID,playerCopy.getID(), this);
        notifyObservers(message);
    }

    /**
     * create a FireMessage that the client send to the server
     */
    public void createFireMessage() {
        FireMessage message = new FireMessage(playerCopy.getID(), this);
        notifyObservers(message);
    }

    /**
     * create a FireModeMessage that the client send to the server
     * @param optionalID id of the optional firemode selected by the player
     */
    public void createOptionalMessage(int optionalID) {
        OptionalMessage message = new OptionalMessage(optionalID,playerCopy.getID(), this);
        notifyObservers(message);
    }

    /**
     * create a PlayerMessage that the client send to the server
     * @param playerID id of the player
     */
    public void createPlayerMessage(int playerID) {
        PlayerMessage message = new PlayerMessage(playerID, playerCopy.getID(), this);
        notifyObservers(message);
    }

    /**
     * create a PassTurnMessage that the client send to the server
     */
    public void createPassTurnMessage (){
        PassTurnMessage message = new PassTurnMessage(playerCopy.getID(),this);
        notifyObservers(message);
    }


    /**
     * create a LoginMessage that the client send to the server
     * @param nickname string of nickname chosen by the player
     * @param matchID The id of the match on which do you wanna reconnect
     *                If is the first login or don't you wanna reconnect ignore this param
     */
    public void createLoginMessage(String nickname, int matchID){
        LoginMessage message = new LoginMessage(nickname, matchID);
        notifyObservers(message);
    }

    /**
     * create a SettingMessage that the client send to the server
     * @param map map type
     * @param skulls number of skull
     * @param suddenDeath true if suddenDeath is selected
     */
    public void createSettingMessage(int map, int skulls, boolean suddenDeath) {
        notifyObservers(new SettingMessage(map, skulls, suddenDeath));
    }

    /**
     * create a CharacterMessage that the client send to the server
     * @param characterID id of the character chosen
     * @throws NotCharacterException If is chose a character not present
     */
    public void createCharacterMessage(int characterID) throws NotCharacterException {
        if(verifyCharacter(characterID))
            notifyObservers( new CharacterMessage(characterID, playerCopy.getID(), this));
        else throw new NotCharacterException("This character can't be chosen.");
    }

    /**
     * create a WeaponMessage that the client sends to the server
     * @param weaponCard the weapon card
     */
    public void createWeaponMessage(WeaponCard weaponCard){
        WeaponMessage message = new WeaponMessage(weaponCard,playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a DiscardPowerupMessage that the client sends to the server
     * @param powerupCard the powerup card to be discarded
     */
    public void createDiscardPowerupMessage(PowerupCard powerupCard){
        DiscardPowerupMessage message = new DiscardPowerupMessage(powerupCard, playerCopy.getID(), this);
        notifyObservers(message);
    }

    /**
     * create a DiscardPowerupMessage that the client sends to the server
     * @param weaponCard the weapon card to be discarded
     */
    public void createDiscardWeaponMessage(WeaponCard weaponCard){
        DiscardWeaponMessage message = new DiscardWeaponMessage(weaponCard, playerCopy.getID(), this);
        notifyObservers(message);
    }

    /**
     * verify that the character choosen by the player is cotained in the ArrayList of available characters
     * @param characterID id of the character
     * @return  boolean true if the character is possible
     */
    private boolean verifyCharacter(int characterID) {
        for(Character character : possibleCharacter) {
            if(character.getId()==characterID) return true;
        }
        return false;
    }


    /**
     * is used by the controller to print string for the user
     * @param string printed from controller
     */
    @Override
    public synchronized void printFromController(String string) {
        synchronized (ui) {
            ui.printFromController(string);
        }
    }


    /**
     * set possible character for the player
     * @param possibleCharacter list of possible character
     */
    @Override
    public void setPossibleCharacter(List<Character> possibleCharacter){
        this.possibleCharacter = possibleCharacter;
        try {
            ui.chooseCharacter(possibleCharacter);
        }
        catch (Exception e){

        }
    }

    /**
     * This method is called whenever the player object of this user is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o    Always null
     * @param arg  The message with which update the player board
     */
    @Override
    public synchronized void update(java.util.Observable o /*will be always NULL*/, Object arg) {
        HandlerPlayerViewMessage message = (HandlerPlayerViewMessage) arg;
        if(message.getAck()>lastAck  || message.getAck()==-1) { //message ack==-1 if isn't a PlayerModelMessage
            if(message.getAck()!=-1) lastAck = message.getAck();
            message.handleMessage(this);
        }
    }

    /**
     * Call a ui method to notify the user about the status of the login
     * @param success the status, if is valid or not
     * @param isFirst if the user is the first of the match
     */
    public void handleLogin(boolean success, boolean isFirst) {
        ui.login(success, isFirst);
    }

     /**
      * Call a ui method to notify the user his has been disconnected from the macth
      * A user can be disconnected due to network problem or due to inactivity
     */
    public void handleDisconnection() {
        ui.disconnect(matchId);
    }


    /**
     * Notify the user about the start of the message
     * @param matchId the id of the match just started
     */
    public synchronized void handleStartGameMessage(int matchId) {
        this.matchId = matchId;
        synchronized (ui) {
            ui.startGame();
        }
        notifyAll();
    }

    /**
     * This method is call by a PlayerMessage object.
     * It copy the content of the message in this object
     * @param p the player contained in the object
     */
    @Override
    public void handlePlayerMessage(Player p) {
        playerCopy = p;
        synchronized(ui) {
            ui.updatePlayer();
        }
    }


    /**
     * This method is call by a NewTurnMessage object.
     * It notify the user that a new turn is begin
     * @param nickname the player's nickname of the turn
     */
    public synchronized void handleTurnMessage(String nickname) {
        while(matchId==-1) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        synchronized (ui) {
         ui.turn(nickname, nickname.equals(playerCopy.getNickname()));
        }
    }

    /**
     * This method is call by a RankingMessage object.
     * It forward to the ui the ranking of the game
     * @param ranking the ranking
     */
    @Override
    public void handleRankingMessage(List<Player> ranking) throws Exception {
        ui.printRanking(ranking);
    }

    /**
     * Set up all views and attach the networkHandler
     * @param networkHandler the client
     */
    public void setUp(Client networkHandler) {
        networkHandler.setUpUi(ui);
        attach(networkHandler);
    }

    /**
     * set ui
     * @param ui for set ui
     */
    public void setUi(UiInterface ui) {
        this.ui = ui;
    }

    /**
     * This method shutdown the server
     */
    public void shutdownServer() {
        Client client = (Client) getObservers().get(0);
        client.closeAll();
        System.exit(0);
    }

}




