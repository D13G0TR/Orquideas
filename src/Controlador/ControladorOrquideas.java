package Controlador;

import Modelo.BaseDeDatos;
import Modelo.Orquidea;
import Vista.VistaPrincipal;
import java.sql.ResultSet;

public class ControladorOrquideas {
    private BaseDeDatos db;
    private VistaPrincipal vista;

    public ControladorOrquideas(BaseDeDatos db, VistaPrincipal vista) {
        this.db = db;
        this.vista = vista;

        // Asignar eventos a los botones
        vista.btnGuardar.addActionListener(e -> guardarDatos());
        vista.btnMostrar.addActionListener(e -> mostrarRegistros());
    }

    // Método para guardar datos ingresados en la base de datos
    private void guardarDatos() {
        try {
            // Obtener datos desde la vista
            String nombre = vista.txtNombre.getText();
            double humedad = Double.parseDouble(vista.txtHumedad.getText());
            double temperatura = Double.parseDouble(vista.txtTemperatura.getText());

            // Crear objeto Orquidea
            Orquidea orquidea = new Orquidea(nombre, humedad, temperatura);

            // Guardar en la base de datos
            db.guardarOrquidea(orquidea.getNombre(), orquidea.getHumedad(), orquidea.getTemperatura());

            // Mostrar mensaje de éxito
            vista.mostrarMensaje("Datos guardados correctamente.");
            
            // Limpiar campos
            limpiarCampos();
        } catch (Exception e) {
            vista.mostrarMensaje("Error al guardar datos: " + e.getMessage());
        }
    }

    // Método para mostrar registros almacenados en la base de datos
    private void mostrarRegistros() {
        try {
            // Obtener registros desde la base de datos
            ResultSet rs = db.obtenerRegistros();

            // Construir una cadena con los datos obtenidos
            StringBuilder registros = new StringBuilder();
            while (rs.next()) {
                registros.append("ID: ").append(rs.getInt("id"))
                         .append(", Nombre: ").append(rs.getString("nombre"))
                         .append(", Humedad: ").append(rs.getDouble("humedad"))
                         .append(", Temperatura: ").append(rs.getDouble("temperatura"))
                         .append(", Fecha: ").append(rs.getTimestamp("fecha_registro"))
                         .append("\n");
            }

            // Mostrar los registros en el JTextArea
            vista.txtAreaRegistro.setText(registros.toString());
        } catch (Exception e) {
            vista.mostrarMensaje("Error al mostrar registros: " + e.getMessage());
        }
    }

    // Método para limpiar los campos de texto después de guardar datos
    private void limpiarCampos() {
        vista.txtNombre.setText("");
        vista.txtHumedad.setText("");
        vista.txtTemperatura.setText("");
    }
}
