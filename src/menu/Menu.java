package menu;

import connection.ConnectionFactory;

import javax.swing.*;
import java.sql.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Menu {
    public static void main(String[] args) throws SQLException, ParseException {
        logo();
        login();
    }

    public static void logo(){
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\t\n" +
                ":: \t\t\t\t    ARTE MOTOS\t\t    \t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
    }

    public static void login() throws SQLException, ParseException {
        Scanner sc = new Scanner(System.in);
        String senha_banco = null;
        Connection con = ConnectionFactory.getConnection();
        ResultSet myResultSet;
        Statement myStatement = con.createStatement();


        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\t\n" +
                ":: \t\t     INFORME USUÁRIO E SENHA    \t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");


        System.out.print("USUÁRIO >>> ");
        String usuario = sc.nextLine();
        System.out.print("SENHA >>> ");
        String senha = sc.nextLine();
        try {
            String sql = "select senha from Usuario where login = '" + usuario + "'";
            myResultSet = myStatement.executeQuery(sql);


            while (myResultSet.next()) {
                senha_banco = myResultSet.getString(1);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no banco de dados: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
        if (senha.equals(senha_banco)) {
            System.out.println("Login realizado com sucesso");
            menuOpcao();
        }
        else{
            System.out.println("Credenciais inválidas");
        }
    }
    public static void menuOpcao() throws SQLException, ParseException {
        Scanner sc = new Scanner(System.in);

        logo();
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t\t  1 - CLIENTES\t\t\t\t\t::\n" +
                "::\t\t\t\t  2 - PEÇAS\t\t\t\t\t\t::\n" +
                "::\t\t\t\t  3 - VENDAS\t\t\t\t\t::\n" +
                "::\t\t\t\t  4 - SAIR\t\t\t\t\t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print(" Opção >>> ");
        int opcao = sc.nextInt();
        switch (opcao) {
            case 1:
                menuClientes();
                menuOpcao();
                break;
            case 2:
                menuPecas();
                menuOpcao();
                break;
            case 3:
                menuVendas();
                menuOpcao();
                break;
            case 4:
                System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                        "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                        "::\t\t\t\t\t ATÉ MAIS!          \t\t::\n" +
                        "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                        "::::::::::::::::::::::::::::::::::::::::::::::::::");
                break;
            default:
                System.out.println("Opção inválida!!!");
        }
    }


    public static void menuClientes() throws SQLException, ParseException {
        Scanner sc = new Scanner(System.in);

        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\t\t\t\t\t\n" +
                "::\t\t\tESCOLHA A OPÇÃO DESEJADA            ::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\t\t\t\t\t\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t  1 - CADASTRAR CLIENTE\t\t\t\t::\n" +
                "::\t\t\t  2 - CONSULTAR CLIENTE\t\t\t\t::\n" +
                "::\t\t\t  3 - ATUALIZAR CLIENTE             ::\n" +
                "::            4 - EXCLUIR CLIENTE\t\t\t\t::\n" +
                "::\t\t\t  5 - SAIR   \t\t\t\t\t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print(" Opção >>> ");
        int opcao = sc.nextInt();

        switch (opcao) {
            case 1:
                System.out.println("Cadastro");
                criarCliente();
                break;
            case 2:
                System.out.println("Consulta");
                consultaCliente();
                break;
            case 3:
                System.out.println("Atualiza");
                menuAtualizaCliente();
                break;
            case 4:
                System.out.println("Exclui");
                menuExcluiCliente();
                break;
            case 5:
                menuOpcao();
                break;
            default:
                System.out.println("Opção inválida!!!");
        }

    }


    public static void criarCliente() throws SQLException {
        /*
         * Cria objeto cliente e insere na base
         * busca ID do cliente para inserir como fk em Endereço, Email e Telefone.
         */
        Scanner scanner = new Scanner(System.in);
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql = "";


        try {

            sql = "\n" +
                    "INSERT INTO Cliente (nome, tipo, documento, DDD, telefone, email, rua, numero, cep, cidade, estado) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            Cliente c = new Cliente();

            System.out.println("Informe o nome completo: ");
            System.out.print(">>> ");
            c.nome = scanner.nextLine().toUpperCase();
            System.out.println("Informe o tipo Fisico ou jurídico: ");
            System.out.print(">>> ");
            c.tipo = scanner.nextLine().toUpperCase();
            System.out.println("Informe o CPF/CNPJ: ");
            System.out.print(">>> ");
            c.documento = scanner.nextLine();
            System.out.println("Informe o DDD: ");
            System.out.print(">>> ");
            c.ddd = scanner.nextLine();
            System.out.println("Informe o telefone ");
            System.out.print(">>> ");
            c.telefone = scanner.nextLine();
            System.out.println("Informe o email: ");
            System.out.print(">>> ");
            c.email = scanner.nextLine().toLowerCase();
            System.out.println("Informe a rua: ");
            System.out.print(">>> ");
            c.rua = scanner.nextLine().toUpperCase();
            System.out.println("Informe o numero: ");
            System.out.print(">>> ");
            c.numero = scanner.nextLine();
            System.out.println("Informe o CEP: ");
            System.out.print(">>> ");
            c.cep = scanner.nextLine();
            System.out.println("Informe a cidade: ");
            System.out.print(">>> ");
            c.cidade = scanner.nextLine().toUpperCase();
            System.out.println("Informe o estado: ");
            System.out.print(">>> ");
            c.estado = scanner.nextLine().toUpperCase();
            ps.setString(1, c.nome);
            ps.setString(2, c.tipo);
            ps.setString(3, c.documento);
            ps.setString(4, c.ddd);
            ps.setString(5, c.telefone);
            ps.setString(6, c.email);
            ps.setString(7, c.rua);
            ps.setString(8, c.numero);
            ps.setString(9, c.cep);
            ps.setString(10, c.cidade);
            ps.setString(11, c.estado);
            ps.executeUpdate();//executa um insert na base

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
        System.out.println("::\t\t\t INSERIDO COM SUCESSSO              ::");
    }

    public static void consultaCliente() throws SQLException {
        /**
         * Faz busca com Nome para retornar dados do cliente cliente
         */
        Scanner sc = new Scanner(System.in);
        Connection con = ConnectionFactory.getConnection();
        ResultSet myResultSet;
        Statement myStatement = con.createStatement();

        System.out.println("Informe parte do nome do cliente: ");
        System.out.print(" >>> ");
        String nome = sc.nextLine();

        try {
            String sql = "select * from Cliente where nome like '%" + nome +"%'";
            myResultSet = myStatement.executeQuery(sql);
            System.out.println("-------------------------------------------------------------------------------------------");
            while (myResultSet.next()){
                System.out.println("ID:        " + myResultSet.getInt("idCliente"));
                System.out.println("Nome:      " + myResultSet.getString("nome"));
                System.out.println("Tipo:      " + myResultSet.getString("tipo"));
                System.out.println("Documento: " + myResultSet.getString("documento"));
                System.out.println("Telefone : " + myResultSet.getString("DDD") + " " + myResultSet.getString("telefone"));
                System.out.println("Email:     " + myResultSet.getString("email"));
                System.out.println("Rua:       " + myResultSet.getString("rua"));
                System.out.println("Numero:    " + myResultSet.getString("numero"));
                System.out.println("CEP:       " + myResultSet.getString("cep"));
                System.out.println("cidade:    " + myResultSet.getString("cidade"));
                System.out.println("estado:    " + myResultSet.getString("estado"));
                System.out.println("-------------------------------------------------------------------------------------------");


            }

        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro no banco de dados: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            ConnectionFactory.closeConnection(con);
        }
    }
    public static void menuAtualizaCliente() throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t1 - INFORMAR ID CLIENTE\t\t\t\t::\n" +
                "::\t\t\t2 - CONSULTAR ID CLIENTES    \t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print("Opção >>> ");
        int op = sc.nextInt();

        switch (op){
            case 1:
                System.out.println("Informe o id");
                System.out.print(">>> ");
                int id = sc.nextInt();
                atualizaCliente(id);
                break;
            case 2:
                consultaCliente();
                System.out.println("Informe o id do cliende: ");
                id = sc.nextInt();
                atualizaCliente(id);
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }


    }

    public static void atualizaCliente(int id){
        Scanner scanner = new Scanner(System.in);
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql = "";


        try {

            sql = "UPDATE Cliente SET nome=?, tipo=?, documento=?, DDD=?, telefone=?, email=? , rua=?, numero= ?, " +
                    "cep=?, cidade=?, estado=? WHERE idCliente=" + id;
            ps = con.prepareStatement(sql);

            System.out.println("Informe o nome completo: ");
            System.out.print(">>> ");
            String nome = scanner.nextLine().toUpperCase();
            System.out.println("Informe o tipo Fisico ou jurídico: ");
            System.out.print(">>> ");
            String tipo = scanner.nextLine().toUpperCase();
            System.out.println("Informe o CPF/CNPJ: ");
            System.out.print(">>> ");
            String documento = scanner.nextLine();
            System.out.println("Informe o DDD: ");
            System.out.print(">>> ");
            String ddd = scanner.nextLine();
            System.out.println("Informe o telefone ");
            System.out.print(">>> ");
            String telefone = scanner.nextLine();
            System.out.println("Informe o email: ");
            System.out.print(">>> ");
            String email = scanner.nextLine().toLowerCase();
            System.out.println("Informe a rua: ");
            System.out.print(">>> ");
            String rua = scanner.nextLine().toUpperCase();
            System.out.println("Informe o numero: ");
            System.out.print(">>> ");
            String numero = scanner.nextLine();
            System.out.println("Informe o CEP: ");
            System.out.print(">>> ");
            String cep = scanner.nextLine();
            System.out.println("Informe a cidade: ");
            System.out.print(">>> ");
            String cidade = scanner.nextLine().toUpperCase();
            System.out.println("Informe o estado: ");
            System.out.print(">>> ");
            String estado = scanner.nextLine().toUpperCase();
            ps.setString(1, nome);
            ps.setString(2, tipo);
            ps.setString(3, documento);
            ps.setString(4, ddd);
            ps.setString(5, telefone);
            ps.setString(6, email);
            ps.setString(7, rua);
            ps.setString(8, numero);
            ps.setString(9, cep);
            ps.setString(10, cidade);
            ps.setString(11, estado);
            ps.executeUpdate();//executa um insert na base

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
        System.out.println("::\t\t\t ATUALIZADO COM SUCESSSO              ::\n");
    }

    public static void menuExcluiCliente() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String tabela = "Cliente";
        String nome_coluna = "idCliente";
        System.out.println("\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t1 - INFORMAR ID CLIENTE\t\t\t\t::\n" +
                "::\t\t\t2 - CONSULTAR ID CLIENTES    \t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print("Opção >>> ");
        int op = sc.nextInt();

        switch (op){
            case 1:
                System.out.println("Informe o id");
                System.out.print(">>> ");
                int id = sc.nextInt();
                excluiRegistro(tabela, nome_coluna,id);
                break;
            case 2:
                consultaCliente();
                System.out.println("Informe o id do cliende: ");
                System.out.print(">>> ");
                id = sc.nextInt();
                excluiRegistro(tabela, nome_coluna, id);
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }


    }

    public static void excluiRegistro(String tabela, String nome_coluna, int id){
        Scanner sc = new Scanner(System.in);
        int op;
        System.out.println("Deseja excluir permanentemente o registro? \n1 - Não \n2 - SIM");
        System.out.print("Opçao >>> ");
        op = sc.nextInt();
        switch (op){
            case 1:
                System.out.println("Operação cancelada");
                break;
            case 2:
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps;
                String sql = "";

                try {

                    sql = "DELETE FROM " + tabela + " WHERE "+ nome_coluna +" =" + id;
                    ps = con.prepareStatement(sql);
                    ps.executeUpdate();//executa um insert na base

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    ConnectionFactory.closeConnection(con);
                }
                System.out.println("::\t\t\t EXCLUÍDO COM SUCESSSO              ::\n");
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }
    }


    public static void menuPecas() throws SQLException, ParseException {
        Scanner sc = new Scanner(System.in);

        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t  1 - CADASTRAR PEÇA\t\t\t\t::\n" +
                "::\t\t\t  2 - CONSULTAR PEÇA\t\t\t\t::\n" +
                "::\t\t\t  3 - ATUALIZAR PEÇA                ::\n" +
                "::            4 - EXCLUIR PEÇA\t\t    \t\t::\n" +
                "::\t\t\t  5 - SAIR   \t\t\t\t\t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print(" Opção >>> ");
        int opcao = sc.nextInt();

        switch (opcao) {
            case 1:
                //System.out.println("Cadastro");
                criarPeca();
                break;
            case 2:
                //System.out.println("Consulta");
                consultaPeca();
                break;
            case 3:
                //System.out.println("Atualiza");
                menuAtualizaPeca();
                break;
            case 4:
                //System.out.println("Exclui");
                menuExcluiPeca();
                break;
            case 5:
                menuOpcao();
                break;
            default:
                System.out.println("Opção inválida!!!");
        }

    }

    public static void criarPeca() throws SQLException {
        /*
         * Cria objeto peça e insere na base
         */
        Scanner scanner = new Scanner(System.in);
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps;
        NumberFormat nformat = NumberFormat.getInstance(Locale.US);


        String sql = "";

        try {

            sql = "\n" +
                    "INSERT INTO Peca (nomePeca, descricao, valor) " +
                    "VALUES (?, ?, ?)";
            ps = con.prepareStatement(sql);
            Peca p = new Peca();

            System.out.println("Informe o nome da peça:");
            System.out.print(">>> ");
            p.nome = scanner.nextLine().toUpperCase();
            System.out.println("Informe a descrição da peça:");
            System.out.print(">>> ");
            p.descricao = scanner.nextLine().toUpperCase();
            System.out.println("Informe o valor da peça, utilize ponto para separar os centavos:");
            System.out.print(">>> ");
            p.valor = nformat.parse(scanner.nextLine()).floatValue();

            ps.setString(1, p.nome);
            ps.setString(2, p.descricao);
            ps.setFloat(3, p.valor);

            ps.executeUpdate();//executa um insert na base

        } catch (SQLException | ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
        System.out.println("::\t\t\t INSERIDO COM SUCESSSO              ::");
    }

    public static void consultaPeca() throws SQLException {
        /**
         * Faz busca com Nome para retornar dados do cliente cliente
         */
        Scanner sc = new Scanner(System.in);
        Connection con = ConnectionFactory.getConnection();
        ResultSet myResultSet;
        Statement myStatement = con.createStatement();

        System.out.println("Informe parte do nome da peça: ");
        System.out.print(" >>> ");
        String nome = sc.nextLine().toUpperCase();

        try {
            String sql = "select * from Peca where nomePeca like '%" + nome +"%'";
            myResultSet = myStatement.executeQuery(sql);
            System.out.println("-------------------------------------------------------------------------------------------");
            while (myResultSet.next()){
                System.out.println("ID:             " + myResultSet.getInt("idPeca"));
                System.out.println("Nome Peça:      " + myResultSet.getString("nomePeca"));
                System.out.println("Descrição:      " + myResultSet.getString("descricao"));
                System.out.println("Valor :         R$" + myResultSet.getFloat("valor"));
                System.out.println("-------------------------------------------------------------------------------------------");


            }

        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro no banco de dados: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            ConnectionFactory.closeConnection(con);
        }
    }

    public static void menuAtualizaPeca() throws SQLException, ParseException {
        Scanner sc = new Scanner(System.in);

        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t1 - INFORMAR ID PEÇA\t\t\t\t::\n" +
                "::\t\t\t2 - CONSULTAR ID PEÇAS      \t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print("Opção >>> ");
        int op = sc.nextInt();

        switch (op){
            case 1:
                System.out.println("Informe o id");
                System.out.print(">>> ");
                int id = sc.nextInt();
                atualizaPeca(id);
                break;
            case 2:
                consultaPeca();
                System.out.println("Informe o id da peça: ");
                id = sc.nextInt();
                atualizaPeca(id);
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }


    }


    public static void atualizaPeca(int id) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        Connection con = ConnectionFactory.getConnection();
        NumberFormat nformat = NumberFormat.getInstance(Locale.US);
        PreparedStatement ps;
        String sql = "";

        System.out.println("Informe o nome da peça: ");
        System.out.print(">>> ");
        String nome = scanner.nextLine().toUpperCase();
        System.out.println("Informe a descrição da peça: ");
        System.out.print(">>> ");
        String descricao = scanner.nextLine().toUpperCase();
        System.out.println("Informe o valor da peça, utilize ponto para separar os centavos: ");
        System.out.print(">>> ");
        float valor = nformat.parse(scanner.nextLine()).floatValue();

        System.out.println("Confirma a atualização? ");
        System.out.println("Peça " + nome + " Descrição " + "\n valor: R$" + valor);
        System.out.println("1 - SIM\n2 - NÃO");
        System.out.print(">>> ");
        int op = scanner.nextInt();

        switch (op){
            case 1:

                try {

                    sql = "UPDATE Peca SET nomePeca=?, descricao=?, valor=? WHERE idPeca=" + id;
                    ps = con.prepareStatement(sql);

                    ps.setString(1, nome);
                    ps.setString(2, descricao);
                    ps.setFloat(3, valor);

                    ps.executeUpdate();//executa um insert na base

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    ConnectionFactory.closeConnection(con);
                }
                System.out.println("::\t\t\t ATUALIZADO COM SUCESSSO              ::\n");
                break;
            case 2:
                System.out.println("Alteração cancelada");
                break;
            default:
                System.out.println("Opção inválida");
        }



    }


    public static void menuExcluiPeca() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String tabela = "Peca";
        String nome_coluna = "idPeca";
        System.out.println("\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t1 - INFORMAR ID PECA \t\t\t\t::\n" +
                "::\t\t\t2 - CONSULTAR ID PEÇAS    \t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print("Opção >>> ");
        int op = sc.nextInt();

        switch (op){
            case 1:
                System.out.println("Informe o id");
                System.out.print(">>> ");
                int id = sc.nextInt();
                excluiRegistro(tabela, nome_coluna ,id);
                break;
            case 2:
                consultaPeca();
                System.out.println("Informe o id da Peça: ");
                System.out.print(">>> ");
                id = sc.nextInt();
                excluiRegistro(tabela, nome_coluna, id);
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }


    }



    public static void menuVendas() throws SQLException, ParseException {
        Scanner sc = new Scanner(System.in);

        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t  1 - CADASTRAR VENDA\t\t\t\t::\n" +
                "::\t\t\t  2 - CONSULTAR VENDA\t\t\t\t::\n" +
                "::\t\t\t  3 - ATUALIZAR VENDA               ::\n" +
                "::            4 - EXCLUIR VENDA \t    \t\t::\n" +
                "::\t\t\t  5 - SAIR   \t\t\t\t\t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print(" Opção >>> ");
        int opcao = sc.nextInt();

        switch (opcao) {
            case 1:
                //System.out.println("Cadastro");
                criarVenda();
                break;
            case 2:
                //System.out.println("Consulta");
                consultaVenda();
                break;
            case 3:
                //System.out.println("Atualiza");
                menuAtualizaVenda();
                break;
            case 4:
                //System.out.println("Exclui");
                menuExcluiVenda();
                break;
            case 5:
                menuOpcao();
                break;
            default:
                System.out.println("Opção inválida!!!");
        }

    }

    public static void criarVenda() throws SQLException {
        /*
         * Cria objeto venda e insere na base
         *
         */
        Scanner scanner = new Scanner(System.in);
        Connection con = ConnectionFactory.getConnection();
        ResultSet myResultSet;
        Statement myStatement = con.createStatement();
        PreparedStatement ps;
        String sql = "";

        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t1 - INFORMAR ID CLIENTE\t\t\t\t::\n" +
                "::\t\t\t2 - CONSULTAR ID CLIENTES    \t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print(">>> ");
        int op = scanner.nextInt(), id_cliente = 0, id_peca = 0, quantidade;

        switch (op){
            case 1:
                System.out.println("Informe o ID do cliente: ");
                System.out.print(">>> ");
                id_cliente = scanner.nextInt();
                break;
            case 2:
                consultaCliente();
                System.out.println("Informe o ID do cliente:");
                System.out.print(">>> ");
                id_cliente = scanner.nextInt();
                break;
            default:
                System.out.println("Opção inválida.");
        }



        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t1 - INFORMAR ID PEÇA\t\t\t\t::\n" +
                "::\t\t\t2 - CONSULTAR ID PEÇAS      \t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print(">>> ");
        op = scanner.nextInt();
        switch (op) {
            case 1:
                System.out.println("Informe o ID da peça: ");
                System.out.print(">>> ");
                id_peca = scanner.nextInt();
                break;
            case 2:
                consultaPeca();
                System.out.println("Informe o ID da peça:");
                System.out.print(">>> ");
                id_peca = scanner.nextInt();
                break;
            default:
                System.out.println("Opção inválida.");
        }

        System.out.println("Informe a quantidade: ");
        System.out.print(">>>");
        quantidade = scanner.nextInt();

        float valor_peca = 0;

        try {
            sql = "select * from Peca where idPeca = " + id_peca;
            myResultSet = myStatement.executeQuery(sql);
            while (myResultSet.next()){
                valor_peca =  myResultSet.getFloat("valor");
            }

        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro no banco de dados: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            ConnectionFactory.closeConnection(con);
        }

        con = ConnectionFactory.getConnection();
        // System.out.println("valor peça" + valor_peca);
        float valor_total = quantidade * valor_peca;
        // System.out.println("valor total " +  valor_total);

        try {
            Venda v = new Venda();


            sql = "INSERT INTO Venda (quantidade, valorTotal, idUsuario, idPeca, idCliente) " +
                    "VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            v.quantidade = quantidade;
            v.valor_total = valor_total;
            v.idUsuario = 1;
            v.idPeca = id_peca;
            v.idCliente = id_cliente;
            //v.data = "now()";
            ps.setInt(1, v.quantidade);
            //ps.setString(2, v.data);
            ps.setFloat(2, v.valor_total);
            ps.setInt(3, v.idUsuario);
            ps.setInt(4, v.idPeca);
            ps.setInt(5, v.idCliente);
            ps.executeUpdate();//executa um insert na base

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
        System.out.println("::\t\t\t INSERIDO COM SUCESSSO              ::");

        System.out.println("Deseja emitir nota fiscal da venda?\n1 - SIM\n2 - NÃO");
        System.out.print(">>> ");
        op = scanner.nextInt();

        switch (op){
            case 1:
                int id_nota = idNotaMax();

                emiteNota(id_nota);

                break;
            case 2:
                break;
            default:
                System.out.println("Opção inválida.");
        }

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

    public static void emiteNota(int id_nota) throws SQLException {

        Connection con = ConnectionFactory.getConnection();
        ResultSet myResultSet;
        Statement myStatement = con.createStatement();

        try {
            String sql = "select idVenda, idUsuario, nome, tipo, " +
                    "documento, nomePeca, p.descricao, p.valor, quantidade, v.valorTotal, v.dataVenda  \n" +
                    "from Venda v \n" +
                    "inner join Cliente c \n" +
                    "on v.idCliente = c.idCliente \n" +
                    "inner join Peca p \n" +
                    "on v.idPeca = p.idPeca \n" +
                    "where idVenda = " + id_nota;
            myResultSet = myStatement.executeQuery(sql);


            while (myResultSet.next()) {
                  System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                        "::\t\t\t\t\t\t               \t\t\t\t\t\t\t\t\t\t\t  ::\n" +
                        "::  \t\t\t\t\t\t         ARTE MOTOS                                   ::\t\n" +
                        "::\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  ::\t\n" +
                        "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\t\t\n" +
                        "::\tEndereço: R. Ubaldino do Amaral, 63 - Alto da Glória, Curitiba - PR, 80060-195::\n" +
                        "::  \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  ::\t\t\n" +
                        "::  Telefone: (41) 1909-1909\t\t\t\t\t\t\t\t\t\t\t\t\t  ::\t\n" +
                        "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                        "::\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  ::\t\n" +
                        ":: Numero NF: "+myResultSet.getInt("IdVenda")+" | Nome: "+myResultSet.getString("nome")+" | Documento: "+myResultSet.getString("documento")+"\t\t\t\t    \t\n" +
                        ":: Nome peça: "+myResultSet.getString("nomePeca")+"                                         \t\t\t\t\t\t  \t\n" +
                        ":: Descrição peça: "+myResultSet.getString("p.descricao")+"\t\t\t\t\t\t\t\t\t\t\t\t\t  \t\n" +
                        ":: Quantidade : "+myResultSet.getInt("quantidade")+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  \t\n" +
                        ":: Valor : "+myResultSet.getFloat("p.valor")+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  \t\n" +
                        ":: data:  "+myResultSet.getDate("v.dataVenda")+"\t\t\t\t\t\t\t\t\t\tValor total: R$"+myResultSet.getFloat("v.valorTotal")+"   \n" +
                        "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");


            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no banco de dados: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }

    }

    public static void consultaVenda() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ResultSet myResultSet;
        Statement myStatement = con.createStatement();

        try {
            String sql = "select idVenda, idUsuario, nome, tipo, documento, nomePeca, p.descricao, " +
                    "p.valor, quantidade, v.valorTotal, dataVenda  \n" +
                    "from Venda v\n" +
                    "inner join Cliente c\n" +
                    "on v.idCliente = c.idCliente\n" +
                    "inner join Peca p\n" +
                    "on v.idPeca = p.idPeca";
            myResultSet = myStatement.executeQuery(sql);

            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            while (myResultSet.next()) {
                System.out.println("ID          : " + myResultSet.getInt("idVenda"));
                System.out.println("Nome        : " + myResultSet.getString("nome"));
                System.out.println("Nome Peça   : " + myResultSet.getString("nomePeca"));
                System.out.println("Quantidade  : " + myResultSet.getInt("quantidade"));
                System.out.println("Valor       : " + myResultSet.getInt("p.valor"));
                System.out.println("Valor Total : " + myResultSet.getInt("v.valorTotal"));
                System.out.println("Data venda  : " + myResultSet.getDate("dataVenda"));

                System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
    }


    public static void menuExcluiVenda() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String tabela = "Venda";
        String nome_coluna = "idVenda";
        System.out.println("\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t1 - INFORMAR ID VENDA \t\t\t\t::\n" +
                "::\t\t\t2 - CONSULTAR ID VENDA    \t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print("Opção >>> ");
        int op = sc.nextInt();

        switch (op){
            case 1:
                System.out.println("Informe o id");
                System.out.print(">>> ");
                int id = sc.nextInt();
                excluiRegistro(tabela, nome_coluna ,id);
                break;
            case 2:
                consultaVenda();
                System.out.println("Informe o id da Venda: ");
                System.out.print(">>> ");
                id = sc.nextInt();
                excluiRegistro(tabela, nome_coluna, id);
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }
    }


    public static void menuAtualizaVenda() throws SQLException, ParseException {
        Scanner sc = new Scanner(System.in);

        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t1 - INFORMAR ID VENDA\t\t\t\t::\n" +
                "::\t\t\t2 - CONSULTAR ID VENDA      \t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print("Opção >>> ");
        int op = sc.nextInt();

        switch (op){
            case 1:
                System.out.println("Informe o id");
                System.out.print(">>> ");
                int id = sc.nextInt();
                atualizaVenda(id);
                break;
            case 2:
                Menu.consultaVenda();
                System.out.println("Informe o id da venda: ");
                id = sc.nextInt();
                atualizaVenda(id);
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }


    }


    public static void atualizaVenda(int id) throws ParseException, SQLException {

        Scanner scanner = new Scanner(System.in);
        Connection con = ConnectionFactory.getConnection();
        ResultSet myResultSet;
        Statement myStatement = con.createStatement();
        PreparedStatement ps;
        String sql = "";

        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t1 - INFORMAR ID CLIENTE\t\t\t\t::\n" +
                "::\t\t\t2 - CONSULTAR ID CLIENTES    \t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print(">>> ");
        int op = scanner.nextInt(), id_cliente = 0, id_peca = 0, quantidade;

        switch (op){
            case 1:
                System.out.println("Informe o ID do cliente: ");
                System.out.print(">>> ");
                id_cliente = scanner.nextInt();
                break;
            case 2:
                Menu.consultaCliente();
                System.out.println("Informe o ID do cliente:");
                System.out.print(">>> ");
                id_cliente = scanner.nextInt();
                break;
            default:
                System.out.println("Opção inválida.");
        }



        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::\t\t\t1 - INFORMAR ID PEÇA\t\t\t\t::\n" +
                "::\t\t\t2 - CONSULTAR ID PEÇAS      \t\t::\n" +
                "::\t\t\t\t\t\t\t\t\t\t\t\t::\n" +
                "::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.print(">>> ");
        op = scanner.nextInt();
        switch (op) {
            case 1:
                System.out.println("Informe o ID da peça: ");
                System.out.print(">>> ");
                id_peca = scanner.nextInt();
                break;
            case 2:
                Menu.consultaPeca();
                System.out.println("Informe o ID da peça:");
                System.out.print(">>> ");
                id_peca = scanner.nextInt();
                break;
            default:
                System.out.println("Opção inválida.");
        }

        System.out.println("Informe a quantidade: ");
        System.out.print(">>>");
        quantidade = scanner.nextInt();

        float valor_peca = 0;



        con = ConnectionFactory.getConnection();
        // System.out.println("valor peça" + valor_peca);
        float valor_total = quantidade * valor_peca;
        // System.out.println("valor total " +  valor_total);
        int id_usuario = 1;
        try {

            sql = "UPDATE Venda SET quantidade=?, valorTotal=?, idPeca=? , idCliente=? WHERE idVenda=" + id;
            ps = con.prepareStatement(sql);

            ps.setInt(1, quantidade);
            //ps.setString(2, v.data);
            ps.setFloat(2, valor_total);
            ps.setInt(3, id_peca);
            ps.setInt(4, id_cliente);
            ps.executeUpdate();//executa um insert na base

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
        System.out.println("::\t\t\t ALTERADO COM SUCESSSO              ::");

        System.out.println("Deseja emitir nota fiscal da venda?\n1 - SIM\n2 - NÃO");
        System.out.print(">>> ");
        op = scanner.nextInt();

        switch (op){
            case 1:
                int id_nota = idNotaMax();

                emiteNota(id_nota);

                break;
            case 2:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }


}
class Cliente{
    String nome, documento, tipo, ddd, telefone, email, rua, numero, cep, cidade, estado;
}

class Peca{
    String nome, descricao;
    float valor;
}

class Venda{
    int quantidade, idUsuario, idPeca, idCliente;
    float valor_total;
}