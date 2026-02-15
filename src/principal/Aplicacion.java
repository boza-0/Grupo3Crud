package principal;

import controlador.ControladorAlojamientos;
import conexion.ConexionBD;
import vista.VentanaAlojamientosAlta;

/**
 *
 * author BERJANO MUÑOZ, RAFAEL
 * author BOZA VILLAR, RICARDO
 * author CALIXTO DEL HOYO, JUAN
 * author GARCÍA MARCHENA, ÁLVARO
 */
public class Aplicacion {

    public static void main(String[] args) {

        ConexionBD jdbc = ConexionBD.getInstancia();
        jdbc.setConexion("configuracion/bdrural.properties");

        VentanaAlojamientosAlta vista = new VentanaAlojamientosAlta();
        new ControladorAlojamientos(vista);

        vista.setVisible(true);
    }
}
