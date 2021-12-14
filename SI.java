import java.net.*;
import java.io.*;

public class SI extends Server {
	static int DEFAULT_PORT = 2000;
	static String cc, cc_hashed;
	static boolean res;

	public static void Ticketing(String host_st, int port_st, String hash, PrintWriter out) {
		String[] si_list = new String[3];
		si_list[0] = host_st;
		si_list[1] = port_st;
		si_list[2] = hash;
		out.println(si_list);
	}

	public void run(String DEFAULT_HOST_ST, int port_ST) {

		int port = DEFAULT_PORT;
		System.out.println(DEFAULT_HOST_ST);
		ServerSocket servidor = null;

		// Creates a server socket API java.net.ServerSocket
		try {
			servidor = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Erro ao criar socket SI");
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("Servico SI a espera de ligacoes no porto " + port);

		while (true) {
			try {

				// Listens for a connection to be made to the socket and accepts it: API
				// java.net.ServerSocket
				Socket ligacao_si = servidor.accept();

				BufferedReader in = new BufferedReader(new InputStreamReader(ligacao_si.getInputStream()));
				PrintWriter out = new PrintWriter(ligacao_si.getOutputStream(), true);
				cc= in.readLine();

				// encripta o cc
				cc_hashed = hash.getMd5(cc);
				// out.println(cc_hashed);

				// verifica se o cc esta registado
				if (verification.getHash(cc_hashed) == 1) {
					res = true;
					System.out.print("Utilizador autenticado com a hash " + cc_hashed);
					// out.println(res);

				} else {
					res = false;
					out.print("Erro: Utilizador nao encontrado");
					ligacao_si.close();
					System.exit(-1);
					// out.println(res);
				}

				out.flush();

				Ticketing(DEFAULT_HOST_ST, port_ST, cc_hashed, out);

				out.close();
				ligacao_si.close();

			} catch (IOException e) {
				System.out.println("Erro na execucao do servidor: " + e);
				System.exit(1);
			}
		}

	}
}