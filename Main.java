/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author m.mejia1
 */
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // -------------------------------
        // 1. Instancias del problema (FIJAS)
        // -------------------------------
        int[] instance = {3, 1, 1, 2, 2, 1, 7, 8, 5, 6, 4, 9};
        int replicas = 5;

        System.out.println("------------------------------------------------");
        System.out.println("   EXPERIMENTO SET PARTITION - BACKTRACKING");
        System.out.println("   IDE: Registrar manualmente según el caso");
        System.out.println("------------------------------------------------");

        // -------------------------------
        // 2. Ejecutar algoritmo
        // -------------------------------
        long avgTime = ExperimentTimer.runReplicas(() -> {
            SetPartitionBacktracking spp = new SetPartitionBacktracking();
            spp.canPartition(Arrays.copyOf(instance, instance.length));
        }, replicas);

        // -------------------------------
        // 3. Imprimir resultados
        // -------------------------------
        System.out.println("Instancia: " + Arrays.toString(instance));
        System.out.println("Réplicas: " + replicas);
        System.out.println("Tiempo promedio: " + avgTime / 1_000_000.0 + " ms");
        System.out.println("Tiempo promedio (ns): " + avgTime);
        System.out.println("------------------------------------------------");
    }
}
