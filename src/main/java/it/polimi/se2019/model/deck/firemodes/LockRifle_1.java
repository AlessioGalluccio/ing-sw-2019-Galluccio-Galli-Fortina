package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.deck.TargetingScopeCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.PlayerMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.List;

public class LockRifle_1 extends FireMode {
    GameHandler gameHandler;
    Controller controller;
    Player author;

    private static final int NUM_DAMAGE = 2;
    private static final int NUM_MARK = 1;

    private static final String firstMsgStr = "Select a player from possible targets";
    private static final boolean firstMsgBool = false;

    public void  LockRifle_1(Shoot shoot) {
        //TOD vedere se è compatibile con json
        gameHandler = shoot.getGameHandler();
        controller = shoot.getController();
        author = shoot.getPlayerAuthor();
    }

    @Override
    public List<Target> sendPossibleTarget(Player player, PlayerView playerView, GameHandler gameHandler) {
        List<Target> listTarget = new ArrayList<>();
        for(Player playerOfGame : gameHandler.getOrderPlayerList()){
            if(playerOfGame.getID() != this.author.getID() && playerOfGame.isVisibleBy(this.author)){
                listTarget.add(playerOfGame);
            }
        }
        //TODO invio dei target
        return listTarget;
    }

    @Override
    public void fire(List<ViewControllerMessage> stack, GameHandler gameHandler) {
        //utilizza metodi con polimorfismo. uno con il parametro del tipo corretto, un con quello generale. se non è quello corretto, non fai niente
        firstTarget(stack.get(0), gameHandler);
    }

    @Override
    public boolean controlMessage(List<ViewControllerMessage> stack, GameHandler gameHandler) throws NotEnoughAmmoException {
        //TODO provvisorio, controllalo e scrivilo meglio
        if(stack.get(0).getMessageID() == Identificator.PLAYER_VIEW_MESSAGE){
            for(int i = 1; i < stack.size(); i++){
                if(stack.get(i).getMessageID() != Identificator.TARGETING_SCOPE_MESSAGE){
                    return false;
                }
            }
            return true;
        }
        return false;

    }

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> messageListExpected = new ArrayList<>();

        StringAndMessage firstTarget = new StringAndMessage(Identificator.PLAYER_VIEW_MESSAGE, firstMsgStr, firstMsgBool );
        messageListExpected.add(firstTarget);

        return messageListExpected;
    }

    @Override
    public boolean giveOnlyMarks() {
        return false;
    }

    /**
     * handle the effect of the first target
     * @param msg the target
     * @param gameHandler the handler of the game
     */
    private void firstTarget(PlayerMessage msg, GameHandler gameHandler){
        Player authorPlayer = gameHandler.getPlayerByID(msg.getAuthorID());
        Player targetPlayer = gameHandler.getPlayerByID(msg.getPlayerID());
        for(int i = 0; i < NUM_DAMAGE; i++){
            try{
                targetPlayer.receiveDamageBy(authorPlayer);
            }catch (NotPresentException e){
                msg.getAuthorView().printFromController("Can't do more damage to this player");
            }catch (YouOverkilledException e) {
                //TODO
            }catch (YouDeadException e) {
                //TODO
            }
        }
        for(int i = 0; i < NUM_MARK; i++){
            try{
                targetPlayer.receiveMarkBy(authorPlayer);
            }catch(TooManyException e){
                msg.getAuthorView().printFromController("You have already three marks on this Player, you will not add more marks");
            }
        }

    }

    /**
     * this method should be not used if program works correctly, but needed for Polymorphism
     * @param target the target
     * @param gameHandler the handler of the game
     */
    private void firstTarget(ViewControllerMessage target, GameHandler gameHandler) {
        //TODO non deve far nulla, decidi se aggiungere una eccezione
    }



    @Override
    public void addCell(Shoot shoot, int x, int y) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addPlayerTarget(Shoot shoot, int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        shoot.addPlayerTargetFromFireMode(target);
    }

    @Override
    public void addTargetingScope(Shoot shoot, int targetingCardID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        Player author = shoot.getPlayerAuthor();
        //TODO manca metodo per ottenere TargetingCard da ID
    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException {

    }

    @Override
    public void addNope() throws WrongInputException {

    }

}


