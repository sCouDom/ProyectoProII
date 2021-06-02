package entidades;

import archivo.Citas;

import javax.swing.*;
import java.util.ArrayList;

public class Donante extends Persona{
    int idDonante;
    String nombreDonante;

    public String getNombreDonante() {
        return nombreDonante;
    }

    public void setNombreDonante(String nombreDonante) {
        this.nombreDonante = nombreDonante;
    }

    public int getIdDonante() {
        return idDonante;
    }

    public void setIdDonante(int idDonante) {
        this.idDonante = idDonante;
    }

    public void donacion(ArrayList<String> citas){
        Citas.añadirCita(citas, "Introducir ID Donante");
    }
}
