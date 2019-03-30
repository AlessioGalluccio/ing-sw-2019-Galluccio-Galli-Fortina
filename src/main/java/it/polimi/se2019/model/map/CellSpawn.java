package it.polimi.se2019.model.map;

public class CellSpawn extends Cell {
    private ArrayList<WeaponCard> weapon;
    private Color color;
    private Room room;

    /**
     *
     * @return list of WeaponCard that can be pick here
     */
    public ArrayList<WeaponCard> getWeapon() {
        return weapon;
    }

    public Color getColor() {
        return color;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public Card grabCard(){
    }

    @Override
    public void reloadCard(){

    }

    public void updateWeaponCard(){

    }
}
