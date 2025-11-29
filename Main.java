/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author m.mejia1
 */
import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        // ------------------------------------------------
        // 1. Instancia grande del problema
        // ------------------------------------------------
        int[] instance = IntStream.range(1, 7001).toArray();
        int replicas = 5;

        System.out.println("------------------------------------------------");
        System.out.println(" EXPERIMENTO SET PARTITION - BACKTRACKING");
        System.out.println(" IDE: [CAMBIAR]");
        System.out.println("------------------------------------------------");

        // ------------------------------------------------
        // 2. Ejecutar algoritmo con réplicas
        // ------------------------------------------------
        long avgTime = ExperimentTimer.runReplicas(() -> {
            SetPartitionBacktracking spp = new SetPartitionBacktracking();
            spp.canPartition(Arrays.copyOf(instance, instance.length));
        }, replicas);

        // ------------------------------------------------
        // 3. Resultados
        // ------------------------------------------------
        System.out.println("Instancia: (tamaño = " + instance.length + ")");
        System.out.println("Réplicas: " + replicas);
        System.out.println("Tiempo promedio (s): " + avgTime / 1_000_000_000.0);
        System.out.println("Tiempo promedio (min): " + avgTime / 60_000_000_000.0);

    }
}
