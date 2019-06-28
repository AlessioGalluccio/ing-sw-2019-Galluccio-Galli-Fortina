package it.polimi.se2019.model.deck;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.player.ColorRYB;

import java.lang.reflect.Type;
import java.util.*;

public class AmmoDeck extends Deck<AmmoCard> {

    public AmmoDeck(PowerupDeck powerupDeck) {
        super(initializeCard(powerupDeck));
    }

    /**
     * Generate 36 cards according to the game rule
     * @return deck of Ammo Cards
     */
    private static Stack<AmmoCard> initializeCard(PowerupDeck powerupDeck){
        Stack<AmmoCard> deck = new Stack<>();
        int id=0;
        int IDtype =0;
        for(ColorRYB c: ColorRYB.values()) {
            for(int i=0; i<2; i++) {
                deck.add(new AmmoPowerupCard(c,c, powerupDeck, id++, IDtype));
            }
            IDtype++;
        }

        for(ColorRYB c1 : ColorRYB.values()) {
            for(ColorRYB c2 : ColorRYB.values()) {
                for(int i=0; i<4; i++) {
                    if(c1.compareTo(c2) < 0) deck.add(new AmmoPowerupCard(c1, c2, powerupDeck, id++, IDtype));
                }
                IDtype++;
            }
        }

        for(ColorRYB c1 : ColorRYB.values()){
            for(ColorRYB c2: ColorRYB.values()) {
                 for(int i=0; i<3; i++){
                    if(c1 != c2) deck.add(new AmmoOnlyCard(c1, c2, id++, IDtype));
                }
                IDtype++;
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    /**
     * Used by Gson to serialize the deck.
     * Return a Type object according to the param: ArrayList or Stack
     * @param arrayListORStack True if you want an ArrayList, false for a Stack
     * @return A Type object of the specify class
     */
    @Override
    protected Type getType(boolean arrayListORStack) {
        return arrayListORStack ?
                new TypeToken<ArrayList<AmmoCard>>() {}.getType() :
                new TypeToken<Stack<AmmoCard>>() {}.getType();
    }
}
