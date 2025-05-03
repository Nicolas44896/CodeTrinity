// Clase para representar una carta
public class Card {
    private final String suit;
    private final int value;
    private int row; //nuevo

    public Card(String suit, int value, int row) {
        this.suit = suit;
        this.value = value;
        this.row = row;
    }
    // Getters y Setters nuevos
    public int getRow(){
        return row;
    }
    public void setRow(int row){
        this.row = row;
    }
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}