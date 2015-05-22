package proxectobd;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static proxectobd.Conexion.estado;

public class Table extends JFrame implements ActionListener {

    JButton select, delete, insert;
    DefaultTableModel dtm;

    public Table() {
        super("Base de datos");
        //array bidimencional de objetos con los datos de la tabla

        Object[][] data = null;
        //array de String's con los tÌtulos de las columnas
        String[] columnNames = {"cod", "nome", "apelido"};
        //se crea la Tabla
        dtm = new DefaultTableModel(data, columnNames);
        final JTable table = new JTable(dtm);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        //Creamos un JscrollPane y le agregamos la JTable
        JScrollPane scrollPane = new JScrollPane(table);
        //Agregamos el JScrollPane al contenedor y botones
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(botones(), BorderLayout.SOUTH);
        pack();
        setVisible(true);
        //manejamos la salida
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    if (!Conexion.estado.isClosed()) {
                        Conexion.estado.close();
                    } else {
                        System.exit(0);
                    }

                    if (!Conexion.con.isClosed()) {
                        Conexion.con.close();
                    } else {
                        System.exit(0);
                    }
                    System.exit(0);
                } catch (SQLException ex) {
                    System.out.println("error" + ex);
                }
            }
        });
    }

    public JPanel botones() {
        JPanel panel = new JPanel();
        select = new JButton("select");
        delete = new JButton("delete");
        insert = new JButton("insert");
        panel.add(select);
        select.addActionListener(this);
        panel.add(delete);
        delete.addActionListener(this);
        panel.add(insert);
        insert.addActionListener(this);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(select)) {
            /**if (dtm.getRowCount() > 0) {
                for (int i = 0; i < dtm.getRowCount(); i++) {
                    dtm.removeRow(i);
                }
            }**/
            try {
                Conexion.con();
                Conexion.estado = Conexion.con.createStatement();
                ResultSet resultado = Conexion.estado.executeQuery("select * from alumnos");
                while (resultado.next()) {
                    Object[] novaFila = {resultado.getString("cod"), resultado.getString("nome"), resultado.getString("apelido")};
                    dtm.addRow(novaFila);
                }
            } catch (SQLException ex) {
                System.out.println("error" + ex);
            }
        }
        if (e.getSource().equals(delete)) {

        }
        if (e.getSource().equals(insert)) {
            try {
                Conexion.con();
                Conexion.estado = Conexion.con.createStatement();
                int cod = Integer.parseInt(JOptionPane.showInputDialog("Introduce un int identificador"));
                String nome = JOptionPane.showInputDialog("Introduce un nome");
                String apelido = JOptionPane.showInputDialog("Introduce un apelido");
                estado.executeUpdate("insert into alumnos values(" + cod + ",'" + nome + "','" + apelido + "')");
            } catch (SQLException ex) {
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
