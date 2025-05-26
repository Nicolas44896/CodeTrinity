package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Card;

public class PyramidView {
    private final JFrame frame = new JFrame("Pyramid Card Game");
    private final JPanel pyramidPanel = new JPanel();
    private final JPanel rightPanel = new JPanel();
    private final JButton auxDeckButton = new JButton("Mazo Auxiliar");
    private final JButton activeCardButton = new JButton("Carta activa");

    public PyramidView() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        pyramidPanel.setLayout(new BoxLayout(pyramidPanel, BoxLayout.Y_AXIS));
        frame.add(pyramidPanel, BorderLayout.CENTER);

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(auxDeckButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(activeCardButton);
        rightPanel.add(Box.createVerticalGlue());
        frame.add(rightPanel, BorderLayout.EAST);

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
        JButton button = new JButton(card.toString());
        button.setPreferredSize(new Dimension(60, 100));
        return button;
    }

    public void resetCardButton(JButton button) {
        button.setText("");
        button.setEnabled(false);
        button.setBackground(null);
    }
}
