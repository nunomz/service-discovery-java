import java.util.*;
import java.net.*;
import java.io.*;

public class Client {

	static final int DEFAULT_PORT = 2000;
	static final String DEFAULT_HOST = "127.0.0.1";
	static Scanner sc = new Scanner(System.in);
	static String cc, hash_verified_c, escolha, resposta_menu_ticketing, menu, resposta_menu_consultar,escolha2, descricao;
	static boolean res;
	//static String escolha;
	//static String resposta;

	static String servidor = DEFAULT_HOST;
	static int porto = DEFAULT_PORT;
	static int porto_st = 3000;

	public static void main(String[] args) {

		/*if (args.length != 1) {
			System.out.println("Erro: use java presencesClient <ip>");
			System.exit(-1);
		} */

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
		ST(args);
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

			while (res != true) {

				// recebe msg a pedir o cc
				System.out.println(in.readLine());
				cc = sc.nextLine();

				// envia o cc ao server
				out.println(cc);

				// recebe o resultado
				String wtf = in.readLine();
				res = Boolean.valueOf(wtf);
				if (res == true) {
					System.out.println("Utilizador autorizado.\n\n**********Bem-Vindo**********");
					break;
				} else {
					System.out.println("\nA chave introduzida não se encontra registada.");
				}
			}

			// limpa o buffer 2
			out.flush();

			// recebe a hash encriptada. não é suposta fazer print!! retirar de comentário
			// apenas para testar.
			hash_verified_c = in.readLine();
			// System.out.println("A sua hash e " + hash_verified_c);
			System.out.println(in.readLine());
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
			// System.out.print(st_in.readLine());

			// envia o cc ao ST
			st_out.println(cc);

			// envia a hash ao ST
			st_out.println(hash_verified_c);

			// flush 1
			st_out.flush();

			// recebe o resultado
			String resultado = st_in.readLine();
			System.out.println(resultado);

			// recebe o menu do ST
			while (!(menu = st_in.readLine()).equals("/")) {
				System.out.println(menu);
				st_out.flush();
			}

			escolha = sc.nextLine();
			st_out.println(escolha);

			st_out.flush();
		
			// recebe a resposta
			resposta_menu_ticketing = st_in.readLine();

			st_out.flush();

			// verifica se a resposta é input invalido 
			while (resposta_menu_ticketing.equals("0")) {
				// escreve mensagem de erro
				System.out.println(st_in.readLine());
				while (!(menu = st_in.readLine()).equals("/")) {
					System.out.println(menu);
					st_out.flush();
				}
				escolha = sc.nextLine();

				st_out.println(escolha);
				st_out.println();
				st_out.flush();
				resposta_menu_ticketing = st_in.readLine();
				st_out.flush();
			}

			// verifica se a resposta é o 0 ou um dos menus (1 para consulta e 2 para registo)
			if(resposta_menu_ticketing.equals("1")){
				//recebe o menu de consultar
				while (!(menu = st_in.readLine()).equals("/")) {
					System.out.println(menu);
				}
				escolha2 = sc.nextLine();
				st_out.println(escolha2);
				st_out.flush();
				System.out.println(escolha2);
				st_out.println();

				// recebe e  verifica a resposta ao menu consultar: 1 para sockets e 2 para rmi
				resposta_menu_consultar = st_in.readLine();
				while (resposta_menu_consultar.equals("0")) {
					// escreve mensagem de erro
					System.out.println(st_in.readLine());
					while (!(menu = st_in.readLine()).equals("/")) {
						System.out.println(menu);
						st_out.flush();
					}
					escolha = sc.nextLine();

					st_out.println(escolha);
					st_out.println();
					st_out.flush();
					resposta_menu_consultar = st_in.readLine();
					st_out.flush();
				}

				if(resposta_menu_consultar.equals("1")){
					// java sockets
					System.out.println("st_in.readLine()");
				}else if(resposta_menu_consultar.equals("2")){
					//java rmi
					System.out.println(st_in.readLine());
				}


			}else if(resposta_menu_ticketing.equals("2")){
				System.out.println(st_in.readLine());
				descricao = sc.nextLine();
				st_out.println(descricao);
				st_out.flush();


			}else{
				System.out.println("Erro");
			}
			// termina a ligacao
			// ligacao.close();
			// System.out.println("Terminou a ligacao!");

		} catch (IOException e) {
			System.out.println("Erro ao comunicar com o servidor: " + e);
			System.exit(1);
		}
	}
}