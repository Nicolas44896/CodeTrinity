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
    private Card auxiliaryCard = null;
    private JLabel auxiliaryCardLabel = new JLabel("No card drawn");
    private Card currentDeckCard;
    private JButton deckButton;
    private JButton activeDeckCardButton;
    public void setupPyramid() {
        for (int row = 0; row < 7; row++) {
            List<Card> currentRow = new ArrayList<>();
            for (int col = 0; col <= row; col++) {
                Card auxCard = deck.drawCard();
                auxCard.setRow(row);
                currentRow.add(auxCard);
            }
            pyramid.add(currentRow);
        }
    }

    public void displayPyramidGUI() {
        JFrame frame = new JFrame("Pyramid Card Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Panel para la pirámide
        JPanel pyramidPanel = new JPanel();
        pyramidPanel.setLayout(new BoxLayout(pyramidPanel, BoxLayout.Y_AXIS));
        for (int row = 0; row < pyramid.size(); row++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            for (int col = 0; col <= row; col++) {
                Card card = pyramid.get(row).get(col);
                JButton cardButton = new JButton(card.toString());
                cardButton.addActionListener(new CardButtonListener(card, cardButton, row, col));
                rowPanel.add(cardButton);
            }
            pyramidPanel.add(rowPanel);
        }

        // Panel inferior con mazo
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        deckButton = new JButton("Sacar carta del mazo");
        activeDeckCardButton = new JButton("Carta activa");
        activeDeckCardButton.setVisible(false);

        deckButton.addActionListener(e -> {
            currentDeckCard = deck.drawCard();
            if (currentDeckCard != null) {
                activeDeckCardButton.setText(currentDeckCard.toString());
                activeDeckCardButton.setVisible(true);
            } else {
                deckButton.setEnabled(false);
                activeDeckCardButton.setText("Sin cartas");
            }
        });

        activeDeckCardButton.addActionListener(e -> {
            if (selectedCards.contains(currentDeckCard)) {
                selectedCards.remove(currentDeckCard);
                selectedButtons.remove(activeDeckCardButton);
                activeDeckCardButton.setBackground(null);
            } else {
                selectedCards.add(currentDeckCard);
                selectedButtons.add(activeDeckCardButton);
                activeDeckCardButton.setBackground(Color.YELLOW);
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
        });

        bottomPanel.add(deckButton);
        bottomPanel.add(activeDeckCardButton);

        // Agregar todo al frame
        frame.add(pyramidPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
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
            } else if(selectedCards.size() < 2 && cartaValida(card)) {
                selectedCards.add(card);
                selectedButtons.add(button);
                button.setBackground(Color.YELLOW); // Highlight selection
            } else {
                JOptionPane.showMessageDialog(null, "You can only select last row!");
            }
            if (selectedCards.get(0).getValue() == 13 ) removeSelectedCards();
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

    private boolean cartaValida(Card auxCard) {
        return auxCard.getRow() == pyramid.size()-1;
    }

    private void removeSelectedCards() {
        for (JButton button : selectedButtons) {
            button.setVisible(false); // Ocultar botón
        }
        for (Card card : selectedCards) {
            for (int row = 0; row < pyramid.size(); row++) {
                List<Card> currentRow = pyramid.get(row);
                if (currentRow.contains(card)) {
                    currentRow.set(currentRow.indexOf(card), null); // Eliminar carta de la pirámide
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