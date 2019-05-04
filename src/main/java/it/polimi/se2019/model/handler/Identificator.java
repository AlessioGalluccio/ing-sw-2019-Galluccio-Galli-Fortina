package it.polimi.se2019.model.handler;

import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.firemodes.*;

public class Identificator {

    //list of ID of messages
    public static final int NOPE_MESSAGE = 0;
    public static final int ACTION_MESSAGE = 1;
    public static final int CARD_SPAWN_CHOOSE_MESSAGE = 2;
    public static final int CELL_MESSAGE = 3;
    public static final int FIRE_MODE_MESSAGE = 4;
    public static final int NEWTON_MESSAGE = 5;
    public static final int PLAYER_VIEW_MESSAGE = 6;
    public static final int RELOAD_MESSAGE = 7;
    public static final int TAGBACK_GRANADE_MESSAGE = 8;
    public static final int TARGETING_SCOPE_MESSAGE = 9;
    public static final int TARGET_MESSAGE = 10;
    public static final int TELEPORTER_MESSAGE = 11;
    public static final int LOGIN_MESSAGE = 12;
    public static final int END_MESSAGE = 13;
    public static final int UNDO_MESSAGE = 14;

    //list of ID of actions
    public static final int MOVE = 1;
    public static final int GRAB = 2;
    public static final int SHOOT = 3;

    //list of ID of Firemodes
    public static final int CYBERBLADE_1 = 1;
    public static final int CYBERBLADE_2 = 2;
    public static final int CYBERBLADE_3 = 3;
    public static final int ELECTROSCYTHE_1 = 4;
    public static final int ELECTROSCYTHE_2 = 5;
    public static final int FLAMETHROWER_1 = 6;
    public static final int FLAMETHROWER_2 = 7;
    //TODO finisci







    public static FireMode getFiremodeByID(int id){
        switch(id){
            case CYBERBLADE_1 : return new CyberBlade_1();
            case CYBERBLADE_2 : return new CyberBlade_2();
            case CYBERBLADE_3 : return new CyberBlade_3();
            case ELECTROSCYTHE_1 : return new ElectroScythe_1();
            case ELECTROSCYTHE_2: return new ElectroScythe_2();
            case FLAMETHROWER_1 : return new FlameThrower_1();
            case FLAMETHROWER_2: return new FlameThrower_2();
            //TODO finisci

            default: throw new WrongIdRuntimeException(); //should not happen. Runtime Exception
        }

    }

}
