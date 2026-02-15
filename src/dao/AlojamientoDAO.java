package dao;

import conexion.JDBC;
import modelo.Alojamiento;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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

        String sql =
            "INSERT INTO alojamientos " +
            "(nombre, poblacion, provincia, capacidad, tipo, ubicacion, alquilado) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps =
                 jdbc.getConexion().prepareStatement(sql)) {

            ps.setString(1, a.getNombre());
            ps.setString(2, a.getPoblacion());
            ps.setString(3, a.getProvincia());
            ps.setInt(4, a.getCapacidad());
            ps.setInt(5, a.getTipo());
            ps.setString(6, a.getUbicacion().name());
            ps.setBoolean(7, a.isAlquilado());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
