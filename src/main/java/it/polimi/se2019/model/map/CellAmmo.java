package it.polimi.se2019.model.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.se2019.cloneable.SkinnyObjectExclusionStrategy;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.view.ModelViewMess.CellModelMessage;

import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

public class CellAmmo extends Cell {
    private AmmoCard ammo;
    private transient AmmoDeck deck;
    private boolean disable;

    public CellAmmo(Border north, Border east, Border south, Border west, int x, int y, AmmoDeck deck) {
        super(north, east, south, west, x, y);
        this.deck = deck;
        disable=false;
    }

    public CellAmmo(int x, int y) {
        super(new Passage(), new Passage(), new Passage(),new Passage(), x, y);
        disable=true;
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
        if(ammo==null&&!disable) ammo = deck.pick();
        notifyObservers(new CellModelMessage(this.clone()));
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
     * Print this cell on System.out line by line
     * @param row the line to print
     */
    @Override
    @SuppressWarnings("squid:S106")
    public void printRow( int row) {
        ConsoleColor color = ConsoleColor.colorByColor(getRoom().getColor());
        String space = ConsoleColor.WHITE_BOLD_BRIGHT + "▦" + color;
        if(!disable) System.out.print(color);
        switch (row) {
            case 0:
                getNorthBorder().printByDirection(0,true, color);
                break;
            case 1:
                getWestBorder().printByDirection(1, false, color);
                System.out.print(space + space);
                if(getPlayerHere().size()==1) {
                    System.out.print(ConsoleColor.colorByColor(getPlayerHere().get(0).getCharacter().getColor()));
                    System.out.print("◉");
                }else System.out.print(space);
                System.out.print(space);
                if(getPlayerHere().size()==2) {
                    System.out.print(ConsoleColor.colorByColor(getPlayerHere().get(1).getCharacter().getColor()));
                    System.out.print("◉");
                }else System.out.print(space);
                System.out.print(space + space);
                getEastBorder().printByDirection(1, false, color);
                if(getCoordinateX()==2&&getCoordinateY()==3||getCoordinateX()==2&&getCoordinateY()==2) printWeapon(getCoordinateY(), 1);
                break;
            case 2:
                getWestBorder().printByDirection(2, false, color);
                System.out.print(space + space);
                System.out.print(space);
                if(getPlayerHere().size()==5) {
                    System.out.print(ConsoleColor.colorByColor(getPlayerHere().get(4).getCharacter().getColor()));
                    System.out.print("◉");
                }else System.out.print(space);
                System.out.print(space);
                System.out.print(space + space);
                getEastBorder().printByDirection(2, false, color);
                if(getCoordinateX()==2&&getCoordinateY()==3||getCoordinateX()==2&&getCoordinateY()==2) printWeapon(getCoordinateY(), 2);
                break;
            case 3:
                getWestBorder().printByDirection(3, false, color);
                if(ammo!=null) {
                    System.out.print(space + space
                            + ConsoleColor.colorByColor(ammo.getAmmo().get(0).toString()) +
                            "✚" + ConsoleColor.colorByColor(ammo.getAmmo().get(1).toString()) +
                            "✚" + thirdAmmo(ammo.getAmmo()) +
                            "✚" + ConsoleColor.colorByColor(getRoom().getColor()) +
                            space + space);
                } else System.out.print(space + space + space + space + space + space + space);
                getEastBorder().printByDirection(3, false, color);
                break;
            case 4:
                getWestBorder().printByDirection(4, false, color);
                System.out.print(ConsoleColor.WHITE_BOLD_BRIGHT + "▦▦▦▦▦▦▦" + color);
                getEastBorder().printByDirection(4, false, color);
                break;
            case 5:
                getWestBorder().printByDirection(5, false, color);
                System.out.print(space + space);
                if(getPlayerHere().size()==3) {
                    System.out.print(ConsoleColor.colorByColor(getPlayerHere().get(2).getCharacter().getColor()));
                    System.out.print("◉");
                }else System.out.print(space);
                System.out.print(space);
                if(getPlayerHere().size()==4) {
                    System.out.print(ConsoleColor.colorByColor(getPlayerHere().get(3).getCharacter().getColor()));
                    System.out.print("◉");
                }else System.out.print(space);
                System.out.print(space + space);
                getEastBorder().printByDirection(5, false,color);
                break;
            case 6:
                getSouthBorder().printByDirection(6, true, color);
                break;
            default:
        }
        System.out.print(ConsoleColor.RESET);
    }

    private ConsoleColor thirdAmmo(List<ColorRYB> ammo) {
        if(ammo.size()==3) return ConsoleColor.colorByColor(ammo.get(2).toString());
        else return ConsoleColor.BLACK_BOLD;
    }

    private void printWeapon(int y, int row) {
        switch (row) {
            case 1:
                if(y==2)
                break;
        }
    }
}
