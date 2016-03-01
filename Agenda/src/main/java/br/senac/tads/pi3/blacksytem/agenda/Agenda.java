/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.blacksytem.agenda;
  import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rafael Mello
 */
public class Agenda {
    
 static Scanner leia = new Scanner(System.in); 

    private Connection obterConexao() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        // Passo 1: Registrar driver JDBC.
        Class.forName("org.apache.derby.jdbc.ClientDataSource");

        // Passo 2: Abrir a conexÃ£o
        conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/agendabd;SecurityMechanism=3",
                "app", // usuario
                "app"); // senha
        return conn;
    }

    public void listarPessoas() {
        Statement stmt = null;
        Connection conn = null;

        String sql = "SELECT ID_PESSOA, NM_PESSOA, DT_NASCIMENTO, VL_TELEFONE, VL_EMAIL FROM TB_PESSOA";
        try {
            conn = obterConexao();
            stmt = conn.createStatement();
            ResultSet resultados = stmt.executeQuery(sql);

            DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");

            while (resultados.next()) {
                Long id = resultados.getLong("ID_PESSOA");
                String nome = resultados.getString("NM_PESSOA");
                Date dataNasc = resultados.getDate("DT_NASCIMENTO");
                String email = resultados.getString("VL_EMAIL");
                String telefone = resultados.getString("VL_TELEFONE");
                System.out.println(String.valueOf(id) + ", " + nome + ", " + formatadorData.format(dataNasc) + ", " + email + ", " + telefone);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void inserirPessoas(){
        
        Statement stmt = null;
        PreparedStatement stm = null;
        Connection conn = null;
        
        System.out.println("Insira nome do contato");
        String nome = leia.next();
        System.out.println("Insira Data de Nascimento");
        String data = leia.next();
        System.out.println("Insira Telefone");
        String telefone = leia.next();
        System.out.println("Insira E-mail");
        String email = leia.next();
        
        Contato contato = new Contato(nome, data, telefone, email);
        
        try {
            String sql = "INSERT INTO TB_PESSOA ( NM_PESSOA, DT_NASCIMENTO, VL_TELEFONE, VL_EMAIL), values(?,?,?,?)";
            conn= obterConexao();
        
            stm = conn.prepareStatement(sql);
            stm.setString(1, contato.getNM_PESSOA());
            stm.setString(2, contato.getDT_NASCIMENTO());
            stm.setString(3, contato.getTELEFONE());
            stm.setString(4, contato.getVL_EMAIL());    
            
            stm.executeUpdate();
            System.out.println("Cadastro realizado com Sucesso");
            JOptionPane.showMessageDialog(null, "Cadastro realizado com Sucesso");
            stm.close();
            conn.close();
            
            
        } catch (SQLException ex) {
            System.out.println("Erro no cadastro.");
            System.out.println(ex.getMessage());
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Dado não inserido.");
        }
        
    }

}
    

