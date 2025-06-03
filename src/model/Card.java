package model;

public class Card {
    private final String suit;
    private final int value;
    private int row;
    public final boolean isComodin;
    public Card(String suit, int value, int row, boolean isComodin) {
        this.suit = suit;
        this.value = value;
        this.row = row;
        this.isComodin=isComodin;
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }
    public boolean isComodin() {
        return isComodin;
    }
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        if(isComodin){
            return isComodin ? "JOKER" : value + " of " + suit;
        }else {
            return value + " of " + suit;
        }
    }
}
