package it.polimi.se2019.view.clientView;


import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.configureMessage.SettingMessage;
import it.polimi.se2019.ui.UiInterface;
import it.polimi.se2019.view.ModelViewMess.HandlerPlayerViewMessage;
import it.polimi.se2019.view.ModelViewMess.StartGameMessage;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.network.configureMessage.LoginMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;
import java.util.Observable;


public class ClientView extends View /*View implement observer/observable*/{

    private Player playerCopy;
    private Map mapCopy;
    private ArrayList<Target> possibleTarget;
    private ArrayList<Target> selectedTarget;
    private ArrayList<Character> possibleCharacter;
    private Character choosenCharacter;
    private Client client;
    private UiInterface ui;
    private int matchId = 100;

    public ClientView() {
    }

    public Player getPlayerCopy() {
        return playerCopy;
    }

    public ArrayList<Target> getSelectedTarget() {
        return selectedTarget;
    }

    public Character getChoosenCharacter() {
        return choosenCharacter;
    }

    public ArrayList<Character> getPossibleCharacter() {
        return possibleCharacter;
    }

    public ArrayList<Target> getPossibleTarget() {

        return possibleTarget;
    }

    public Map getMapCopy() {
        return mapCopy;
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
    public void createCardSpawnChooseMessage(PowerupCard cardChoosen, PowerupCard cardDiscarded) {
        CardSpawnChooseMessage message = new CardSpawnChooseMessage(cardChoosen, cardDiscarded,playerCopy.getID(),this);
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

        for (int counter = 0; counter < possibleTarget.size(); counter++) {
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
        if (this.possibleCharacter.contains(this.choosenCharacter))
            return true;

        return false;
    }


    /**
     * is used by the controller to print string for the user
     * @param string
     */
    @Override
    public void printFromController(String string) {
        //Call method from UiInterface
    }

    @Override
    public void handleStartGameMessage(StartGameMessage startGameMessage) {
        ui.startGame();
    }

    @Override
    public void handlePlayerMessage(Player p) {
        playerCopy = p;
    }


    /**
     * is used by RMIClient
     * @param possibleTarget
     */
     //TODO create the targetmessage after receiving the Arraylist
    public void setPossibleTarget( ArrayList<Target> possibleTarget){
        this.possibleTarget = possibleTarget;
    }



    public void updatePlayerCopy(Player playerCopy){

        this.playerCopy = playerCopy;
    }

    public void setPossibleCharacter(ArrayList<Character> possibleCharacter){
        this.possibleCharacter = possibleCharacter;
    }

    @Override
    public void update(java.util.Observable o /*will be always NULL*/, Object arg) {
        HandlerPlayerViewMessage message = (HandlerPlayerViewMessage) arg;
        message.handleMessage(this);
        //ui.printPlayer()
    }

    public void handleLogin(boolean success, boolean isFirst) {
        ui.login(success, isFirst);
    }

    public void handleDisconnection() {
        ui.disconnect();
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

    public int getMatchId() {
        return matchId;
    }
}




