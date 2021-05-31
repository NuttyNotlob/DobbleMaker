package com.company.tests;

import com.company.CardGenerator;
import com.company.DobbleCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardGeneratorTest {
    private static CardGenerator cardGenerator = new CardGenerator();

    static {
        cardGenerator.generateCards();
    }

    @Test
    void correctNoCards() {
        // Test to ensure generator is creating the correct amount of cards
        assertTrue(DobbleCard.getCardsMade() == cardGenerator.getNumberCards());
    }

    @Test
    void correctNoSymbols() {
        // Test to ensure generator is assigning the correct number of images
        assertTrue(cardGenerator.getImageValue() == cardGenerator.getNumberSymbols());
    }

    @Test
    void checkCardSymbols() {
        // Test that checks each DobbleCard, ensuring they have correct number of symbols, and one symbol in common
        // with all other cards
        for (DobbleCard card : cardGenerator.getAllCards()) {
            // Check it has correct number of symbols
            assertTrue(card.getImageStrings().size() == cardGenerator.getPrimeValue() + 1);

            // Check against every other card that it has one and only one symbol in common
            for (DobbleCard otherCard : cardGenerator.getAllCards()) {
                if (card != otherCard) {
                    // This tests by creating a copy of the list of image Strings, and then taking the intersect of it
                    // with every other card's list of image Strings. If there's not just one in the intersect, it fails
                    List<String> imageIntersections = new ArrayList<>(card.getImageStrings());
                    imageIntersections.retainAll(otherCard.getImageStrings());
                    assertTrue(imageIntersections.size() == 1);
                }
            }
        }
    }

}