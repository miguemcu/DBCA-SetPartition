/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author m.mejia1
 */
public class ExperimentTimer {

    public static long measureExecutionTime(Runnable algorithm) {
        long start = System.nanoTime();
        algorithm.run();
        long end = System.nanoTime();
        return end - start;  // Tiempo en nanosegundos
    }

    public static long runReplicas(Runnable algorithm, int replicas) {
        long total = 0;
        for (int i = 0; i < replicas; i++) {
            total += measureExecutionTime(algorithm);
        }
        return total / replicas;  // Retorna el promedio
    }
}

