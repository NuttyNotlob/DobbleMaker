package com.company;

import java.util.ArrayList;

public class CardGenerator {

    // This card generation is created using a projected plane. For the theory behind some of the maths used in this,
    // please go to https://www.youtube.com/watch?v=VTDKqW_GLkw&t=961s

    // Parameters for calculation
    private static int primeValue = 11;
    private static int symbolsPerCard = primeValue + 1; // The number of symbols on each card
    private static int numberCards = (int)Math.pow(primeValue, 2) + primeValue + 1; // The number of cards required
    private static int numberSymbols = numberCards; // For ease of use, as this is the same as numberCards

    // Will use two arrays of DobbleCards to represent the square array (primeValue ^2) and the vanishing points
    // (primeValue + 1)
    private static DobbleCard[][] squareArrayCards = new DobbleCard[primeValue][primeValue];
    private static DobbleCard[] vanishingPointsCards = new DobbleCard[primeValue + 1];

    private static ArrayList<DobbleCard> allCards = new ArrayList<>();

    private static int imageValue = 1; // For assigning image filename. Starting at 1 to reach numberSymbols at the end

    static {
        // First need to initialise the cards in the arrays
        for (int i = 0; i < primeValue; i++) {
            for (int j = 0; j < primeValue; j++) {
                squareArrayCards[i][j] = new DobbleCard();
            }
        }
        for (int i = 0; i <= primeValue; i++) {
            vanishingPointsCards[i] = new DobbleCard();
        }
    }

    public static void main(String[] args) {
        generateCards();
    }

    public static void generateCards() {

        // First we do the vertical lines and the vertical slants in our projected plane array. These are essentially
        // starting from the top row (going from each element in that row), going through the different possible
        // vertical lines. Each goes down by one row on each increment, but the slant of the lines is changed by how
        // much they move horizontally in the array with each increment

        // i represents our position along the top row
        for (int i = 0; i < primeValue; i++) {

            // j represents the horizontal movement per increment we are on (so determines the slant of our line)
            for (int j = 0; j < primeValue; j++) {

                // k represents the increment of the line, so this loop takes us through the whole line. The line comes
                // back around the array in a projected plane, hence the use of % primeValue
                for (int k = 0; k < primeValue; k++) {
                    assignImage(squareArrayCards[((i + j*k) % primeValue)][k]);
                }

                // We also assign our vanishing point array here
                assignImage(vanishingPointsCards[j]);

                imageValue++;
            }
        }

        // Finally the horizontal lines, and the associated vanishing point. Remember, i is the horizontal j represents
        // the vertical
        for (int j = 0; j < primeValue; j++) {
            for (int i = 0; i < primeValue; i++) {
                // Assigning all in the same horizontal line the same image
                assignImage(squareArrayCards[i][j]);
            }
            // All horizontal lines meet at an infinitely distant point, which means they all go into one card. Here,
            // this is our final vanishing point
            assignImage(vanishingPointsCards[primeValue]);

            // Now we move onto the next line, and so the next image
            imageValue++;
        }

        // And finally, all the vanishingPoints also share a symbol
        for (DobbleCard card : vanishingPointsCards) {
            assignImage(card);
        }

        // We now add all the DobbleCards into one combined list
        for (int i = 0; i < primeValue; i++) {
            for (int j = 0; j < primeValue; j++) {
                allCards.add(squareArrayCards[i][j]);
            }
        }
        for (DobbleCard card : vanishingPointsCards) {
            allCards.add(card);
        }
    }

    private static void assignImage(DobbleCard card) {
        // A fairly simple method, but allows the easy changing of file paths of file types if required
        card.addImage("images/" + imageValue + ".jpg");
    }

    public static int getPrimeValue() {
        return primeValue;
    }

    public static void setPrimeValue(int primeValue) {
        CardGenerator.primeValue = primeValue;
    }

    public static int getSymbolsPerCard() {
        return symbolsPerCard;
    }

    public static void setSymbolsPerCard(int symbolsPerCard) {
        CardGenerator.symbolsPerCard = symbolsPerCard;
    }

    public static int getNumberCards() {
        return numberCards;
    }

    public static void setNumberCards(int numberCards) {
        CardGenerator.numberCards = numberCards;
    }

    public static int getNumberSymbols() {
        return numberSymbols;
    }

    public static void setNumberSymbols(int numberSymbols) {
        CardGenerator.numberSymbols = numberSymbols;
    }

    public static DobbleCard[][] getSquareArrayCards() {
        return squareArrayCards;
    }

    public static void setSquareArrayCards(DobbleCard[][] squareArrayCards) {
        CardGenerator.squareArrayCards = squareArrayCards;
    }

    public static DobbleCard[] getVanishingPointsCards() {
        return vanishingPointsCards;
    }

    public static void setVanishingPointsCards(DobbleCard[] vanishingPointsCards) {
        CardGenerator.vanishingPointsCards = vanishingPointsCards;
    }

    public static int getImageValue() {
        return imageValue;
    }

    public static void setImageValue(int imageValue) {
        CardGenerator.imageValue = imageValue;
    }

    public static ArrayList<DobbleCard> getAllCards() {
        return allCards;
    }

    public static void setAllCards(ArrayList<DobbleCard> allCards) {
        CardGenerator.allCards = allCards;
    }
}
