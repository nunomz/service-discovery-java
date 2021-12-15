import java.net.*;
import java.io.*;

public class ST extends Server {
    static int DEFAULT_PORT = 3000;
    static String cc;
    static String cc_hashed;
    static String hash_st;
    // private static ServerSocket servidor; tirei daqui e continua a funcionar
    static int choice = 0;
    static String tecnologia, ip, porta, nome, descricao;

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

                // BufferedReader in = new BufferedReader(new
                // InputStreamReader(ligacao_st.getInputStream()));
                // PrintWriter out = new PrintWriter(ligacao_st.getOutputStream(), true);

                ObjectOutputStream out = new ObjectOutputStream(ligacao_st.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(ligacao_st.getInputStream());

                String[] lista_respostas = (String[]) in.readObject();

                if (lista_respostas[0].equals("1")) {

                } else if (lista_respostas[0].equals("2")) {

                    descricao = lista_respostas[1];

                    if (lista_respostas[2].equals("1")) {
                        tecnologia = String.valueOf(lista_respostas[2]);
                        ip = String.valueOf(lista_respostas[3]);
                        porta = String.valueOf(lista_respostas[4]);

                    } else if (lista_respostas[2].equals("2")) {
                        tecnologia = String.valueOf(lista_respostas[2]);
                        ip = String.valueOf(lista_respostas[3]);
                        porta = String.valueOf(lista_respostas[4]);
                        nome = String.valueOf(lista_respostas[5]);

                    }
                }
                System.out.println(" " + descricao + " " + tecnologia + " " + ip + " " + porta + " " + " " + nome);

            } catch (Exception e) {
                System.out.println("Erro na execucao do servidor: " + e);
                System.exit(1);
            }
        }

    }
}
