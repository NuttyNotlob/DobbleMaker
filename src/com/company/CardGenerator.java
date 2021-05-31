package com.company;

import java.util.ArrayList;
import java.util.List;

public class CardGenerator {

    // This card generation is created using a projected plane. For the theory behind some of the maths used in this,
    // please go to https://www.youtube.com/watch?v=VTDKqW_GLkw&t=961s

    // Parameters for calculation
    private static int PRIME_VALUE = 7;
    private static int symbolsPerCard = PRIME_VALUE + 1; // The number of symbols on each card
    private static int numberCards = (int)Math.pow(PRIME_VALUE, 2) + PRIME_VALUE + 1; // The number of cards required
    private static int numberSymbols = numberCards; // For ease of use, as this is the same as numberCards

    // Will use two arrays of DobbleCards to represent the square array (primeValue ^2) and the vanishing points
    // (primeValue + 1)
    private static DobbleCard[][] squareArrayCards = new DobbleCard[PRIME_VALUE][PRIME_VALUE];
    private static DobbleCard[] vanishingPointsCards = new DobbleCard[PRIME_VALUE + 1];

    private static ArrayList<DobbleCard> allCards = new ArrayList<>();

    private static int imageValue = 1; // For assigning image filename. Starting at 1 to reach numberSymbols at the end

    static {
        // First need to initialise the cards in the arrays
        for (int i = 0; i < PRIME_VALUE; i++) {
            for (int j = 0; j < PRIME_VALUE; j++) {
                squareArrayCards[i][j] = new DobbleCard();
            }
        }
        for (int i = 0; i <= PRIME_VALUE; i++) {
            vanishingPointsCards[i] = new DobbleCard();
        }
    }

    public static void main(String[] args) {
        generateCards();
        drawCards();
        drawPages();
    }

    public static void generateCards() {

        // First we do the vertical lines and the vertical slants in our projected plane array. These are essentially
        // starting from the top row (going from each element in that row), going through the different possible
        // vertical lines. Each goes down by one row on each increment, but the slant of the lines is changed by how
        // much they move horizontally in the array with each increment

        // i represents our position along the top row
        for (int i = 0; i < PRIME_VALUE; i++) {

            // j represents the horizontal movement per increment we are on (so determines the slant of our line)
            for (int j = 0; j < PRIME_VALUE; j++) {

                // k represents the increment of the line, so this loop takes us through the whole line. The line comes
                // back around the array in a projected plane, hence the use of % primeValue
                for (int k = 0; k < PRIME_VALUE; k++) {
                    assignImage(squareArrayCards[((i + j*k) % PRIME_VALUE)][k]);
                }

                // We also assign our vanishing point array here
                assignImage(vanishingPointsCards[j]);

                imageValue++;
            }
        }

        // Finally the horizontal lines, and the associated vanishing point. Remember, i is the horizontal j represents
        // the vertical
        for (int j = 0; j < PRIME_VALUE; j++) {
            for (int i = 0; i < PRIME_VALUE; i++) {
                // Assigning all in the same horizontal line the same image
                assignImage(squareArrayCards[i][j]);
            }
            // All horizontal lines meet at an infinitely distant point, which means they all go into one card. Here,
            // this is our final vanishing point
            assignImage(vanishingPointsCards[PRIME_VALUE]);

            // Now we move onto the next line, and so the next image
            imageValue++;
        }

        // And finally, all the vanishingPoints also share a symbol
        for (DobbleCard card : vanishingPointsCards) {
            assignImage(card);
        }

        // We now add all the DobbleCards into one combined list
        for (int i = 0; i < PRIME_VALUE; i++) {
            for (int j = 0; j < PRIME_VALUE; j++) {
                allCards.add(squareArrayCards[i][j]);
            }
        }
        for (DobbleCard card : vanishingPointsCards) {
            allCards.add(card);
        }
    }

    public static void drawCards() {
        for (DobbleCard card : allCards) {
            card.drawCard();
        }
    }

    private static void drawPages() {
        // Create the pages, assigning cards to each
        List<CardPage> cardPages = new ArrayList<>();

        // Work out how many pages we'll be making
        int noPages = (int) Math.ceil((double)allCards.size() / (double)CardPage.getCardsPerPage());

        // Loop through the cards, adding them to a page. When we have enough cards, we add a new page
        for (int i = 0; i < allCards.size(); i+= CardPage.getCardsPerPage()) {
            // Create our list of cards going onto the page
            List<DobbleCard> pageCards = new ArrayList<>();

            // Add each card to this list (as long as there's a card to add)
            pageCards.add(allCards.get(i));
            if (i + 1 < allCards.size()) { pageCards.add(allCards.get(i+1)); }
            if (i + 2 < allCards.size()) { pageCards.add(allCards.get(i+2)); }
            if (i + 3 < allCards.size()) { pageCards.add(allCards.get(i+3)); }

            // Create our new page with this card list
            cardPages.add(new CardPage(pageCards));
        }

        // Now we draw each page
        for (CardPage page : cardPages) {
            page.drawPage();
        }

    }

    private static void assignImage(DobbleCard card) {
        // A fairly simple method, but allows the easy changing of all file paths of file types if required
        card.addImage("symbol" + imageValue + ".png");
    }

    public static int getPrimeValue() {
        return PRIME_VALUE;
    }

    public static int getNumberCards() {
        return numberCards;
    }

    public static int getNumberSymbols() {
        return numberSymbols;
    }

    public static int getImageValue() {
        return imageValue;
    }

    public static ArrayList<DobbleCard> getAllCards() {
        return allCards;
    }
}
