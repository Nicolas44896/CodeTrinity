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
        JButton drawAuxiliaryCardButton = new JButton("Draw Auxiliary Card");
        JButton auxiliaryCardButton = new JButton("No card drawn");

        // Agregar el botón y la etiqueta al marco
        /*JFrame frame = new JFrame("Pyramid Card Game");
        frame.add(drawAuxiliaryCardButton, BorderLayout.SOUTH);
        frame.add(auxiliaryCardLabel, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(7, 1)); // 7 rows for the pyramid*/

        JFrame frame = new JFrame("Pyramid Card Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout()); // Usar BorderLayout para organizar los componentes principales

        // Panel principal para la pirámide
        JPanel pyramidPanel = new JPanel();
        pyramidPanel.setLayout(new BoxLayout(pyramidPanel, BoxLayout.Y_AXIS)); // Apilar filas verticalmente

        // Botón y etiqueta para el mazo auxiliar
        JPanel auxiliaryPanel = new JPanel();
        auxiliaryPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        auxiliaryPanel.add(drawAuxiliaryCardButton);
        auxiliaryPanel.add(auxiliaryCardLabel);

        drawAuxiliaryCardButton.addActionListener(e -> {  //Agregar boton para extraer cartas del mazo auxiliar
            auxiliaryCard = deck.drawCard();
            if (auxiliaryCard != null) {
                auxiliaryCardLabel.setText(auxiliaryCard.toString());
                if (selectedCards.size() == 1) {
                    int sum = selectedCards.get(0).getValue() + auxiliaryCard.getValue();
                    if (sum == 13) {
                        removeSelectedCards();
                        auxiliaryCard = null; // Descartar la carta del mazo auxiliar
                        auxiliaryCardButton.setText("No card drawn");
                        auxiliaryCardButton.setEnabled(false); // Deshabilitar el botón
                    } else {
                        JOptionPane.showMessageDialog(null, "Selected cards do not sum to 13!");
                        clearSelection();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Select a card from the pyramid first!");
                }
            } else {
                auxiliaryCardLabel.setText("No more cards in the deck");
            }
        });

       /* for (int row = 0; row < pyramid.size(); row++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            for (int col = 0; col <= row; col++) {
                Card card = pyramid.get(row).get(col);
                JButton cardButton = new JButton(card.toString());
                cardButton.addActionListener(new CardButtonListener(card, cardButton, row, col)); //
                rowPanel.add(cardButton);
            }
            frame.add(rowPanel);
        }

        frame.setVisible(true);*/
        // Crear las filas de la pirámide
        //JPanel pyramidPanel = new JPanel();
        pyramidPanel.setLayout(new BoxLayout(pyramidPanel, BoxLayout.Y_AXIS));
        for (int row = 0; row < pyramid.size(); row++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            for (int col = 0; col <= row; col++) {
                Card card = pyramid.get(row).get(col);
                JButton cardButton = new JButton(card.toString());
                cardButton.addActionListener(new CardButtonListener(card, cardButton, row, col));
                rowPanel.add(cardButton);
            }
            pyramidPanel.add(rowPanel);
        }

        // Agregar los paneles al marco
        frame.add(auxiliaryPanel, BorderLayout.NORTH);
        frame.add(pyramidPanel, BorderLayout.CENTER);
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
        private void handleAuxiliaryCardSelection() {
            if (auxiliaryCard != null && selectedCards.size() == 1) {
                int sum = selectedCards.get(0).getValue() + auxiliaryCard.getValue();
                if (sum == 13) {
                    removeSelectedCards();
                    auxiliaryCard = null; // Descartar la carta del mazo auxiliar
                    auxiliaryCardLabel.setText("No card drawn");
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