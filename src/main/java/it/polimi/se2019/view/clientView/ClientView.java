package it.polimi.se2019.view.clientView;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.rmi.RMIClient;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;



public class ClientView {

    private Player playerCopy;
    private Map mapCopy;
    private ArrayList<Target> possibleTarget;
    private ArrayList<Target> selectedTarget;
    private ArrayList<Character> possibleCharacter;
    private Character choosenCharacter;
    private RMIClient rmiClient;






    public ClientView(Player playerCopy, Map mapCopy, ArrayList<Target> possibleTarget,
                      ArrayList<Target> selectedTarget, ArrayList<Character> possibleCharacter,
                      Character choosenCharacter) {
        this.playerCopy = playerCopy;
        this.mapCopy = mapCopy;
        this.possibleTarget = possibleTarget;
        this.selectedTarget = selectedTarget;
        this.possibleCharacter = possibleCharacter;
        this.choosenCharacter = choosenCharacter;
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

    /**
     * create a TargetMessage that the client send to the server
     * @param target
     * @param authorID
     * @param authorView
     */

    public void createTargetMessage(Target target, int authorID, PlayerView authorView){

        if(verifyTarget()) {
            TargetMessage message = new TargetMessage(target,authorID,authorView);
            rmiClient.send(message);

        }

    }

    /**
     * create a CellMessage that the client send to the server
     * @param x
     * @param y
     * @param authorID
     * @param authorView
     * @return
     */

    public CellMessage createCellMessage(int x, int y, int authorID, PlayerView authorView){
        CellMessage message = new CellMessage(x,y,authorID,authorView);
        rmiClient.send(message);
        return message;
    }

    /**
     * create a PlayerMessage that the client send to the server
     * @param playerID
     * @param authorID
     * @param authorView
     * @return
     */

    public PlayerMessage createPlayerViewMessage(int playerID, int authorID, PlayerView authorView){
        PlayerMessage message = new PlayerMessage(playerID,authorID,authorView);
        rmiClient.send(message);
        return message;
    }

    /**
     * create a CardSpawnChooseMessage that the client send to the server
     * @param cardChoosen
     * @param cardDiscarded
     * @param authorID
     * @param authorView
     * @return
     */
    public CardSpawnChooseMessage createCardSpawnChooseMessage(PowerupCard cardChoosen, PowerupCard cardDiscarded,
                                                               int authorID, PlayerView authorView){
        CardSpawnChooseMessage message = new CardSpawnChooseMessage(cardChoosen, cardDiscarded,authorID,authorView);
        rmiClient.send(message);
        return message;
    }

    /**
     * create a NopeMessage that the client send to the server
     * @param authorID
     * @param authorView
     * @return
     */

    public NopeMessage createNopeMessage(int authorID, PlayerView authorView){
        NopeMessage message = new NopeMessage(authorID,authorView);
        rmiClient.send(message);
        return message;
    }

    /**
     * create a ActionMessage that the client send to the server
     * @param actionID
     * @param authorID
     * @param authorView
     * @return
     */
    public ActionMessage createActionMessage(int actionID, int authorID, PlayerView authorView){
        ActionMessage message = new ActionMessage(actionID,authorID,authorView);
        rmiClient.send(message);
        return message;
    }


    /**
     * create a NewtonMessage that the client send to the server
     * @param usedCard
     * @param authorID
     * @param authorView
     * @return
     */
    public NewtonMessage createNewtonMessage(NewtonCard usedCard, int authorID, PlayerView authorView){
        NewtonMessage message = new NewtonMessage(usedCard,authorID,authorView);
        rmiClient.send(message);
        return message;
    }

    /**
     * create a ReloadMessage that the client send to the server
     * @param weapon
     * @param authorID
     * @param authorView
     * @return
     */

    public ReloadMessage createReloadMessage(WeaponCard weapon, int authorID, PlayerView authorView){
        ReloadMessage message = new ReloadMessage(weapon,authorID,authorView);
        rmiClient.send(message);
        return message;
    }

    /**
     * create a TeleporterMessage that the client send to the server
     * @param usedCard
     * @param authorID
     * @param authorView
     * @return
     */
    public TeleporterMessage createTeleporterMessage(TeleporterCard usedCard, int authorID, PlayerView authorView){
        TeleporterMessage message = new TeleporterMessage(usedCard,authorID,authorView);
        rmiClient.send(message);
        return message;
    }

    /**
     * create a TargetingScopeMessage that the client send to the server
     * @param usedCard
     * @param colorRYB
     * @param authorID
     * @param authorView
     * @return
     */
    public TargetingScopeMessage createTargetingScopeMessage(TargetingScopeCard usedCard, ColorRYB colorRYB,
                                                             int authorID, PlayerView authorView){
        TargetingScopeMessage message = new TargetingScopeMessage(usedCard, colorRYB,authorID,authorView);
        rmiClient.send(message);
        return message;
    }

    /**
     * create a TagbackGranateMessage that the client send to the server
     * @param usedCard
     * @param authorID
     * @param authorView
     * @return
     */


    public TagbackGranateMessage createTagbackGranadeMessage(TagbackGranedCard usedCard, int authorID,
                                                             PlayerView authorView){
        TagbackGranateMessage message = new TagbackGranateMessage(usedCard,authorID,authorView);
        rmiClient.send(message);
        return message;
    }

    /**
     * create a FireModeMessage that the client send to the server
     * @param firemodeID
     * @param authorID
     * @param authorView
     * @return
     */
    public FireModeMessage createFireModeMessage(int firemodeID, int authorID, PlayerView authorView){
        FireModeMessage message = new FireModeMessage(firemodeID,authorID,authorView);
        rmiClient.send(message);
        return message;
    }

    /**
     * create a EndMessage that the client send to the server
     * @param messageID
     * @param authorID
     * @param authorView
     * @return
     */

    public EndMessage createEndMessage (int messageID, int authorID, PlayerView authorView){
        EndMessage message = new EndMessage(authorID,authorView);
        rmiClient.send(message);
        return message;
    }

    /**
     * create a UndoMessage that the client send to the server
     * @param messageID
     * @param authorID
     * @param authorView
     * @return
     */
    public UndoMessage createUndoMessage(int messageID, int authorID, PlayerView authorView){
        UndoMessage message = new UndoMessage(authorID,authorView);
        rmiClient.send(message);
        return message;
    }

    /**
     * create a LoginMessage that the client send to the server
     * @param messageID
     * @param nickname
     * @param choosenCharacter
     * @param authorID
     * @param authorView
     * @return
     */
    public LoginMessage createLoginMessage(int messageID, String nickname, Character choosenCharacter, int authorID,
                                           PlayerView authorView){
        LoginMessage message = new LoginMessage(nickname,choosenCharacter,authorID,authorView);
        rmiClient.send(message);
        return message;
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
     * is used by the RMIClient to print string arrived from the server
     * @param string
     */

    public void printFromController(String string) {

        System.out.println(string);
    }


    /**
     * is used by RMIClient
     * @param possibleTarget
     */
     /* TODO create the targetmessage after receiving the Arraylist

      */

    public void setPossibleTarget( ArrayList<Target> possibleTarget){
        this.possibleTarget = possibleTarget;

    }



    public void updatePlayerCopy(Player playerCopy){

        this.playerCopy = playerCopy;
    }

    public void setPossibleCharacter(ArrayList<Character> possibleCharacter){

        this.possibleCharacter = possibleCharacter;
    }










}




