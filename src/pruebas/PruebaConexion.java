package pruebas;

import conexion.ConexionBD;

/**
 * 
 * author BERJANO MUÑOZ, RAFAEL
 * author BOZA VILLAR, RICARDO
 * author CALIXTO DEL HOYO, JUAN
 * author GARCÍA MARCHENA, ÁLVARO
 */
public class PruebaConexion {

    public static void main(String[] args) {

        ConexionBD jdbc = ConexionBD.getInstancia();

        boolean conectado = jdbc.setConexion("configuracion/bdrural.properties");

        if (conectado) {
            System.out.println("Conexión establecida correctamente");
            jdbc.cerrarConexion();
        } else {
            System.out.println("Error en la conexión");
        }
    }
}
