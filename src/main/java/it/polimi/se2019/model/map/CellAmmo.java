package it.polimi.se2019.model.map;

public class CellAmmo extends Cell {
    private AmmoCard ammo;

    /**
     *
     * @return ammmo card that can be grab here
     */
    public AmmoCard getAmmo(){

    }

    @Override
    public Card grabCard() {
        return super.grabCard();
    }

    @Override
    public void reloadCard() {
        super.reloadCard();
    }
}
