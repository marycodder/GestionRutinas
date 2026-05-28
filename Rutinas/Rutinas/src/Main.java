import backend.RutinaService;
import frontend.VentanaPrincipal;

public class Main{
    public static void main(String[] args) {
        RutinaService backend = new RutinaService();
        VentanaPrincipal ventana = new VentanaPrincipal(backend);
        ventana.setVisible(true);
    }
}