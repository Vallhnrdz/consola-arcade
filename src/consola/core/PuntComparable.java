package consola.core;

/**
 * Contrato para los objetos de puntuación que se pueden comparar entre sí,
 * lo que permite armar el ranking Top 3 de cada juego.
 */
public interface PuntComparable {

    /**
     * @return la cantidad de puntos que representa este objeto
     */
    int getPuntos();

    /**
     * Compara esta puntuación con otra.
     *
     * @param otra puntuación contra la que se compara
     * @return un valor mayor que 0 si esta puntuación es mayor (mejor),
     *         menor que 0 si es menor (peor), o 0 si son iguales
     */
    int comparar(PuntComparable otra);
}
