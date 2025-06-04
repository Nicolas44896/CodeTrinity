package view;

import javax.swing.*;
import java.awt.*;

import model.Card;

public class PyramidView {
    private final JFrame frame = new JFrame("Pyramid Card Game");
    private final JPanel pyramidPanel = new JPanel();
    private final JPanel rightPanel = new JPanel();
    private final JButton activeCardButton = new JButton("Carta activa");
    private final JButton auxDeckButton = crearMazoAuxiliarButton();
    private final JButton jokerButton = crearJokerButton();
    private final JButton restartButton = new JButton("Restart");;
    private final Dimension cardDimension = new Dimension(60, 100);
    private final JPanel startMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Cambio aquí
    private final JButton startButton = new JButton("Start Game");
    private final JButton exitButton = new JButton("Exit");
    private final JButton exitGameButton = new JButton("Exit game");

    private static PyramidView instance;

    private PyramidView() {
        setupFrame();
        setupBackground();
        setupStartMenu();
        setupPyramidPanel();
        setupRightPanel();
        setupInitialVisibility();
    }

    private void setupFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);            // Mantén tamaño fijo
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);  // Centrar ventana
    }


    private void setupBackground() {
        ImageIcon bgIcon = new ImageIcon("assets/fondo5.jpg");
        BackgroundPanel backgroundPanel = new BackgroundPanel(bgIcon.getImage());
        backgroundPanel.setLayout(new GridBagLayout());  // Para centrar el startMenuPanel

        // Configuramos startMenuPanel con un BoxLayout vertical
        startMenuPanel.setLayout(new BoxLayout(startMenuPanel, BoxLayout.Y_AXIS));
        startMenuPanel.setOpaque(true);
        startMenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones centrados
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setEnabled(true);
        exitButton.setEnabled(true);

        backgroundPanel.add(startMenuPanel);

        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }

    private void setupStartMenu() {
        startMenuPanel.setPreferredSize(new Dimension(300, 200));
        startMenuPanel.setOpaque(false);

        // Ajustar tamaño preferido para botones para que se vean bien
        Dimension btnSize = new Dimension(120, 40);
        startButton.setPreferredSize(btnSize);
        exitButton.setPreferredSize(btnSize);

        startMenuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio vertical entre botones
        startMenuPanel.add(startButton);
        startMenuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio vertical entre botones
        startMenuPanel.add(exitButton);
    }

    private void setupPyramidPanel() {
        pyramidPanel.setLayout(new BoxLayout(pyramidPanel, BoxLayout.Y_AXIS));
        pyramidPanel.setOpaque(false);
        pyramidPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 40));

    }

    private void setupRightPanel() {
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

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.gridy = 3;
        rightPanel.add(restartButton, gbc);

        gbc.gridy = 4;
        rightPanel.add(exitGameButton, gbc);

        Dimension controlButtonSize = new Dimension(120, 40);
        restartButton.setPreferredSize(controlButtonSize);
        exitGameButton.setPreferredSize(controlButtonSize);


        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rightPanel.setOpaque(false);
    }

    private void setupInitialVisibility() {
        activeCardButton.setVisible(false);
        pyramidPanel.setVisible(false);
        rightPanel.setVisible(false);
        startMenuPanel.setVisible(true);

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

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getExitGameButton() {
        return exitGameButton;
    }

    public JButton getJokerButton() {
        return jokerButton;
    }

    public JButton getRestartButton() {
        return restartButton;
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

    public static PyramidView getInstance() {
        if (instance == null) {
            instance = new PyramidView();
        }
        return instance;
    }

    public void startGamePanel() {
        ImageIcon bgIcon = new ImageIcon("assets/fondo5.jpg");
        BackgroundPanel backgroundPanel = new BackgroundPanel(bgIcon.getImage());
        backgroundPanel.setLayout(new BorderLayout());  // Usamos BorderLayout para ocupar todo el espacio

        JPanel pyramidWrapper = new JPanel(new BorderLayout());
        pyramidWrapper.setOpaque(false);
        pyramidWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 40));
        pyramidWrapper.add(pyramidPanel, BorderLayout.CENTER);

        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.setOpaque(false);

        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rightPanel.setPreferredSize(new Dimension(220, 0));

        gamePanel.add(pyramidWrapper, BorderLayout.CENTER);
        gamePanel.add(rightPanel, BorderLayout.EAST);

        // Mostrar solo los paneles del juego
        startMenuPanel.setVisible(false);
        pyramidPanel.setVisible(true);
        rightPanel.setVisible(true);
        activeCardButton.setVisible(true);
        auxDeckButton.setVisible(true);
        jokerButton.setVisible(true);
        restartButton.setVisible(true);
        exitGameButton.setVisible(true);


        backgroundPanel.add(gamePanel, BorderLayout.CENTER);  // Colocar gamePanel en el centro del fondo

        frame.setContentPane(backgroundPanel);
        frame.revalidate();
        frame.repaint();
    }


}
