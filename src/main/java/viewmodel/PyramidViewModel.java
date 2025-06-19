package viewmodel;

import model.*;
import model.Observable;
import model.Observer;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


public class PyramidViewModel extends Observable {
    private PyramidModel model;
    private final PyramidView view;
    private final List<Card> selectedCards = new ArrayList<>();
    private final List<JButton> selectedButtons = new ArrayList<>();
    private Card currentDeckCard;
    private final Deque<Card> auxiliaryDiscardPile = new ArrayDeque<>();
    private int cantJoker = 2; // Cantidad de comodines disponibles
    private int cantReverse = 3;


    public PyramidViewModel(PyramidModel model, PyramidView view) {
        this.model = model;
        this.view = view;
        addObserver(view);
        setupListeners();
        initialize();
        joker();
    }

    private void handleCardSelection(Card card, JButton button) throws Exception {
        if (model.isGameOver()) {
            if (model.isPyramidEmpty()) {
                view.showMessage("¡Felicidades! Has completado la pirámide.");
                notifyObservers();
            }

            notifyObservers();
        }
        if (card == null) return;

        if (selectedCards.contains(card)) {
            selectedCards.remove(card);
            selectedButtons.remove(button);
            button.setBackground(null);
            return;
        }

        if (selectedCards.size() < 2 && (card.getRow() == -1 || model.isCardFree(card))) {
            selectedCards.add(card);
            selectedButtons.add(button);
            button.setBackground(Color.YELLOW);
        } else {
            playSound("assets/sonido_incorrecto.wav");
            view.showMessage("Carta no válida.");
            return;
        }

        // Caso de eliminación directa de una sola carta de valor 13
        if (selectedCards.size() == 1 && selectedCards.get(0).getValue() == 13) {
            removeSelectedCards();
            return;
        }

        if (selectedCards.size() == 2) {
            Card c1 = selectedCards.get(0);
            Card c2 = selectedCards.get(1);

            if (c1.isComodin() && c2.isComodin()) {
                clearSelection();
            } else if (c1.isComodin() && c2.getValue() == 13) {
                clearSelection();
            } else if (c1.isComodin() || c2.isComodin()) {
                removeSelectedCards();
            } else if (c1.getValue() + c2.getValue() == 13) {
                removeSelectedCards();
            } else {
                playSound("assets/sonido_incorrecto.wav");
                view.showMessage("Las cartas no suman 13");
                clearSelection();

            }
        }
    }

    private void setupView() {
        List<List<Card>> pyramid = model.getPyramid();
        JPanel pyramidPanel = view.getPyramidPanel();

        for (int row = 0; row < pyramid.size(); row++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            rowPanel.setOpaque(false); // Hacer el panel de fila transparente
            for (int col = 0; col <= row; col++) {
                Card card = pyramid.get(row).get(col);
                JButton cardButton = view.createCardButton(card);
                cardButton.addActionListener(e -> {
                    try {
                        handleCardSelection(card, cardButton);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });
                rowPanel.add(cardButton);
            }
            pyramidPanel.add(rowPanel);
        }
    }

    private void setupListeners() {
        view.getAuxDeckButton().addActionListener(e -> {
            Card nextCard = model.getDeck().drawCard();
            clearSelection();
            ;
            if (cantReverse == -1) {
                JOptionPane.showMessageDialog(view.getFrame(), "No se pueden dar más vueltas al mazo auxiliar.");
                view.getAuxDeckButton().setEnabled(false);
                return;
            } else if (nextCard == null && !auxiliaryDiscardPile.isEmpty()) {
                JOptionPane.showMessageDialog(view.getFrame(), "Se llegó al final del mazo auxiliar. El mazo se recargará.");
                cantReverse--;
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
                activeCardButton.setText(view.formatCardLabel(currentDeckCard));
                activeCardButton.setPreferredSize(new Dimension(20, 100));
                activeCardButton.setVisible(true);

            } else {
                currentDeckCard = null;
                activeCardButton.setText("Sin cartas");
                activeCardButton.setVisible(false);
            }
        });

        view.getActiveCardButton().addActionListener(e -> {
            JButton button = view.getActiveCardButton();
            try {
                handleCardSelection(currentDeckCard, button);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        view.getStartButton().addActionListener(e -> {
            startNewGame();
        });

        view.getExitButton().addActionListener(e -> System.exit(0));

        view.getExitGameButton().addActionListener(e -> System.exit(0));

        view.getRestartButton().addActionListener(e -> {
            // Reiniciar el modelo y estado
            model = new PyramidModel();

            // Limpiar el estado interno del controlador si hay algo que resetear
            currentDeckCard = null;
            auxiliaryDiscardPile.clear();
            cantJoker = 2;
            cantReverse = 3;
            selectedCards.clear();
            selectedButtons.clear();

            // Resetear la vista para que esté limpia y vuelva al menú principal
            view.showStartMenu();
        });


    }

    private void joker() {
        JButton jokerButton = view.getJokerButton();
        jokerButton.addActionListener(e -> {
            Card jokerCard = new Card("Joker", 0, -1, true);
            try {
                handleCardSelection(jokerCard, jokerButton);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

    }



    private void removeSelectedCards() throws Exception {
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

                if (!card.isComodin()) {
                    if (!auxiliaryDiscardPile.isEmpty()) {
                        currentDeckCard = auxiliaryDiscardPile.pop();
                        view.getActiveCardButton().setText(view.formatCardLabel(currentDeckCard));
                        view.getActiveCardButton().setVisible(true);
                    } else {
                        currentDeckCard = null;
                        view.getActiveCardButton().setText("");
                        view.getActiveCardButton().setVisible(true);
                        if (model.getDeck().isEmpty()) {
                            view.getAuxDeckButton().setEnabled(false);
                        }
                    }
                } else {
                    cantJoker--;
                    if (cantJoker == 0) {
                        view.getJokerButton().setVisible(false);
                        view.getJokerButton().setEnabled(false);
                    }
                    button.setVisible(true);
                }
            }
        }
//        if (model.isPyramidEmpty()) {
//            notifyObservers();
//        }
        selectedCards.clear();
        selectedButtons.clear();
        playSound("assets/sonido_correcto.wav");
    }

    private void playSound(String file) throws Exception {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(file));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al reproducir el sonido: " + file);
        }
    }

    private void clearSelection() {
        for (JButton button : selectedButtons) {
            button.setBackground(null);
        }
        selectedCards.clear();
        selectedButtons.clear();
    }

    public void startNewGame() {
        model = new PyramidModel();  // nuevo modelo = juego nuevo
        initialize();                // conectar todo
        view.startGamePanel();       // ir al panel de juego
    }

    private void returnToMainMenu() {
        JFrame frame = view.getMainFrame();
        JPanel mainMenu = view.getStartMenuPanel();  // cambio aquí

        frame.getContentPane().removeAll();
        frame.setContentPane(mainMenu);
        frame.revalidate();
        frame.repaint();
    }

    public void initialize() {
        currentDeckCard = null;
        auxiliaryDiscardPile.clear();
        cantJoker = 2;
        cantReverse = 3;
        view.setupGameView();
        view.getAuxDeckButton().setEnabled(true);
        view.getActiveCardButton().setEnabled(true);
        view.getJokerButton().setVisible(true);
        view.getJokerButton().setEnabled(true);
        clearSelection();
        setupView();
    }



}
