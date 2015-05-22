package proxectobd;

import java.sql.*;
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
            System.out.println("Error"+ex);
        }

    }

    public void print() {
        try {
            estado = con.createStatement();
            JOptionPane.showMessageDialog(null, "Introduce valores");
            int cod = Integer.parseInt(JOptionPane.showInputDialog("Introduce un int identificador"));
            String nome = JOptionPane.showInputDialog("Introduce un nome");
            String apelido = JOptionPane.showInputDialog("Introduce un apelido");
            estado.executeUpdate("insert into alumnos values(" + cod + ",'" + nome + "','" + apelido + "')");
            ResultSet resultado = estado.executeQuery("select * from alumnos");
            System.out.println("cod \t nome \t apelido \t");

            while (resultado.next()) {
                System.out.println(resultado.getString("cod") + "\t" + resultado.getString("nome") + "\t" + resultado.getString("apelido"));
            }
            resultado.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }

}
