package it.polimi.se2019.view;

import it.polimi.se2019.model.player.Shoot;

public class ShootMessage extends ViewControllerMessage {

    private Shoot shoot;

    public ShootMessage(Shoot shoot) {
        this.shoot = shoot;
    }

    public Shoot getShoot() {
        return shoot;
    }
}
