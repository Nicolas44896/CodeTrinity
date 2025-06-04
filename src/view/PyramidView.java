package view;

import javax.swing.*;
import java.awt.*;

import model.Card;

public class PyramidView {
    private final JFrame frame = new JFrame("Pyramid Card Game");
    private final JPanel pyramidPanel = new JPanel();
    private final JPanel rightPanel = new JPanel();
    private final JButton activeCardButton = new JButton("Carta activa");
    private static PyramidView instance;
    private final JButton auxDeckButton = crearMazoAuxiliarButton();
    private final Dimension cardDimension = new Dimension(60, 100);
    private final JButton jokerButton = crearJokerButton();

    private PyramidView() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        ImageIcon bgIcon = new ImageIcon(("assets/fondo5.jpg"));
        JPanel backgroundPanel = new BackgroundPanel(bgIcon.getImage());
        frame.setContentPane(backgroundPanel);

        pyramidPanel.setLayout(new BoxLayout(pyramidPanel, BoxLayout.Y_AXIS));
        frame.add(pyramidPanel, BorderLayout.CENTER);

        // Replace the rightPanel layout setup in PyramidView's constructor:
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.gridy = 0;
        rightPanel.add(auxDeckButton, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 20, 10, 20);
        rightPanel.add(activeCardButton, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        rightPanel.add(jokerButton, gbc);

        frame.add(rightPanel, BorderLayout.EAST);

        pyramidPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pyramidPanel.setOpaque(false);
        rightPanel.setOpaque(false);


        activeCardButton.setVisible(false);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPyramidPanel() {
        return pyramidPanel;
    }

    public JButton getAuxDeckButton() {
        return auxDeckButton;
    }

    public JButton getActiveCardButton() {
        return activeCardButton;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public JButton createCardButton(Card card) {
        JButton button = new JButton(formatCardLabel(card));
        button.setPreferredSize(cardDimension);
        return button;
    }

    public String formatCardLabel(Card card) {
        return "<html><center>" + card.getValue() + "<br>de<br>" + card.getSuit() + "</center></html>";
    }

    private JButton crearMazoAuxiliarButton() {
        String label = "<html><center>Mazo<br>Auxiliar</center></html>";
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(120, 40));
        button.setOpaque(false);
        return button;
    }
    private JButton crearJokerButton() {
        String label = "<html><center>Joker</center></html>";
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(120, 40));
        button.setOpaque(false);
        return button;
    }

    public JButton jokerButton() {
        return jokerButton;
    }


    public static PyramidView getInstance() {
        if (instance == null) {
            instance = new PyramidView();
        }
        return instance;
    }

}