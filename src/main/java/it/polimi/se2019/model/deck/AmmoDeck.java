package it.polimi.se2019.model.deck;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.player.ColorRYB;

import java.lang.reflect.Type;
import java.util.*;

public class AmmoDeck extends Deck<AmmoConvertibleCard> {

    public AmmoDeck(PowerupDeck powerupDeck) {
        super(initializeCard(powerupDeck));
    }

    /**
     * Generate 36 cards according to the game rule
     * @return deck of Ammo Cards
     */
    private static Stack<AmmoConvertibleCard> initializeCard(PowerupDeck powerupDeck){
        Stack<AmmoConvertibleCard> deck = new Stack<>();
        int id=0;
        for(int i=0; i<2; i++) {
            for(ColorRYB c: ColorRYB.values()) {
                deck.add(new AmmoPowerupCard(c,c, powerupDeck, id++));
            }
        }

        for(int i=0; i<4; i++) {
            for(ColorRYB c1: ColorRYB.values()) {
                for (ColorRYB c2 : ColorRYB.values()) {
                    if(c1.compareTo(c2) < 0) deck.add(new AmmoPowerupCard(c1, c2, powerupDeck, id++));
                }
            }
        }

        for(int i=0; i<3; i++) {
            for(ColorRYB c1: ColorRYB.values()) {
                for (ColorRYB c2 : ColorRYB.values()) {
                    if(c1 != c2) deck.add(new AmmoOnlyCard(c1, c2, id++));
                }
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    @Override
    protected Type getType(boolean arrayListORStack) {
        return arrayListORStack ?
                new TypeToken<ArrayList<AmmoConvertibleCard>>() {}.getType() :
                new TypeToken<Stack<AmmoConvertibleCard>>() {}.getType();
    }
}
