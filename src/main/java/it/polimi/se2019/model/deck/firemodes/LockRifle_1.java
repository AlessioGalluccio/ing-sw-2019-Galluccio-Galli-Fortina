package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;
import it.polimi.se2019.view.PlayerView;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.PlayerMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.List;

public class LockRifle_1 extends FireMode {

    private static final int NUM_DAMAGE = 2;
    private static final int NUM_MARK = 1;

    private static final String firstMsgStr = "Select a player from possible targets";
    private static final boolean firstMsgBool = false;

    @Override
    public List<Target> sendPossibleTarget(Player player, PlayerView playerView, GameHandler gameHandler) {
        List<Target> listTarget = new ArrayList<>();
        for(Player playerOfGame : gameHandler.getOrderPlayerList()){
            if(playerOfGame.getID() == player.getID()){
                break;
            }
            else{
                if(playerOfGame.isVisibleBy(player)){
                    listTarget.add(playerOfGame);
                }
            }
        }
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


}


