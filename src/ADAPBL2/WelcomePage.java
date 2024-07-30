package ADAPBL2;
import javax.swing.*;
import java.awt.*;
public class WelcomePage extends JFrame {
    private static final long serialVersionUID = 1L;
    public WelcomePage() {
        setTitle("Welcome to Connect Four");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        BackgroundPanel backgroundPanel = new BackgroundPanel("w2.jpg.jpg");
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Connect Four!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 45));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setOpaque(false);
        backgroundPanel.add(welcomeLabel, BorderLayout.CENTER);
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.PLAIN, 20));
        startButton.setBackground(Color.LIGHT_GRAY);
        startButton.setOpaque(true);
        startButton.addActionListener(e -> {
            new Game1().setVisible(true);
            setVisible(false);});
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomePage().setVisible(true));
    }
}
