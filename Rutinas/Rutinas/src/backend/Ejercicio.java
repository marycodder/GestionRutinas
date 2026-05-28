package backend;

public class Ejercicio {

    private int codigo;
    private String nombre;
    private String tipo;
    private String intensidad;
    private int tiempo;
    private String descripcion;
    private int ultimaSemanaUsado;

    public Ejercicio(int codigo, String nombre, String tipo, String intensidad, int tiempo, String descripcion, int ultimaSemanaUsado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.intensidad = intensidad;
        this.tiempo = tiempo;
        this.descripcion = descripcion;
        this.ultimaSemanaUsado = ultimaSemanaUsado;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getIntensidad() {
        return intensidad;
    }

    public int getTiempo() {
        return tiempo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getUltimaSemanaUsado() {
        return ultimaSemanaUsado;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " - " + tipo + " - " + intensidad + " - " + tiempo + " min";
    }
}