package consola.juegos;

import consola.core.Juego;
import java.util.Random;
import java.util.Scanner;

public class DinoRunner extends Juego {

    private static final int ANCHO = 25; // Ancho ajustado para mejorar la visualización con emojis
    private final Random random = new Random();

    public DinoRunner() {
        super("Dino Runner Arcade");
    }

    @Override
    protected int jugar(Scanner sc) {
        limpiarPantalla();
        System.out.println(GREEN + BOLD + "=== 🦖 DINO RUNNER ARCADE 🦖 ===" + RESET);
        System.out.println("Reglas de juego:");
        System.out.println(" - 🌵 = Cactus  -> Requiere SALTAR (opción 1)");
        System.out.println(" - 🦅 = Ave     -> Requiere AGACHARSE (opción 1)");
        System.out.println(" - ⭐ = Bonus   -> Requiere RECOGER (opción 1)");
        System.out.println("\nLlega a 300 puntos para ganar la carrera.");
        System.out.println("\nPresiona ENTER para iniciar...");
        sc.nextLine();

        int puntos = 0;
        boolean vivo = true;
        int baseDelay = 90;

        String[] carril = new String[ANCHO];
        for (int i = 0; i < ANCHO; i++) carril[i] = " ";

        while (vivo && puntos < 300) {
            // Avanzar el escenario
            System.arraycopy(carril, 1, carril, 0, ANCHO - 1);

            // Generar nuevo objeto en el carril
            if (random.nextInt(100) < 30) {
                int tipo = random.nextInt(3);
                if (tipo == 0) carril[ANCHO - 1] = "🌵";
                else if (tipo == 1) carril[ANCHO - 1] = "🦅";
                else carril[ANCHO - 1] = "⭐";
            } else {
                carril[ANCHO - 1] = " ";
            }

            limpiarPantalla();
            dibujarMapa(carril, puntos);

            // Detectar qué hay frente al dinosaurio (casilla 3)
            String proximo = carril[3];

            if (proximo.equals("🌵")) {
                System.out.println(RED + BOLD + "¡CACTUS CERCA! 🌵 -> ¡SALTA AHORA!" + RESET);
                System.out.println("1) 🦘 SALTAR   2) 🏃 SEGUIR CORRIENDO");
                int resp = leerEntero(sc, "Acción: ", 1, 2);
                if (resp != 1) vivo = false;
                else puntos += 20;
            } else if (proximo.equals("🦅")) {
                System.out.println(PURPLE + BOLD + "¡AVE VOLANDO BAJO! 🦅 -> ¡AGÁCHATE!" + RESET);
                System.out.println("1) 🙇 AGÁCHATE   2) 🦘 SALTAR");
                int resp = leerEntero(sc, "Acción: ", 1, 2);
                if (resp != 1) vivo = false;
                else puntos += 20;
            } else if (proximo.equals("⭐")) {
                System.out.println(YELLOW + BOLD + "¡ESTRELLA DETECTADA! ⭐" + RESET);
                System.out.println("1) 🖐️ RECOGER   2) 🚫 IGNORAR");
                int resp = leerEntero(sc, "Acción: ", 1, 2);
                if (resp == 1) puntos += 50;
            } else {
                puntos += 5;
            }

            try {
                Thread.sleep(Math.max(25, baseDelay - (puntos / 8)));
            } catch (InterruptedException ignored) {}
        }

        limpiarPantalla();
        if (vivo) {
            System.out.println(GREEN + BOLD + "🏆 ¡FELICIDADES! Completaste el circuito con " + puntos + " puntos." + RESET);
        } else {
            System.out.println(RED + BOLD + "💥 ¡HAS CHOCADO! Puntuación final: " + puntos + " pts" + RESET);
        }
        System.out.println("\nPresiona ENTER para volver al menú...");
        sc.nextLine();
        return puntos;
    }

    private void dibujarMapa(String[] carril, int puntos) {
        System.out.println(CYAN + "PUNTOS: " + YELLOW + puntos + RESET + " / 300");
        System.out.println("┌" + "──".repeat(ANCHO + 1) + "┐");

        System.out.print("│ ");
        for (int i = 0; i < ANCHO; i++) {
            if (i == 3) {
                System.out.print("🦖"); // Dinosaurio del jugador
            } else {
                System.out.print(carril[i].equals(" ") ? "  " : carril[i]);
            }
        }
        System.out.println(" │");

        System.out.println("└" + "──".repeat(ANCHO + 1) + "┘");
    }
}