package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class RutinaService {
    private ArrayList<Ejercicio> ejercicios;
    private ArrayList<Ejercicio> rutinaGenerada;

    public RutinaService() {
        this.ejercicios = new ArrayList<>();
        this.rutinaGenerada = new ArrayList<>();
    }

    public void cargarEjerciciosCSV(String rutaArchivo) throws Exception {
        ejercicios.clear();

        BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));

        String linea = reader.readLine();

        while ((linea = reader.readLine()) != null) {

            if (linea.trim().isEmpty()) {
                continue;
            }

            String[] datos = linea.split(";");

            if (datos.length != 7) {
                reader.close();
                throw new Exception("Formato incorrecto en línea: " + linea);
            }

            int codigo = Integer.parseInt(datos[0]);
            String nombre = datos[1];
            String tipo = datos[2];
            String intensidad = datos[3];
            int tiempo = Integer.parseInt(datos[4]);
            String descripcion = datos[5];
            int ultimaSemana = Integer.parseInt(datos[6]);

            Ejercicio ejercicio = new Ejercicio(codigo, nombre, tipo, intensidad, tiempo, descripcion, ultimaSemana);

            ejercicios.add(ejercicio);
        }

        reader.close();
    }

    public ArrayList<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public int getCantidadTotalEjercicios() {
        return ejercicios.size();
    }

    public int getTiempoTotalDisponible() {
        int total = 0;

        for (Ejercicio ejercicio : ejercicios) {
            total = total + ejercicio.getTiempo();
        }

        return total;
    }

    public int contarPorTipo(String tipo) {
        int contador = 0;

        for (Ejercicio ejercicio : ejercicios) {
            if (ejercicio.getTipo().equalsIgnoreCase(tipo)) {
                contador++;
            }
        }

        return contador;
    }

    public int contarPorIntensidad(String intensidad) {
        int contador = 0;

        for (Ejercicio ejercicio : ejercicios) {
            if (ejercicio.getIntensidad().equalsIgnoreCase(intensidad)) {
                contador++;
            }
        }

        return contador;
    }

    public void guardarEjerciciosEnBD() throws Exception {
        GestorEjerciciosBD gestorBD = new GestorEjerciciosBD();

        gestorBD.crearTabla();
        gestorBD.eliminarDatos();

        for (Ejercicio ejercicio : ejercicios) {
            gestorBD.insertarEjercicio(ejercicio);
        }
    }

    public void cargarEjerciciosDesdeBD() throws Exception {
        GestorEjerciciosBD gestorBD = new GestorEjerciciosBD();

        gestorBD.crearTabla();

        this.ejercicios = gestorBD.cargarEjercicios();
    }


    public void generarRutina(int cantidadCardio, int cantidadFuerza, String intensidadCardio, String intensidadFuerza, int semanaActual) throws Exception {
        rutinaGenerada.clear();

        int contadorCardio = 0;
        int contadorFuerza = 0;

        for (Ejercicio ejercicio : ejercicios) {

            boolean noFueUsadoSemanaAnterior = ejercicio.getUltimaSemanaUsado() == 0 ||
            ejercicio.getUltimaSemanaUsado() != semanaActual -1;

            if (ejercicio.getTipo().equalsIgnoreCase("Cardiovascular")
                    && ejercicio.getIntensidad().equalsIgnoreCase(intensidadCardio)
                    && noFueUsadoSemanaAnterior
                    && contadorCardio < cantidadCardio) {

                rutinaGenerada.add(ejercicio);
                contadorCardio++;
            }

            if (ejercicio.getTipo().equalsIgnoreCase("Fuerza")
                    && ejercicio.getIntensidad().equalsIgnoreCase(intensidadFuerza)
                    && noFueUsadoSemanaAnterior
                    && contadorFuerza < cantidadFuerza) {

                rutinaGenerada.add(ejercicio);
                contadorFuerza++;
            }
        }

        if (contadorCardio < cantidadCardio || contadorFuerza < cantidadFuerza) {
            throw new Exception("No existen suficientes ejercicios disponibles para generar la rutina solicitada.");
        }
    }

    public ArrayList<Ejercicio> getRutinaGenerada() {
        return rutinaGenerada;
    }

    public int getTiempoTotalRutina(){
        int total = 0;
            for (Ejercicio ejercicio : rutinaGenerada) {
                total = total + ejercicio.getTiempo();
            }

            return total;
    }

    public int contarRutinaPorTipo(String tipo){
        int contador = 0;
        for (Ejercicio ejercicio : rutinaGenerada) {
            if (ejercicio.getTipo().equalsIgnoreCase(tipo)) {
                contador++;
            }
        }
        return contador;
    }

    public int contarRutinaPorIntensidad(String intensidad){
        int contador = 0;
        for (Ejercicio ejercicio : rutinaGenerada) {
            if (ejercicio.getIntensidad().equalsIgnoreCase(intensidad)) {
                contador++;
            }
        }
        return contador;
    }


}
