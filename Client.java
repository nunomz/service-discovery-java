import java.util.*;
import java.net.*;
import java.io.*;

public class Client {

	static final int DEFAULT_PORT = 2000;
	static final String DEFAULT_HOST = "127.0.0.1";
	static Scanner sc = new Scanner(System.in);
	static String cc, hash_verified_c, escolha, resposta_menu_ticketing, menu, resposta_menu_consultar, escolha2,
			descricao;
	static boolean res;
	// static String escolha;
	// static String resposta;
	static String[] lista_final;
	static String servidor = DEFAULT_HOST;
	static int porto = DEFAULT_PORT;
	static int porto_st = 3000;

	public static void main(String[] args) {

		// Create a representation of the IP address of the Server: API
		// java.net.InetAddress
		try {
			InetAddress serverAddress = InetAddress.getByName(servidor);
		} catch (IOException e) {
			System.out.println("Erro");
		}

		// chama primeiro o SI
		SI(args);

		// chama o ST depois de o SI correr
		// ST(args);
	}

	public static void SI(String[] args) {

		Socket ligacao_si = null;

		// Create a client socket
		try {
			ligacao_si = new Socket(servidor, porto);
		} catch (IOException e) {
			System.out.println("Erro");
		}

		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(ligacao_si.getInputStream()));
			PrintWriter out = new PrintWriter(ligacao_si.getOutputStream(), true);

			System.out.println("Introduza o seu numero de CC");
			cc = sc.nextLine();
			out.println(cc);

			// limpa o buffer 2
			out.flush();

			// System.out.println("A sua hash e " + hash_verified_c);
			String lista_SI;
			lista_SI = in.readLine();
			String[] Lista_SI2 = new String[3];
			// Lista_SI2 = lista_SI
			System.out.print(lista_SI);
			/*
			 * //para não fechar a ligação instantaneamente
			 * String msg;
			 * while ((msg = in.readLine()) != null)
			 * System.out.println("Resposta do servidor: " + msg);
			 */

			ligacao_si.close();
			// System.out.println("Terminou a ligacao ao SI");

			return;

		} catch (IOException e) {
			System.out.println("Erro ao comunicar com o servidor: " + e);
			System.exit(1);
		}
	}

	public static void ST(String[] args) {

		Socket ligacao_st = null;

		// Create a client socket
		try {
			ligacao_st = new Socket(servidor, porto_st);
		} catch (IOException e) {
			System.out.println("Erro");
		}
		try {
			BufferedReader st_in = new BufferedReader(new InputStreamReader(ligacao_st.getInputStream()));
			PrintWriter st_out = new PrintWriter(ligacao_st.getOutputStream());

			// printa o titulo

			System.out.print("****BEM VINDO AO SERVICO DE TICKETING****");

			// Printa o menu

			System.out.println(
					"********************************\nO que deseja fazer?            *\n                               *\n1-Consulta de servicos         *\n                               *\n                               *\n2-Registo de servico           *\n                               *\n********************************\n/");
			String escolha_menu_um;
			escolha_menu_um = sc.nextLine();
			lista_final[0] = escolha_menu_um;

			switch (escolha_menu_um) {
				case "1":// CASO SEJA ESCOLHIDO CONSULTAR SERVIÇOS
					System.out.println(
							"********************************\nTecnologia a consultar?        *\n                               *\n1-JAVA Sockets TCP             *\n                               *\n                               *\n2-JAVA RMI                     *\n                               *\n********************************\n/");
					lista_final[1] = sc.nextLine();
					break;

				case "2":// CASO SEJA ESCOLHIDO REGISTO DE SERVIÇOS
					System.out.println("Forneca a descricao do servico de rede");
					lista_final[1] = sc.nextLine();
					System.out.println(
							"********************************\nTecnologia desejada?        *\n                               *\n1-JAVA Sockets TCP             *\n                               *\n                               *\n2-JAVA RMI                     *\n                               *\n********************************\n/");
					lista_final[2] = sc.nextLine();
					switch (lista_final[2]) {
						case "1":
							System.out.println("Forneca o IP: ");
							lista_final[3] = sc.nextLine();
							System.out.println("Forneca a porta: ");
							lista_final[4] = sc.nextLine();
							break;

						case "2":
							System.out.println("Forneca o IP: ");
							lista_final[3] = sc.nextLine();
							System.out.println("Forneca a porta: ");
							lista_final[4] = sc.nextLine();
							System.out.println("Forneca o nome: ");
							lista_final[5] = sc.nextLine();
							break;

						default:
							System.out.println("Erro: nao existe essa opcao");
							System.exit(-1);
							break;
					}
					break;
				default:
					System.out.println("Erro: nao existe essa opcao");
					System.exit(-1);
					break;
			}

		} catch (IOException e) {
			System.out.println("Erro ao comunicar com o servidor: " + e);
			System.exit(1);
		}
	}
}