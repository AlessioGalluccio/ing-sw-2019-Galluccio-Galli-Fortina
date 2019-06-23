package it.polimi.se2019.model.deck;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
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
     *
     * @return WeaponCard's ID
     */
    @Override
    public int getID(){
        return ID;
    }

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
        ArrayList<ColorRYB> reloadCost = new ArrayList<>(ammoNotGranted);
        reloadCost.add(ammoGranted);
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
     * @return A deep copy of correctMessages
     */
    public List<StringAndMessage> getCorrectMessages() {
        return new ArrayList<>(correctMessages);  //basta una copia dell'array perchè StringAndMessage è immutabile
    }

    /**
     * Set the fire mode chosen by the player for the very next shoot, it will used in order to fire
     * @param fireModeChosen fire mode chosen by the player
     */
    public void setFireMode(FireMode fireModeChosen) {
        this.fireModeChoosen = fireModeChosen;
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
    protected List<FireMode> duplicateFireMode(List<FireMode> fireModeList) {
        GsonBuilder g = new GsonBuilder()
                .registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>())
                .registerTypeAdapter(Target.class, new JsonAdapter<Target>());
        Gson gson = g.create();

        Type TYPE = new TypeToken<List<FireMode>>() {
        }.getType();

        fireModeList =  gson.fromJson(gson.toJson(fireModeList, TYPE), TYPE);

        return fireModeList;
    }

    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {

    }

    @Override
    public void discard() {
        //ATTENTION: you can't discard the weapon, they have to be replace on the map!
    }


    @Override
    public String toString() {
        String string = toStringShort();
        string += "\n\tDescription: \n\t" + description;
        string += toStringFireMode();
        return string;
    }

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

    public String toStringShort() {
        String string = name + ": " +
                ConsoleColor.colorByColor(ammoGranted.toString()) +
                "✚ " + ConsoleColor.RESET;
        if(ammoNotGranted!=null) {
            for (ColorRYB c : ammoNotGranted) {
                if (c != null) string += ConsoleColor.colorByColor(c.toString()) + "✚" + ConsoleColor.RESET;
            }
        }
        return string;
    }
}
