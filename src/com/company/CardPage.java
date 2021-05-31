package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardPage {
    // Page fields
    private List<DobbleCard> cards;
    private static int pagesMade = 0;
    private int pageNumber;
    private static ArrayList<CardPage.CardPositions> cardPositions = new ArrayList<>();
    private static int cardsPerPage = 4;

    // Page print fields
    private static int pageWidth = 3508;
    private static int pageHeight = 2480;

    // Internal class to store card positions as an array
    private static class CardPositions {
        private int xPosition;
        private int yPosition;

        public CardPositions(int xPosition, int yPosition) {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
        }

        public int getxPosition() {
            return xPosition;
        }

        public int getyPosition() {
            return yPosition;
        }
    }

    static {
        // Need to add the same number of positions as the cards per page variable. These are the same for each page
        cardPositions.add(new CardPositions(75, 100));
        cardPositions.add(new CardPositions(770, 1180));
        cardPositions.add(new CardPositions(1465, 100));
        cardPositions.add(new CardPositions(2160, 1180));
    }

    public CardPage(List<DobbleCard> cards) {
        this.cards = cards;
        pagesMade++;
        pageNumber = pagesMade;
    }

    public void drawPage() {
        // Create our page image
        BufferedImage pageImg = new BufferedImage(pageWidth, pageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = pageImg.createGraphics();

        // Set a white background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, pageWidth, pageHeight);

        // Loop through cards and add their image to the page
        for (int i = 0; i < cards.size(); i++) {

            try {
                // Find the card image
                BufferedImage cardImg = ImageIO.read(new File("src/com/company/images/cards/card" + cards.get(i).getCardNumber() + ".png"));

                // Draw it in position
                g2d.drawImage(cardImg,
                        cardPositions.get(i).getxPosition(),
                        cardPositions.get(i).getyPosition(),
                        DobbleCard.getCardWidth(),
                        DobbleCard.getCardHeight(),
                        null);

            } catch (IOException e) {
                System.out.println("Card " + cards.get(i).getCardNumber() + " not found");
            }
        }

        // Close our graphics renderer and create a new png file with the card number as the file name
        g2d.dispose();

        File file = new File("src/com/company/images/pages/page" + pageNumber + ".png");
        try {
            ImageIO.write(pageImg, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Printout to the console for confirmation. This doesn't confirm the page has been created correctly, only
        // that all the code has run properly
        System.out.println("Page " + pageNumber + " created");
    }

    public static int getCardsPerPage() {
        return cardsPerPage;
    }
}
