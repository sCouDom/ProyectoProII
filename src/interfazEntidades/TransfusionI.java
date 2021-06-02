package interfazEntidades;

import conexion.Conector;
import entidades.Transfusion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

public class TransfusionI extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JButton consultarConsultaButton;
    private JButton nuevaConsultaButton;
    private JButton cambiarConsultaButton;
    private JButton deshacerConsultaButton;
    private JTable table1;
    private JTextField textField3;
    private JPanel panelRaiz;
    DefaultTableModel modelo;

    public TransfusionI(){
        JFrame frame = new JFrame("Transfusiones");
        frame.setContentPane(panelRaiz);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,900);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        modelo = new DefaultTableModel();
        modelo.addColumn("colEnfermero");
        modelo.addColumn("colDonante");
        modelo.addColumn("colFecha");
        modelo.addRow(new Object[] {
                "<html><div style='font-size: 10px;'><b>ID Enfermeria</b></div></html>",
                "<html><div style='font-size: 10px;'><b>ID Donante</b></div></html>",
                "<html><div style='font-size: 10px;'><b>Fecha</b></div></html>"
        });
        table1.setModel(modelo);
        mostrarDatos();


        consultarConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Integer idPeticion = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduce el ID del donante"));
                try {
                    ResultSet resultado = Conector.consulta("SELECT * FROM Transfusion where idDonante=" + idPeticion);
                    while (resultado.next()) {
                        if (idPeticion.toString().equals(resultado.getString("idDonante"))) {
                            JOptionPane.showMessageDialog(null, resultado.getString("idEnfermero"));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error en la consulta" + e);

                }
            }
        });

        nuevaConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Transfusion transfusion = recuperarDatos();
                String insert = String.format("insert into Transfusion(idDonante,idEnfermero,fecha) " +
                        "VALUES ('%s','%s','%s')", transfusion.getIdEnfermero(), transfusion.getIdDonante(), transfusion.getFecha());
                Conector.sentenciaSql(insert);
                mostrarDatos();
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
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
                        textField3.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 2).toString());
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }

            }
        });

        deshacerConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Transfusion transfusion = recuperarDatos();
                String delete = String.format("DELETE FROM Transfusion WHERE idDonante = %d ", transfusion.getIdDonante());
                Conector.sentenciaSql(delete);
                mostrarDatos();
            }
        });

        cambiarConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Transfusion transfusion = recuperarDatos();
                String edit = String.format("UPDATE Transfusion SET fecha='%s' WHERE idEnfermero =%s", transfusion.getFecha(), transfusion.getIdEnfermero());
                Conector.sentenciaSql(edit);
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                mostrarDatos();
            }
        });


    }


    public void mostrarDatos() {
        while (modelo.getRowCount() > 1) {
            modelo.removeRow(1);
        }

        try {
            ResultSet resultado = Conector.consulta("SELECT * FROM Transfusion");
            while (resultado.next()) {

                Object[] oConsulta = {resultado.getString("idEnfermero"), resultado.getString("idDonante"), resultado.getString("fecha")};
                modelo.addRow(oConsulta);
            }

        } catch (Exception e) {
            System.out.println("Error en la consulta" + e);

        }
    }

    public Transfusion recuperarDatos() {
        Transfusion consulta = new Transfusion();
        consulta.setIdDonante((textField2.getText().isEmpty()) ? 0 : Integer.parseInt(textField2.getText()));
        consulta.setIdEnfermero(Integer.parseInt(textField1.getText()));
        consulta.setFecha(textField3.getText());
        return consulta;
    }

}
