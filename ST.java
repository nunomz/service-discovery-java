import java.net.*;
import java.io.*;

public class ST extends Server {
    static int DEFAULT_PORT = 3000;
    static String cc;
    static String cc_hashed;
    static String hash_st;
    // private static ServerSocket servidor; tirei daqui e continua a funcionar
    static int choice = 0;
    static String ip, porta, nome, descricao;

    public void run() {

        ServerSocket servidor = null;

        // Creates a server socket API java.net.ServerSocket
        try {
            servidor = new ServerSocket(port);
        } catch (Exception e) {
            System.err.println("erro ao criar socket ST");
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println("Servico ST a espera de ligacoes no porto " + port);

        while (true) {
            try {

                // Listens for a connection to be made to the socket and accepts it: API
                // java.net.ServerSocket
                Socket ligacao_st = servidor.accept();

                System.out.println("\nO cliente conectou-se ao serviço de ticketing.");

                BufferedReader in = new BufferedReader(new InputStreamReader(ligacao_st.getInputStream()));
                PrintWriter out = new PrintWriter(ligacao_st.getOutputStream(), true);


               
            } catch (IOException e) {
                System.out.println("Erro na execucao do servidor: " + e);
                System.exit(1);
            }
        }

    }
}
