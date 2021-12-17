import java.net.*;
import java.io.*;
import java.util.*;

public class ST extends Server {
    static String hash_st, csv_servicos = "servicos.csv";
    static String[] lista_csv;
    static List<List<String>> lines;

    public void toCsv(String[] lista) throws Exception {
        FileWriter writer = new FileWriter(csv_servicos, true); // true para fazer append, sem ele escreve por cima do
                                                                // que estiver

        for (int i = 0; i < lista.length; i++) {
            writer.append(String.valueOf(lista[i]));
            writer.append(",");
        }
        writer.append("\n");
        // writer.toString();
        // writer.flush();
        writer.close();
    }

    // https://stackoverflow.com/questions/40074840/reading-a-csv-file-into-a-array
    public static void csvToString() {
        // String fileName= "rmi.csv";
        File file = new File(csv_servicos);

        // this gives you a 2-dimensional array of strings
        lines = new ArrayList<>();
        Scanner inputStream;

        try {
            inputStream = new Scanner(file);

            while (inputStream.hasNext()) {
                String line = inputStream.next();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }

            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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

                // Recebe a hash, e o cc, é necessario fazer a confirmação
                hash_st = hash.getMd5(lista_respostas[1]);
                if (hash_st.equals(lista_respostas[0])) {
                    System.out.println("*Hashes coincidem!*");
                } else {
                    System.out.println("*Erro:Hashes nao coincidem*");
                    System.exit(-1);
                    out.close();
                    ligacao_st.close();
                }

                if (lista_respostas[2].equals("1")) {

                    csvToString();
                    out.writeObject(lines); // envia arraylist 2d

                } else if (lista_respostas[2].equals("2")) {

                    if (lista_respostas[4].equals("1")) {
                        lista_csv = new String[5];

                        lista_csv[0] = String.valueOf(lista_respostas[1]); // cc
                        lista_csv[1] = String.valueOf(lista_respostas[3]); // descricao
                        lista_csv[2] = String.valueOf(lista_respostas[4]); // "SOCKETS TCP"; //tecnologia
                        lista_csv[3] = String.valueOf(lista_respostas[5]); // ip
                        lista_csv[4] = String.valueOf(lista_respostas[6]); // porta
                    } else if (lista_respostas[4].equals("2")) {
                        lista_csv = new String[6];

                        lista_csv[0] = String.valueOf(lista_respostas[1]); // cc
                        lista_csv[1] = String.valueOf(lista_respostas[3]); // descricao
                        lista_csv[2] = String.valueOf(lista_respostas[4]); // "RMI"; //tecnologia
                        lista_csv[3] = String.valueOf(lista_respostas[5]); // ip
                        lista_csv[4] = String.valueOf(lista_respostas[6]); // porta
                        lista_csv[5] = String.valueOf(lista_respostas[7]); // nome
                    }
                    toCsv(lista_csv);
                    out.close();
                    ligacao_st.close();
                   
                }

            } catch (Exception e) {
                System.out.println("Erro na execucao do servidor: " + e);
                System.exit(1);
            }
        }

    }
}
