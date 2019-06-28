package it.polimi.se2019.model.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.se2019.cloneable.SkinnyObjectExclusionStrategy;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.view.ModelViewMess.CellModelMessage;

import java.util.List;
import java.util.ArrayList;

public class CellAmmo extends Cell {
    private static final long serialVersionUID = 2030465796514725765L;
    private AmmoCard ammo;
    private transient AmmoDeck deck;

    public CellAmmo(Border north, Border east, Border south, Border west, int x, int y, AmmoDeck deck) {
        super(north, east, south, west, x, y);
        this.deck = deck;
    }

    CellAmmo(int x, int y) {
        super(new Passage(), new Passage(), new Passage(),new Passage(), x, y, true);
    }


    /**
     * Each cell has some card on it, this method grab a card
     * @param cardID ID of the card I wanna grab, for this type of cell there only one card on it. Can be omitted.
     * @return the card on the cell
     * @throws NotCardException If card has already taken or there not a card with the ID
     */
    @Override
    public Card grabCard(int cardID) throws NotCardException {
        if(ammo == null) throw new NotCardException("Card already taken");
        Card cardToReturn = ammo;
        ammo = null;
        notifyObservers(new CellModelMessage(this.clone()));
        return cardToReturn;
    }
    /**
     * Each cell has some card on it, this method grab a card and replace that with another one.
     * This type of cell can't replace the card on it with another, so this method do the same of grabCard(nt cardID)
     * @param cardID ID of the card I wanna grab, for this type of cell there only one card on it. Can be omitted.
     * @param card The card to replace with
     * @return the card on the cell
     * @throws NotCardException If card has already taken or there not a card with the ID
     */
    @Override
    public Card grabCard(int cardID, WeaponCard card) throws NotCardException {
        return grabCard(cardID);
    }

    /**
     *
     * @return ammo card
     */
    public AmmoCard getAmmo() {
        return ammo;
    }

    /**
     * Each cell has some card on it, this method return a list of that cards' IDs
     * This type of cell has only one card on it, the list will have size == 1
     * @return the IDs of the cards on this cell
     */
    @Override
    public List<Integer> getCardID() {
        ArrayList<Integer> cardId = new ArrayList<>();
        cardId.add(0);
        return cardId;
    }

    /**
     * If the card on the cell was picked, put a new card on the cell
     */
    @Override
    protected void reloadCard() {
        if(ammo==null&& isActive()) {
            ammo = deck.pick();
            notifyObservers(new CellModelMessage(this.clone()));
        }
    }

    /**
     * Create a deep copy of the cell
     * @return a deep copy of the cell
     */
    public Cell clone() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(AmmoCard.class, new JsonAdapter<AmmoCard>())
                .registerTypeAdapter(Border.class, new JsonAdapter<Border>())
                .registerTypeAdapter(Cell.class, new JsonAdapter<Cell>())
                .setExclusionStrategies(new SkinnyObjectExclusionStrategy())
                .create();

        return gson.fromJson(gson.toJson(this), CellAmmo.class);
    }

    /**
     * Print the row in the middle of the cell
     */
    @Override
    String printMiddleRow() {
        String s ="";
        ConsoleColor color = ConsoleColor.colorByColor(getRoom().getColor());
        String space = ConsoleColor.BLACK + "◙" + color;
        s+=getWestBorder().printByDirection(3, false, color);
        if(ammo!=null) {
           s+= space + space
                    + ConsoleColor.colorByColor(ammo.getAmmo().get(0).toString()) +
                    "▲" + ConsoleColor.colorByColor(ammo.getAmmo().get(1).toString()) +
                    "▲" + thirdAmmo(ammo.getAmmo()) +
                    "▲" + ConsoleColor.colorByColor(getRoom().getColor()) +
                    space + space;
        } else s+=space + space + space + space + space + space + space;
        s+=getEastBorder().printByDirection(3, false, color);
        return  s;
    }

    /**
     * The cards on this cell can has or three ammo or two ammo and one powerup.
     * This method change the color of the third element on base of the case.
     * @param ammo The list with the ammo on this cell.
     * @return The color of the third element
     */
    private ConsoleColor thirdAmmo(List<ColorRYB> ammo) {
        if(ammo.size()==3) return ConsoleColor.colorByColor(ammo.get(2).toString());
        else return ConsoleColor.WHITE;
    }
}
