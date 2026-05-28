package frontend;

import backend.RutinaService;
import javax.swing.*;
import java.awt.*;


public class VentanaPrincipal extends JFrame {

    private RutinaService backend;
    private JButton btCargarBD;
    private JButton btGenerarRutina;
    private JTextArea textArea;
    private JLabel titulo;
    private JButton btAnterior;
    private JButton btSiguiente;
    private int indiceEjercicioActual;

    public VentanaPrincipal(RutinaService backend) {
        super("Sistema de Rutinas");

        this.backend = backend;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        configurarInterfazGrafica();

        setVisible(true);
    }

    private void configurarInterfazGrafica() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        titulo = new JLabel("Sistema de Gestión de Rutinas");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);

        btCargarBD = new JButton("Cargar ejercicios desde SQLite");
        btCargarBD.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btCargarBD);

        btGenerarRutina = new JButton("Generar rutina");
        btGenerarRutina.setAlignmentX(Component.CENTER_ALIGNMENT);
        btGenerarRutina.setEnabled(false);
        panel.add(btGenerarRutina);

        btAnterior = new JButton("Anterior");
        btAnterior.setAlignmentX(Component.CENTER_ALIGNMENT);
        btAnterior.setEnabled(false);
        panel.add(btAnterior);

        btSiguiente = new JButton("Siguiente");
        btSiguiente.setAlignmentX(Component.CENTER_ALIGNMENT);
        btSiguiente.setEnabled(false);
        panel.add(btSiguiente);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);

        setContentPane(panel);

        btCargarBD.addActionListener(e -> {
            cargarEjercicios();
        });

        btGenerarRutina.addActionListener(e -> {
            generarRutina();
        });

        btAnterior.addActionListener(e -> {
            ejercicioAnterior();
        });

        btSiguiente.addActionListener(e -> {
            ejercicioSiguiente();
        });

    }

    private void cargarEjercicios() {
        try {
            backend.cargarEjerciciosDesdeBD();

            String texto = "";

            texto += "Ejercicios cargados correctamente desde SQLite.\n\n";
            texto += "Cantidad total: " + backend.getCantidadTotalEjercicios() + "\n";
            texto += "Tiempo total disponible: " + backend.getTiempoTotalDisponible() + " minutos\n\n";

            texto += "Cantidad por tipo:\n";
            texto += "Cardiovascular: " + backend.contarPorTipo("Cardiovascular") + "\n";
            texto += "Fuerza: " + backend.contarPorTipo("Fuerza") + "\n\n";

            texto += "Cantidad por intensidad:\n";
            texto += "Básico: " + backend.contarPorIntensidad("Básico") + "\n";
            texto += "Intermedio: " + backend.contarPorIntensidad("Intermedio") + "\n";
            texto += "Avanzado: " + backend.contarPorIntensidad("Avanzado") + "\n";
            texto += "Alto rendimiento: " + backend.contarPorIntensidad("Alto rendimiento") + "\n\n";

            texto += "Ya puede generar una rutina.";

            textArea.setText(texto);
            btGenerarRutina.setEnabled(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar ejercicios: " + ex.getMessage());
        }
    }

    private void generarRutina() {
        try {
            String cantidadCardioTexto = JOptionPane.showInputDialog(this, "Ingrese cantidad de ejercicios cardiovasculares:");
            String cantidadFuerzaTexto = JOptionPane.showInputDialog(this, "Ingrese cantidad de ejercicios de fuerza:");


            String intensidadCardio = JOptionPane.showInputDialog(this, "Ingrese intensidad para cardio: Básico, Intermedio, Avanzado o Alto rendimiento");
            String intensidadFuerza = JOptionPane.showInputDialog(this, "Ingrese intensidad para fuerza: Básico, Intermedio, Avanzado o Alto rendimiento");

            String semanaTexto = JOptionPane.showInputDialog(this, "Ingrese semana actual:");

            int cantidadCardio = Integer.parseInt(cantidadCardioTexto);
            int cantidadFuerza = Integer.parseInt(cantidadFuerzaTexto);
            int semanaActual = Integer.parseInt(semanaTexto);

            backend.generarRutina(cantidadCardio, cantidadFuerza, intensidadCardio, intensidadFuerza, semanaActual);

            indiceEjercicioActual = 0;
            mostrarEjercicioActual();
            btAnterior.setEnabled(false);
            btSiguiente.setEnabled(true);
            return;

            //String texto = "";

            //texto += "Rutina generada correctamente:\n\n";

            //for (var ejercicio : backend.getRutinaGenerada()) {
                //texto += ejercicio.toString() + "\n";
            //}

            //texto += "\nResumen de rutina:\n";
            //texto += "Cardiovascular: " + backend.contarRutinaPorTipo("Cardiovascular") + "\n";
            //texto += "Fuerza: " + backend.contarRutinaPorTipo("Fuerza") + "\n";
            //texto += "Básico: " + backend.contarRutinaPorIntensidad("Básico") + "\n";
            //texto += "Intermedio: " + backend.contarRutinaPorIntensidad("Intermedio") + "\n";
            //texto += "Avanzado: " + backend.contarRutinaPorIntensidad("Avanzado") + "\n";
            //texto += "Alto rendimiento: " + backend.contarRutinaPorIntensidad("Alto rendimiento") + "\n";
            //texto += "Tiempo total rutina: " + backend.getTiempoTotalRutina() + " minutos\n";

            //textArea.setText(texto);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Debe ingresar números válidos en las cantidades y semana.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar rutina: " + ex.getMessage());
        }
    }


    private void mostrarEjercicioActual(){
        if (backend.getRutinaGenerada().isEmpty()) {
            textArea.setText("No hay rutina generada.");
            return;
        }

        var ejercicio = backend.getRutinaGenerada().get(indiceEjercicioActual);

        String texto = "";

        texto += "Revisión de rutina\n\n";
        texto += "Ejercicio " + (indiceEjercicioActual + 1) + " de " + backend.getRutinaGenerada().size() + "\n\n";
        texto += "Nombre: " + ejercicio.getNombre() + "\n";
        texto += "Tipo: " + ejercicio.getTipo() + "\n";
        texto += "Intensidad: " + ejercicio.getIntensidad() + "\n";
        texto += "Tiempo estimado: " + ejercicio.getTiempo() + " minutos\n\n";
        texto += "Descripción:\n" + ejercicio.getDescripcion();

        textArea.setText(texto);

        btAnterior.setEnabled(indiceEjercicioActual > 0);

        if (indiceEjercicioActual == backend.getRutinaGenerada().size() - 1) {
            btSiguiente.setText("Resumen de la rutina");
        } else {
            btSiguiente.setText("Siguiente");
        }
    }

    private void ejercicioAnterior() {
        if (indiceEjercicioActual > 0) {
            indiceEjercicioActual--;
            mostrarEjercicioActual();
        }
    }

    private void ejercicioSiguiente() {
        if (indiceEjercicioActual < backend.getRutinaGenerada().size() - 1) {
            indiceEjercicioActual++;
            mostrarEjercicioActual();
        } else {
            mostrarResumenRutina();
        }
    }

    private void mostrarResumenRutina() {
        String texto = "";

        texto += "Resumen de la rutina\n\n";

        texto += "Cantidad por tipo:\n";
        texto += "Cardiovascular: " + backend.contarRutinaPorTipo("Cardiovascular") + "\n";
        texto += "Fuerza: " + backend.contarRutinaPorTipo("Fuerza") + "\n\n";

        texto += "Cantidad por intensidad:\n";
        texto += "Básico: " + backend.contarRutinaPorIntensidad("Básico") + "\n";
        texto += "Intermedio: " + backend.contarRutinaPorIntensidad("Intermedio") + "\n";
        texto += "Avanzado: " + backend.contarRutinaPorIntensidad("Avanzado") + "\n";
        texto += "Alto rendimiento: " + backend.contarRutinaPorIntensidad("Alto rendimiento") + "\n\n";

        texto += "Tiempo total estimado: " + backend.getTiempoTotalRutina() + " minutos\n";

        textArea.setText(texto);

        btAnterior.setEnabled(true);
        btSiguiente.setEnabled(false);
    }
}