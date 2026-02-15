package conexion;

import java.sql.*;
import java.io.InputStream;
import java.util.Properties;

/**
 * Componente JDBC
 *
 * author BERJANO MUÑOZ, RAFAEL
 * author BOZA VILLAR, RICARDO
 * author CALIXTO DEL HOYO, JUAN
 * author GARCÍA MARCHENA, ÁLVARO
 */
public class ConexionBD {

    private static ConexionBD instancia;

    private Connection conexion;
    private String sentenciaSQL;
    private ResultSet cursor;

    private ConexionBD() {
    }

    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public boolean setConexion(String rutaProperties) {
        try {
            Properties propiedades = new Properties();

            InputStream is = ConexionBD.class
                    .getClassLoader()
                    .getResourceAsStream(rutaProperties);

            if (is == null) {
                throw new RuntimeException(
                        "No se encuentra el fichero de propiedades: " + rutaProperties
                );
            }

            propiedades.load(is);

            String driver = propiedades.getProperty("driver");
            String url = propiedades.getProperty("url");
            String usuario = propiedades.getProperty("usuario");
            String password = propiedades.getProperty("password");

            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, password);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setSentenciaSQL(String strSQL) {
        this.sentenciaSQL = strSQL;
    }

    /**
     * Ejecuta la sentencia SQL almacenada.
     * Si es un SELECT, deja disponible el ResultSet.
     * Si es INSERT/UPDATE/DELETE, simplemente se ejecuta.
     */
    public boolean ejecutar() {
        try {
            Statement sentencia = conexion.createStatement();

            boolean tieneResultado = sentencia.execute(sentenciaSQL);

            if (tieneResultado) {
                cursor = sentencia.getResultSet();
            } else {
                cursor = null;
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getCursor() {
        return cursor;
    }

    public boolean cerrarCursor() {
        try {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
