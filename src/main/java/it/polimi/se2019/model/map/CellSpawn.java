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
            if(weapon[i]==null && deck.sizeUnususedCard()!=0) weapon[i]=deck.pick();
        }
        notifyObservers(new CellModelMessage(this.clone()));
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
     * Print this cell on System.out line by line
     * @param row the line to print
     */
    @Override
    @SuppressWarnings("squid:S106")
    public void printRow(int row) {
        ConsoleColor color = ConsoleColor.colorByColor(getRoom().getColor());
        String space = ConsoleColor.WHITE_BOLD_BRIGHT + "▦" + color;
        switch (row) {
            case 0:
                getNorthBorder().printByDirection(0, true, color);
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
                break;
            case 3:
                getWestBorder().printByDirection(3, false, color);
                System.out.print(space + space +
                "↺↺↺" +
                space + space);
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
}
