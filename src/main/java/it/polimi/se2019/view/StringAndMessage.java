package it.polimi.se2019.view;

import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

public class StringAndMessage {
    private ViewControllerMessage message;
    private String string;
    private boolean optional;

    public String getString() {
        return string;
    }

    public ViewControllerMessage getMessage() {
        return message;
    }

    public boolean isOptional() {
        return optional;
    }
}
