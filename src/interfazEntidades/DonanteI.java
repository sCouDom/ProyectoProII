package interfazEntidades;

import conexion.Conector;
import entidades.Donante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

public class DonanteI extends JFrame{
    private JPanel panelRaiz;
    private JTextField textField1;
    private JTextField textField2;
    private JTable table1;
    private JButton nuevoDonanteButton;
    private JButton modificarButton;
    private JButton borrarButton;
    DefaultTableModel modelo;

    public DonanteI(){
        JFrame frame = new JFrame("Donantes");
        frame.setContentPane(panelRaiz);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,900);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        modelo = new DefaultTableModel();
        modelo.addColumn("colIdDonante");
        modelo.addColumn("colNombreDonante");
        modelo.addRow(new Object[] {
                "<html><div style='font-size: 10px;'><b>ID Donante</b></div></html>",
                "<html><div style='font-size: 10px;'><b>Nombre Donante</b></div></html>",
        });
        table1.setModel(modelo);
        mostrarDatos();

        nuevoDonanteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Donante t = recuperarDatos();
                String insert = String.format("insert into Donantes(idDonante,nombreDonante) " +
                        "VALUES ('%s','%s')", t.getIdDonante(), t.getNombreDonante());
                Conector.sentenciaSql(insert);
                mostrarDatos();
                textField2.setText("");
                textField1.setText("");
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Donante t = recuperarDatos();
                String delete = String.format("DELETE FROM Donantes WHERE idDonante = %d ", t.getIdDonante());
                Conector.sentenciaSql(delete);
                mostrarDatos();
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Donante t = recuperarDatos();
                String edit = String.format("UPDATE Donantes SET nombreDonante='%s' WHERE idDonante =%s", t.getNombreDonante(), t.getIdDonante());
                Conector.sentenciaSql(edit);
                textField2.setText("");
                textField1.setText("");
                mostrarDatos();
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    if (mouseEvent.getClickCount() == 1) {
                        JTable receptor = (JTable) mouseEvent.getSource();
                        textField1.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 0).toString());
                        textField2.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 1).toString());
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }

            }
        });
    }

    public void mostrarDatos() {
        while (modelo.getRowCount() > 1) {
            modelo.removeRow(1);
        }

        try {
            ResultSet resultado = Conector.consulta("SELECT * FROM Donantes");
            while (resultado.next()) {

                Object[] oDonante = {resultado.getString("idDonante"), resultado.getString("nombreDonante")};
                modelo.addRow(oDonante);
            }

        } catch (Exception e) {
            System.out.println("Error en la consulta" + e);

        }
    }

    public Donante recuperarDatos() {
        Donante d = new Donante();
        d.setIdDonante((textField1.getText().isEmpty()) ? 0 : Integer.parseInt(textField1.getText()));
        d.setNombreDonante(textField2.getText());
        return d;
    }

}
