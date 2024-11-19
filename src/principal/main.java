package principal;

import Controlador.ControladorOrquideas;
import Modelo.BaseDeDatos;
import Vista.VistaPrincipal;

public class main {
    public static void main(String[] args) {
        try {
            BaseDeDatos db = new BaseDeDatos();
            db.conectar();

            VistaPrincipal vista = new VistaPrincipal();
            ControladorOrquideas controlador = new ControladorOrquideas(db, vista);

            vista.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
