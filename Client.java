import java.util.*;
import java.net.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.io.*;

public class Client {

	static final int DEFAULT_PORT = 2000;
	static final String DEFAULT_HOST = "127.0.0.1";
	static Scanner sc = new Scanner(System.in);
	static String cc, servidor = DEFAULT_HOST, host_st;
	static String[] lista_final = new String[8];
	static int porto_st, porto = DEFAULT_PORT;
	// static List<List<String>> lista_servicos;

	public static void connectServiceRMI(String ip_servico, String nome_servico) {
			getTempRequestHandler handler;
			Instant instant = Instant.now();
			handler = new getTempRequestHandler(ip_servico,nome_servico,instant);
			handler.putTemp();
	}

	public static void connectServiceTCP(String ip_servico, int port_servico) {
		try {
			Socket ligacao_tcp = null;

			try {
				System.out.println("Tentando conexão com serviço no IP " + ip_servico + " e na porta " + port_servico);
				ligacao_tcp = new Socket(ip_servico, port_servico);
			} catch (IOException e) {
				System.out.println("Erro na criacao da socket do serviço TCP");
				System.exit(-1);
			}

			System.out.println("Conectado ao serviço TCP");

			BufferedReader in = new BufferedReader(new InputStreamReader(ligacao_tcp.getInputStream()));
			PrintWriter out = new PrintWriter(ligacao_tcp.getOutputStream());
			Instant instant = Instant.now();
			String tsp = instant.toString();
			String request = "getHumidity" + " " + tsp;

			out.println(request); // https://www.timestamp-converter.com/
			out.flush();
			System.out.println(in.readLine());
			System.out.println("Humidade: " + in.readLine());
		} catch (IOException e) {
			System.out.println("Erro ao conectar ao serviço TCP");
		}
	}

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
		ST(host_st, porto_st);
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

			// BufferedReader in = new BufferedReader(new
			// InputStreamReader(ligacao_si.getInputStream()));
			// PrintWriter out = new PrintWriter(ligacao_si.getOutputStream(), true);

