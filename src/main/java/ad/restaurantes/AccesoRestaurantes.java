package ad.restaurantes;

import java.util.List;

public interface AccesoRestaurantes extends AutoCloseable {
    List<String> getTiposCocina();

    List<Restaurante> getRestaurantes(String tipoCocinaElegido, int cantidadRestaurantes);

    void alta(List<Restaurante> restaurantes) throws Exception;
}
