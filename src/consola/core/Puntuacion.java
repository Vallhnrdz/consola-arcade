package consola.core;

/**
 * Resultado de una partida: nombre del jugador y puntos obtenidos.
 * Implementa {@link PuntComparable} para poder ordenarse dentro del Top 3.
 */
public class Puntuacion implements PuntComparable {

    private final String jugador;
    private final int puntos;

    public Puntuacion(String jugador, int puntos) {
        this.jugador = jugador;
        this.puntos = puntos;
    }

    public String getJugador() {
        return jugador;
    }

    @Override
    public int getPuntos() {
        return puntos;
    }

    @Override
    public int comparar(PuntComparable otra) {
        return Integer.compare(this.puntos, otra.getPuntos());
    }

    @Override
    public String toString() {
        return jugador + " - " + puntos + " pts";
    }
}
