package it.polimi.se2019.model.deck;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.JsonAdapter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class WeaponDeck extends Deck<WeaponCard> {

    /**
     *
     * @throws FileNotFoundException If can't find the Json file from which deserialize the cards
     */
    public WeaponDeck() throws FileNotFoundException{
        super(initializeCard());
    }

    /**
     * Read Card form Json file
     * @return List of cards just read
     * @throws FileNotFoundException If can't find the Json file from which deserialize the cards
     */
    private static Stack<WeaponCard> initializeCard() throws FileNotFoundException{
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

        bufferedReader = new BufferedReader(new FileReader("WeaponCard"));



        //Mi serve per passarlo al deserealizzatore
        Type DECK_TYPE = new TypeToken<Stack<WeaponCard>>() {
        }.getType();

        deck  = gson.fromJson(bufferedReader, DECK_TYPE);
        Collections.shuffle(deck);
        return deck;
    }

    @Override
    protected Type getType(boolean ArrayListORStack) {
        return ArrayListORStack ?
                new TypeToken<ArrayList<WeaponCard>>() {}.getType() :
                new TypeToken<Stack<WeaponCard>>() {}.getType();
    }
}
