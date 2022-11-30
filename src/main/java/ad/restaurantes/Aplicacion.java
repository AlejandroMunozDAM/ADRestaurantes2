package ad.restaurantes;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Aplicacion {

    public static void main(String[] args) throws Exception {
        Aplicacion aplicacion = new Aplicacion();
        try (final AccesoRestaurantes mongo = new AccesoMongo(); final AccesoRestaurantes sqlite = new AccesoSqlite()) {
            aplicacion.run(mongo, sqlite);
        }
    }

    // Inyección de dependencias (parte del concepto "inversión de control" (IoC)
    public void run(AccesoRestaurantes connRestaurantes, AccesoRestaurantes connElegidos) {

        List<String> tiposCocina = connRestaurantes.getTiposCocina();
        System.out.println("Tipos de cocina disponibles: " + tiposCocina);

        System.out.println("¿Qué tipo quieres?");
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
        String tipoCocinaElegido = sc.nextLine();
        System.out.println("¿Cuántos restaurantes quieres?");
        int cantidadRestaurantes = sc.nextInt();

        List<Restaurante> restaurantes = connRestaurantes.getRestaurantes(tipoCocinaElegido, cantidadRestaurantes);
        System.out.printf("Se han encontrado %d restaurantes.\n", restaurantes.size());

//        connElegidos.alta(restaurantes);
//        System.out.println("Alta correcta.");
    }

}
