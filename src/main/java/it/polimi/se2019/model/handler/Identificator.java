package it.polimi.se2019.model.handler;

import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.deck.firemodes.*;

public class Identificator {

    //list of ID of messages
    public static final int NOPE_MESSAGE = 0;
    public static final int ACTION_MESSAGE = 1;
    public static final int CARD_SPAWN_CHOOSE_MESSAGE = 2;
    public static final int CELL_MESSAGE = 3;
    public static final int FIRE_MODE_MESSAGE = 4;
    public static final int NEWTON_MESSAGE = 5;
    public static final int PLAYER_MESSAGE = 6;
    public static final int RELOAD_MESSAGE = 7;
    public static final int TAGBACK_GRANADE_MESSAGE = 8;
    public static final int TARGETING_SCOPE_MESSAGE = 9;
    public static final int TARGET_MESSAGE = 10;
    public static final int TELEPORTER_MESSAGE = 11;
    public static final int LOGIN_MESSAGE = 12;
    public static final int PASS_MESSAGE = 13;
    public static final int UNDO_MESSAGE = 14;
    public static final int WEAPON_MESSAGE = 15;
    public static final int FIRE_MESSAGE = 16;

    //list of ID of actions
    public static final int MOVE = 1;
    public static final int GRAB = 2;
    public static final int SHOOT = 3;

    //list of num for Optional
    public static final int FIRST_OPTIONAL = 1;
    public static final int SECOND_OPTIONAL = 2;
    public static final int THIRD_OPTIONAL = 3;

    /**
     * use this method to controll if a weapon has a firemode. contains() gives errors
     * @param weaponCard the weapon
     * @param fireMode the firemode
     * @return true if it contsins, false if not
     */
    public static boolean containsFiremode(WeaponCard weaponCard, FireMode fireMode){
        return (fireMode.getID())/10 == weaponCard.getID();
    }

    //list of ID of Firemodes
   /* public static final int CYBERBLADE_1 = 1;
    public static final int CYBERBLADE_2 = 2;
    public static final int CYBERBLADE_3 = 3;
    public static final int ELECTROSCYTHE_1 = 4;
    public static final int ELECTROSCYTHE_2 = 5;
    public static final int FLAMETHROWER_1 = 6;
    public static final int FLAMETHROWER_2 = 7;*/

    //Sono salvati nel Json delle armi, non servono qui. Eliminiamo?






    //Stesso methodo in GameHandler, usare quello che pesca gli ID dal json della armi

 /*   public static FireMode getFiremodeByID(int id){
        switch(id){

            default: throw new WrongIdRuntimeException(); //should not happen. Runtime Exception
        }

    }*/

}
