package it.polimi.se2019.model.player;

import it.polimi.se2019.model.deck.PointCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Galli
 */
public class Points implements Serializable {
    private static final long serialVersionUID = -7847198853015015370L;
    private ArrayList<PointCard> pointCard = new ArrayList<>();
    private int sum = 0;

    public Points(int num) {
        addNewPoints(num);
    }

    List<PointCard> getPointCard() {
        return new ArrayList<>(pointCard);
    }

    /**
     * get the sum of the points
     * @return the integer sum of the poitns
     */
    int getSum() {
        return sum;
    }

    /**
     * Create new Point Card for a sum of num and add them to the player's list
     * @param num number of points to add
     */
    void addNewPoints(int num) {
        sum = sum + num;

        int num4PointsCards = num / 4;  //division without remainder
        num = num - num4PointsCards * 4;
        int num2PointsCards = num / 2;  //division without remainder
        num = num - num2PointsCards * 2;
        int num1PointsCards = num;

        for(int i = 0; i < num4PointsCards; i++) {
            PointCard temp = new PointCard(4);
            pointCard.add(temp);
        }

        for(int i = 0; i < num2PointsCards; i++) {
            PointCard temp = new PointCard(2);
            pointCard.add(temp);
        }

        for(int i = 0; i < num1PointsCards; i++) {
            PointCard temp = new PointCard(1);
            pointCard.add(temp);
        }
    }
}
