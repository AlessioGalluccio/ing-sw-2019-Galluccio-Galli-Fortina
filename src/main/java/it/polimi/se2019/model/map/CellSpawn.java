package it.polimi.se2019.model.map;

import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.se2019.cloneable.SkinnyObjectExclusionStrategy;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.TooManyException;
import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.view.ModelViewMess.CellModelMessage;

public class CellSpawn extends Cell {
    private static final long serialVersionUID = 4308424924665943011L;
    private final int MAX_WEAPONCARD = 3;
    private WeaponCard[] weapon = new WeaponCard[MAX_WEAPONCARD];
    private ColorRYB color;
    private transient WeaponDeck deck;

    public CellSpawn(Border north, Border east, Border south, Border west, int x, int y, WeaponDeck deck) {
        super(north, east, south, west, x, y);
        this.deck = deck;
    }

    /**
     *
     * @return list of WeaponCard that can be picked here
     */
    public List<WeaponCard> getWeapon() {
        List<WeaponCard> weaponList = new LinkedList<>();
        for(WeaponCard w : weapon) {
            weaponList.add(w);
        }
        return weaponList;
    }

    public ColorRYB getColor() {
        return color;
    }

    /**
     * Grab a new card on this cell
     * @param cardID the card's ID which will be grabbed
     * @return The card with cardID as id
     * @throws NotCardException If the card is not on this cell
     */
    @Override
    public Card grabCard(int cardID) throws NotCardException {
        WeaponCard cardToReturn = null;
        for(int i=0; i<MAX_WEAPONCARD; i++) {
            if(weapon[i]!=null && weapon[i].getID() == cardID) {
                cardToReturn = weapon[i];
                weapon[i] = null;
            }
        }
        if(cardToReturn!=null) {
            notifyObservers(new CellModelMessage(this.clone()));
            return cardToReturn;
        }
        throw new NotCardException("This card is not here! (Already taken or doesn't exist)");
    }

    /**
     * Grab a new card and replace it with another
     * @param cardID the card's ID which will be grabbed and which will be replaced
     * @param card the card to replace with
     * @return The card with cardID as id
     * @throws NotCardException If the card is not on this cell
     */
    public Card grabCard(int cardID, WeaponCard card) throws NotCardException {
        Card cardToReplace = grabCard(cardID);
        for(int i=0; i<MAX_WEAPONCARD; i++) {
            if(weapon[i]==null) {
                weapon[i]=card;
                return cardToReplace;
            }
        }
        return cardToReplace;
    }

    @Override
    public List<Integer> getCardID() {
        ArrayList <Integer> cardId = new ArrayList<>();
        for(WeaponCard card : weapon) {
            if(card != null) cardId.add(card.getID());
        }
        return cardId;
    }

    /**
     * If the card on the cell was picked, put a new card on the cell
     */
    @Override
    protected void reloadCard(){
        for(int i=0; i<MAX_WEAPONCARD; i++) {
            if(weapon[i]==null && deck.sizeUnususedCard()!=0) {
                weapon[i]=deck.pick();
                notifyObservers(new CellModelMessage(this.clone()));
            }
        }
    }

    /**
     * If a player has to discard a Weapon card, this method replace a empty card space with the discarded card
     * @param card card discarded
     * @throws TooManyException if there are already 3 cards on this cell
     */
    //In order to use this method you have to cast Cell in SpawnCell!!
    public void replaceCard(WeaponCard card) throws TooManyException {
        for(int i=0; i<MAX_WEAPONCARD; i++) {
            if(weapon[i]==null) {
                weapon[i] = card;
                notifyObservers(new CellModelMessage(this.clone()));
                return;
            }
        }
        throw new TooManyException("In this cell there are already 3 cards");
    }

    @Override
    protected void setRoom(Room room) {
        super.setRoom(room);
        String colorRoom = getRoom().getColor().toUpperCase();
        switch (colorRoom) {
            case "BLUE":
                color = ColorRYB.BLUE; break;
            case "YELLOW":
                color = ColorRYB.YELLOW; break;
            case "RED":
                color = ColorRYB.RED; break;
            default:
                throw new IllegalArgumentException("Color room is not one of ColorRYB");
        }
    }

    @Override
    public Cell clone() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WeaponCard.class, new JsonAdapter<WeaponCard>())
                .registerTypeAdapter(Border.class, new JsonAdapter<Border>())
                .registerTypeAdapter(Cell.class, new JsonAdapter<Cell>())
                .registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>())
                .setExclusionStrategies(new SkinnyObjectExclusionStrategy())
                .create();

        return gson.fromJson(gson.toJson(this), CellSpawn.class);
    }

    /**
     * Print the row in the middle of the cell
     */
    @Override
    String printMiddleRow() {
        String s ="";
        ConsoleColor printColor = ConsoleColor.colorByColor(getRoom().getColor());
        String space = ConsoleColor.WHITE_BRIGHT + "▦" + printColor;
        s+=getWestBorder().printByDirection(3, false, printColor);
        s+=space + space +
                "↺↺↺" +
                space + space;
        s+=getEastBorder().printByDirection(3, false, printColor);
        return s;
    }
}
