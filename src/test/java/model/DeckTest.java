package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import java.util.List;

public class DeckTest {

    @Test
    //Verifica que el mazo inicial contenga 52 cartas
    public void testDeckInitialization() {
        Deck deck = new Deck();
        assertEquals(52, deck.getDeckSize(), "El mazo debe contener 52 cartas al inicializarse.");
    }

    @Test
    //Verifica que la carta no sea null y que el tamaño del mazo se reduzca a 51.
    public void testDrawCard() {
        Deck deck = new Deck();
        Card card = deck.drawCard();
        assertNotNull(card, "Debe poder sacar una carta del mazo.");
        assertEquals(51, deck.getDeckSize(), "El mazo debe tener 51 cartas después de sacar una.");
    }

    @Test
    // Test para verificar que el orden de las cartas haya cambiado correctamente.
    public void testShuffle() {
        Deck deck = new Deck();
        List<Card> originalOrder = deck.getCards();
        deck.shuffle();
        assertNotEquals(originalOrder, deck.getCards(), "El orden de las cartas debe cambiar después de mezclar.");
    }

    @Test
    // Test para verificar que el mazo se recarga correctamente.
    public void testReloadDeck() {
        Deck deck = new Deck();
        List<Card> newCards = deck.getCards();
        deck.reloadDeck(newCards);
        assertEquals(104, deck.getDeckSize(), "El mazo debe contener 104 cartas después de recargarlo con 52 cartas adicionales.");
    }

    @Test
    // Test para verificar que todas las cartas en el mazo son únicas.
    public void testUniqueCardsInDeck() {
        Deck deck = new Deck();
        List<Card> cards = deck.getCards();
        long uniqueCards = cards.stream().distinct().count();
        assertEquals(cards.size(), uniqueCards, "Todas las cartas en el mazo deben ser únicas.");
    }
}

