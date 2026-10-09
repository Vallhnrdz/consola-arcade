package consola;

import consola.conecta4.PartidaConecta4;
import consola.core.Estadisticas;
import consola.core.Jugable;
import consola.core.Juego;
import consola.core.Puntuacion;
import consola.juegos.BatallaDragon;
import consola.juegos.DinoRunner;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Consola {

    private static final String JUEGO_DRAGON = "Batalla contra el Dragon";
    private static final String JUEGO_DINO = "Dino Runner Arcade";
    private static final String JUEGO_CONECTA4 = "Conecta 4";

    private final Scanner sc = new Scanner(System.in);

    // El tipo Jugable garantiza que solo se puedan agregar objetos que tengan start()
    private final Map<String, Jugable> juegos = new LinkedHashMap<>();
    private final Estadisticas estadisticas = new Estadisticas();

    public Consola() {
        agregarJuego(JUEGO_DRAGON, new BatallaDragon());
        agregarJuego(JUEGO_DINO, new DinoRunner());
        agregarJuego(JUEGO_CONECTA4, new PartidaConecta4());
        cargarEscenarioFicticio();
    }

    private void agregarJuego(String nombre, Jugable juego) {
        juegos.put(nombre, juego);
    }

    /** Datos de ejemplo (desordenados a propósito) para mostrar el Top 3 desde el inicio. */
    private void cargarEscenarioFicticio() {
        estadisticas.registrar(JUEGO_DRAGON, new Puntuacion("Marco", 290));
        estadisticas.registrar(JUEGO_DRAGON, new Puntuacion("Ana", 410));
        estadisticas.registrar(JUEGO_DRAGON, new Puntuacion("Sofía", 150));
        estadisticas.registrar(JUEGO_DRAGON, new Puntuacion("Luis", 365));

        estadisticas.registrar(JUEGO_DINO, new Puntuacion("Diego", 245));
        estadisticas.registrar(JUEGO_DINO, new Puntuacion("Carlos", 300));
        estadisticas.registrar(JUEGO_DINO, new Puntuacion("Elena", 180));

        estadisticas.registrar(JUEGO_CONECTA4, new Puntuacion("Jorge", 150));
        estadisticas.registrar(JUEGO_CONECTA4, new Puntuacion("Camila", 280));
        estadisticas.registrar(JUEGO_CONECTA4, new Puntuacion("Lucía", 90));
        estadisticas.registrar(JUEGO_CONECTA4, new Puntuacion("Andrés", 210));
    }

    public void iniciar() {
        int opcion;
        do {
            Juego.limpiarPantalla();
            mostrarMenuPrincipal();
            opcion = Juego.leerEntero(sc, "Selecciona una opción: ", 0, 2);

            switch (opcion) {
                case 1:
                    lanzarJuego();
                    break;
                case 2:
                    verEstadisticas();
                    break;
                case 0:
                    System.out.println(Juego.GREEN + "\n¡Gracias por jugar en la Consola Arcade POO! Hasta luego." + Juego.RESET);
                    break;
            }
        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println(Juego.CYAN + Juego.BOLD + " ╔══════════════════════════════════════════════════════╗");
        System.out.println(" ║           🎮 RETRO ARCADE CONSOLE (POO) 🎮          ║");
        System.out.println(" ╚══════════════════════════════════════════════════════╝" + Juego.RESET);
        System.out.println(" 1) " + Juego.GREEN + "🚀 Lanzar Juego" + Juego.RESET);
        System.out.println(" 2) " + Juego.YELLOW + "📊 Ver Estadísticas" + Juego.RESET);
        System.out.println(" 0) " + Juego.RED + "❌ Salir" + Juego.RESET);
        System.out.println(Juego.CYAN + " ══════════════════════════════════════════════════════" + Juego.RESET);
    }

    private void lanzarJuego() {
        Juego.limpiarPantalla();
        List<String> nombres = new ArrayList<>(juegos.keySet());

        System.out.println(Juego.YELLOW + Juego.BOLD + "=== SELECCIÓN DE JUEGOS ===" + Juego.RESET);
        for (int i = 0; i < nombres.size(); i++) {
            System.out.println(" [" + (i + 1) + "] " + nombres.get(i));
        }
        System.out.println(" [0] Volver");

        int eleccion = Juego.leerEntero(sc, "\nElige un juego: ", 0, nombres.size());
        if (eleccion != 0) {
            String nombre = nombres.get(eleccion - 1);

            // Aquí se usa start(): lanza el juego y devuelve la puntuación obtenida
            Puntuacion resultado = juegos.get(nombre).start(sc);

            // Luego de terminar el juego se registra la estadística (compara y mantiene el Top 3)
            if (resultado != null) {
                estadisticas.registrar(nombre, resultado);
            }
        }
    }

    private void verEstadisticas() {
        Juego.limpiarPantalla();
        System.out.println(Juego.PURPLE + Juego.BOLD + " ╔══════════════════════════════════════════════════════╗");
        System.out.println(" ║                📊 SECCIÓN ESTADÍSTICAS               ║");
        System.out.println(" ╚══════════════════════════════════════════════════════╝" + Juego.RESET);

        for (String nombre : juegos.keySet()) {
            System.out.println(Juego.CYAN + Juego.BOLD + "\n " + nombre + " - Top 3" + Juego.RESET);
            List<Puntuacion> top = estadisticas.getTop3(nombre);
            if (top.isEmpty()) {
                System.out.println("   (sin registros)");
            }
            for (int i = 0; i < top.size(); i++) {
                System.out.println("   " + (i + 1) + ". " + top.get(i));
            }
        }

        System.out.println("\nPresiona ENTER para regresar al menú...");
        sc.nextLine();
    }
}
