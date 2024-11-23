package Controlador;

import Modelo.BaseDeDatos;
import Vista.VistaPrincipal;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ControladorOrquideas {
    private BaseDeDatos db;
    private VistaPrincipal vista;

    public ControladorOrquideas(BaseDeDatos db, VistaPrincipal vista) {
        this.db = db;
        this.vista = vista;

        // Vincular eventos
        vista.btnGuardarPlanta.addActionListener(e -> guardarPlanta());
        vista.btnGuardarRiego.addActionListener(e -> programarRiego());

        configurarHorasRiego();
        
        cargarPlantasComboBox();
        
        // Cargar los datos en la tabla al iniciar
        cargarTablaPlantas();
    }

    private void guardarPlanta() {
        try {
            String nombre = vista.txtNombrePlanta.getText();

            if (nombre.isEmpty()) {
                vista.mostrarMensaje("El nombre de la planta no puede estar vacío.");
                return;
            }

            db.guardarNombrePlanta(nombre);
            vista.mostrarMensaje("Planta guardada correctamente.");
            vista.txtNombrePlanta.setText("");

            // Actualizar la tabla automáticamente después de guardar
            cargarTablaPlantas();
        } catch (Exception e) {
            vista.mostrarMensaje("Error al guardar la planta: " + e.getMessage());
        }
    }

    private void cargarTablaPlantas() {
        try {
            // Obtener los datos desde la base de datos
            var plantas = db.obtenerPlantas();

            // Modelo de la tabla
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("ID");
            modelo.addColumn("Nombre");
            modelo.addColumn("Fecha de Riego");
            modelo.addColumn("Hora de Riego");

            // Agregar filas al modelo
            for (String[] planta : plantas) {
                modelo.addRow(planta);
            }

            // Asignar el modelo a la tabla
            vista.tblPlantas.setModel(modelo);
        } catch (Exception e) {
            vista.mostrarMensaje("Error al cargar los datos: " + e.getMessage());
        }
    }

    private void programarRiego() {
        try {
            // Obtener la planta seleccionada desde el ComboBox
            String plantaSeleccionada = (String) vista.cmbPlantas.getSelectedItem();
            if (plantaSeleccionada == null) {
                vista.mostrarMensaje("Seleccione una planta.");
                return;
            }

            // Obtener el ID de la planta desde el nombre
            int plantaId = obtenerIdPlanta(plantaSeleccionada);

            // Obtener la fecha seleccionada desde el JDateChooser
            java.util.Date fechaSeleccionada = vista.calendarioRiego.getDate();
            if (fechaSeleccionada == null) {
                vista.mostrarMensaje("Seleccione una fecha.");
                return;
            }
            java.sql.Date fechaSql = new java.sql.Date(fechaSeleccionada.getTime());

            // Obtener la hora seleccionada desde el ComboBox
            String horaTexto = (String) vista.cmbHoraRiego.getSelectedItem();
            if (horaTexto == null) {
                vista.mostrarMensaje("Seleccione una hora.");
                return;
            }
            java.sql.Time horaSql = java.sql.Time.valueOf(horaTexto + ":00");

            // Guardar en la base de datos
            db.guardarRiego(plantaId, fechaSql, horaSql);
            vista.mostrarMensaje("Riego programado correctamente.");

            // Actualizar la tabla automáticamente después de guardar
            cargarTablaPlantas();
        } catch (Exception e) {
            vista.mostrarMensaje("Error al programar el riego: " + e.getMessage());
        }
    }

    private int obtenerIdPlanta(String nombrePlanta) throws Exception {
        // Consulta SQL para obtener el ID de la planta
        String query = "SELECT id FROM orquidea WHERE nombre = ?";

        // Preparar la consulta
        PreparedStatement stmt = db.getConexion().prepareStatement(query);
        stmt.setString(1, nombrePlanta);

        // Ejecutar la consulta
        ResultSet rs = stmt.executeQuery();

        // Si hay un resultado, devolver el ID
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new Exception("Planta no encontrada.");
        }
    }

    private void configurarHorasRiego() {
        vista.cmbHoraRiego.addItem("08:00");
        vista.cmbHoraRiego.addItem("08:30");
        vista.cmbHoraRiego.addItem("09:00");
        vista.cmbHoraRiego.addItem("09:30");
        vista.cmbHoraRiego.addItem("10:00");
        vista.cmbHoraRiego.addItem("10:30");
        vista.cmbHoraRiego.addItem("11:00");
        vista.cmbHoraRiego.addItem("11:30");
        vista.cmbHoraRiego.addItem("12:00");
        vista.cmbHoraRiego.addItem("12:30");
        vista.cmbHoraRiego.addItem("13:00");
        vista.cmbHoraRiego.addItem("13:30");
        vista.cmbHoraRiego.addItem("14:00");
        vista.cmbHoraRiego.addItem("14:30");
        vista.cmbHoraRiego.addItem("15:00");
        vista.cmbHoraRiego.addItem("15:30");
        vista.cmbHoraRiego.addItem("16:00");
        vista.cmbHoraRiego.addItem("16:30");
        vista.cmbHoraRiego.addItem("17:00");
        vista.cmbHoraRiego.addItem("17:30");
        vista.cmbHoraRiego.addItem("18:00");
    }

    private void cargarPlantasComboBox() {
        try {
            // Obtener la lista de plantas desde la base de datos
            var plantas = db.obtenerPlantas();

            // Limpiar el ComboBox antes de agregar nuevos elementos
            vista.cmbPlantas.removeAllItems();

            // Llenar el ComboBox con los nombres de las plantas
            for (String[] planta : plantas) {
                vista.cmbPlantas.addItem(planta[1]); // Agrega solo el nombre de la planta
            }
        } catch (Exception e) {
            vista.mostrarMensaje("Error al cargar las plantas: " + e.getMessage());
        }
    }
}
