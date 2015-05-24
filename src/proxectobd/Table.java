package proxectobd;


import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static proxectobd.Conexion.con;
import static proxectobd.Conexion.estado;

public final class Table extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    JButton select, delete, insert;
    DefaultTableModel dtm;

    public Table() {
        super("Base de datos");
        //se crea la Tabla
        //array bidimencional de objetos con los datos de la tabla
        Object[][] data = null;
        //array de String's con los tÌtulos de las columnas
        String[] columnNames = {"cod", "nome", "apelido"};
        dtm = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(dtm);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        //Creamos un JscrollPane y le agregamos la JTable
        JScrollPane scrollPane = new JScrollPane(table);
        //Agregamos el JScrollPane y botones al contenedor 
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(botones(), BorderLayout.SOUTH);
        pack();
        setVisible(true);
        //manejamos la salida
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);

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
            int i = 0;
            if (dtm.getRowCount() > 0) {
                do {
                    dtm.removeRow(i);
                } while (dtm.getRowCount() > 0);
            }
            try {

                Conexion.con();
                estado = con.createStatement();
                ResultSet resultado = estado.executeQuery("select * from alumnos");
                while (resultado.next()) {
                    Object[] novaFila = {resultado.getString("cod"), resultado.getString("nome"), resultado.getString("apelido")};
                    dtm.addRow(novaFila);

                }
                estado.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("error" + ex);
            }
        }
        if (e.getSource().equals(delete)) {
            try {
                Conexion.con();
                estado = con.createStatement();
                int cod = Integer.parseInt(JOptionPane.showInputDialog("Introduce un int identificador de la fila que quiere borrar"));
                estado.executeUpdate("delete from alumnos where cod=" + cod);
                estado.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("error" + ex);
            }
        }
        if (e.getSource().equals(insert)) {
            Conexion.insertar();

        }
    }

}
