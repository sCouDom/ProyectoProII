package archivo;

import javax.swing.*;
import java.util.ArrayList;

public class Citas {
    public static void añadirCita(ArrayList<String> citas, String peticion){
        citas.add(JOptionPane.showInputDialog(peticion));
    }
    public static void eliminarCita(ArrayList<String> citas){
        citas.remove(0);
    }
}
