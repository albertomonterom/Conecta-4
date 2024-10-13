public class Minimax {
    private static final int AI_PLAYER = 2; // Computadora
    private static final int HUMAN_PLAYER = 1;

    // Metodo para obtener la mejor jugada usando Minimax con poda Alfa-Beta
    public int getBestMove(Board board, int depth) {
        int bestMove = -1;
        int bestValue = Integer.MIN_VALUE;
        int worstValue = Integer.MAX_VALUE;

        for (int col = 0; col < 7; col++) {
            // Si la columna no esta llena
            if (!board.isColumnFull(col)) {
                Board tempBoard = cloneBoard(board);
                tempBoard.makeMove(col, AI_PLAYER);
                int boardValue = minimax(tempBoard, depth, bestValue, worstValue, false);

                if (boardValue > bestValue) {
                    bestValue = boardValue;
                    bestMove = col;
                }
            }
        }
        return bestMove;
    }

    private int minimax(Board board, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (depth == 0 || board.checkWin(AI_PLAYER) || board.checkWin(HUMAN_PLAYER) || board.isFull()) {
            return evaluateBoard(board);
        }
        // Si el turno del jugador es la computadora
        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < 7; col++) {
                if (!board.isColumnFull(col)) {
                    Board tempBoard = cloneBoard(board);
                    tempBoard.makeMove(col, AI_PLAYER);
                    int eval = minimax(tempBoard, depth - 1, alpha, beta, false);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < 7; col++) {
                if (!board.isColumnFull(col)) {
                    Board tempBoard = cloneBoard(board);
                    tempBoard.makeMove(col, HUMAN_PLAYER);
                    int eval = minimax(tempBoard, depth - 1, alpha, beta, true);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }

    // Funcion de evaluacion del tablero (heuristica)
    private int evaluateBoard(Board board) {
        int score = 0;

        // Evaluar las filas
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {  // Solo necesitamos revisar hasta la columna 3 para evitar desbordamiento
                score += evaluateLine(board, row, col, 0, 1);  // Evalúa una fila de 4 fichas
            }
        }

        // Evaluar las columnas
        for (int row = 0; row < 3; row++) {  // Solo necesitamos revisar hasta la fila 2 para evitar desbordamiento
            for (int col = 0; col < 7; col++) {
                score += evaluateLine(board, row, col, 1, 0);  // Evalúa una columna de 4 fichas
            }
        }

        // Evaluar diagonales ascendentes
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                score += evaluateLine(board, row, col, -1, 1);  // Evalúa una diagonal ascendente de 4 fichas
            }
        }

        // Evaluar diagonales descendentes
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                score += evaluateLine(board, row, col, 1, 1);  // Evalúa una diagonal descendente de 4 fichas
            }
        }

        return score;
    }
    private int evaluateLine(Board board, int row, int col, int rowIncrement, int colIncrement) {
        int aiCount = 0;
        int humanCount = 0;

        for (int i = 0; i < 4; i++) {
            if (board.getGrid()[row][col] == AI_PLAYER) {
                aiCount++;
            } else if (board.getGrid()[row][col] == HUMAN_PLAYER) {
                humanCount++;
            }
            row += rowIncrement;
            col += colIncrement;
        }

        // Asignar puntuación a las líneas según las fichas del jugador o la IA
        if (aiCount == 4) {
            return 1000;  // IA ha ganado
        } else if (humanCount == 4) {
            return -1000;  // Humano ha ganado
        } else if (aiCount == 3 && humanCount == 0) {
            return 10;  // IA tiene 3 fichas consecutivas
        } else if (aiCount == 2 && humanCount == 0) {
            return 5;  // IA tiene 2 fichas consecutivas
        } else if (humanCount == 3 && aiCount == 0) {
            return -10;  // Humano tiene 3 fichas consecutivas
        } else if (humanCount == 2 && aiCount == 0) {
            return -5;  // Humano tiene 2 fichas consecutivas
        }

        return 0;  // Si hay una mezcla de fichas o espacios vacíos
    }


    // Metodo para clonar el tablero actual
    private Board cloneBoard(Board original) {
        Board newBoard = new Board();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                newBoard.grid[row][col] = original.grid[row][col];
            }
        }
        return newBoard;
    }
}
