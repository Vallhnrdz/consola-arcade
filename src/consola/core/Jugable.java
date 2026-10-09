package consola.core;

import java.util.Scanner;

/**
 * Contrato que debe cumplir todo objeto que pueda lanzarse desde la consola.
 * <p>
 * La {@code Consola} solo guarda referencias de tipo {@code Jugable}, por lo que
 * el compilador impide agregarle cualquier objeto que no implemente {@link #start(Scanner)}.
 */
public interface Jugable {

    /**
     * Inicia una partida completa y devuelve el resultado obtenido.
     *
     * @param sc scanner compartido de la consola (un único Scanner para toda la aplicación)
     * @return la {@link Puntuacion} obtenida por el jugador, o {@code null} si la partida
     *         no genera puntuación (por ejemplo, un empate)
     */
    Puntuacion start(Scanner sc);
}
