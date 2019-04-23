package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.handler.Identificator;

public class EndMessage extends ViewControllerMessage {

    private int messageID;


    public EndMessage() {

        this.messageID = Identificator.END_MESSAGE;
    }

    @Override
    public int getMessageID() {
        return messageID ;
    }
}
