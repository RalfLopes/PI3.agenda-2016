/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.blacksytem.agenda;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
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

    static InputStreamReader ir = new InputStreamReader(System.in);
    static BufferedReader in = new BufferedReader(ir);

    //Metodo principal.
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {

        opcaoAgenda();

    }

    //Estabelece a conexão com o banco.
    private static Connection obterConexao() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        // Passo 1: Registrar driver JDBC.
        Class.forName("org.apache.derby.jdbc.ClientDataSource");

        // Passo 2: Abrir a conexÃ£o
        conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/agendabd; SecurityMechanism=3",
                "app", // usuario
                "app"); // senha
        return conn;
    }

    //Metodo criado pelo professor, lista o banco na saida.
    public static void listarPessoas() {

        Statement stmt = null;
        Connection conn = null;

        String sql = "SELECT ID_CONTATO, NM_CONTATO, DT_NASCIMENTO, VL_TELEFONE, VL_EMAIL FROM TB_CONTATO";
        try {

            conn = obterConexao();
            stmt = conn.createStatement();

            ResultSet resultados = stmt.executeQuery(sql);

            DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");

            while (resultados.next()) {
                Long id = resultados.getLong("ID_CONTATO");
                String nome = resultados.getString("NM_CONTATO");
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

    //Metodo que insere os dados no banco, Erro quando pede os dados, pois o software não espera a entrada de dados para continuar
    public static void inserirPessoas() throws IOException {

        Statement stmt = null;
        PreparedStatement stm = null;
        Connection conn = null;

        Contato contato = pedirDados();

        //Cria um novo objeto Contato.
        try {

            String sql = "INSERT INTO TB_CONTATO ( NM_CONTATO, DT_NASCIMENTO, VL_TELEFONE, VL_EMAIL) values(?,?,?,?, CURRENT_TIMESTAMP)";
            conn = obterConexao();

            //Insere os dados no banco.
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

    //Incompleto!!!!!!
    public static void alterarPessoas(int idPessoa, String pessoa) {

        Statement stmt = null;
        PreparedStatement stm = null;
        Connection conn = null;

        String sql = "UPDATE PESSOAS WHERE ID_PESSOA ='" + idPessoa + "'";
        try {
            conn = obterConexao();
            stm.setString(1, pessoa);

        } catch (SQLException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void deletarPessoa(long idPessoa) throws SQLException, ClassNotFoundException {

        Statement stmt = null;
        PreparedStatement stm = null;
        Connection conn = null;

        try {
            conn = obterConexao();
            String sql = "delete from TB_CONTATO where ID_CONTATO = ?";
            stm = conn.prepareStatement(sql);
            stm.setLong(1, idPessoa);

            stm.execute();

            stm.close();

            System.out.println("Usuário excluido com sucesso!! ");
        } catch (SQLException e) {
            System.out.println("Não foi possivel a conexão com banco de dados" + e.getMessage());

        } catch (NullPointerException e) {
            System.out.println("Dados não inicializados" + e.getMessage());
        }

    }

    public static void opcaoAgenda() throws IOException, ClassNotFoundException, SQLException {

        System.out.println("========================================\n 1 - Listar"
                + "\n 2- Inserir \n 3- Alterar \n 4- Deletar \n 5- Sair"
                + "\n======================================");
        int opcao = Integer.parseInt(in.readLine());
        switch (opcao) {

            case 1:
                listarPessoas();
                break;
            case 2:
                inserirPessoas();
                break;
            case 3:

                //alterarPessoas(2);
                break;
            case 4:
                Scanner leia = new Scanner(System.in);
                long idPessoa = leia.nextInt();
                System.out.println("Entre com o ID a ser excluído");

                deletarPessoa(idPessoa);
                break;

            case 5:
                System.exit(opcao);
            default:

                opcaoAgenda();
        }

    }

    public static Contato pedirDados() throws IOException {

        DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");

        //Solicita os dados ao usuario.
        System.out.println("Insira nome do contato");
        String nome = in.readLine();
        //Tem alguma função que força o codigo a esperar a entrada, mas eu não lembro qual é!
        System.out.println("Insira Data de Nascimento");
        String data = in.readLine();
        //Tem alguma função que força o codigo a esperar a entrada, mas eu não lembro qual é!
        System.out.println("Insira Telefone");
        String telefone = in.readLine();
        //Tem alguma função que força o codigo a esperar a entrada, mas eu não lembro qual é!
        System.out.println("Insira E-mail");
        String email = in.readLine();
        //Tem alguma função que força o codigo a esperar a entrada, mas eu não lembro qual é!

        Contato dados = new Contato(nome, data, telefone, email);

        return dados;
    }

}
