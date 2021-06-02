package interfazEntidades;

import conexion.Conector;
import entidades.Enfermeria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

public class EnfermeriaI extends JFrame{
    private JPanel panelRaiz;
    private JTextField textField1;
    private JTextField textField2;
    private JButton modificarPersonalButton;
    private JButton nuevoPersonalButton;
    private JButton eliminarPersonalButton;
    private JTable table1;
    private DefaultTableModel modelo;

        public EnfermeriaI(){
            JFrame frame = new JFrame("Donantes");
            frame.setContentPane(panelRaiz);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900,900);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

            modelo = new DefaultTableModel();
            modelo.addColumn("colIdEnfermeria");
            modelo.addColumn("colNombreEnfermeria");
            modelo.addRow(new Object[] {
                    "<html><div style='font-size: 10px;'><b>ID Enfermeria</b></div></html>",
                    "<html><div style='font-size: 10px;'><b>Nombre Enfermeria</b></div></html>",
            });
            table1.setModel(modelo);
            mostrarDatos();

            nuevoPersonalButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Enfermeria t = recuperarDatos();
                    String insert = String.format("insert into Enfermeria(idEnfermeria,nombreEnfermeria) " +
                            "VALUES ('%s','%s')", t.getIdEnfermeria(), t.getNombreEnfermeria());
                    Conector.sentenciaSql(insert);
                    mostrarDatos();
                    textField2.setText("");
                    textField1.setText("");
                }
            });

            eliminarPersonalButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Enfermeria t = recuperarDatos();
                    String delete = String.format("DELETE FROM Enfermeria WHERE idEnfermeria = %d ", t.getIdEnfermeria());
                    Conector.sentenciaSql(delete);
                    mostrarDatos();
                }
            });

            modificarPersonalButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Enfermeria t = recuperarDatos();
                    String edit = String.format("UPDATE Enfermeria SET nombreEnfermeria='%s' WHERE idEnfermeria =%s", t.getNombreEnfermeria(), t.getIdEnfermeria());
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

        public Enfermeria recuperarDatos() {
            Enfermeria t = new Enfermeria();
            t.setIdEnfermeria((textField1.getText().isEmpty()) ? 0 : Integer.parseInt(textField1.getText()));
            t.setNombreEnfermeria(textField2.getText());
            return t;
        }
}
