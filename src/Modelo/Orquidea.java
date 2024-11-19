package Modelo;

public class Orquidea {
    private String nombre;
    private double humedad;
    private double temperatura;

    // Constructor
    public Orquidea(String nombre, double humedad, double temperatura) {
        this.nombre = nombre;
        this.humedad = humedad;
        this.temperatura = temperatura;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }
}
