import java.util.Scanner;

public class Usuario {
    public static void logo(){
        System.out.println("+-------------------------------+\n" +
                           "|           ARTE MOTOS          |\n" +
                           "+-------------------------------+\n");
    }

    public static void cadastrar(string login_senha[][]){
        Scanner s = new Scanner(System.in);

        System.out.println("Informe um login:");
        string login = s.next();
        login_senha[0][0] = login;
        System.out.println("Informe uma senha:");
        string senha = s.next();
        login_senha[0][1] = senha;
        System.out.println("Login" + login + "Senha" + senha);

    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int op;
        string [][] login_senha;
        logo();
        System.out.println("Informe a opção: " +
                "\n 1 - Acessar" +
                "\n 2 - Cadastrar" +
                "\n 3 - Alterar" +
                "\n 4 - Sair");
        System.out.print(" Opção >>> ");
        op = Integer.parseInt(s.nextLine());
        switch (op){
            case 1:
                System.out.println("Acessar");
                break;
            case  2:
                cadastrar(login_senha[][]);
                break;
            case 3:
                System.out.println("Alterar");
                break;
            case 4:
                System.out.println("Sair");
                break;
            default:
                System.out.println("Opção inválida");
        }

    }
}
