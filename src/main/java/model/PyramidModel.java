package model;

import java.util.ArrayList;
import java.util.List;

public class PyramidModel{
    private final List<List<Card>> pyramid;
    private final Deck deck;

    public PyramidModel() {
        pyramid = new ArrayList<>();
        deck = new Deck();
        setupPyramid();
    }

    public void setupPyramid() {
        pyramid.clear();
        for (int row = 0; row < 7; row++) {
            List<Card> currentRow = new ArrayList<>();
            for (int col = 0; col <= row; col++) {
                Card card = deck.drawCard();
                card.setRow(row);
                currentRow.add(card);
            }
            pyramid.add(currentRow);
        }
    }

    public List<List<Card>> getPyramid() {
        return pyramid;
    }

    public Deck getDeck() {
        return deck;
    }

    public void removeCardFromPyramid(Card card) {
        for (List<Card> row : pyramid) {
            int index = row.indexOf(card);
            if (index != -1) {
                row.set(index, null);
                break;
            }
        }
    }

    public boolean isCardFree(Card card) {
        int row = card.getRow();
        int col = -1;

        for (int i = 0; i < pyramid.get(row).size(); i++) {
            if (pyramid.get(row).get(i) == card) {
                col = i;
                break;
            }
        }

        if (col == -1) return false;
        if (row == pyramid.size() - 1) return true;

        Card abajoIzq = pyramid.get(row + 1).get(col);
        Card abajoDer = pyramid.get(row + 1).get(col + 1);

        return abajoIzq == null && abajoDer == null;
    }

    public boolean isGameOver() {
        return noMoreMoves() || isPyramidEmpty();
    }

    public boolean canSelectAnyCard() {
        for (List<Card> row : pyramid) {
            for (Card card : row) {
                if (card != null && isCardFree(card)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean noMoreMoves() {
        return !canSelectAnyCard() && deck.isEmpty();
    }

    public boolean isPyramidEmpty() {
        for (List<Card> row : pyramid) {
            for (Card card : row) {
                if (card != null && isCardFree(card)) {
                    return false;
                }
            }
        }
        return true;
    }
}
