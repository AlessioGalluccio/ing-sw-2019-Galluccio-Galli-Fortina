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


    @Override
    public Card grabCard(int cardID) throws NotCardException {
        if(ammo == null) throw new NotCardException("Card already taken");
        Card cardToReturn = ammo;
        ammo = null;
        notifyObservers(new CellModelMessage(this.clone()));
        return cardToReturn;
    }

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
        String space = ConsoleColor.BLACK + "▦" + color;
        s+=getWestBorder().printByDirection(3, false, color);
        if(ammo!=null) {
           s+= space + space
                    + ConsoleColor.colorByColor(ammo.getAmmo().get(0).toString()) +
                    "✚" + ConsoleColor.colorByColor(ammo.getAmmo().get(1).toString()) +
                    "✚" + thirdAmmo(ammo.getAmmo()) +
                    "✚" + ConsoleColor.colorByColor(getRoom().getColor()) +
                    space + space;
        } else s+=space + space + space + space + space + space + space;
        s+=getEastBorder().printByDirection(3, false, color);
        return  s;
    }

    private ConsoleColor thirdAmmo(List<ColorRYB> ammo) {
        if(ammo.size()==3) return ConsoleColor.colorByColor(ammo.get(2).toString());
        else return ConsoleColor.WHITE;
    }
}
