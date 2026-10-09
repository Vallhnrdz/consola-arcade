package consola.juegos;

import consola.core.Juego;
import java.util.Random;
import java.util.Scanner;

public class BatallaDragon extends Juego {

    private static final int VIDA_HEROE_MAX = 100;
    private static final int VIDA_DRAGON_MAX = 160;
    private static final int MANA_MAXIMO = 50;

    private final Random random = new Random();

    public BatallaDragon() {
        super("Batalla contra el Dragon");
    }

    @Override
    protected int jugar(Scanner sc) {
        int vidaHeroe = VIDA_HEROE_MAX;
        int vidaDragon = VIDA_DRAGON_MAX;
        int mana = MANA_MAXIMO;
        int pociones = 3;
        int combo = 0;
        int turno = 1;

        String debilidad = "NINGUNA";
        String eventoActual = "¡El combate en las profundidades de la cueva ha comenzado!";

        while (vidaHeroe > 0 && vidaDragon > 0) {
            limpiarPantalla();

            // Eventos aleatorios del entorno
            int chanceEvento = random.nextInt(100);
            if (chanceEvento < 18) {
                eventoActual = "[EVENTO] ¡Derrumbe en la cueva! Cadenas de rocas golpean a ambos (-10 HP).";
                vidaHeroe = Math.max(0, vidaHeroe - 10);
                vidaDragon = Math.max(0, vidaDragon - 10);
            } else if (chanceEvento < 35) {
                eventoActual = "[EVENTO] ¡Una corriente mistica envuelve el lugar! Recuperas +15 MP.";
                mana = Math.min(MANA_MAXIMO, mana + 15);
            }

            // Revelar punto débil del dragón
            if (random.nextInt(100) < 45) {
                debilidad = random.nextBoolean() ? "CABEZA" : "COLA";
            } else {
                debilidad = "NINGUNA";
            }

            dibujarEscena(vidaHeroe, vidaDragon, mana, pociones, combo, debilidad, eventoActual);

            System.out.println(BOLD + "\nAcciones de Combate:" + RESET);
            System.out.println(" 1) [Ataque Rapido] " + GREEN + "(Sube Combo x" + (combo + 1) + ")" + RESET);
            System.out.println(" 2) [Golpe de Precision] " + (debilidad.equals("NINGUNA") ? RED + "(Sin objetivo expuesto)" + RESET : YELLOW + "(¡OBJETIVO EXPUESTO: " + debilidad + "!)" + RESET));
            System.out.println(" 3) [Bola de Fuego] (-15 MP)");
            System.out.println(" 4) [Pocion de Salud] (+40 HP | Quedan: " + pociones + ")");
            System.out.println(" 5) [Defender y Cargar] (+15 MP / Mitiga daño)");

            int accion = leerEntero(sc, "\n¡Selecciona tu movimiento (1-5)!: ", 1, 5);
            String resultadoHeroe = "";

            switch (accion) {
                case 1: {
                    combo++;
                    int danoBase = 10 + random.nextInt(8);
                    int danoTotal = danoBase + (combo * 3);
                    vidaDragon = Math.max(0, vidaDragon - danoTotal);
                    resultadoHeroe = "¡Ataque fluido asestado! Daño: " + RED + danoTotal + RESET + " (Combo x" + combo + ")";
                    break;
                }
                case 2: {
                    if (debilidad.equals("NINGUNA")) {
                        resultadoHeroe = RED + "¡El dragon cubrio sus flancos! Fallaste el impacto de precision." + RESET;
                        combo = 0;
                    } else {
                        int dano = 38 + random.nextInt(12);
                        vidaDragon = Math.max(0, vidaDragon - dano);
                        resultadoHeroe = PURPLE + "¡GOLPE CRITICO EN LA " + debilidad + "! Infliges " + RED + dano + " de daño." + RESET;
                        combo++;
                    }
                    break;
                }
                case 3: {
                    if (mana < 15) {
                        resultadoHeroe = RED + "¡No tienes suficiente mana!" + RESET;
                    } else {
                        mana -= 15;
                        int dano = 28 + random.nextInt(10);
                        vidaDragon = Math.max(0, vidaDragon - dano);
                        resultadoHeroe = CYAN + "¡Llamarada magica impacta al dragon por " + RED + dano + " de daño!" + RESET;
                    }
                    break;
                }
                case 4: {
                    if (pociones <= 0) {
                        resultadoHeroe = RED + "¡No te quedan pociones!" + RESET;
                    } else {
                        pociones--;
                        vidaHeroe = Math.min(VIDA_HEROE_MAX, vidaHeroe + 40);
                        resultadoHeroe = GREEN + "¡Usaste una pocion y recuperas 40 HP!" + RESET;
                    }
                    break;
                }
                case 5: {
                    mana = Math.min(MANA_MAXIMO, mana + 15);
                    resultadoHeroe = YELLOW + "Adoptas postura defensiva (+15 MP / Daño enemigo reducido a la mitad)." + RESET;
                    break;
                }
            }

            // Contraataque del dragón
            if (vidaDragon > 0) {
                int danoDragon = 12 + random.nextInt(10);
                if (accion == 5) danoDragon /= 2;
                vidaHeroe = Math.max(0, vidaHeroe - danoDragon);
                eventoActual = resultadoHeroe + "\n[DRAGON] El dragon arremete e inflige " + RED + danoDragon + " de daño." + RESET;
            } else {
                eventoActual = resultadoHeroe;
            }

            turno++;
        }

        limpiarPantalla();
        if (vidaHeroe > 0) {
            System.out.println(GREEN + BOLD + "=======================================================");
            System.out.println("  ¡VICTORIA EPICA! HAS DERROTADO AL DRAGON EN " + turno + " TURNOS.");
            System.out.println("=======================================================" + RESET);
        } else {
            System.out.println(RED + BOLD + "=======================================================");
            System.out.println("  HAS CAIDO EN COMBATE... EL DRAGON REINA EN LA CUEVA.");
            System.out.println("=======================================================" + RESET);
        }
        System.out.println("\nPresiona ENTER para volver al menú...");
        sc.nextLine();

        // Puntaje: daño hecho al dragón + bonificación si ganó (vida restante y rapidez)
        int puntos = VIDA_DRAGON_MAX - vidaDragon;
        if (vidaHeroe > 0) {
            puntos += 100 + (vidaHeroe * 2) + Math.max(0, 200 - (turno * 10));
        }
        return puntos;
    }

    private void dibujarEscena(int vH, int vD, int mana, int pociones, int combo, String debilidad, String log) {
        System.out.println(CYAN + BOLD + "=== ARENA DE COMBATE ===" + RESET);
        System.out.println(" HEROE:  " + generarBarra(vH, VIDA_HEROE_MAX, GREEN) + "  MP: " + CYAN + mana + RESET);
        System.out.println(" DRAGON: " + generarBarra(vD, VIDA_DRAGON_MAX, RED) + "  " + (debilidad.equals("NINGUNA") ? "" : YELLOW + "[PUNTO DEBIL: " + debilidad + "]" + RESET));
        System.out.println(" COMBO: " + YELLOW + "x" + combo + RESET + " | POCIONES: " + GREEN + pociones + RESET);
        System.out.println(WHITE + "-------------------------------------------------------" + RESET);
        System.out.println(log);
        System.out.println(WHITE + "-------------------------------------------------------" + RESET);
    }

    private String generarBarra(int act, int max, String col) {
        int llenos = (int) Math.round(((double) act / max) * 10);
        return col + "█".repeat(llenos) + RESET + "░".repeat(10 - llenos) + " (" + act + "/" + max + ")";
    }
}