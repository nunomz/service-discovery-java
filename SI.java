import java.net.*;
import java.io.*;

public class SI extends Server {
	static int DEFAULT_PORT=2000;
	static String cc, cc_hashed;
	static boolean res;
	static int port=3000;
	static String host="127.0.0.1";

	public static void Ticketing(Socket ligacao_si, BufferedReader in, PrintWriter out){
		out.println("Servico de Ticketing no endereco "+ host +" e porta"+ port);
	}

	public void run(){

		int port=DEFAULT_PORT;
			
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
		
		while(true) {
			try {

				// Listens for a connection to be made to the socket and accepts it: API java.net.ServerSocket				
				Socket ligacao_si = servidor.accept();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(ligacao_si.getInputStream()));
				PrintWriter out = new PrintWriter(ligacao_si.getOutputStream(), true);
				
				while(true){

					// pede o cc ao cliente
					out.println("Introduza o seu numero de CC");
					cc = in.readLine();

					// encripta o cc
					cc_hashed = hash.getMd5(cc);
					// out.println(cc_hashed);

					// verifica se o cc esta registado
					if (verification.getHash(cc_hashed) == 1){
						res = true;
						out.println(res);
						break;
					}else{
						res = false;
						out.println(res);
					}
				}

				// envia a hash encriptada ao cliente. NOTA: só chega aqui se o cliente estiver autenticado
				out.println(cc_hashed);
				
				out.flush();
				
				Ticketing(ligacao_si, in, out);
				
				System.out.println("Cliente com o CC " + cc + " conectado e autenticado com sucesso no porto " + port);

				out.close();
				ligacao_si.close();
				break;

			} catch (IOException e) {
				System.out.println("Erro na execucao do servidor: "+e);
				System.exit(1);
			}
		}

	}
}