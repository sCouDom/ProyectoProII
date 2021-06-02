package entidades;

import archivo.Citas;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Enfermeria extends Persona{
    int idEnfermeria;
    String nombreEnfermeria;

    public int getIdEnfermeria() {
        return idEnfermeria;
    }

    public void setIdEnfermeria(int idEnfermeria) {
        this.idEnfermeria = idEnfermeria;
    }

    public String getNombreEnfermeria() {
        return nombreEnfermeria;
    }

    public void setNombreEnfermeria(String nombreEnfermeria) {
        this.nombreEnfermeria = nombreEnfermeria;
    }

    public void donacion(ArrayList<String> citas){
        Citas.eliminarCita(citas);
    }
}
