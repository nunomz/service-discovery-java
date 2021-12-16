import java.util.*;
import java.net.*;
import java.io.*;

public class Client {

	static final int DEFAULT_PORT = 2000;
	static final String DEFAULT_HOST = "127.0.0.1";
	static Scanner sc = new Scanner(System.in);
	static String cc, servidor = DEFAULT_HOST, host_st;
	static String[] lista_final = new String[8];
	static int porto_st, porto = DEFAULT_PORT;
	//static List<List<String>> lista_servicos;

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

			System.out.println("Introduza o seu numero de CC");
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
		
			System.out.println("IP do Serviço de Ticketing: " + host_st + "\nPort do ST: " + porto_st
					+ "\nChave de Acesso (Hash):" + lista_SI[2]);
			lista_final[0] = lista_SI[2];
			ligacao_si.close();
			out.close();
			// System.out.println("Terminou a ligacao ao SI");

		} catch (Exception e) {
			System.out.println("Erro ao comunicar com o servidor: " + e);
			System.exit(1);
		}
	}

	public static void ST(String host_st2, int porto_st2) {

		Socket ligacao_st = null;

		// Create a client socket
		try {
			ligacao_st = new Socket(host_st2, porto_st2);
			System.out
					.println("Conexão estabelecida com Serviço de Ticketing no IP " + host_st + "e porto " + porto_st);
		} catch (IOException e) {
			System.out.println("Erro");
		}
		try {
			// BufferedReader st_in = new BufferedReader(new
			// InputStreamReader(ligacao_st.getInputStream()));
			// PrintWriter st_out = new PrintWriter(ligacao_st.getOutputStream());

			ObjectInputStream in = new ObjectInputStream(ligacao_st.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(ligacao_st.getOutputStream());
			//ADICIONA o cc à lista apos a hash 
			lista_final[1]= cc;
			// printa o titulo
			
			System.out.print("****BEM VINDO AO SERVICO DE TICKETING****");

			// Printa o menu

			System.out.println(
					"********************************\nO que deseja fazer?            *\n                               *\n1-Consulta de servicos         *\n                               *\n                               *\n2-Registo de servico           *\n                               *\n********************************\n");
			lista_final[2] = sc.nextLine();

			switch (lista_final[2]) {
				case "1":// CASO SEJA ESCOLHIDO CONSULTAR SERVIÇOS
					System.out.println(
							"********************************\nTecnologia a consultar?        *\n                               *\n1-JAVA Sockets TCP             *\n                               *\n                               *\n2-JAVA RMI                     *\n                               *\n********************************\n");
					lista_final[3] = sc.nextLine();

					out.writeObject(lista_final); // envia a lista apenas até a tecnologia
					
					@SuppressWarnings("unchecked") // sem isto dá um aviso ao compilar. parece-me batota mas funciona. tentar resolver
					List<List<String>> lista_servicos = (List<List<String>>)in.readObject();
					
					if(lista_final[3].equals("1")){ // caso escolha TCP
						
						System.out.println("Lista de Serviços com tecnologia Sockets TCP: \n");
                        for (int i = 1; i <= lista_servicos.size(); i++) {
                            if (lista_servicos.get(i - 1).get(2).equals("1")) {
                                System.out.println(
                                        i + "-IP: " + lista_servicos.get(i - 1).get(3) + " PORT: " + lista_servicos.get(i - 1).get(4));
                                System.out.println("  Descricao: " + lista_servicos.get(i - 1).get(1));
                                System.out.println("  Autor do Registo: " + lista_servicos.get(i - 1).get(0));
                                System.out.println("\n");
                            }
                        }

					}else if(lista_final[3].equals("2")){ // caso escolha RMI
						
						System.out.println("Lista de Serviços com tecnologia JAVA RMI: \n");
                        for (int i = 1; i <= lista_servicos.size(); i++) {
                            if (lista_servicos.get(i - 1).get(2).equals("2")) {
                                System.out.println(i + "-IP: " + lista_servicos.get(i - 1).get(3) + " PORT: "
                                        + lista_servicos.get(i - 1).get(4) + " Nome: " + lista_servicos.get(i - 1).get(5));
                                System.out.println("  Descricao: " + lista_servicos.get(i - 1).get(1));
                                System.out.println("  Autor do Registo: " + lista_servicos.get(i - 1).get(0));
                                System.out.println("\n");
                            }
                        }
					}

					break;
				case "2":// CASO SEJA ESCOLHIDO REGISTO DE SERVIÇOS
					System.out.println("Forneca a descricao do servico de rede");
					lista_final[3] = sc.nextLine();
					System.out.println(
							"********************************\nTecnologia desejada?        *\n                               *\n1-JAVA Sockets TCP             *\n                               *\n                               *\n2-JAVA RMI                     *\n                               *\n********************************\n");
					lista_final[4] = sc.nextLine();
					switch (lista_final[4]) {
						case "1":
							System.out.println("Forneca o IP: ");
							lista_final[5] = sc.nextLine();
							System.out.println("Forneca a porta: ");
							lista_final[6] = sc.nextLine();
							break;

						case "2":
							System.out.println("Forneca o IP: ");
							lista_final[5] = sc.nextLine();
							System.out.println("Forneca a porta: ");
							lista_final[6] = sc.nextLine();
							System.out.println("Forneca o nome: ");
							lista_final[7] = sc.nextLine();
							break;

						default:
							System.out.println("Erro: Nao existe essa opcao");
							System.exit(-1);
							break;
					}
					out.writeObject(lista_final);
					System.out.print("Registo efetuado com sucesso. Prima qualquer tecla para terminar a execução do programa. ");
					System.in.read();
					System.exit(1);
					break;
				default:
					System.out.println("Erro: Nao existe essa opcao");
					System.exit(-1);
					break;
			}

			//out.writeObject(lista_final);

		} catch (Exception e) {
			System.out.println("Erro ao comunicar com o servidor: " + e);
			System.exit(1);
		}
	}
}