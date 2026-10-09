package consola.conecta4;

public class TableroConecta4 {
    private char[][] casillas;
    private final int FILAS = 6;
    private final int COLUMNAS = 7;

    // Constructor: Prepara el tablero vacío usando guiones '-'
    public TableroConecta4() {
        casillas = new char[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = '-';
            }
        }
    }

    public void imprimirTablero() {
        System.out.println("\n  1   2   3   4   5   6   7");
        for (int i = 0; i < FILAS; i++) {
            System.out.print("| ");
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(casillas[i][j] + " | ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
    }

    // Gravedad: La ficha cae hasta la fila más baja que esté vacía en esa columna
    public boolean soltarFicha(int columna, char ficha) {
        if (columna < 1 || columna > COLUMNAS) {
            return false; // Fuera de rango
        }
        
        int colIndex = columna - 1; // Ajuste para el índice del arreglo (0-6)

        // Buscamos de abajo (fila 5) hacia arriba (fila 0)
        for (int i = FILAS - 1; i >= 0; i--) {
            if (casillas[i][colIndex] == '-') {
                casillas[i][colIndex] = ficha;
                return true; // Ficha colocada con éxito
            }
        }
        return false; // La columna ya está llena
    }

    public boolean verificarGanador() {
        // 1. Revisar líneas horizontales
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS - 3; j++) {
                if (casillas[i][j] != '-' && 
                    casillas[i][j] == casillas[i][j+1] && 
                    casillas[i][j] == casillas[i][j+2] && 
                    casillas[i][j] == casillas[i][j+3]) {
                    return true;
                }
            }
        }
        // 2. Revisar líneas verticales
        for (int i = 0; i < FILAS - 3; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (casillas[i][j] != '-' && 
                    casillas[i][j] == casillas[i+1][j] && 
                    casillas[i][j] == casillas[i+2][j] && 
                    casillas[i][j] == casillas[i+3][j]) {
                    return true;
                }
            }
        }
        // 3. Revisar diagonales (hacia abajo y derecha)
        for (int i = 0; i < FILAS - 3; i++) {
            for (int j = 0; j < COLUMNAS - 3; j++) {
                if (casillas[i][j] != '-' && 
                    casillas[i][j] == casillas[i+1][j+1] && 
                    casillas[i][j] == casillas[i+2][j+2] && 
                    casillas[i][j] == casillas[i+3][j+3]) {
                    return true;
                }
            }
        }
        // 4. Revisar diagonales (hacia arriba y derecha)
        for (int i = 3; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS - 3; j++) {
                if (casillas[i][j] != '-' && 
                    casillas[i][j] == casillas[i-1][j+1] && 
                    casillas[i][j] == casillas[i-2][j+2] && 
                    casillas[i][j] == casillas[i-3][j+3]) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean estaLleno() {
        // Solo necesitamos revisar la fila superior (fila 0). Si no hay guiones ahí, todo está lleno.
        for (int j = 0; j < COLUMNAS; j++) {
            if (casillas[0][j] == '-') {
                return false;
            }
        }
        return true;
    }
}