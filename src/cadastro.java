package cadastro;

import java.util.Locale;
import java.util.Scanner;

class Consulta{
	void numero() {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		String cpfCnpj;
		
		System.out.print("CPF/CNPJ ");
		cpfCnpj = sc.nextLine();
		
		System.out.println("CPF/CNPJ: " + cpfCnpj);
		sc.close();
	}
}

class Cliente{
	void cadCliente() {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		String[] tel = new String[2];
		String nome, email, cpf, end, complemento, estado, cidade, bairro, cep;
		
		System.out.print("Nome: ");
		nome = sc.nextLine();
		System.out.print("Digite CPF/CNPJ: ");
		cpf = sc.nextLine();
		System.out.print("Digite o tel: ");
		tel[0] = sc.nextLine();
		System.out.print("Digite o cel: ");
		tel[1] = sc.nextLine();
		System.out.print("Digite seu email: ");
		email = sc.nextLine();
		System.out.print("End: ");
		end = sc.nextLine();
		System.out.print("Complemento: ");
		complemento = sc.nextLine();
		System.out.print("Estado: ");
		estado = sc.nextLine();
		System.out.print("Cidade: ");
		cidade = sc.nextLine();
		System.out.print("Bairro: ");
		bairro = sc.nextLine();
		System.out.print("CEP: ");
		cep = sc.nextLine();
		
		System.out.print("Nome: " + nome);
		System.out.println();
		System.out.println("CPF/CNPJ: " + cpf);
		System.out.print("Contatos:" + "   " + "tel: " + tel[0] + "    " + "cel: " + tel[1]);
		System.out.println();
		System.out.println("E-mail: " + email);
		System.out.println("End: " + end);
		System.out.println("complemento: " + complemento);
		System.out.println("Estado: " + estado + "  Cidade: " + cidade + "  Bairro: " + bairro + "  CEP: " + cep);
		
		sc.close();
	}
	
}
class Moto{
	void cadMoto() {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		String marcamodelo, placa, categoria, combustivel, ano, modelo, cilindrada;
		
		System.out.print("Marca/Modelo: ");
		marcamodelo = sc.nextLine();
		System.out.print("Ano/Fabricação: ");
		ano = sc.nextLine();
		System.out.print("Modelo/Fabricação: ");
		modelo = sc.nextLine();
		System.out.print("Placa: ");
		placa = sc.nextLine();
		System.out.print("Categoria: ");
		categoria = sc.nextLine();
		System.out.print("Combustível: ");
		combustivel = sc.nextLine();
		System.out.print("Cilindrada: ");
		cilindrada = sc.nextLine();
		
		System.out.print("Marca/Modelo: " + marcamodelo);
		System.out.println();
		System.out.print("Ano/Fabricação: " + ano);
		System.out.println();
		System.out.print("Modelo/Fabricação: " + modelo);
		System.out.println();
		System.out.print("Placa: " + placa);
		System.out.println();
		System.out.print("Categoria: " + categoria);
		System.out.println();
		System.out.print("Combustível: " + combustivel);
		System.out.println();
		System.out.print("Cilindrada: " + cilindrada);
		System.out.println();		
		sc.close();
	}
}
public class cadastro_cliente {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		int opcao;
		
		do {
			System.out.println("Escolha uma opção: ");
			System.out.println("1 - Consultar Cliente");
			System.out.println("2 - Cadastrar Cliente");
			System.out.println("3 - Cadastrar Moto");
			System.out.println("4 - Sair");
			System.out.print("Opção: ");
			opcao = sc.nextInt();
			switch(opcao) {
			case 1:
				Consulta dado = new Consulta();
				dado.numero();
			break;
			case 2:
				Cliente cadC = new Cliente();
				cadC.cadCliente();
			break;
			case 3:
				Moto cadM = new Moto();
				cadM.cadMoto();
			break;
			default: System.out.println("Até mais!");
				
			}
			break;
		}
		while(opcao > 0 && opcao < 4);
		
		
		sc.close();
	}

}