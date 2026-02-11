package dao;

import infraestructura.JDBC;
import modelo.Alojamiento;

/**
 * DAO para la tabla alojamientos
 *
 * author BERJANO MUÑOZ, RAFAEL
 * author BOZA VILLAR, RICARDO
 * author CALIXTO DEL HOYO, JUAN
 * author GARCÍA MARCHENA, ÁLVARO
 */
public class AlojamientoDAO {

    private JDBC jdbc;

    public AlojamientoDAO(JDBC jdbc) {
        this.jdbc = jdbc;
    }

    public boolean insertar(Alojamiento a) {

        try {
            String sql =
                "INSERT INTO alojamientos " +
                "(nombre, poblacion, provincia, capacidad, tipo, ubicacion, alquilado) VALUES (" +
                "'" + a.getNombre() + "', " +
                "'" + a.getPoblacion() + "', " +
                "'" + a.getProvincia() + "', " +
                a.getCapacidad() + ", " +
                a.getTipo() + ", " +
                "'" + a.getUbicacion() + "', " +
                (a.isAlquilado() ? 1 : 0) +
                ")";

            jdbc.setSentenciaSQL(sql);
            return jdbc.ejecutar();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
