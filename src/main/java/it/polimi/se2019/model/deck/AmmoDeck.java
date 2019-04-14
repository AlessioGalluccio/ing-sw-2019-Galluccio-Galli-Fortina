package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;

import java.util.Stack;

public class AmmoDeck extends Deck<AmmoConvertibleCard> {


    public AmmoDeck() {
        super(initializeCard());
    }

    /**
     * Generate 36 cards according to the game rule
     * @return deck of Ammo Cards
     */
    private static Stack<AmmoConvertibleCard> initializeCard(){
        Stack<AmmoConvertibleCard> deck = new Stack<>();
        for(int i=0; i<2; i++) {
            for(ColorRYB c: ColorRYB.values()) {
                deck.add(new AmmoPowerupCard(c,c));
            }
        }

        for(int i=0; i<4; i++) {
            for(ColorRYB c1: ColorRYB.values()) {
                for (ColorRYB c2 : ColorRYB.values()) {
                    if(c1.compareTo(c2) < 0) deck.add(new AmmoPowerupCard(c1, c2));
                }
            }
        }

        for(int i=0; i<3; i++) {
            for(ColorRYB c1: ColorRYB.values()) {
                for (ColorRYB c2 : ColorRYB.values()) {
                    if(c1 != c2) deck.add(new AmmoOnlyCard(c1, c2));
                }
            }
        }
        return deck;
    }
}
