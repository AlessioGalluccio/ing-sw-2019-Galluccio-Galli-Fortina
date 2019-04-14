package it.polimi.se2019.model.deck;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.player.Color;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class WeaponDeck extends Deck<WeaponCard> {

    public WeaponDeck(){
        super(initializeCard());
    }

    /**
     * Read Card form Json file
     * @return List of cards just read
     */
    private static Stack<WeaponCard> initializeCard(){
        Stack<WeaponCard> deck;
        BufferedReader bufferedReader = null;

        /*
        Bisogna registrare tutti tutte le classi astratte o interfacce che vengono usate nell'oggetto
        Ex. FireMode è una classe astratta con tanti figli, devo registarla in modo che json sappia risalire al figlio corretto
         */
        GsonBuilder g = new GsonBuilder()
                .registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>())
                .registerTypeAdapter(WeaponCard.class, new JsonAdapter<WeaponCard>());
        Gson gson = g.create();

        //Creo un reader per leggere da file
        try {
            bufferedReader = new BufferedReader(new FileReader("WeaponCard"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Mi serve per passarlo al deserealizzatore
        Type DECK_TYPE = new TypeToken<Stack<WeaponCard>>() {
        }.getType();

        deck  = gson.fromJson(bufferedReader, DECK_TYPE);
        return deck;
    }
}
