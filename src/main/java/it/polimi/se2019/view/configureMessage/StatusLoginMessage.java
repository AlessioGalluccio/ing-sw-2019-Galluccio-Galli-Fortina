package it.polimi.se2019.view.configureMessage;

public class StatusLoginMessage {
    private boolean status;

    public StatusLoginMessage(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}
