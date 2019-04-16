package it.polimi.se2019.model.deck;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.player.ColorRYB;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Stack;

public class PowerupDeck extends Deck<PowerupCard> {


    public PowerupDeck(){
        super(initializeCard());
    }

    /**
     * Generate 24 cards according to the game rule
     * (6 card for each powerup, 2 for color)
     * @return deck of Powerup Cards
     */
    private static Stack<PowerupCard> initializeCard(){
        Stack<PowerupCard> deck = new Stack<>();
        for(int i=0; i<2; i++) {
            for(ColorRYB c : ColorRYB.values()) {
                deck.add(new TargetingScopeCard(c));
                deck.add(new NewtonCard(c));
                deck.add(new TeleporterCard(c));
                deck.add(new TagbackGranedCard(c));
            }
        }
        return deck;
    }

    @Override
    protected Type getType(boolean ArrayListORStack) {
        return ArrayListORStack ?
                new TypeToken<ArrayList<PowerupCard>>() {}.getType() :
                new TypeToken<Stack<PowerupCard>>() {}.getType();
    }
}


