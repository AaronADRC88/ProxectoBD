package proxectobd;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Conexion {

    static Connection con = null;
    static Statement estado;

    public Conexion() {
    }

    public static void con() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/FirstBD", "root", "");
            System.out.println("dentro");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error" + ex);
        }

    }

    public static void insertar() {
        try {
            Conexion.con();
            estado = con.createStatement();
            int cod = Integer.parseInt(JOptionPane.showInputDialog("Introduce un int identificador"));
            String nome = JOptionPane.showInputDialog("Introduce un nome");
            String apelido = JOptionPane.showInputDialog("Introduce un apelido");
            estado.executeUpdate("insert into alumnos values(" + cod + ",'" + nome + "','" + apelido + "')");
            estado.close();
            con.close();
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Clave primaria en uso", "ERROR", JOptionPane.WARNING_MESSAGE);
            insertar();

        } catch (SQLException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void borrar() {
        try {
            Conexion.con();
            estado = con.createStatement();
            int cod = Integer.parseInt(JOptionPane.showInputDialog("Introduce el int identificador de la fila que quiere borrar"));
            estado.executeUpdate("delete from alumnos where cod=" + cod);
            estado.close();
            con.close();
        } catch (java.lang.NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Introduce un numero entero", "ERROR", JOptionPane.WARNING_MESSAGE);
            borrar();
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }
    }

}
