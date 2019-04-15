package it.polimi.se2019.model.deck;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.player.Color;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.view.ViewControllerMess.StringAndMessage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class WeaponCard implements Card {

    private List<StringAndMessage> correctMessages;
    private ColorRYB ammoGranted;
    private ArrayList<ColorRYB> ammoNotGranted;
    private String name;
    private String description;
    private ColorRYB color;
    private boolean reload;
    private FireMode fireModeChoosen;


    public ArrayList<ColorRYB> getBuyCost(){

        return null; //TODO implementare
    }

    public ArrayList<Color> getReloadCost() {

        return null; //TODO implementare
    }

    public String getName(){
        return name;
    }

    public boolean isReloaded(){

        return reload; //TODO implementare
    }

    /**
     *
     * @return A deep copy of correctMessages
     */
    public List<StringAndMessage> getCorrectMessages() {
        return new ArrayList(correctMessages);  //basta una copia dell'array perchè StringAndMessage è immutabile
    }

    public void setFireMode(FireMode fireModeChoosen) {

    }

    public FireMode getFireModeByID(int ID) {
        return null; //TODO implementare
    }

    public abstract List<FireMode> getFireMode();

    /**
     * HELPER METHOD
     * Serialize and deserialize FireMode in order to make a deep copy
     * @param fireModeList fire modes to copy
     * @return deep copy of fireModeList
     */
    protected List<FireMode> duplicateFireMode(List<FireMode> fireModeList) {
        GsonBuilder g = new GsonBuilder().registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>());
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

    }

    @Override
    public void useCard() {

    }
}
