package it.polimi.se2019.model.deck;

import java.lang.reflect.Type;
import java.util.Stack;

@Deprecated
public class PointDeck extends Deck<PointCard> {

    public PointDeck(Stack<PointCard> unusedCard) {
        super(unusedCard);
    }

    @Override
    protected Type getType(boolean ArrayListORStack) {
        return null;
    }
}
