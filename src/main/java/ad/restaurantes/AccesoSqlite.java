package ad.restaurantes;

import java.sql.*;
import java.util.List;

public class AccesoSqlite implements AccesoRestaurantes {

    private final Connection conn;
    private final PreparedStatement psAlta;

    public AccesoSqlite() throws SQLException {
        // conectar
        // quitar autocommit
        // preparar sentencia
        // crear la tabla si no existe
        conn = DriverManager.getConnection("","rest","deamu");
        conn.setAutoCommit(false);
        psAlta = conn.prepareStatement("insert into restaurantes (name,street,zipcode,cuisine,borough) values (?,?,?,?,?)");
        conn.prepareStatement("CREATE TABLE IF NOT EXIST restaurantes ()");
    }

    @Override
    public List<String> getTiposCocina() {
        return null;
    }

    @Override
    public List<Restaurante> getRestaurantes(String tipoCocinaElegido, int cantidadRestaurantes) {
        return null;
    }

    @Override
    public void alta(List<Restaurante> restaurantes) throws Exception {
        // bucle
        //    dar de alta cada uno por separado
        // commit
        for (Restaurante rest :
                restaurantes) {
            try {
                psAlta.setString(1,rest.name());
                psAlta.setString(2,rest.street());
                psAlta.setString(3,rest.zipcode());
                psAlta.setString(4,rest.cuisine());
                psAlta.setString(5,rest.borough());
                psAlta.execute();
            } catch (SQLException e) {
                conn.rollback();
                throw new Exception("Fallo al dar de alta restaurantes.");
            }
        }
        conn.commit();
    }

    @Override
    public void close() throws Exception {
        conn.close();
    }
}
