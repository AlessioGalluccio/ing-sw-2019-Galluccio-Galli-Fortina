package it.polimi.se2019.model.deck;

import com.google.gson.*;
import com.google.gson.reflect.*;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.controller.actions.firemodes.FireMode;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Galli
 */
public class WeaponDeck extends Deck<WeaponCard> {

    public WeaponDeck() {
        super(initializeCard());
    }

    /**
     * Read Card form Json file
     * @return List of cards just read, null if FileNotFoundException is thrown
     */
    private static Stack<WeaponCard> initializeCard() {
        Stack<WeaponCard> deck = null;

        GsonBuilder g = new GsonBuilder()
                .registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>())
                .registerTypeAdapter(WeaponCard.class, new JsonAdapter<WeaponCard>());
        Gson gson = g.create();
        try (BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(
                WeaponDeck.class.getClassLoader().getResourceAsStream("WeaponCard")))) {
            //Create a reader for the file
            Type DECK_TYPE = new TypeToken<Stack<WeaponCard>>() {
            }.getType();

            deck  = gson.fromJson(bufferedReader, DECK_TYPE);
            Collections.shuffle(deck);
        } catch (FileNotFoundException | NullPointerException e) {
            Logger.getLogger("it.polimi.se2019.model.deck").log(Level.WARNING, "WeaponCard.json don't found!", e);
        } catch (IOException e) {
            Logger.getLogger("it.polimi.se2019.model.deck").log(Level.WARNING, "Reader file close", e);
        }
        return deck;
    }

    /**
     * Used by Gson to serialize the deck
     * Return a Type object according to the param: ArrayList or Stack
     * @param arrayListORStack True if you want an ArrayList, false for a Stack
     * @return A Type object of the specify class
     */
    @Override
    protected Type getType(boolean arrayListORStack) {
        return arrayListORStack ?
                new TypeToken<ArrayList<WeaponCard>>() {}.getType() :
                new TypeToken<Stack<WeaponCard>>() {}.getType();
    }
}
