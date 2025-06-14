package view;

import javax.swing.*;
import java.awt.*;

import model.Card;
import model.PyramidModel;

public class PyramidView {
    private final JFrame frame = new JFrame("Pyramid Card Game");
    private final JPanel pyramidPanel = new JPanel();
    private final JPanel rightPanel = new JPanel();
    private final JButton activeCardButton = new JButton("Carta activa");
    private final JButton auxDeckButton = crearMazoAuxiliarButton();
    private final JButton jokerButton = crearJokerButton();
    private final JButton restartButton = new JButton("Restart");
    private final JPanel startMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Cambio aquí
    private final JButton startButton = new JButton("Iniciar Juego");
    private final JButton exitButton = new JButton("Salir");
    private final JButton exitGameButton = new JButton("Salir del Juego");
    private final Image backgroundImage = new ImageIcon("assets/fondo2.jpg").getImage();
    private final BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
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
        frame.setSize(1280, 1024);            // Mantén tamaño fijo
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);  // Centrar ventana
    }

    private void setupBackground() {
        ImageIcon bgIcon = new ImageIcon("assets/fondo4.jpg");
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

        Dimension controlButtonSize = new Dimension(110, 40);
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
        JButton button = new JButton(formatCardLabel(card)); // o lo que uses como texto
        Dimension fixedSize = new Dimension(70, 90);
        button.setPreferredSize(fixedSize);
        button.setMinimumSize(fixedSize);
        button.setMaximumSize(fixedSize);
        return button;
    }

    public String formatCardLabel(Card card) {
        StringBuilder symbols = new StringBuilder();
        String valueStr = "";
        if (card.getValue() < 11 && card.getValue() > 1) {
            valueStr = String.valueOf(card.getValue());
            for (int i = 0; i < card.getValue(); i++) {
                symbols.append(card.getSuit()).append(" ");
            }
        } else if (card.getValue() == 11) {
            valueStr = "";
            symbols.append("J");
            symbols.append(card.getSuit());
        } else if (card.getValue() == 12) {
            valueStr = "";
            symbols.append("Q");
            symbols.append(card.getSuit());
        } else if (card.getValue() == 13) {
            valueStr = "";
            symbols.append("K");
            symbols.append(card.getSuit());
        } else if (card.getValue() == 1 || card.getValue() == 14) {
            valueStr = "";
            symbols.append("A");
            symbols.append(card.getSuit());
        }
        // Número en las esquinas superiores
        return "<html><div style='text-align:center;'>"
                + "<div style='font-size:10px; display:flex; justify-content:space-between;'>"
                + "<span>" + valueStr  + "</span></div>"
                + symbols.toString().trim() + "</div>"
                + "</div></html>";
    }

    private JButton crearMazoAuxiliarButton() {
        String label = "<html><center>Mazo<br>Auxiliar</center></html>";
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(120, 40));
        button.setOpaque(false);
        return button;
    }

    private JButton crearJokerButton() {
        JButton button = new JButton();

        // Cargamos imagen del joker
        ImageIcon jokerIcon = new ImageIcon("assets/jokerCards.jpeg");
        Image image = jokerIcon.getImage().getScaledInstance(60, 90, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);
        button.setIcon(scaledIcon);
        button.setPreferredSize(new Dimension(60, 90));
        button.setPreferredSize(new Dimension(60, 90));
        button.setMaximumSize(new Dimension(60,90));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        return button;
    }

    public static PyramidView getInstance() {
        if (instance == null) {
            instance = new PyramidView();
        }
        return instance;
    }

    public void startGamePanel() {
        backgroundPanel.removeAll();

        JPanel pyramidWrapper = new JPanel(new BorderLayout());
        pyramidWrapper.setOpaque(false);
        pyramidWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 40));
        pyramidWrapper.add(pyramidPanel, BorderLayout.CENTER);

        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rightPanel.setPreferredSize(new Dimension(220, 0));

        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.setOpaque(false);
        gamePanel.add(pyramidWrapper, BorderLayout.CENTER);
        gamePanel.add(rightPanel, BorderLayout.EAST);

        startMenuPanel.setVisible(false);
        pyramidPanel.setVisible(true);
        rightPanel.setVisible(true);
        activeCardButton.setVisible(false);
        auxDeckButton.setVisible(true);
        jokerButton.setVisible(true);
        restartButton.setVisible(true);
        exitGameButton.setVisible(true);

        backgroundPanel.add(gamePanel, BorderLayout.CENTER);
        frame.setContentPane(backgroundPanel);
        frame.revalidate();
        frame.repaint();
    }

    public JFrame getMainFrame() {
        return frame;
    }

    public void showStartMenu() {
        backgroundPanel.removeAll();

        startMenuPanel.setVisible(true);
        pyramidPanel.setVisible(false);
        rightPanel.setVisible(false);
        activeCardButton.setVisible(false);

        backgroundPanel.add(startMenuPanel, BorderLayout.CENTER);
        frame.setContentPane(backgroundPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void setupGameView(PyramidModel model) {
        JPanel pyramidPanel = getPyramidPanel();
        pyramidPanel.removeAll();
        pyramidPanel.revalidate();
        pyramidPanel.repaint();
    }

    public JPanel getStartMenuPanel() {
        return startMenuPanel;
    }
}
