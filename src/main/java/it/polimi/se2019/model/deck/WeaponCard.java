package it.polimi.se2019.model.deck;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.controller.actions.firemodes.FireMode;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.view.StringAndMessage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class WeaponCard implements Card {

    private static final long serialVersionUID = -3963551867744677662L;
    private List<StringAndMessage> correctMessages;
    private ColorRYB ammoGranted;
    private ArrayList<ColorRYB> ammoNotGranted;
    private String name;
    private String description;
    private ColorRYB color;
    private boolean reload;
    private FireMode fireModeChoosen;
    private int ID;

    /**
     * The ID is an unique number for each card.
     * Can't exist two card of the SAME DECK with the same ID. If there are different there may be the same ID.
     * @return The unique ID of the card.
     */
    @Override
    public int getID(){
        return ID;
    }

    /**
     * In general, the ID Type isn't unique for each card.
     * It is the same for the card with the same status.
     * Weapon cards are all different, so ID Type and ID are the same.
     * @return the id type of the card
     */
    @Override
    public int getIDtype(){
        return ID;
    }


    /**
     *
     * @return WeaponCard's buy cost
     */
    public List<ColorRYB> getBuyCost(){
        return ammoNotGranted;
    }

    /**
     *
     * @return A copy of WeaponCard's reload cost
     */
    public List<ColorRYB> getReloadCost() {
        ArrayList<ColorRYB> reloadCost = new ArrayList<>();
        reloadCost.add(ammoGranted);
        if(ammoNotGranted!=null && !ammoNotGranted.isEmpty()) reloadCost.addAll(ammoNotGranted);
        return reloadCost;
    }

    /**
     *
     * @return WeaponCard's name
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @return True if weapon is reloaded, else false
     */
    public boolean isReloaded(){
        return reload;
    }
    /**
     *
     * @return Deep copy of the fire mode list available with this weapon
     */
    public abstract List<FireMode> getFireMode();

    /**
     * reload the weapon
     */
    public void reload() {
        reload = true;
    }

    /**
     * set reload parameter to false
     */
    public void unload(){
        reload = false;
    }

    /**
     * HELPER METHOD
     * Serialize and deserialize FireMode in order to make a deep copy
     * @param fireModeList fire modes to copy
     * @return deep copy of fireModeList
     */
    List<FireMode> duplicateFireMode(List<FireMode> fireModeList) {
        GsonBuilder g = new GsonBuilder()
                .registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>())
                .registerTypeAdapter(Target.class, new JsonAdapter<Target>());
        Gson gson = g.create();

        Type TYPE = new TypeToken<List<FireMode>>() {
        }.getType();

        fireModeList =  gson.fromJson(gson.toJson(fireModeList, TYPE), TYPE);

        return fireModeList;
    }

    /**
     * Set his deck for the card
     * But weapons are created reading a Json file, so thi method is never called
     * @param deck Deck of relatives cards to set
     * @throws AlreadyDeckException If you try to reset the deck, it can change during game
     */
    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {
        //create by Json File
    }

    /**
     * In general, discard the card
     * But weapons CANNOT be discarded, so this method dose nothing.
     */
    @Override
    public void discard() {
        //ATTENTION: you can't discard the weapon, they have to be replace on the map!
    }


    /**
     * String the full card, representing each attributes with symbols and color
     * Works with UTF-8 and ANSI code
     * @return The representation of the card
     */
    @Override
    public String toString() {
        String string = toStringShort();
        string += "\n\tDescription: \n\t" + description;
        string += toStringFireMode();
        return string;
    }

    /**
     * String the fire mode of this card, representing each attributes with symbols and color
     * Works with UTF-8 and ANSI code
     * @return The representation of the fire mode list
     */
    private String toStringFireMode() {
        String string = "";
        for(FireMode fireMode : getFireMode()) {
            for(AmmoBag ammoBag : fireMode.costOfFiremodeNotReloading()) {
                if(ammoBag.getBlueAmmo()==0 &&
                ammoBag.getRedAmmo()==0 &&
                ammoBag.getYellowAmmo()==0) string += "\n\tCost firemode: FREE";
                else string += "\n\tCost firemode: " + ammoBag.toString();
            }
        }
        return string;
    }

    /**
     * String the only the card's name and the cost, representing each attributes with symbols and color
     * Works with UTF-8 and ANSI code
     * @return A short representation of the card
     */
    public String toStringShort() {
        String string = name + ": " +
                ConsoleColor.colorByColor(ammoGranted.toString()) +
                "▲ " + ConsoleColor.RESET;
        if(ammoNotGranted!=null) {
            for (ColorRYB c : ammoNotGranted) {
                if (c != null) string += ConsoleColor.colorByColor(c.toString()) + "▲" + ConsoleColor.RESET;
            }
        }
        return string;
    }
}
