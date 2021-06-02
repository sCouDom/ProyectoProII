import conexion.Conector;
import entidades.Donante;
import entidades.Enfermeria;
import interfazEntidades.DonanteI;
import interfazEntidades.EnfermeriaI;
import interfazEntidades.TransfusionI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JPanel panelRaiz;
    private JTextField textField1;
    private JButton cerrarBaseDeDatosButton;
    private JButton nuevaCitaButton;
    private JButton donacionAcabadaButton;
    static ArrayList<String> citas = new ArrayList<>();
    Enfermeria enfermeria = new Enfermeria();
    Donante donante = new Donante();

    public App(){
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Conector.conectar();
                if (Conector.conectar()==true){
                    textField1.setText("\t Conectado");
                }
                TransfusionI transfusionI = new TransfusionI();
                transfusionI.setVisible(true);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Conector.conectar();
                if (Conector.conectar()==true){
                    textField1.setText("\t Conectado");
                }
                EnfermeriaI enfermeria = new EnfermeriaI();
                enfermeria.setVisible(true);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Conector.conectar();
                if (Conector.conectar()==true){
                    textField1.setText("\t Conectado");
                }
                DonanteI donante = new DonanteI();
                donante.setVisible(true);
            }
        });

        cerrarBaseDeDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Conector.cerrar();
                textField1.setText("\t Conexion cerrada");
            }
        });

        nuevaCitaButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                donante.donacion(citas);
            }
        }));

        donacionAcabadaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(citas.size()!=0)
                    enfermeria.donacion(citas);
            }
        });


    }

    public static void main(String[] args) {
        File citasArchivo = new File("C:\\Users\\seryi\\IdeaProjects\\ProyectoProII\\src\\archivo\\citas.txt");
        try {
            Scanner sc = new Scanner(citasArchivo);
            while (sc.hasNextLine()){
                citas.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelRaiz);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(1200,600);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    FileWriter fw = new FileWriter("C:\\Users\\seryi\\IdeaProjects\\ProyectoProII\\src\\archivo\\citas.txt", false);
                    for (int i=0; i < citas.size(); i++){
                        fw.write(citas.get(i)+"\n");
                    }
                    fw.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            }
        });

    }
}
