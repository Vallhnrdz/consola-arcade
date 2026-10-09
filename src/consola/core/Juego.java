package consola.core;

import java.util.Scanner;

/**
 * Clase base de los juegos de la consola. Implementa {@link Jugable}:
 * {@link #start(Scanner)} pide el nombre del jugador, ejecuta la lógica del juego
 * ({@link #jugar(Scanner)}) y devuelve la puntuación obtenida.
 */
public abstract class Juego implements Jugable {

    // Códigos ANSI para colores y estilos en consola
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    private final String nombre;

    public Juego(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public final Puntuacion start(Scanner sc) {
        limpiarPantalla();
        System.out.print(YELLOW + "Ingresa tu nombre para el ranking: " + RESET);
        String jugador = sc.nextLine().trim();
        if (jugador.isEmpty()) {
            jugador = "Anónimo";
        }
        int puntos = jugar(sc);
        return new Puntuacion(jugador, puntos);
    }

    /**
     * Lógica propia de cada juego.
     *
     * @return los puntos obtenidos en la partida
     */
    protected abstract int jugar(Scanner sc);

    public static int leerEntero(Scanner sc, String mensaje, int min, int max) {
        while (true) {
            System.out.print(YELLOW + mensaje + RESET);
            String linea = sc.nextLine().trim();
            try {
                int n = Integer.parseInt(linea);
                if (n >= min && n <= max) {
                    return n;
                }
            } catch (NumberFormatException e) {
                // continua al mensaje de error
            }
            System.out.println(RED + "⚠ Entrada inválida. Ingresa un número entre " + min + " y " + max + "." + RESET);
        }
    }

    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
