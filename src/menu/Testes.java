package menu;


import connection.ConnectionFactory;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.ConnectionFactory;


public class Testes {
    public static void main(String[] args) throws SQLException {
        int id = idNotaMax();
        System.out.println(id);

    }
    public static int idNotaMax() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ResultSet myResultSet;
        Statement myStatement = con.createStatement();
        int id_nota = 0;


        try {
            String sql = "SELECT MAX(idVenda) as idVenda FROM Venda";
            myResultSet = myStatement.executeQuery(sql);


            while (myResultSet.next()) {
                id_nota = myResultSet.getInt("idVenda");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
        return id_nota;
    }
}
