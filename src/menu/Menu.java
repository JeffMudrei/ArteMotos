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
    public static void main(String[] args) throws SQLException {
        cabecalho();
        menuOpcao();
    }
    public static void menuOpcao() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcao;


        System.out.println("#        Escolha uma opção:         #");
        System.out.println("#        1 - Consultar Cliente      #");
        System.out.println("#        2 - Cadastrar Cliente      #");
        System.out.println("#        3 - Cadastrar Moto         #");
        System.out.println("#        4 - Cadastrar Servicos     #");
        System.out.println("#        5 - Cadastrar peças        #");
        System.out.println("#        6 - Sair                   #");
        System.out.print("             Opção >>> ");
        opcao = sc.nextInt();

        switch(opcao) {
            case 1:
                break;
            case 2:
                System.out.println("#        Cadastrar cliente          #");
                createCliente();
            case 3:
                String nome, documento;
                Scanner scanner = new Scanner(System.in);
                System.out.println("#        Cadastrar moto             #");
                System.out.println();
                System.out.println("Informe o nome do cliente");
                System.out.print(">>> ");
                nome = scanner.nextLine().toUpperCase();
                System.out.println("Informe o numero do documento (somente numeros) ");
                System.out.print(">>> ");
                documento = scanner.nextLine();

                createMoto(nome, documento);
            case 4:
                System.out.println("#         Cadastrar Servico         #");
                createServico();

            case 5:
                System.out.println("#         Cadastrar peça            #");
                createPeca();
            case 6:
                System.out.println("#            Até mais!!             #");
                break;

            default:
                System.out.println("Opção inválida!!!");
                menuOpcao();
        }

    }
    public static void cabecalho(){
        System.out.println("#####################################");
        System.out.println("#            ARTE  MOTOS            #");
        System.out.println("#####################################");

    }

    public static void createCliente() throws SQLException {
        /*
         * Cria objeto cliente e insere na base
         * busca ID do cliente para inserir como fk em Endereço, Email e Telefone.
         */
        Scanner scanner = new Scanner(System.in);
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql = "", documento = "", nome = "" ;


        try {

            sql = "INSERT INTO  CLIENTE (NMCLIENTE, TIPOCLIENTE, `CPF/CNPJ`) VALUES (?,?,?)";
            ps = con.prepareStatement(sql);
            Cliente c = new Cliente();

            System.out.println("Informe o nome completo: ");
            System.out.print(">>> ");
            c.nome = scanner.nextLine().toUpperCase();
            nome = c.nome;
            System.out.println("Informe o nome tipo Fisico ou jurídico: ");
            System.out.print(">>> ");
            c.tipo = scanner.nextLine().toUpperCase();
            System.out.println("Informe o nome CPF/CNPJ: ");
            System.out.print(">>> ");
            c.cpf_cnpj = scanner.nextLine();
            documento = c.cpf_cnpj;
            ps.setString(1, c.nome);
            ps.setString(2, c.tipo);
            ps.setString(3, c.cpf_cnpj);
            ps.executeUpdate();//executa um insert na base

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }

        System.out.println();

        int id = selectIdCliente(nome, documento);
        createEndereco(id);
        createEmail(id);
        createTelefone(id);
        System.out.println("Incluir Moto? \n1 - SIM\n2-NAO");
        System.out.print(">>> ");
        int op = scanner.nextInt();
        switch (op){
            case 1:
                createMoto(nome, documento);

            case 2:
                break;
            default:
                System.out.println("Opçao inválida: ");
                break;
        }

        System.out.println("Incluir serviço? \n1 - SIM\n2-NAO");
        System.out.print(">>> ");
        op = scanner.nextInt();
        switch (op){
            case 1:
                createServico();
            case 2:
                menuOpcao();
            default:
                System.out.println("Opçao inválida: ");
                menuOpcao();
        }

    }
    public static void createEndereco(int id){
        /*
         * Cria endereço, usa PK de Cliente como FK (parametro id) */
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql = "";

        try {

            sql = "INSERT INTO ENDERECO (CEP, NMRUA, NRCASA, COMPLEMENTO, ESTADO, CIDADE, BAIRRO, CLIENTE_IDCLIENTE)\n" +
                    "   VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            Scanner scanner = new Scanner(System.in);
            Endereco end = new Endereco();

            System.out.println("Informe o CEP (somente numeros): ");
            System.out.print(" >>> ");
            end.cep = Integer.parseInt(scanner.nextLine());
            System.out.println("Informe o nome da rua: ");
            System.out.print(" >>> ");
            end.rua = scanner.nextLine().toUpperCase();
            System.out.println("Informe o numero (somente numeros): ");
            System.out.print(" >>> ");
            end.numero = Integer.parseInt(scanner.nextLine());
            System.out.println("Informe o Complemento: ");
            System.out.print(" >>> ");
            end.complemento = scanner.nextLine().toUpperCase();
            System.out.println("Informe o estado : ");
            System.out.print(" >>> ");
            end.estado = scanner.nextLine().toUpperCase();
            System.out.println("Informe a cidade: ");
            System.out.print(" >>> ");
            end.cidade = scanner.nextLine().toUpperCase();
            System.out.println("Informe o bairro:");
            System.out.print(" >>> ");
            end.bairro = scanner.nextLine().toUpperCase();
            end.id_cliente = id;

            ps.setInt(1, end.cep);
            ps.setString(2,end.rua);
            ps.setInt(3, end.numero);
            ps.setString(4, end.complemento);
            ps.setString(5, end.estado);
            ps.setString(6, end.cidade);
            ps.setString(7, end.bairro);
            ps.setInt(8, end.id_cliente);
            ps.executeUpdate();//executa um insert na base

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
        System.out.println("Inserido com sucesso...");
        System.out.println();
    }
    public static void createEmail(int id){ //id é pk de cliente

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql = "";


        try {

            sql = "insert into EMAIL (EMAIL, CLIENTE_IDCLIENTE) values (?, ?)";
            ps = con.prepareStatement(sql);
            Scanner scanner = new Scanner(System.in);
            Email email = new Email();

            System.out.println("Informe o e-mail para contato: ");
            System.out.print(" >>> ");
            email.email = scanner.nextLine().toLowerCase();
            email.id_cliente = id;

            ps.setString(1, email.email);
            ps.setInt(2, email.id_cliente);
            ps.executeUpdate();//executa um insert na base

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
    }

    public static void createTelefone(int id){ // id é pk de cliente

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql = "";


        try {

            sql = "insert into TELEFONE (DDD, TELEFONE, CLIENTE_IDCLIENTE) values (?, ?, ?)";
            ps = con.prepareStatement(sql);
            Scanner scanner = new Scanner(System.in);
            Telefone telefone = new Telefone();

            System.out.println("Informe o DDD: ");
            System.out.print(" >>> ");
            telefone.ddd = scanner.nextLine();
            System.out.println("Informe o telefone: ");
            System.out.print(" >>> ");
            telefone.telefone = scanner.nextLine();
            telefone.id_cliente = id;


            ps.setString(1, telefone.ddd);
            ps.setString(2, telefone.telefone);
            ps.setInt(3, telefone.id_cliente);
            ps.executeUpdate();//executa um insert na base

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }

    }

    public static void createMoto(String nome, String documento) throws SQLException {
        /**
         * Usa nome e documento para retornar ID do cliente e inserir como FK em MOTO
         */
        Scanner scanner = new Scanner(System.in);
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        int id;

        id = pesquisaIdCliente(nome, documento);

        try {

            Moto moto = new Moto();
            sql = "insert into MOTO_CLIENTE (MARCA, MODELO, COR, COMBUSTIVEL, ID_CLIENTE) values (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);

            System.out.println("Informe a marca: ");
            System.out.print(" >>> ");
            moto.marca = scanner.nextLine();
            System.out.println("Informe o modelo:");
            System.out.print(" >>> ");
            moto.modelo = scanner.nextLine();
            System.out.println("Informe a cor: ");
            System.out.print(" >>> ");
            moto.cor = scanner.nextLine();
            System.out.println("Informe o combustivel: ");
            System.out.print(" >>> ");
            moto.combustivel = scanner.nextLine();
            moto.id_cliente = id;


            ps.setString(1, moto.marca);
            ps.setString(2, moto.modelo);
            ps.setString(3, moto.cor);
            ps.setString(4, moto.combustivel);
            ps.setInt(5, moto.id_cliente);
            ps.executeUpdate();//executa um insert na base

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }

    }

    public static void createPeca() throws SQLException {
        /**
         * Cria ojeto peça.
         */
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql = "";


        try {

            sql = "INSERT INTO  CLIENTE (NMCLIENTE, TIPOCLIENTE, `CPF/CNPJ`) VALUES (?,?,?)";
            ps = con.prepareStatement(sql);
            Scanner scanner = new Scanner(System.in);
            Peca p = new Peca();


            System.out.println("Informe o nome da peca: ");
            System.out.print(">>> ");
            p.nome = scanner.nextLine().toUpperCase();
            System.out.println("Informe o valor da peca (use ponto e não virgula)");
            System.out.print(">>> ");
            p.valor = Float.parseFloat(scanner.nextLine());
            System.out.println("A descrição do item ");
            System.out.print(">>> ");
            p.descricao = scanner.nextLine().toUpperCase();


            ps.setString(1, p.nome);
            ps.setFloat(2, p.valor);
            ps.setString(3, p.descricao);

            ps.executeUpdate();//executa um insert na base

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
        System.out.println("Inserido com sucesso...");
        System.out.println();
        menuOpcao();

    }

    public static int pesquisaIdCliente(String nome, String documento) throws SQLException {
        /**
         * Faz busca com Nome e Documento para retornar o ID do cliente
         */
        int id = 0;
        Connection con = ConnectionFactory.getConnection();
        ResultSet myResultSet;
        Statement myStatement = con.createStatement();

        try {
            String sql = "select IDCLIENTE from CLIENTE WHERE NMCLIENTE = '" + nome  +"' and `CPF/CNPJ` = '" + documento + "'";
            myResultSet = myStatement.executeQuery(sql);


            while (myResultSet.next()){
                id = myResultSet.getInt(1);
            }

        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro no banco de dados: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            ConnectionFactory.closeConnection(con);
        }
        return id;
    }


    public static void createServico() throws SQLException {
        /**
         * Cria serviço e da opção de incluir ID do cliente ou buscar pelo nome e documento
         * Pergunta se foram usadas peças para o serviço e inclui o registro na tabela PECA_SERVICO
         */
        Connection con = ConnectionFactory.getConnection();
        Scanner sc = new Scanner(System.in);
        PreparedStatement ps;
        String sql, nome, documento, op, nome_servico = null, descricao_servico = null;
        int id = 0;


        System.out.println("Possui o ID do cliente? \nDigite SIM para informar o ID\n " +
                "Digite NAO para informar o nome e documento" +
                "\n SAIR para sair:");
        System.out.print(">>> ");
        op = sc.nextLine().toUpperCase();
        if (op.equals("SAIR")){
            menuOpcao();
        }
        if (op.equals("SIM")){
            System.out.println("Informe o ID");
            System.out.print(">>> ");
            id = sc.nextInt();

        }
        else if (op.equals("NAO")){
            System.out.println("Informe o Nome");
            System.out.print(">>> ");
            nome = sc.nextLine().toUpperCase();
            System.out.println("Informe o numero do documento (somente numeros)");
            System.out.print(">>> ");
            documento = sc.nextLine();
            id = pesquisaIdCliente(nome, documento);

        }
        else{
            System.out.println("Opçao inválida");
            createServico();
        }


        try {

            sql = "insert into SERVICO (NMSERVICO, VALOR, DESCRICAO, IDCLIENTE) values(?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            Scanner scanner = new Scanner(System.in);
            NumberFormat nformat = NumberFormat.getInstance(Locale.US);
            Servico servico = new Servico();


            System.out.println("Informe o nome do servico");
            System.out.print(">>> ");
            servico.nome = scanner.nextLine().toUpperCase();
            nome_servico = servico.nome;
            System.out.println("Informe valor do servico utilize ponto para separar os centavos: ");
            System.out.print(">>> ");
            servico.valor = nformat.parse(scanner.nextLine()).floatValue();
            System.out.println("Informe a descrição do servico");
            System.out.print(">>> ");
            servico.descricao = scanner.nextLine().toUpperCase();
            descricao_servico = servico.descricao;
            servico.id_ciente = id;


            ps.setString(1, servico.nome);
            ps.setFloat(2, servico.valor);
            ps.setString(3, servico.descricao);
            ps.setInt(4, servico.id_ciente);
            ps.executeUpdate();//executa um insert na base


            //System.out.println("Deseja incluir peças no serviço?");

        } catch (SQLException | ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Foram utilizadas peças no serviço? \n 1 - SIM \n 2 - NAO");
        System.out.print(">>> ");
        int opcao = scanner.nextInt();
        switch (opcao){
            case 1:
                createPecaServico(nome_servico, descricao_servico, id);

                System.out.println("Deseja incluir outras peças para o mesmo serviço?\nSim - 1\nNao - 2");
                System.out.print(">>> ");
                int op_incluir = scanner.nextInt();
                while(op_incluir == 1){
                    createPecaServico(nome_servico, descricao_servico, id);
                    System.out.println("Deseja incluir outras peças para o mesmo serviço?\nSim - 1\nNao - 2");
                    System.out.print(">>> ");
                    op_incluir = scanner.nextInt();

                }
                break;
            default:
                System.out.println("Opçao inválida");
        }

        System.out.println("Inserido com sucesso...");
        System.out.println();


    }
    public static void createPecaServico(String nome_servico, String descricao_servico, int id) throws SQLException {
        PecaServico pecaServico = new PecaServico();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe o nome da peca: ");
        System.out.print(">>> ");
        String nome_peca = scanner.nextLine();
        int id_peca = selectIdPeca(nome_peca);
        int id_servico = selectIdServico(nome_servico, descricao_servico, id);
        System.out.println("Informe a quantidade de " + nome_peca);
        System.out.print(">>>");
        int quantidade = scanner.nextInt();


        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql = "";


        try {

            sql = "INSERT INTO PECA_SERVICO (PECA_IDPECA, SERVICO_IDSERVICO, QUANTIDADE) VALUES (?,?,?)";
            ps = con.prepareStatement(sql);
            pecaServico.id_peca = id_peca;
            pecaServico.id_servico = id_servico;
            pecaServico.quantidade = quantidade;

            ps.setInt(1, id_peca);
            ps.setInt(2, id_servico);
            ps.setInt(3, quantidade);

            ps.executeUpdate();//executa um insert na base

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con);
        }
        System.out.println("Inserido com sucesso...");
        System.out.println();

    }

    /* Funções de select para retorno de ID's */


    public static int selectIdCliente(String nome, String documento) throws SQLException {
        int id = 0;
        Connection con = ConnectionFactory.getConnection();
        ResultSet myResultSet;
        Statement myStatement = con.createStatement();

        try {
            String sql = "select IDCLIENTE from CLIENTE WHERE NMCLIENTE = '" + nome  +"' and `CPF/CNPJ` = '" + documento + "'";
            myResultSet = myStatement.executeQuery(sql);


            while (myResultSet.next()){
                id = myResultSet.getInt(1);
            }

        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro no banco de dados: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            ConnectionFactory.closeConnection(con);
        }
        return id;
    }

    public static int selectIdPeca(String nome) throws SQLException {
        int id = 0;
        Scanner scanner = new Scanner(System.in);
        Connection con = ConnectionFactory.getConnection();
        ResultSet myResultSet;
        Statement myStatement = con.createStatement();

        try {
            String sql = "select * from PECA WHERE NMPECA LIKE '%" + nome  +"%'";
            myResultSet = myStatement.executeQuery(sql);


            while (myResultSet.next()){
                System.out.println("+----------------------------------------------------------------------------------+");
                System.out.print("| ID: " + myResultSet.getInt("IDPECA"));
                System.out.print(" | Nome: " + myResultSet.getString("NMPECA"));
                System.out.print(" | Valor: " + myResultSet.getFloat("VALOR"));
                System.out.println(" | Descrição: " + myResultSet.getString("DESCRICAO"));

            }
            System.out.println("+----------------------------------------------------------------------------------+");
            System.out.println("informe o ID da peça: ");
            System.out.print(">>> ");
            id = scanner.nextInt();

        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro no banco de dados: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            ConnectionFactory.closeConnection(con);
        }
        return id;
    }

    public static int selectIdServico(String nome, String descricao, int id_cliente) throws SQLException {
        int id = 0;
        Connection con = ConnectionFactory.getConnection();
        ResultSet myResultSet;
        Statement myStatement = con.createStatement();

        try {
            String sql = "select IDSERVICO\n" +
                    "from SERVICO\n" +
                    "WHERE NMSERVICO = '" + nome + "' " +
                    "AND DESCRICAO = '" + descricao +"' " +
                    "and IDCLIENTE = " + id_cliente;
            myResultSet = myStatement.executeQuery(sql);


            while (myResultSet.next()){
                id = myResultSet.getInt("IDSERVICO");
            }


        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro no banco de dados: " + ex);
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            ConnectionFactory.closeConnection(con);
        }
        return id;

    }





}

class Cliente{
    String nome, tipo, cpf_cnpj;
}
class Peca{
    String nome, descricao;
    Float valor;
}
class Endereco{
    Integer cep, numero, id_cliente;
    String rua, complemento = "", estado, bairro, cidade;
}

class Email{
    String email;
    int id_cliente;
}

class Telefone{
    String ddd, telefone;
    int id_cliente;
}

class Moto{

    String marca, modelo, cor, combustivel;
    int id_cliente;

}

class Servico{
    String nome, descricao;
    float valor;
    int id_ciente;
}

class PecaServico{
    int id_peca, id_servico, quantidade;
}