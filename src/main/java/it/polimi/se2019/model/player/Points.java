package it.polimi.se2019.model.player;

import it.polimi.se2019.model.deck.PointCard;

import java.util.ArrayList;

public class Points {
    private ArrayList<PointCard> pointCard;
    private int sum = 0;

    public Points(int num) {
        addNewPoints(num);
    }

    public ArrayList<PointCard> getPointCard() {

        return pointCard;
    }

    public int getSum() {

        return sum;
    }

    public void addNewPoints(int num) {
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

        sum = sum + num;
    }
}
