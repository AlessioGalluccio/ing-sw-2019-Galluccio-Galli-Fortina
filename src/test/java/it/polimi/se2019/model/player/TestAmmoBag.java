package it.polimi.se2019.model.player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAmmoBag {
    private AmmoBag firstAmmo;
    private AmmoBag secondAmmo;

    @Before
    public void initTest(){
        firstAmmo = new AmmoBag(1,2,3);
        secondAmmo = new AmmoBag(2,2,2);
    }

    @Test
    public void getRedAmmo() {
        assertEquals(firstAmmo.getRedAmmo(), 1);
    }

    @Test
    public void getYellowAmmo() {
        assertEquals(firstAmmo.getYellowAmmo(), 2);
    }

    @Test
    public void getBlueAmmo() {
        assertEquals(firstAmmo.getBlueAmmo(), 3);
    }


    @Test
    public void testSumAmmoBag(){
        AmmoBag newAmmo = AmmoBag.sumAmmoBag(firstAmmo, secondAmmo);
        assertEquals(3, newAmmo.getRedAmmo());
        assertEquals(4, newAmmo.getYellowAmmo());
        assertEquals(5, newAmmo.getBlueAmmo());
    }

}
