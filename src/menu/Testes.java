package menu;


import java.sql.SQLException;
import java.util.Scanner;

public class Testes {
    public static void main(String[] args) throws SQLException {


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
                System.out.println("UM");
                break;
            case 2:
                System.out.println("#        Cadastrar cliente          #");
                ///createCliente();
                break;
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
                break;
            // createMoto(nome, documento);
            case 4:
                System.out.println("#         Cadastrar Servico         #");
                //createServico();
                break;
            case 5:
                System.out.println("#         Cadastrar peça            #");
                //createPeca();
                break;
            case 6:
                System.out.println("#            Até mais!!             #");
                break;
            default:
                System.out.println("Op invalida");
                Menu.menuOpcao();
        }
        //Menu.createServico();
    }
}

