package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.WeaponDeck;

import java.util.ArrayList;

public class Map3 extends Map {

    private static final String description = "This map is excellent for 5 players!";

    public Map3(WeaponDeck weaponDeck, AmmoDeck ammoDeck) {
        /*
        The first instruction of this constructor MUST be super()
        So we can't generate the cell[][] matrix and pass it to super() but we have to recall a static method inside super()
        In order to create rooms and cells together I create a new class (InitializeMap)
         */
        super(initialize(weaponDeck, ammoDeck), "This map is excellent for 5 players!", 3);
    }

    private static InitializeMap initialize(WeaponDeck weaponDeck, AmmoDeck ammoDeck) {
        InitializeMap init = new InitializeMap(3, 4);

        init.cell[0][0] = new CellAmmo(new Door(), new Passage(), new Wall(), new Wall(), 0, 0, ammoDeck);
        init.cell[0][1] = new CellAmmo(new Door(), new Door(), new Wall(), new Passage(), 0, 1, ammoDeck);

        ArrayList<Cell> cellForRoom = new ArrayList<>();
        cellForRoom.add(init.cell[0][0]);
        cellForRoom.add(init.cell[0][1]);

        init.room.add(new Room(null, "WHITE", new ArrayList<>(cellForRoom)));

        init.createCommonYellowGreenRoom(weaponDeck, ammoDeck);

        init.cell[1][1] = new CellAmmo(new Door(), new Wall(), new Door(), new Wall(), 1, 1, ammoDeck);

        cellForRoom.clear();
        cellForRoom.add(init.cell[1][1]);

        init.room.add(new Room(null, "PURPLE", new ArrayList<>(cellForRoom)));

        init.createCommonRedRoom(weaponDeck, ammoDeck);

        init.cell[2][2] = new CellSpawn(new Wall(), new Door(), new Door(), new Passage(), 2, 2, weaponDeck);

        cellForRoom.clear();
        cellForRoom.add(init.cell[1][2]);
        cellForRoom.add(init.cell[2][2]);

        init.room.add(new Room((CellSpawn) init.cell[2][2], "BLUE", new ArrayList<>(cellForRoom)));

        return init;
    }

    public static String getDescription() {
        return description;
    }

}
