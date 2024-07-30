package ADAPBL2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Game1 extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private JButton[] buttons;
    private char[][] grid;
    private char currentPlayer;
    private boolean gameActive;

    public Game1() {
        grid = new char[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[row][col] = ' ';
            }
        }
        currentPlayer = 'R';
        gameActive = true;
        setTitle("Connect Four");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(ROWS + 1, COLS));
        buttons = new JButton[COLS];
        for (int col = 0; col < COLS; col++) {
            buttons[col] = new JButton(String.valueOf(col));
            buttons[col].addActionListener(new ButtonListener());
            panel.add(buttons[col]);
        }
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                panel.add(new Cell(row, col));
            }
        }

        add(panel, BorderLayout.CENTER);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!gameActive) return;

            JButton button = (JButton) e.getSource();
            int col = Integer.parseInt(button.getText());

            if (validate(col)) {
                makeMove(col, currentPlayer);
                if (isWinner(currentPlayer)) {
                    gameActive = false;
                    if (currentPlayer == 'R') {
                        JOptionPane.showMessageDialog(null, "User won!");
                        System.out.println("User won!");
                    } else {
                        JOptionPane.showMessageDialog(null, "AI won!");
                        System.out.println("AI won!");
                    }
                } else if (isFull()) {
                    gameActive = false;
                    JOptionPane.showMessageDialog(null, "It's a tie!");
                } else {
                    currentPlayer = (currentPlayer == 'R') ? 'Y' : 'R';
                    if (currentPlayer == 'Y') {
                        int aiMove = getBestMove(grid, currentPlayer);
                        makeMove(aiMove, currentPlayer);
                        if (isWinner(currentPlayer)) {
                            gameActive = false;
                            JOptionPane.showMessageDialog(null, "AI won!");
                            System.out.println("AI won!");
                        } else if (isFull()) {
                            gameActive = false;
                            JOptionPane.showMessageDialog(null, "It's a tie!");
                        } else {
                            currentPlayer = 'R';
                        }
                    }
                }
            }
        }
    }

    private class Cell extends JPanel {
        private static final long serialVersionUID = 1L;
        private int row, col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            setPreferredSize(new Dimension(100, 100));
            setBackground(Color.BLUE);  // Set the background color to blue
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (grid[row][col] == 'R') {
                g.setColor(Color.RED);
            } else if (grid[row][col] == 'Y') {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.BLACK);
            }
            g.fillOval(10, 10, 80, 80);
        }
    }

    private boolean validate(int col) {
        return grid[0][col] == ' ';
    }

    private void makeMove(int col, char player) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (grid[row][col] == ' ') {
                grid[row][col] = player;
                repaint();
                break;
            }
        }
    }

    private boolean isWinner(char player) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (grid[row][col] == player &&
                        grid[row][col + 1] == player &&
                        grid[row][col + 2] == player &&
                        grid[row][col + 3] == player) {
                    return true;
                }
            }
        }

        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS; col++) {
                if (grid[row][col] == player &&
                        grid[row + 1][col] == player &&
                        grid[row + 2][col] == player &&
                        grid[row + 3][col] == player) {
                    return true;
                }
            }
        }

        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (grid[row][col] == player &&
                        grid[row - 1][col + 1] == player &&
                        grid[row - 2][col + 2] == player &&
                        grid[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }

        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (grid[row][col] == player &&
                        grid[row + 1][col + 1] == player &&
                        grid[row + 2][col + 2] == player &&
                        grid[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isFull() {
        for (int col = 0; col < COLS; col++) {
            if (grid[0][col] == ' ') {
                return false;
            }
        }
        return true;
    }

    private int getBestMove(char[][] grid, char player) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int col = 0; col < COLS; col++) {
            if (validate(col)) {
                int row = getAvailableRow(col);
                grid[row][col] = player;
                int score = minimax(grid, 0, false, player);
                grid[row][col] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }
            }
        }
        return bestMove;
    }
    private int minimax(char[][] grid, int depth, boolean isMaximizing, char player) {
        char opponent = (player == 'R') ? 'Y' : 'R';

        if (isWinner(player)) {
            return 1;
        }
        if (isWinner(opponent)) {
            return -1;
        }
        if (isFull() || depth == 5) {
            return 0;
        }
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int col = 0; col < COLS; col++) {
                if (validate(col)) {
                    int row = getAvailableRow(col);
                    grid[row][col] = player;
                    int score = minimax(grid, depth + 1, false, player);
                    grid[row][col] = ' ';
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int col = 0; col < COLS; col++) {
                if (validate(col)) {
                    int row = getAvailableRow(col);
                    grid[row][col] = opponent;
                    int score = minimax(grid, depth + 1, true, player);
                    grid[row][col] = ' ';
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    private int getAvailableRow(int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (grid[row][col] == ' ') {
                return row;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game1 gui = new Game1();
                gui.setVisible(true);
            }
        });
    }
}
