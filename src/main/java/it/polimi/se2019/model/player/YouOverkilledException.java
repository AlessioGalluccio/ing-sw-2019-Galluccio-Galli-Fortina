package it.polimi.se2019.model.player;

public class YouOverkilledException extends Exception  {
    private static final long serialVersionUID = 1885831335830250743L;

    public YouOverkilledException() {
        super();
    }

    public YouOverkilledException(String message) {
        super(message);
    }
}
