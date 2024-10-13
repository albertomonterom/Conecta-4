/*
    This class represents the game state and contains
    the methods needed to interact with the board
*/
public class Board {
    int [][] grid; // Grid del tablero como una matriz 6 x 78
    private static final int ROWS = 6;
    private static final int COLS = 7;
    public Board () {
        grid = new int[ROWS][COLS];
        // Inicializamos el tablero vacío
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = 0; // 0 indica que la casilla está vacía
            }
        }
    }
    // Método para verificar si una columna está llena
    public boolean isColumnFull(int col) {
        return grid[0][col] != 0;
    }
    // Método para insertar una ficha en la columna seleccionada
    public boolean makeMove(int col, int player) {
        if(isColumnFull(col)) {
            return false;
        }

        for (int row = ROWS - 1; row >= 0; row--) {
            if(grid[row][col] == 0) {
                grid[row][col] = player; // 1 para el humano, 2 para la computadora
                return true;
            }
        }
        return false;
    }
    // Metodo para verificar si hay un ganador
    public boolean checkWin(int player) {
        // Revisar filas, columnas y diagonales

        // Verificacion de filas
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (grid[row][col] == player && grid[row][col + 1] == player && grid[row][col + 2] == player && grid[row][col + 3] == player ) {
                    return true;
                }
            }
        }

        // Verificacion de columnas
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS; col++) {
                if (grid[row][col] == player && grid[row + 1][col] == player && grid[row + 2][col] == player && grid[row + 3][col] == player) {
                    return true;
                }
            }
        }

        // Verificacion de diagonales ascendentes
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (grid[row][col] == player && grid[row - 1][col + 1] == player && grid[row - 2][col + 2] == player && grid[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }

        // Verificacion de diagonales descendentes
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (grid[row][col] == player && grid[row + 1][col + 1] == player && grid[row + 2][col + 2] == player && grid[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }
    // Metodo para verificar si el tablero esta lleno
    public boolean isFull() {
        for (int i = 0; i < COLS; i++) {
            if (!isColumnFull(i)) {
                return false;
            }
        }
        return true;
    }
    public int getCell(int row, int col) {
        return grid[row][col];
    }

    public int[][] getGrid() {
        return grid;
    }
}
