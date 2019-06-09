package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.WeaponDeck;

import java.util.ArrayList;

public class Map1 extends Map {

    private static final String description = "This map is excellent for any number of players!";

    public Map1(WeaponDeck weaponDeck, AmmoDeck ammoDeck) {
        /*
        The first instruction of this constructor MUST be super()
        So we can't generate the cell[][] matrix and pass it to super() but we have to recall a static method inside super()
        In order to create rooms and cells together I create a new class (InitializeMap)
         */
        super(initialize(weaponDeck, ammoDeck), "This map is excellent for any number of players!", 1);
    }

    private static InitializeMap initialize(WeaponDeck weaponDeck, AmmoDeck ammoDeck) {
        InitializeMap init = new InitializeMap(3, 4);

        //cell[X][Y]
        init.cell[0][0] = null;

        init.cell[1][0] = new CellAmmo(new Door(), new Door(), new Wall(), new Wall(), 1, 0, ammoDeck);

        ArrayList<Cell> cellForRoom = new ArrayList<>();
        cellForRoom.add(init.cell[1][0]);

        init.room.add(new Room(null, "WHITE", new ArrayList<>(cellForRoom)));

        init.createCommonYellowGreenRoom(weaponDeck, ammoDeck);

        init.cell[1][1] = new CellAmmo(new Wall(), new Wall(), new Door(), new Passage(), 1,1, ammoDeck);
        init.cell[0][1] = new CellSpawn(new Door(), new Passage(), new Wall(), new Wall(), 0 ,1, weaponDeck);

        cellForRoom.clear();
        cellForRoom.add(init.cell[1][1]);
        cellForRoom.add(init.cell[0][1]);

        init.room.add(new Room((CellSpawn) init.cell[0][1], "RED", new ArrayList<>(cellForRoom)));

        init.cell[0][2] = new CellAmmo(new Wall(), new Passage(), new Door(), new Wall(), 0, 2, ammoDeck);
        init.cell[1][2] = new CellAmmo(new Wall(), new Passage(), new Wall(), new Passage(), 1, 2, ammoDeck);
        init.cell[2][2] = new CellSpawn(new Wall(), new Door(), new Door(), new Passage(), 2,2, weaponDeck);

        init.addCommonBlueRoom();

        return init;
    }

    public static String getDescription() {
        return description;
    }
}
