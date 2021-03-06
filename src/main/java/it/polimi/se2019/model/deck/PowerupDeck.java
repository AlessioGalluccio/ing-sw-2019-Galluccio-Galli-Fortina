package it.polimi.se2019.model.deck;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.player.ColorRYB;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * @author Galli
 */
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
        int id=0;
        for(int i=0; i<2; i++){
            for(ColorRYB c : ColorRYB.values()){
                deck.add(new TargetingScopeCard(c, id++, c.ordinal()));
                deck.add(new NewtonCard(c, id++, 10 + c.ordinal()));
                deck.add(new TeleporterCard(c, id++, 20 + c.ordinal()));
                deck.add(new TagbackGrenadeCard(c, id++, 30 + c.ordinal()));
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    /**
     * Used by Gson to serialize the deck
     * Return a Type object according to the param: ArrayList or Stack
     * @param arrayListORStack True if you want an ArrayList, false for a Stack
     * @return A Type object of the specify class
     */
    @Override
    protected Type getType(boolean arrayListORStack) {
        return arrayListORStack ?
                new TypeToken<ArrayList<PowerupCard>>() {}.getType() :
                new TypeToken<Stack<PowerupCard>>() {}.getType();
    }
}


