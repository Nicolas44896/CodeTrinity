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
    private JButton auxDeck;
    private JButton activeAuxDeckCardButton;

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
                Dimension buttonSize = new Dimension(60, 100); // Ancho x Alto
                JButton cardButton = new JButton(card.toString());
                cardButton.setPreferredSize(buttonSize);
                cardButton.setMinimumSize(buttonSize);
                cardButton.setMaximumSize(buttonSize);


                cardButton.addActionListener(new CardButtonListener(card, cardButton, row, col));
                rowPanel.add(cardButton);
            }
            pyramidPanel.add(rowPanel);
        }

        // Panel inferior con mazo
        JPanel rightPanel = new JPanel();
        rightPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        auxDeck = new JButton("Mazo Auxiliar");
        activeAuxDeckCardButton = new JButton("Carta activa");
        activeAuxDeckCardButton.setVisible(false);

        auxDeck.setAlignmentX(Component.CENTER_ALIGNMENT);
        activeAuxDeckCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(auxDeck);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(activeAuxDeckCardButton);
        rightPanel.add(Box.createVerticalGlue());

        auxDeck.addActionListener(e -> {
            currentDeckCard = deck.drawCard();
            if (currentDeckCard != null) {
                currentDeckCard.setRow(-1);
                activeAuxDeckCardButton.setText(currentDeckCard.toString());
                activeAuxDeckCardButton.setVisible(true);
            } else {
                auxDeck.setEnabled(false);
                activeAuxDeckCardButton.setText("Sin cartas");
            }
        });

        activeAuxDeckCardButton.addActionListener(e -> {
            if (selectedCards.contains(currentDeckCard)) {
                selectedCards.remove(currentDeckCard);
                selectedButtons.remove(activeAuxDeckCardButton);
                activeAuxDeckCardButton.setBackground(null);
            } else {
                selectedCards.add(currentDeckCard);
                selectedButtons.add(activeAuxDeckCardButton);
                activeAuxDeckCardButton.setBackground(Color.YELLOW);
            }
            if (currentDeckCard.getValue() == 13)  {
                removeSelectedCards();
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

        // Agregar todo al frame
        frame.add(pyramidPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
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
                JOptionPane.showMessageDialog(null, "Esta carta no es valida!");
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
        int row = auxCard.getRow();
        int col = -1;

        // Buscamos la columna de la carta
        for (int i = 0; i < pyramid.get(row).size(); i++) {
            if (pyramid.get(row).get(i) == auxCard) {
                col = i;
                break;
            }
        }

        // Si no se encontró (algo raro), no es válida
        if (col == -1) return false;

        // Si está en la última fila, siempre es válida
        if (row == pyramid.size() - 1) return true;

        // Si está en otra fila, debe chequear si está liberada
        Card abajoIzq = pyramid.get(row + 1).get(col);
        Card abajoDer = pyramid.get(row + 1).get(col + 1);

        return abajoIzq == null && abajoDer == null;
    }

    private void removeSelectedCards() {

        for (int i = 0; i < selectedCards.size(); i++) {
            Card card = selectedCards.get(i);
            JButton button = selectedButtons.get(i);

            // Si la carta está en la pirámide, la removemos visual y lógicamente
            if (card.getRow() != -1) {
                button.setEnabled(false);
                button.setText("");
                button.setBackground(null);

                for (int row = 0; row < pyramid.size(); row++) {
                    List<Card> currentRow = pyramid.get(row);
                    if (currentRow.contains(card)) {
                        currentRow.set(currentRow.indexOf(card), null);
                        break;
                    }
                }
            } else {
                // Si es del mazo auxiliar, simplemente deseleccionamos
                button.setBackground(null);
                button.setVisible(false);
            }
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