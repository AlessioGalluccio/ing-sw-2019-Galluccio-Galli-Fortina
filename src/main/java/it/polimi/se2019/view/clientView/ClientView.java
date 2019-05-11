package it.polimi.se2019.view.clientView;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.rmi.RMIClient;
import it.polimi.se2019.view.ViewControllerMess.*;

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

    public void createTargetMessage(Target target){
        /* TODO
                la verifica dei target non può avvenire allo stesso modo ( esempio selezionare un movimento comporta la
                selezione di una sola cella e non multiple.
         */
        if(verifyTarget()) {
            TargetMessage message = new TargetMessage(target);
            rmiClient.send(message);

        }

    }

    public CellMessage createCellMessage(int x, int y){
        CellMessage message = new CellMessage(x,y);
        rmiClient.send(message);
        return message;
    }

    public PlayerMessage createPlayerViewMessage(int playerID){
        PlayerMessage message = new PlayerMessage(playerID);
        rmiClient.send(message);
        return message;
    }

    public CardSpawnChooseMessage createCardSpawnChooseMessage(PowerupCard cardChoosen, PowerupCard cardDiscarded){
        CardSpawnChooseMessage message = new CardSpawnChooseMessage(cardChoosen, cardDiscarded);
        rmiClient.send(message);
        return message;
    }

    public NopeMessage createNopeMessage(){
        NopeMessage message = new NopeMessage();
        rmiClient.send(message);
        return message;
    }

    public ActionMessage createActionMessage(int actionID){
        ActionMessage message = new ActionMessage(actionID);
        rmiClient.send(message);
        return message;
    }


    public NewtonMessage createNewtonMessage(NewtonCard usedCard){
        NewtonMessage message = new NewtonMessage(usedCard);
        rmiClient.send(message);
        return message;
    }

    public ReloadMessage createReloadMessage(WeaponCard weapon){
        ReloadMessage message = new ReloadMessage(weapon);
        rmiClient.send(message);
        return message;
    }

    public TeleporterMessage createTeleporterMessage(TeleporterCard usedCard){
        TeleporterMessage message = new TeleporterMessage(usedCard);
        rmiClient.send(message);
        return message;
    }

    public TargetingScopeMessage createTargetingScopeMessage(TargetingScopeCard usedCard, ColorRYB colorRYB){
        TargetingScopeMessage message = new TargetingScopeMessage(usedCard, colorRYB);
        rmiClient.send(message);
        return message;
    }

    public TagbackGranateMessage createTagbackGranadeMessage(TagbackGranedCard usedCard){
        TagbackGranateMessage message = new TagbackGranateMessage(usedCard);
        rmiClient.send(message);
        return message;
    }

    public FireModeMessage createFireModeMessage(int firemodeID){
        FireModeMessage message = new FireModeMessage(firemodeID);
        rmiClient.send(message);
        return message;
    }


    public EndMessage createEndMessage (int messageID){
        EndMessage message = new EndMessage();
        rmiClient.send(message);
        return message;
    }

    public UndoMessage createUndoMessage(int messageID){
        UndoMessage message = new UndoMessage();
        rmiClient.send(message);
        return message;
    }

    public LoginMessage createLoginMessage(int messageID, String nickname, Character choosenCharacter){
        LoginMessage message = new LoginMessage(nickname,choosenCharacter);
        rmiClient.send(message);
        return message;
    }






 //TODO se i target sono anche i soli movimento allora può essere selezionata una sola cella


    private boolean verifyTarget(){

        for (int counter = 0; counter < possibleTarget.size(); counter++) {
            if(possibleTarget.get(counter).equals(selectedTarget)){
                return true;
            }
        }

        return false;
    }

    private boolean verifyCharacter(){
        if (this.possibleCharacter.contains(this.choosenCharacter))
            return true;

        return false;
    }


    /*
    printFromController() is used by the RMIClient to print string arrived from the server
     */

    public void printFromController(String string) {

        System.out.println(string);
    }


    /*
    setPossibleTarget() is used by RMIClient

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



