package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.WeaponDeck;

import java.util.ArrayList;

public class Map2 extends Map {

    private static final String description = "This map is excellent for any number of players!";
    private static final long serialVersionUID = 4304018998670436291L;

    public Map2(WeaponDeck weaponDeck, AmmoDeck ammoDeck) {
        /*
        The first instruction of this constructor MUST be super()
        So we can't generate the cell[][] matrix and pass it to super() but we have to recall a static method inside super()
        In order to create rooms and cells together I create a new class (InitializeMap)
         */
        super(initialize(weaponDeck, ammoDeck), "This map is excellent for any number of players!", 2);
    }

    private static InitializeMap initialize(WeaponDeck weaponDeck, AmmoDeck ammoDeck) {
        InitializeMap init = new InitializeMap(3, 4);

        init.cell[0][0] = new CellAmmo(new Door(), new Passage(), new Wall(), new Wall(), 0 ,0, ammoDeck);
        init.cell[1][0] = new CellAmmo(new Door(), new Passage(), new Wall(), new Passage(), 1, 0, ammoDeck);
        init.cell[2][0] = new CellAmmo(new Wall(), new Door(), new Wall(), new Passage(), 2, 0, ammoDeck);

        ArrayList<Cell> cellForRoom = new ArrayList<>();
        cellForRoom.add(init.cell[0][0]);
        cellForRoom.add(init.cell[1][0]);
        cellForRoom.add(init.cell[2][0]);

        init.room.add(new Room(null, "WHITE", new ArrayList<>(cellForRoom)));

        init.cell[3][0] = new CellSpawn(new Passage(), new Wall(), new Wall(), new Door(), 3, 0, weaponDeck);
        init.cell[3][1] = new CellAmmo(new Wall(), new Wall(), new Passage(), new Door(), 3, 1, ammoDeck);

        cellForRoom.clear();
        cellForRoom.add(init.cell[3][0]);
        cellForRoom.add(init.cell[3][1]);

        init.room.add(new Room((CellSpawn) init.cell[3][0], "YELLOW", new ArrayList<>(cellForRoom)));

        init.cell[2][1] = new CellAmmo(new Door(), new Door(), new Wall(), new Passage(), 2, 1, ammoDeck);
        init.cell[1][1] = new CellAmmo(new Door(), new Passage(), new Door(), new Wall(), 1,1, ammoDeck);

        cellForRoom.clear();
        cellForRoom.add(init.cell[2][1]);
        cellForRoom.add(init.cell[1][1]);

        init.room.add(new Room(null, "PURPLE", new ArrayList<>(cellForRoom)));

        init.createCommonRedRoom(weaponDeck, ammoDeck);

        init.cell[2][2] = new CellSpawn(new Wall(), new Wall(), new Door(), new Passage(), 2, 2, weaponDeck);

        cellForRoom.clear();
        cellForRoom.add(init.cell[1][2]);
        cellForRoom.add(init.cell[2][2]);

        init.room.add(new Room((CellSpawn) init.cell[2][2], "BLUE", new ArrayList<>(cellForRoom)));

        cellForRoom.clear();
        init.cell[3][2] = new CellAmmo(3,2);
        cellForRoom.add(init.cell[3][2]);
        init.room.add(new Room(null, "BLACK", new ArrayList<>(cellForRoom)));

        return init;
    }

    public static String getDescription() {
        return description;
    }
}
