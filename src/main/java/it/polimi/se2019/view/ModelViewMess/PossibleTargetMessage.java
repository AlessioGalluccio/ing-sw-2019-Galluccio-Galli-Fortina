package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.network.Client;

import java.util.ArrayList;
import java.util.List;

public class PossibleTargetMessage implements ModelViewMessage{

    private static final long serialVersionUID = 6466147418722129048L;
    private ArrayList<? extends Target> possibleTarget;

    public PossibleTargetMessage(List<? extends Target> possibleTarget) {
        this.possibleTarget = (ArrayList<? extends Target>) possibleTarget;
    }

    public List<? extends Target> getPossibleTarget() {
        return possibleTarget;
    }

    @Override
    public void handleMessage(Client client) {

    }
}
