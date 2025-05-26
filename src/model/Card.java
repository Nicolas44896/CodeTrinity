package model;

public class Card {
    private final String suit;
    private final int value;
    private int row;

    public Card(String suit, int value, int row) {
        this.suit = suit;
        this.value = value;
        this.row = row;
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
