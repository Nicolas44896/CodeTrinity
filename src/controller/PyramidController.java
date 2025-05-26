package controller;

import model.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;

public class PyramidController {
    private final PyramidModel model;
    private final PyramidView view;
    private final List<Card> selectedCards = new ArrayList<>();
    private final List<JButton> selectedButtons = new ArrayList<>();
    private Card currentDeckCard;
    private final Deque<Card> auxiliaryDiscardPile = new ArrayDeque<>();

    public PyramidController(PyramidModel model, PyramidView view) {
        this.model = model;
        this.view = view;
        this.model.setupPyramid();
        setupView();
        setupListeners();
    }

    private void setupView() {
        List<List<Card>> pyramid = model.getPyramid();
        JPanel pyramidPanel = view.getPyramidPanel();

        for (int row = 0; row < pyramid.size(); row++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            for (int col = 0; col <= row; col++) {
                Card card = pyramid.get(row).get(col);
                JButton cardButton = view.createCardButton(card);
                cardButton.addActionListener(e -> handleCardSelection(card, cardButton));
                rowPanel.add(cardButton);
            }
            pyramidPanel.add(rowPanel);
        }
    }

    private void setupListeners() {
        view.getAuxDeckButton().addActionListener(e -> {
            Card nextCard = model.getDeck().drawCard();

            if (nextCard == null && !auxiliaryDiscardPile.isEmpty()) {
                // Aviso que se terminó el mazo auxiliar
                JOptionPane.showMessageDialog(view.getFrame(),
                        "Se llegó al final del mazo auxiliar. El mazo se recargará.");

                // Restauramos las cartas al mazo
                List<Card> recycled = new ArrayList<>(auxiliaryDiscardPile);
                Collections.reverse(recycled);
                model.getDeck().reloadDeck(recycled);
                auxiliaryDiscardPile.clear();
                nextCard = model.getDeck().drawCard();
            }

            JButton activeCardButton = view.getActiveCardButton();

            if (nextCard != null) {
                if (currentDeckCard != null) {
                    auxiliaryDiscardPile.push(currentDeckCard);
                }
                currentDeckCard = nextCard;
                currentDeckCard.setRow(-1);
                activeCardButton.setText(currentDeckCard.toString());
                activeCardButton.setVisible(true);
            } else {
                currentDeckCard = null;
                activeCardButton.setText("Sin cartas");
                activeCardButton.setVisible(false);
            }
        });

        view.getActiveCardButton().addActionListener(e -> {
            JButton button = view.getActiveCardButton();
            handleCardSelection(currentDeckCard, button);
        });
    }




    private void handleCardSelection(Card card, JButton button) {
        if (card == null) return;

        if (selectedCards.contains(card)) {
            selectedCards.remove(card);
            selectedButtons.remove(button);
            button.setBackground(null);
        } else if (selectedCards.size() < 2 && (card.getRow() == -1 || model.isCardFree(card))) {
            selectedCards.add(card);
            selectedButtons.add(button);
            button.setBackground(Color.YELLOW);
        } else {
            view.showMessage("Carta no válida.");
            return;
        }

        if (card.getValue() == 13) {
            removeSelectedCards();
        } else if (selectedCards.size() == 2) {
            int sum = selectedCards.get(0).getValue() + selectedCards.get(1).getValue();
            if (sum == 13) {
                removeSelectedCards();
            } else {
                view.showMessage("Las cartas no suman 13");
                clearSelection();
            }
        }
    }

    private void removeSelectedCards() {
        for (int i = 0; i < selectedCards.size(); i++) {
            Card card = selectedCards.get(i);
            JButton button = selectedButtons.get(i);

            if (card.getRow() != -1) {
                button.setEnabled(false);
                button.setText("");
                button.setBackground(null);
                model.removeCardFromPyramid(card);
            } else {
                // Si es la carta activa del mazo auxiliar
                button.setBackground(null);
                button.setVisible(false);

                if (!auxiliaryDiscardPile.isEmpty()) {
                    currentDeckCard = auxiliaryDiscardPile.pop();
                    view.getActiveCardButton().setText(currentDeckCard.toString());
                    view.getActiveCardButton().setVisible(true);
                } else {
                    currentDeckCard = null;
                    view.getActiveCardButton().setText("");
                    view.getActiveCardButton().setVisible(false);
                    view.getAuxDeckButton().setEnabled(false);
                }
            }
        }

        selectedCards.clear();
        selectedButtons.clear();
    }


    private void clearSelection() {
        for (JButton button : selectedButtons) {
            button.setBackground(null);
        }
        selectedCards.clear();
        selectedButtons.clear();
    }
}
