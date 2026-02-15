package controlador;

import dao.AlojamientoDAO;
import dao.TipoDeAlojamientoDAO;
import conexion.ConexionBD;
import modelo.Alojamiento;
import modelo.TipoDeAlojamiento;
import vista.VentanaAlojamientosAlta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * author BERJANO MUÑOZ, RAFAEL
 * author BOZA VILLAR, RICARDO
 * author CALIXTO DEL HOYO, JUAN
 * author GARCÍA MARCHENA, ÁLVARO
 */
public class ControladorAlojamientos {

    private VentanaAlojamientosAlta vista;
    private ConexionBD jdbc;
    private TipoDeAlojamientoDAO tipoDAO;

    public ControladorAlojamientos(VentanaAlojamientosAlta vista) {
        this.vista = vista;
        this.jdbc = ConexionBD.getInstancia();
        this.tipoDAO = new TipoDeAlojamientoDAO(jdbc);

        cargarTiposDeAlojamiento();
        inicializarEstado();
        inicializarEventos();
    }
    
    @SuppressWarnings("unchecked")
    private void cargarTiposDeAlojamiento() {
        vista.getTipoAlojamientoComboBox().removeAllItems();
        for (TipoDeAlojamiento tipo : tipoDAO.obtenerTiposDeAlojamiento()) {
            vista.getTipoAlojamientoComboBox().addItem(tipo);
        }
        vista.getTipoAlojamientoComboBox().setSelectedIndex(-1);
    }

    private void inicializarEstado() {

        vista.getNombreTxt().setEnabled(false);
        vista.getPoblacionTxt().setEnabled(false);
        vista.getProvinciaTxt().setEnabled(false);
        vista.getTipoAlojamientoComboBox().setEnabled(false);
        vista.getEnPoblacionRadioButton().setEnabled(false);
        vista.getAisladoRadioButton().setEnabled(false);
        vista.getCapacidadTxt().setEnabled(false);

        vista.getNuevoButton().setVisible(true);
        vista.getSalirButton().setVisible(true);
        vista.getAceptarButton().setVisible(false);
        vista.getCancelarButton().setVisible(false);

        vista.getMensajeLabel().setText(" ");
    }

    private void inicializarEventos() {

        vista.getNuevoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activarEdicion();
            }
        });

        vista.getAceptarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aceptarAlta();
            }
        });

        vista.getCancelarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
                inicializarEstado();
            }
        });

        vista.getSalirButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jdbc.cerrarConexion();
                System.exit(0);
            }
        });
    }

    private void activarEdicion() {

        vista.getNombreTxt().setEnabled(true);
        vista.getPoblacionTxt().setEnabled(true);
        vista.getProvinciaTxt().setEnabled(true);
        vista.getTipoAlojamientoComboBox().setEnabled(true);
        vista.getEnPoblacionRadioButton().setEnabled(true);
        vista.getAisladoRadioButton().setEnabled(true);
        vista.getCapacidadTxt().setEnabled(true);

        vista.getNuevoButton().setVisible(false);
        vista.getSalirButton().setVisible(false);
        vista.getAceptarButton().setVisible(true);
        vista.getCancelarButton().setVisible(true);

    }

    private void aceptarAlta() {

        String nombre = vista.getNombreTxt().getText().trim();
        String poblacion = vista.getPoblacionTxt().getText().trim();
        String provincia = vista.getProvinciaTxt().getText().trim();
        String capacidadTxt = vista.getCapacidadTxt().getText().trim();
        
        @SuppressWarnings("unchecked")
        TipoDeAlojamiento tipo =
                (TipoDeAlojamiento) vista.getTipoAlojamientoComboBox().getSelectedItem();

        if (nombre.isEmpty() || poblacion.isEmpty() || provincia.isEmpty() || capacidadTxt.isEmpty()) {
            vista.getMensajeLabel().setText("Todos los campos son obligatorios");
            return;
        }

        if (tipo == null) {
            vista.getMensajeLabel().setText("Debe seleccionar un tipo de alojamiento");
            return;
        }

        int capacidad;
        try {
            capacidad = Integer.parseInt(capacidadTxt);
        } catch (NumberFormatException e) {
            vista.getMensajeLabel().setText("Capacidad debe ser un número");
            return;
        }

        if (capacidad <= 0) {
            vista.getMensajeLabel().setText("Capacidad debe ser mayor que 0");
            return;
        }

        String ubicacion;
        if (vista.getEnPoblacionRadioButton().isSelected()) {
            ubicacion = "En población";
        } else if (vista.getAisladoRadioButton().isSelected()) {
            ubicacion = "Aislado";
        } else {
            vista.getMensajeLabel().setText("Debe seleccionar una ubicación");
            return;
        }

        Alojamiento a = new Alojamiento();
        a.setNombre(nombre);
        a.setPoblacion(poblacion);
        a.setProvincia(provincia);
        a.setCapacidad(capacidad);
        a.setTipo(tipo.getCodigo());
        a.setUbicacion(ubicacion);
        a.setAlquilado(false);

        AlojamientoDAO dao = new AlojamientoDAO(jdbc);

        if (!dao.insertar(a)) {
            vista.getMensajeLabel().setText("Error al insertar el alojamiento");
            return;
        }

        vista.getMensajeLabel().setText("Registro añadido");
        vista.getAceptarButton().setEnabled(false);
        vista.getCancelarButton().setEnabled(false);
        javax.swing.Timer timer = new javax.swing.Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
                inicializarEstado();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void limpiarFormulario() {
        vista.getNombreTxt().setText("");
        vista.getPoblacionTxt().setText("");
        vista.getProvinciaTxt().setText("");
        vista.getCapacidadTxt().setText("");
        vista.getTipoAlojamientoComboBox().setSelectedIndex(-1);
        vista.getUbicacionGrupo().clearSelection();
        vista.getMensajeLabel().setText(" ");
    }
}
