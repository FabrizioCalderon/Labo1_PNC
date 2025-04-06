import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // 1. Definición de la clase jugador
    static class Jugador {
        private int id;
        private String nombre;
        private int golesMarcados;
        private int partidosJugados;

        // Constructor
        public Jugador(String nombre, int id) {
            this.id = id;
            this.nombre = nombre;
            this.golesMarcados = 0;
            this.partidosJugados = 0;
        }

        // Getters
        public String getNombre() {
            return nombre;
        }

        public int getId() {
            return id;
        }

        public int getGolesMarcados() {
            return golesMarcados;
        }
        
        public int getPartidosJugados() {
            return partidosJugados;
        }
        
        // Método para actualizar estadísticas después de un partido
        public void actualizarEstadisticas(int golesMarcadosEnPartido) {
            this.golesMarcados += golesMarcadosEnPartido;
            this.partidosJugados++;
        }

        // toString para imprimir el objeto
        @Override
        public String toString() {
            return "Jugador{" +
                    "nombre='" + nombre + '\'' +
                    ", id=" + id +
                    ", golesMarcados=" + golesMarcados +
                    ", partidosJugados=" + partidosJugados +
                    '}';
        }
    }

    // Definición de la clase Partido
    static class Partido {
        private List<Jugador> equipoA;
        private List<Jugador> equipoB;
        private Map<Jugador, Integer> golesPorJugador;
        private int golesEquipoA;
        private int golesEquipoB;

        // Constructor
        public Partido(List<Jugador> equipoA, List<Jugador> equipoB) {
            this.equipoA = equipoA;
            this.equipoB = equipoB;
            this.golesEquipoA = 0;
            this.golesEquipoB = 0;
            this.golesPorJugador = new HashMap<>();
            
            // Inicializar mapa de goles por jugador
            for (Jugador jugador : equipoA) {
                golesPorJugador.put(jugador, 0);
            }
            for (Jugador jugador : equipoB) {
                golesPorJugador.put(jugador, 0);
            }
        }

        // Registrar gol de un jugador
        public void registrarGol(Jugador jugador, int cantidadGoles) {
            if (equipoA.contains(jugador)) {
                golesEquipoA += cantidadGoles;
            } else if (equipoB.contains(jugador)) {
                golesEquipoB += cantidadGoles;
            } else {
                throw new IllegalArgumentException("El jugador no pertenece a ningún equipo del partido");
            }
            
            // Actualizar goles del jugador en este partido
            int golesActuales = golesPorJugador.getOrDefault(jugador, 0);
            golesPorJugador.put(jugador, golesActuales + cantidadGoles);
        }

        // Obtener equipo ganador
        public String equipoGanador() {
            if (golesEquipoA > golesEquipoB) {
                return "Equipo A";
            } else if (golesEquipoB > golesEquipoA) {
                return "Equipo B";
            } else {
                return "Empate";
            }
        }

        // Obtener detalles del partido
        public String detallesPartido() {
            StringBuilder detalles = new StringBuilder();
            detalles.append("Resultado: Equipo A ").append(golesEquipoA)
                   .append(" - ").append(golesEquipoB).append(" Equipo B\n");
            detalles.append("Ganador: ").append(equipoGanador()).append("\n");
            detalles.append("Goles por jugador:\n");
            
            for (Map.Entry<Jugador, Integer> entry : golesPorJugador.entrySet()) {
                if (entry.getValue() > 0) {
                    detalles.append("- ").append(entry.getKey().getNombre())
                           .append(": ").append(entry.getValue()).append(" goles\n");
                }
            }
            
            return detalles.toString();
        }
        
        // 1. Calcular el jugador con más goles de un partido
        public Jugador jugadorConMasGoles() {
            return golesPorJugador.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        }
        
        // Actualizar estadísticas de todos los jugadores después del partido
        public void finalizarPartido() {
            // Actualizar estadísticas de cada jugador
            for (Jugador jugador : equipoA) {
                jugador.actualizarEstadisticas(golesPorJugador.getOrDefault(jugador, 0));
            }
            for (Jugador jugador : equipoB) {
                jugador.actualizarEstadisticas(golesPorJugador.getOrDefault(jugador, 0));
            }
        }
    }
    
    // 2. Filtrar y mostrar jugadores con más de 5 goles
    public static List<Jugador> jugadoresConMasDeCincoGoles(List<Jugador> jugadores) {
        return jugadores.stream()
            .filter(jugador -> jugador.getGolesMarcados() > 5)
            .collect(Collectors.toList());
    }
    
    // Método para mostrar los jugadores con más de 5 goles
    public static void mostrarJugadoresConMasDeCincoGoles(List<Jugador> jugadores) {
        List<Jugador> jugadoresFiltered = jugadoresConMasDeCincoGoles(jugadores);
        
        if (jugadoresFiltered.isEmpty()) {
            System.out.println("No hay jugadores con más de 5 goles.");
        } else {
            System.out.println("Jugadores con más de 5 goles:");
            jugadoresFiltered.forEach(jugador -> 
                System.out.println("- " + jugador.getNombre() + ": " + jugador.getGolesMarcados() + " goles")
            );
        }
    }

    public static void main(String[] args) {
        // Crear algunos jugadores
        Jugador jugador1 = new Jugador("Messi", 1);
        Jugador jugador2 = new Jugador("Ronaldo", 2);
        Jugador jugador3 = new Jugador("Neymar", 3);
        Jugador jugador4 = new Jugador("Mbappé", 4);
        Jugador jugador5 = new Jugador("Lewandowski", 5);
        Jugador jugador6 = new Jugador("Benzema", 6);
        
        // Crear equipos
        List<Jugador> equipoA = Arrays.asList(jugador1, jugador2, jugador3);
        List<Jugador> equipoB = Arrays.asList(jugador4, jugador5, jugador6);
        
        // Simular varios partidos
        for (int i = 0; i < 3; i++) {
            System.out.println("\n----- Partido " + (i+1) + " -----");
            
            // Crear un nuevo partido
            Partido partido = new Partido(equipoA, equipoB);
            
            // Registrar algunos goles aleatoriamente
            Random random = new Random();
            for (Jugador jugador : equipoA) {
                int goles = random.nextInt(5); // 0-2 goles
                if (goles > 0) {
                    partido.registrarGol(jugador, goles);
                    System.out.println(jugador.getNombre() + " marcó " + goles + " goles");
                }
            }
            
            for (Jugador jugador : equipoB) {
                int goles = random.nextInt(5); // 0-2 goles
                if (goles > 0) {
                    partido.registrarGol(jugador, goles);
                    System.out.println(jugador.getNombre() + " marcó " + goles + " goles");
                }
            }
            
            // Mostrar detalles del partido
            System.out.println("\nDetalles del partido:");
            System.out.println(partido.detallesPartido());
            
            // Mostrar el jugador con más goles del partido
            Jugador goleador = partido.jugadorConMasGoles();
            if (goleador != null) {
                System.out.println("Jugador con más goles en este partido: " + 
                    goleador.getNombre() + " con " + partido.golesPorJugador.get(goleador) + " goles");
            } else {
                System.out.println("No hubo goles en este partido");
            }
            
            // Finalizar el partido y actualizar estadísticas
            partido.finalizarPartido();
        }
        
        // Mostrar estadísticas finales de todos los jugadores
        System.out.println("\n----- Estadísticas finales de la temporada -----");
        List<Jugador> todosLosJugadores = new ArrayList<>();
        todosLosJugadores.addAll(equipoA);
        todosLosJugadores.addAll(equipoB);
        
        for (Jugador jugador : todosLosJugadores) {
            System.out.println(jugador);
        }
        
        // Mostrar jugadores con más de 5 goles
        System.out.println("\n----- Jugadores destacados -----");
        mostrarJugadoresConMasDeCincoGoles(todosLosJugadores);
    }
}