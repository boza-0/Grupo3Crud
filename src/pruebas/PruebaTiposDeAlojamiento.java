package pruebas;

import dao.TipoDeAlojamientoDAO;
import infraestructura.JDBC;
import modelo.TipoDeAlojamiento;

import java.util.List;

/**
 * 
 * author BERJANO MUÑOZ, RAFAEL
 * author BOZA VILLAR, RICARDO
 * author CALIXTO DEL HOYO, JUAN
 * author GARCÍA MARCHENA, ÁLVARO
 */
public class PruebaTiposDeAlojamiento {

    public static void main(String[] args) {

        JDBC jdbc = JDBC.getInstancia();
        jdbc.setConexion("configuracion/bdrural.properties");

        TipoDeAlojamientoDAO dao = new TipoDeAlojamientoDAO(jdbc);
        List<TipoDeAlojamiento> tiposDeAlojamiento = dao.obtenerTiposDeAlojamiento();

        if (tiposDeAlojamiento.isEmpty()) {
            System.out.println("No hay tipos de alojamiento registrados en la base de datos.");
        } else {
            for (TipoDeAlojamiento tipo : tiposDeAlojamiento) {
                System.out.println(tipo.getCodigo() + " - " + tipo.getDescripcion());
            }
        }

        jdbc.cerrarConexion();
    }
}
