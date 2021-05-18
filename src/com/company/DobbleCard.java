package com.company;

import java.util.ArrayList;
import java.util.List;

public class DobbleCard {
    private List<String> imageStrings;
    private static int cardsMade = 0;
    private int cardNumber;

    public DobbleCard() {
        this.imageStrings = new ArrayList<>();
        cardsMade++;
        cardNumber = cardsMade;
    }

    public void addImage(String imageString) {
        if (imageStrings.size() > 13) {
            System.out.println("This isn't good for card " + cardsMade + ", they have too many symbols");
        }
        imageStrings.add(imageString);
    }

    public List<String> getImageStrings() {
        return imageStrings;
    }

    public static int getCardsMade() {
        return cardsMade;
    }

    public int getCardNumber() {
        return cardNumber;
    }

}
