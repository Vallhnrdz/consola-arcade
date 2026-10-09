# 🎮 Retro Arcade Console (POO)

Simulación de una consola de videojuegos en Java, por consola. Permite lanzar tres juegos y consultar el Top 3 de puntuaciones de cada uno. El proyecto aplica herencia, interfaces, polimorfismo y paquetes.

## Juegos

| Juego | Descripción | Puntaje |
|---|---|---|
| **Batalla contra el Dragon** | Combate por turnos contra un dragón (ataques, magia, pociones y eventos aleatorios). | Daño hecho al dragón, más una bonificación si el héroe gana (vida restante y rapidez). |
| **Dino Runner Arcade** | Carrera donde se debe saltar, agacharse o recoger estrellas. | Puntos acumulados durante la carrera. |
| **Conecta 4** | Partida de dos jugadores en un tablero de 6x7. | `(42 − fichas colocadas) × 10` para el ganador; un empate no registra puntuación. |

## Estructura del proyecto

```
src/consola/
 ├─ Main.java               Punto de entrada
 ├─ Consola.java            Menú principal, lanzamiento de juegos y estadísticas
 ├─ core/
 │   ├─ Jugable.java        Interfaz: Puntuacion start(Scanner)
 │   ├─ PuntComparable.java Interfaz: int comparar(PuntComparable)
 │   ├─ Puntuacion.java     Jugador + puntos (implementa PuntComparable)
 │   ├─ Estadisticas.java   Top 3 por juego
 │   └─ Juego.java          Clase abstracta base (implementa Jugable)
 ├─ juegos/
 │   ├─ BatallaDragon.java
 │   └─ DinoRunner.java
 └─ conecta4/
     ├─ PartidaConecta4.java (implementa Jugable)
     └─ TableroConecta4.java
```

## Diseño

### Interfaces

- **`Jugable`**: contrato de todo lo que se puede lanzar desde la consola. Define `Puntuacion start(Scanner sc)`, que ejecuta la partida y devuelve el resultado (o `null` si no hay puntuación, como en un empate).
- **`PuntComparable`**: contrato para comparar puntuaciones. `comparar(otra)` devuelve un valor mayor que 0, menor que 0 o igual a 0 según la puntuación sea mayor, menor o igual.

### Clases

- **`Juego`** (abstracta) implementa `Jugable`. Su `start()` pide el nombre del jugador, llama a la lógica propia del juego (`jugar()`) y devuelve la `Puntuacion`. `BatallaDragon` y `DinoRunner` la extienden.
- **`PartidaConecta4`** implementa `Jugable` directamente.
- **`Puntuacion`** implementa `PuntComparable`.

### Solo objetos con `start()`

`Consola` guarda los juegos en un `Map<String, Jugable>`. Como el tipo es la interfaz, el compilador no permite agregar objetos que no implementen `start()`.

### Estadísticas (Top 3 por juego)

1. Al terminar un juego, `Consola` obtiene la `Puntuacion` devuelta por `start()` y la registra en `Estadisticas`.
2. `Estadisticas.registrar()` compara la nueva puntuación con las existentes usando `comparar()`, la inserta en su posición y descarta lo que quede fuera del Top 3. En caso de empate, la puntuación más antigua conserva su posición.
3. La opción **Ver Estadísticas** muestra el Top 3 actual de cada juego. Los datos se pierden al cerrar la aplicación.
4. Al iniciar se carga un escenario ficticio con puntuaciones de ejemplo para mostrar el funcionamiento.

## Requisitos

- JDK 11 o superior.
- Una terminal con soporte de colores ANSI y UTF-8 (para los emojis).

## Compilar y ejecutar

Desde la raíz del proyecto:

```bash
# Compilar
javac -encoding UTF-8 -d out $(find src -name "*.java")

# Ejecutar
java -cp out consola.Main
```

En Windows (PowerShell):

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out consola.Main
```

## Uso

1. **Lanzar Juego**: elige un juego, ingresa tu nombre y juega.
2. **Ver Estadísticas**: consulta el Top 3 de cada juego.
3. **Salir**.
