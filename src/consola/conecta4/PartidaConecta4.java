package consola.conecta4;

import consola.core.Jugable;
import consola.core.Puntuacion;
import java.util.Scanner;

/**
 * Partida de Conecta 4 para dos jugadores. Implementa {@link Jugable}.
 * <p>
 * Puntaje del ganador: mientras más rápido gane (menos fichas en el tablero),
 * más puntos obtiene. Un empate no genera puntuación.
 */
public class PartidaConecta4 implements Jugable {

    private static final int TOTAL_CASILLAS = 42;
    private static final int PUNTOS_POR_CASILLA_LIBRE = 10;

    @Override
    public Puntuacion start(Scanner sc) {
        TableroConecta4 tablero = new TableroConecta4();

        String nombre1 = pedirNombre(sc, "Jugador 1");
        String nombre2 = pedirNombre(sc, "Jugador 2");

        boolean turnoJugador1 = true;
        boolean juegoTerminado = false;
        int fichasColocadas = 0;
        Puntuacion resultado = null;

        System.out.println("\n¡COMIENZA EL JUEGO DE CONECTA 4!");
        System.out.println(nombre1 + " jugará con 'X'");
        System.out.println(nombre2 + " jugará con 'O'");

        while (!juegoTerminado) {
            tablero.imprimirTablero();

            String nombreActual = turnoJugador1 ? nombre1 : nombre2;
            char fichaActual = turnoJugador1 ? 'X' : 'O';

            boolean movimientoValido = false;

            while (!movimientoValido) {
                System.out.print("Turno de " + nombreActual + " (" + fichaActual + "). Elige una columna (1-7): ");

                try {
                    int columna = Integer.parseInt(sc.nextLine().trim());
                    movimientoValido = tablero.soltarFicha(columna, fichaActual);

                    if (!movimientoValido) {
                        System.out.println("Movimiento inválido. La columna está llena o el número es incorrecto.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Por favor, ingresa solo números del 1 al 7.");
                }
            }
            fichasColocadas++;

            if (tablero.verificarGanador()) {
                tablero.imprimirTablero();
                System.out.println("¡FELICIDADES! " + nombreActual + " ha ganado en Conecta 4.");
                int puntos = (TOTAL_CASILLAS - fichasColocadas) * PUNTOS_POR_CASILLA_LIBRE;
                resultado = new Puntuacion(nombreActual, puntos);
                juegoTerminado = true;
            } else if (tablero.estaLleno()) {
                tablero.imprimirTablero();
                System.out.println("¡EMPATE! El tablero está completamente lleno.");
                juegoTerminado = true;
            } else {
                turnoJugador1 = !turnoJugador1;
            }
        }

        System.out.println("\nPresiona ENTER para volver al menú...");
        sc.nextLine();
        return resultado;
    }

    private String pedirNombre(Scanner sc, String porDefecto) {
        System.out.print("Nombre del " + porDefecto + ": ");
        String nombre = sc.nextLine().trim();
        return nombre.isEmpty() ? porDefecto : nombre;
    }
}
