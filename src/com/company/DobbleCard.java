package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class DobbleCard {
    private List<String> imageStrings;
    private static int cardsMade = 0;
    private int cardNumber;

    // Card drawing parameters
    private int cardWidth = 1200;
    private int cardHeight = 1200;
    private int borderThickness = 30;
    private Color backgroundColor = Color.WHITE;
    private Color outerRingColor = new Color(102, 0, 153); // Purple color
    int maxSymbolDimension = 210;

    private static ArrayList<SymbolPositions> symbolPositions = new ArrayList<>();

    // Internal class to store symbol positions as an array
    private static class SymbolPositions {
        private int xPosition;
        private int yPosition;

        public SymbolPositions(int xPosition, int yPosition) {
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
        // Symbol placement positions. Will need as many here as there are symbols
        symbolPositions.add(new SymbolPositions(270, 168));
        symbolPositions.add(new SymbolPositions(540, 65));
        symbolPositions.add(new SymbolPositions(831, 242));
        symbolPositions.add(new SymbolPositions(598, 302));
        symbolPositions.add(new SymbolPositions(118, 398));
        symbolPositions.add(new SymbolPositions(370, 411));
        symbolPositions.add(new SymbolPositions(922, 572));
        symbolPositions.add(new SymbolPositions(132, 696));
        symbolPositions.add(new SymbolPositions(656, 542));
        symbolPositions.add(new SymbolPositions(424, 680));
        symbolPositions.add(new SymbolPositions(397, 910));
        symbolPositions.add(new SymbolPositions(700, 856));
    }

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

    public void drawCard() {
        // Shuffle the symbolPositions list, so that symbols do not show in same place on different cards
        Collections.shuffle(symbolPositions);

        // Create our card image
        BufferedImage cardImg = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = cardImg.createGraphics();

        // Set a clear background
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, cardWidth, cardHeight);

        // Create the initial card circle border
        g2d.setComposite(AlphaComposite.Src);
        g2d.setColor(outerRingColor);
        g2d.fillOval(0, 0, cardWidth, cardHeight);

        // And then the inner card circle that the symbols are drawn onto
        g2d.setColor(backgroundColor);
        g2d.fillOval(0 + (borderThickness / 2), 0 + (borderThickness / 2), cardWidth - borderThickness, cardHeight - borderThickness);

        // Loop through all symbols, and add them to the card after rotating and scaling
        for (int i = 0; i < symbolPositions.size(); i++) {

            try {
                // Create a BufferedImage from our symbol image
                BufferedImage img = ImageIO.read(new File("src/com/company/images/symbols/" + imageStrings.get(i)));

                // Get a random rotation angle to be applied
                int rotationAngle = new Random().nextInt(360);

                // Rotate symbol
                BufferedImage rotatedImage = rotateImage(img, rotationAngle);

                // Scale symbol to fit in the allocated symbol area, according to its rotated height and width
                double scale = maxSymbolDimension / (float) Math.max(rotatedImage.getHeight(), rotatedImage.getWidth());
                int symbolWidth = (int) (scale * rotatedImage.getWidth());
                int symbolHeight = (int) (scale * rotatedImage.getHeight());

                // Draw our rotated image onto the card in the correct position
                g2d.drawImage(rotatedImage,
                        symbolPositions.get(i).getxPosition(),
                        symbolPositions.get(i).getyPosition(),
                        symbolWidth,
                        symbolHeight,
                        null);
            } catch (IOException e) {
                System.out.println("Image at src/com/company/images/symbols/" + imageStrings.get(i) + " not found");
            }
        }

        // Close our graphics renderer and create a new png file with the card number as the file name
        g2d.dispose();

        File file = new File("src/com/company/images/cards/card" + cardNumber + ".png");
        try {
            ImageIO.write(cardImg, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage rotateImage(BufferedImage img, double angle) {
        // Rotation parameters (including new width and height that need to be calculated)
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        // Create a new image to return as the rotated image
        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dRotate = rotated.createGraphics();
        AffineTransform at = new AffineTransform();

        // Need to move the image over and down before rotating so that it's in the center after the rotation, so that
        // we don't lose any of the image outside of the borders
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        // Apply the rotation
        int x = w / 2;
        int y = h / 2;
        at.rotate(rads, x, y);
        g2dRotate.setTransform(at);

        // Need to draw a background (with the same background color as the card) around image to cover the transparent
        // background where the image no longer fits the full BufferedImage. No need for preciseness here, so
        // overdrawing to ensure it's all covered
        g2dRotate.setBackground(Color.WHITE);
        g2dRotate.clearRect(-newWidth, -newHeight, 3 * newWidth, 3 * newHeight);

        // Draw new rotated image onto the BufferedImage and return the result
        g2dRotate.drawImage(img, 0, 0, null);
        g2dRotate.dispose();

        return rotated;
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
