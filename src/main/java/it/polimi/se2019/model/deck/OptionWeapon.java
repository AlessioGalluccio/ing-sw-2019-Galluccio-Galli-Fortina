package it.polimi.se2019.model.deck;

import java.util.ArrayList;
import java.util.List;
import it.polimi.se2019.view.ViewControllerMess.StringAndMessage;

public class OptionWeapon {

    private List<StringAndMessage> correctMessages;
    private ArrayList<FireMode> optionalFireModeList;
    private FireMode primaryFireMode;
    private ArrayList<FireMode> fireModeChoosen;

    public void setFireMode(ArrayList<FireMode> fire) {

    }

    public List<StringAndMessage> getCorrectMessages() {

        return correctMessages;
    }

    public FireMode getFireModeByID(int ID) {
        return null; //TODO implementare
    }

    public void verify(){ //lancia delle eccezioni

    }
}
