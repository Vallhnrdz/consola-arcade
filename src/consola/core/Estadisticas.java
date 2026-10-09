package consola.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Guarda el Top 3 de puntuaciones de cada juego.
 * Cada vez que se registra una puntuación se compara contra las existentes
 * y solo se conservan las tres mejores, de mayor a menor.
 */
public class Estadisticas {

    public static final int TAMANO_TOP = 3;

    private final Map<String, List<Puntuacion>> topPorJuego = new LinkedHashMap<>();

    /**
     * Registra una puntuación en el ranking del juego indicado.
     * Se inserta en su posición según {@link PuntComparable#comparar(PuntComparable)};
     * si queda fuera de las tres primeras posiciones, se descarta.
     * En caso de empate, la puntuación más antigua conserva la posición.
     */
    public void registrar(String juego, Puntuacion nueva) {
        List<Puntuacion> top = topPorJuego.computeIfAbsent(juego, k -> new ArrayList<>());

        int pos = 0;
        while (pos < top.size() && nueva.comparar(top.get(pos)) <= 0) {
            pos++;
        }

        if (pos < TAMANO_TOP) {
            top.add(pos, nueva);
            if (top.size() > TAMANO_TOP) {
                top.remove(top.size() - 1);
            }
        }
    }

    /**
     * @return lista (solo lectura) con el Top 3 actual del juego, de mejor a peor;
     *         vacía si el juego aún no tiene registros
     */
    public List<Puntuacion> getTop3(String juego) {
        List<Puntuacion> top = topPorJuego.get(juego);
        if (top == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(top);
    }
}
