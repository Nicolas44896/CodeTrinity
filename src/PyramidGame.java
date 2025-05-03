import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PyramidGame {
    private final List<List<Card>> pyramid = new ArrayList<>();
    private final Deck deck = new Deck();
    private final List<Card> selectedCards = new ArrayList<>();
    private final List<JButton> selectedButtons = new ArrayList<>();


    public void setupPyramid() {
        for (int row = 0; row < 7; row++) {
            List<Card> currentRow = new ArrayList<>();
            for (int col = 0; col <= row; col++) {
                currentRow.add(deck.drawCard());
            }
            pyramid.add(currentRow);
        }
    }

    public void displayPyramidGUI() {
        JFrame frame = new JFrame("Pyramid Card Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(7, 1)); // 7 rows for the pyramid

        for (int row = 0; row < pyramid.size(); row++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            for (int col = 0; col <= row; col++) {
                Card card = pyramid.get(row).get(col);
                JButton cardButton = new JButton(card.toString());
                cardButton.addActionListener(new CardButtonListener(card, cardButton, row, col)); //
                //cardButton.setEnabled(true); // Disable buttons for now
                rowPanel.add(cardButton);
            }
            frame.add(rowPanel);
        }

        frame.setVisible(true);
    }
    private class CardButtonListener implements ActionListener {
        private final Card card;
        private final JButton button;
        private final int row;
        private final int col;

        public CardButtonListener(Card card, JButton button, int row, int col) {
            this.card = card;
            this.button = button;
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedCards.contains(card)) {
                selectedCards.remove(card);
                selectedButtons.remove(button);
                button.setBackground(null); // Deselect
            } else {
                selectedCards.add(card);
                selectedButtons.add(button);
                button.setBackground(Color.YELLOW); // Highlight selection
            }
            if (selectedCards.size() == 2) {
                int sum = selectedCards.get(0).getValue() + selectedCards.get(1).getValue();
                if (sum == 13) {
                    removeSelectedCards();
                } else {
                    JOptionPane.showMessageDialog(null, "Selected cards do not sum to 13!");
                    clearSelection();
                }
            }
        }
    }
    private void clearSelection() {
        for (JButton button : selectedButtons) {
            button.setBackground(null); // Reset button color
        }
        selectedCards.clear();
        selectedButtons.clear();
    }
    private void removeSelectedCards() {
        for (JButton button : selectedButtons) {
            button.setVisible(false); // Hide button
        }
        for (Card card : selectedCards) {
            for (int row = 0; row < pyramid.size(); row++) {
                List<Card> currentRow = pyramid.get(row);
                if (currentRow.contains(card)) {
                    currentRow.set(currentRow.indexOf(card), null); // Remove card from pyramid
                    break;
                }
            }
        }
        selectedCards.clear();
        selectedButtons.clear();
    }

    public static void main(String[] args) {
        PyramidGame game = new PyramidGame();
        game.setupPyramid();
        game.displayPyramidGUI();
    }
}