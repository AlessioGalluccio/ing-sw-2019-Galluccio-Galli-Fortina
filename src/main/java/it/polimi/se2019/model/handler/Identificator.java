package it.polimi.se2019.model.handler;

import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.WeaponCard;

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
    public static final int OPTIONAL_MESSAGE = 17;
    public static final int CONNECTION_MESSAGE = 18;
    public static final int DISCARD_POWERUP_MESSAGE = 19;
    public static final int DISCARD_WEAPON_MESSAGE = 20;
    public static final int CHARACTER_MESSAGE = 21;

    //list of ID of actions
    public static final int MOVE = 1;
    public static final int GRAB = 2;
    public static final int SHOOT = 3;

    //list of num for Optional
    public static final int FIRST_OPTIONAL = 1;
    public static final int SECOND_OPTIONAL = 2;
    public static final int THIRD_OPTIONAL = 3;

    //list of ID of powerups
    public static final int TARGETING_SCOPE_RED = 0;
    public static final int TARGETING_SCOPE_YELLOW = 1;
    public static final int TARGETING_SCOPE_BLUE = 2;
    public static final int NEWTON_RED = 10;
    public static final int NEWTON_YELLOW = 11;
    public static final int NEWTON_BLUE = 12;
    public static final int TELEPORTER_RED = 20;
    public static final int TELEPORTER_YELLOW = 21;
    public static final int TELEPORTER_BLUE = 22;
    public static final int TAGBACK_GRENADE_RED = 30;
    public static final int TAGBACK_GRENADE_YELLOW = 31;
    public static final int TAGBACK_GRENADE_BLUE = 32;

    /**
     * Use this method to control if a weapon has a firemode. contains() gives errors
     * @param weaponCard the weapon
     * @param fireMode the firemode
     * @return true if it contains, false if not
     */
    public static boolean containsFiremode(WeaponCard weaponCard, FireMode fireMode){
        return (fireMode.getID())/10 == weaponCard.getID();
    }

    public static int createFiremode(int weaponId, int fireModeID) {
        return weaponId*10 + fireModeID;
    }

}
