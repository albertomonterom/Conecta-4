import javax.swing.*;
import java.awt.*;

public class Connect4GUI extends JFrame {
    private Board board;
    private Minimax ai;
    private JButton[] buttons;  // Botones para cada columna
    private JPanel panelBoard;  // Panel para mostrar el tablero
    private JLabel[][] cells;   // Celdas del tablero
    private int currentPlayer;  // 1 para humano, 2 para IA
    private int depth;          // Profundidad del árbol Minimax

    public Connect4GUI() {
        board = new Board();
        ai = new Minimax();
        currentPlayer = 1;  // El humano empieza primero

        // Crear la ventana
        setTitle("Conecta 4 - Juego");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el tablero de botones (botones para seleccionar columnas)
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1, 7));
        buttons = new JButton[7];
        for (int i = 0; i < 7; i++) {
            JButton button = new JButton("Col " + (i + 1));
            final int col = i;
            button.addActionListener(e -> handleMove(col));
            buttons[i] = button;
            panelButtons.add(button);
        }
        add(panelButtons, BorderLayout.NORTH);

        // Crear el panel del tablero (para mostrar las fichas)
        panelBoard = new JPanel();
        panelBoard.setLayout(new GridLayout(6, 7));
        cells = new JLabel[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                cells[row][col] = new JLabel();
                cells[row][col].setOpaque(true);
                cells[row][col].setBackground(Color.BLACK);
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.WHITE));
                panelBoard.add(cells[row][col]);
            }
        }
        add(panelBoard, BorderLayout.CENTER);

        // Preguntar la profundidad del árbol al usuario
        String input = JOptionPane.showInputDialog(this, "Indique la profundidad del árbol para la IA:", "Configuración", JOptionPane.QUESTION_MESSAGE);
        depth = Integer.parseInt(input);

        // Mostrar la ventana
        setVisible(true);
    }

    // Manejar el movimiento del jugador humano
    private void handleMove(int col) {
        if (currentPlayer == 1) {
            // El humano hace un movimiento
            if (board.makeMove(col, currentPlayer)) {
                updateBoard();
                if (board.checkWin(currentPlayer)) {
                    JOptionPane.showMessageDialog(this, "¡Felicidades! Has ganado.");
                    resetGame();
                } else if (board.isFull()) {
                    JOptionPane.showMessageDialog(this, "Empate.");
                    resetGame();
                } else {
                    currentPlayer = 2;  // Cambiar turno a la IA
                    aiMove();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Columna llena. Elige otra.");
            }
        }
    }

    // Mover la IA
    private void aiMove() {
        if (currentPlayer == 2) {
            int aiMove = ai.getBestMove(board, depth);  // La IA elige la mejor jugada
            board.makeMove(aiMove, currentPlayer);
            updateBoard();
            if (board.checkWin(currentPlayer)) {
                JOptionPane.showMessageDialog(this, "La IA ha ganado. Mejor suerte la próxima vez.");
                resetGame();
            } else if (board.isFull()) {
                JOptionPane.showMessageDialog(this, "Empate.");
                resetGame();
            } else {
                currentPlayer = 1;  // Cambiar turno al jugador humano
            }
        }
    }

    // Actualizar el tablero en la interfaz gráfica
    private void updateBoard() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (board.getGrid()[row][col] == 1) {
                    cells[row][col].setBackground(Color.RED);  // Jugador humano
                } else if (board.getGrid()[row][col] == 2) {
                    cells[row][col].setBackground(Color.YELLOW);  // IA
                } else {
                    cells[row][col].setBackground(Color.BLACK);  // Vacío
                }
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.WHITE));  // Líneas blancas
            }
        }
    }

    // Reiniciar el juego
    private void resetGame() {
        board = new Board();
        currentPlayer = 1;
        updateBoard();
    }

    public static void main(String[] args) {
        new Connect4GUI();
    }
}