			// para enviar e receber arrays
			ObjectInputStream in = new ObjectInputStream(ligacao_si.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(ligacao_si.getOutputStream());

			System.out.print("Introduza o seu numero de CC: ");
			cc = sc.nextLine();
			out.writeObject(cc);

			// limpa o buffer 2
			out.flush();

			// System.out.println("A sua hash e " + hash_verified_c);
			// String lista_SI;
			String[] lista_SI = (String[]) in.readObject();
			// Lista_SI2 = lista_SI

			host_st = lista_SI[0];
			porto_st = Integer.valueOf(lista_SI[1]);

			System.out.println("\nServiço de Ticketing encontrado!\n  IP: " + host_st + "\n  Port: " + porto_st
					+ "\n  Chave de Acesso (Hash): " + lista_SI[2]);
			lista_final[0] = lista_SI[2];
			ligacao_si.close();
			out.close();
			
			System.out.print("\nPrima qualquer tecla para terminar a ligação com o SI e conectar ao ST apresentado. (CTRL+C para sair) ");
			sc.nextLine();

		} catch (Exception e) {
			System.out.println("Erro ao comunicar com o servidor SI: " + e);
			System.exit(1);
		}
	}

	public static void ST(String host_st2, int porto_st2) {

		Socket ligacao_st = null;

		// Create a client socket
		try {
			ligacao_st = new Socket(host_st2, porto_st2);
			System.out
					.println("\nConexão estabelecida com Serviço de Ticketing no IP " + host_st + " e porto " + porto_st);
		} catch (IOException e) {
			System.out.println("Erro");
		}
		try {
			// BufferedReader st_in = new BufferedReader(new
			// InputStreamReader(ligacao_st.getInputStream()));
			// PrintWriter st_out = new PrintWriter(ligacao_st.getOutputStream());

			ObjectInputStream in = new ObjectInputStream(ligacao_st.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(ligacao_st.getOutputStream());
			// ADICIONA o cc à lista apos a hash
			lista_final[1] = cc;
			// printa o titulo

			System.out.println("\n\n****BEM VINDO AO SERVICO DE TICKETING****\n");

			// Printa o menu

			System.out.println("O que deseja fazer?");
			System.out.println("\n  1. Consulta de servicos");
			System.out.println("  2. Registo de servico");
			System.out.print("\nIntroduza a sua escolha: ");
			lista_final[2] = sc.nextLine();

			switch (lista_final[2]) {
				case "1":// CASO SEJA ESCOLHIDO CONSULTAR SERVIÇOS
					System.out.println("\nTecnologia a consultar?");
					System.out.println("\n  1. JAVA Sockets TCP");
					System.out.println("  2. JAVA RMI");
					System.out.print("\nIntroduza a sua escolha: ");
					lista_final[3] = sc.nextLine();

					out.writeObject(lista_final); // envia a lista apenas até a tecnologia

					@SuppressWarnings("unchecked") // sem isto dá um aviso ao compilar. parece-me batota mas funciona.
													// tentar resolver
					List<List<String>> lista_servicos = (List<List<String>>) in.readObject();

					if (lista_final[3].equals("1")) { // caso escolha TCP

						System.out.println("\nLista de Serviços com tecnologia Sockets TCP: \n");
						for (int i = 1; i <= lista_servicos.size(); i++) {
							if (lista_servicos.get(i - 1).get(2).equals("1")) {
								System.out.println("  " +
										i + ". IP: " + lista_servicos.get(i - 1).get(3) + " PORT: "
												+ lista_servicos.get(i - 1).get(4));
								System.out.println("     Descricao: " + lista_servicos.get(i - 1).get(1));
								System.out.println("     Autor do Registo: " + lista_servicos.get(i - 1).get(0));
								System.out.println("\n");
							}
						}

						System.out.print("A que servico se pretende conectar? (escolha o indice) ");
						int escolha = sc.nextInt();
						sc.nextLine();
						connectServiceTCP(lista_servicos.get(escolha - 1).get(3),
								Integer.valueOf(lista_servicos.get(escolha - 1).get(4)));

					} else if (lista_final[3].equals("2")) { // caso escolha RMI

						System.out.println("\nLista de Serviços com tecnologia JAVA RMI: \n");
						for (int i = 1; i <= lista_servicos.size(); i++) {
							if (lista_servicos.get(i - 1).get(2).equals("2")) {
								System.out.println("  " + i + ". IP: " + lista_servicos.get(i - 1).get(3) + " PORT: "
										+ lista_servicos.get(i - 1).get(4) + " Nome: "
										+ lista_servicos.get(i - 1).get(5));
								System.out.println("     Descricao: " + lista_servicos.get(i - 1).get(1));
								System.out.println("     Autor do Registo: " + lista_servicos.get(i - 1).get(0));
								System.out.println("\n");
							}
						}

						System.out.print("A que servico se pretende conectar? (escolha o indice) ");
						int escolha = sc.nextInt();
						sc.nextLine();
						connectServiceRMI(lista_servicos.get(escolha - 1).get(3),lista_servicos.get(escolha - 1).get(5));
					}

					break;
				case "2":// CASO SEJA ESCOLHIDO REGISTO DE SERVIÇOS
					System.out.print("\nForneca a descricao do servico de rede: ");
					lista_final[3] = sc.nextLine();
					System.out.println("\nTecnologia desejada?");
					System.out.println("\n  1. JAVA Sockets TCP");
					System.out.println("  2. JAVA RMI");
					System.out.print("\nIntroduza a sua escolha: ");
					lista_final[4] = sc.nextLine();
					switch (lista_final[4]) {
						case "1":
							System.out.print("\nForneca o IP: ");
							lista_final[5] = sc.nextLine();
							System.out.print("Forneca a porta: ");
							lista_final[6] = sc.nextLine();
							break;

						case "2":
							System.out.print("\nForneca o IP: ");
							lista_final[5] = sc.nextLine();
							System.out.print("Forneca a porta: ");
							lista_final[6] = sc.nextLine();
							System.out.print("Forneca o nome: ");
							lista_final[7] = sc.nextLine();
							break;

						default:
							System.out.println("\nErro: Nao existe essa opcao");
							System.exit(-1);
							break;
					}
					out.writeObject(lista_final);
					System.out.print(
							"Registo efetuado com sucesso. Prima qualquer tecla para terminar a execução do programa. ");
					System.in.read();
					System.exit(1);
					break;
				default:
					System.out.println("Erro: Nao existe essa opcao");
					System.exit(-1);
					break;
			}

			// out.writeObject(lista_final);

		} catch (Exception e) {
			System.out.println("Erro ao comunicar com o servidor ST: " + e);
			e.printStackTrace(System.out);
			System.exit(1);
		}
	}
}