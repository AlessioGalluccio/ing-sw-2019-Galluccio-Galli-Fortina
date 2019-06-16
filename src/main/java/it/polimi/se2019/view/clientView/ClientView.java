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
import java.util.ArrayList;


public class ClientView extends View /*View implement observer/observable*/{

    private Player playerCopy;
    private ArrayList<Target> possibleTarget;
    private ArrayList<Target> selectedTarget;
    private ArrayList<Character> possibleCharacter;
    private Character choosenCharacter;
    private transient UiInterface ui;
    private int lastAck;
    private int matchId = 100;

    public Player getPlayerCopy() {
        return playerCopy;
    }

    public List<Target> getSelectedTarget() {
        return selectedTarget;
    }

    public Character getChoosenCharacter() {
        return choosenCharacter;
    }

    public List<Character> getPossibleCharacter() {
        return possibleCharacter;
    }

    public List<Target> getPossibleTarget() {
        return possibleTarget;
    }

    public int getMatchId() {
        return matchId;
    }

    /**
     * create a TargetMessage that the client send to the server
     * @param target
     */
    public void createTargetMessage(Target target){

        if(verifyTarget()) {
            TargetMessage message = new TargetMessage(target,playerCopy.getID(),this);
            notifyObservers(message);
        }
    }

    /**
     * create a CellMessage that the client send to the server
     * @param x
     * @param y
     */
    public void createCellMessage(int x, int y){
        CellMessage message = new CellMessage(x,y,playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a PlayerMessage that the client send to the server
     * @param playerID
     */
    public void createPlayerViewMessage(int playerID, int authorID){
        PlayerMessage message = new PlayerMessage(playerID, playerCopy.getID(),this);
        notifyObservers(message);
    }


    /**
     * create a CardSpawnChooseMessage that the client send to the server
     * @param cardChoosen
     * @param cardDiscarded
     */
   /* public void createCardSpawnChooseMessage(PowerupCard cardChoosen, PowerupCard cardDiscarded) {
        CardSpawnChooseMessage message = new CardSpawnChooseMessage(cardChoosen, cardDiscarded,playerCopy.getID(),this);
        notifyObservers(message);
    }
    */

    /**
     * create a NopeMessage that the client send to the server
     */
    public void createNopeMessage(){
        NopeMessage message = new NopeMessage(playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a ActionMessage that the client send to the server
     * @param actionID
     */
    public void createActionMessage(int actionID){
        ActionMessage message = new ActionMessage(actionID,playerCopy.getID(),this);
        notifyObservers(message);
    }


    /**
     * create a NewtonMessage that the client send to the server
     * @param usedCard
     */
    public void createNewtonMessage(NewtonCard usedCard){
        NewtonMessage message = new NewtonMessage(usedCard,playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a ReloadMessage that the client send to the server
     * @param weapon
     */
    public void createReloadMessage(WeaponCard weapon){
        ReloadMessage message = new ReloadMessage(weapon, playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a TeleporterMessage that the client send to the server
     * @param usedCard
     */
    public void createTeleporterMessage(TeleporterCard usedCard){
        TeleporterMessage message = new TeleporterMessage(usedCard,playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a TargetingScopeMessage that the client send to the server
     * @param usedCard
     * @param colorRYB
     */
    public void createTargetingScopeMessage(TargetingScopeCard usedCard, ColorRYB colorRYB){
        TargetingScopeMessage message = new TargetingScopeMessage(usedCard, colorRYB, playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a TagbackGranateMessage that the client send to the server
     * @param usedCard
     */
    public void createTagbackGranadeMessage(TagbackGranedCard usedCard){
        TagbackGranateMessage message = new TagbackGranateMessage(usedCard,playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a FireModeMessage that the client send to the server
     * @param firemodeID
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
     * @param optionalID
     */
    public void createOptionalMessage(int optionalID) {
        OptionalMessage message = new OptionalMessage(optionalID,playerCopy.getID(), this);
        notifyObservers(message);
    }

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
     * create a UndoMessage that the client send to the server
     */
    public void createUndoMessage() {
        UndoMessage message = new UndoMessage(playerCopy.getID(),this);
        notifyObservers(message);
    }

    /**
     * create a LoginMessage that the client send to the server
     * @param nickname
     */
    public void createLoginMessage(String nickname, int matchID){
        LoginMessage message = new LoginMessage(nickname, matchID);
        notifyObservers(message);
    }

    /**
     * create a SettingMessage that the client send to the server
     * @param map
     * @param skulls
     * @param suddenDeath
     */
    public void createSettingMessage(int map, int skulls, boolean suddenDeath) {
        notifyObservers(new SettingMessage(map, skulls, suddenDeath));
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
     * verify that the target choosen by the player is contained in the ArrayList of available targets
     * @return
     */
    private boolean verifyTarget(){
        for(int counter = 0; counter < possibleTarget.size(); counter++) {
            if(possibleTarget.get(counter).equals(selectedTarget)){
                return true;
            }
        }
        return false;
    }

    /**
     * verify that the character choosen by the player is cotained in the ArrayList of available characters
     * @return
     */
    private boolean verifyCharacter(){
        return this.possibleCharacter.contains(this.choosenCharacter);
    }


    /**
     * is used by the controller to print string for the user
     * @param string
     */
    @Override
    public synchronized void printFromController(String string) {
        ui.printFromController(string);
    }

    /**
     * is used by RMIClient
     * @param possibleTarget
     */
     //TODO create the targetmessage after receiving the Arraylist
    public void setPossibleTarget( ArrayList<Target> possibleTarget){
        this.possibleTarget = possibleTarget;
    }


    public void setPossibleCharacter(ArrayList<Character> possibleCharacter){
        this.possibleCharacter = possibleCharacter;
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
        ui.startGame();
    }

    /**
     * This method is call by a PlayerMessage object.
     * It copy the content of the message in this object
     * @param p the player contained in the object
     */
    @Override
    public void handlePlayerMessage(Player p) {
        playerCopy = p;
        //synchronized(ui) ui.printPlayer()
    }


    /**
     * This method is call by a NewTurnMessage object.
     * It notify the user that a new turn is begin
     * @param nickname the player's nickname of the turn
     */
    public void handleTurnMessage(String nickname) {
        ui.turn(nickname, nickname.equals(playerCopy.getNickname()));
    }

    /**
     * This method is call by a RankingMessage object.
     * It forward to the ui the ranking of the game
     * @param ranking the ranking
     */
    @Override
    public void handleRankingMessage(List<Player> ranking) {
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

    public void setUi(UiInterface ui) {
        this.ui = ui;
    }

    /**
     * This method shutdown the RMI server
     */
    public  void shutdownServer() {
        Client client = (Client) getObservers().get(0);
        client.unreferenced();
    }

}




