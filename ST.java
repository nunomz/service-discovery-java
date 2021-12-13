import java.net.*;
import java.io.*;

public class ST extends Server {
    static int DEFAULT_PORT = 3000;
    static String cc;
    static String cc_hashed;
    static String hash_st;
    // private static ServerSocket servidor; tirei daqui e continua a funcionar
    static int choice = 0;
    static String escolha;

    public static String toStringMenu() {
        return ("********************************\nO que deseja fazer?            *\n                               *\n1-Consulta de servicos         *\n                               *\n                               *\n2-Registo de servico           *\n                               *\n********************************\n");
        /*
         * nao funciona com prints, apenas com return. why tho ;-;
         * System.out.println("O que deseja fazer?            *");
         * System.out.println("                               *");
         * System.out.println("1-Consulta de servicos         *");
         * System.out.println("                               *");
         * System.out.println("                               *");
         * System.out.println("2-Registo de servico           *");
         * System.out.println("                               *");
         * System.out.println("********************************");
         */
    }

    public static void runConsulta(Socket ligacao_st, BufferedReader in, PrintWriter out) {
        //return ("********************************\nQue servico pretende?          *\n                               *\n1-Serviço de Temperatura       *\n                               *\n                               *\n2-Serviço de Humidade          *\n                               *\n********************************\n");
        while(true){
            try{
                out.println("********************************\nTecnologia a consultar?        *\n                               *\n1-JAVA Sockets TCP             *\n                               *\n                               *\n2-JAVA RMI                     *\n                               *\n********************************\n/");
                out.flush();
                escolha = in.readLine();
                System.out.print(escolha);

                while (!(escolha.equals("1")) && !(escolha.equals("2"))) {
                    out.println(0);
                    out.println("Escolha invalida\n");
                    out.flush();
                    out.println("********************************\nTecnologia a consultar?        *\n                               *\n1-JAVA Sockets TCP             *\n                               *\n                               *\n2-JAVA RMI                     *\n                               *\n********************************\n/");
                    out.flush();

                    escolha = in.readLine();
                    System.out.print(escolha);

                    // System.out.println("o cliente escolheu " + escolha);
                }
                if(escolha.equals("1")){
                    out.println(1);
                    out.println("sockets goes here");
                    System.out.println("O cliente escolheu consultar serviços com tecnologia JAVA Sockets");
                }else if(escolha.equals("2")){
                    out.println(2);
                    out.println("rmi goes here");
                    System.out.println("O cliente escolheu consultar serviços com tecnologia JAVA RMI");
                }
            } catch (IOException e) {
                System.out.println("Erro na execucao da consulta de servicos: " + e);
                System.exit(1);
            }
        }

    }

    public static String runRegisto() {
        return ("REGISTO SHOULD BE HERE");
    }

    public void run() {

        int port = DEFAULT_PORT;

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

                // out.println("*****Servico de Ticketing*****");

                cc = in.readLine();
                cc_hashed = in.readLine();

                hash_st = hash.getMd5(cc);

                if (cc_hashed.equals(hash_st) == false) {
                    out.println("\nOs valores da hash fornecida e da calculada nao correspondem");
                    out.flush();
                    out.close();
                    ligacao_st.close();
                } else {
                    out.println("*****Servico de Ticketing*****");
                }

                out.flush();

                // envia o menu
                out.println(
                        "********************************\nO que deseja fazer?            *\n                               *\n1-Consulta de servicos         *\n                               *\n                               *\n2-Registo de servico           *\n                               *\n********************************\n/"); 
                escolha = in.readLine();

                // substituir menus printados por funcoes?
                while (!(escolha.equals("1")) && !(escolha.equals("2"))) {
                    out.println(0);
                    out.println("Escolha invalida\n");
                    out.flush();
                    out.println(
                            "********************************\nO que deseja fazer?            *\n                               *\n1-Consulta de servicos         *\n                               *\n                               *\n2-Registo de servico           *\n                               *\n********************************\n/");
                    out.flush();

                    escolha =in.readLine();
                    System.out.print(escolha);

                    // System.out.println("o cliente escolheu " + escolha);
                }

                if (escolha.equals("1")) {
                    out.println(1);
                    System.out.println("O cliente escolheu Consultar");
                    runConsulta(ligacao_st, in, out);
                } else if (escolha.equals("2")) {
                    out.println(2);
                    out.println("REGISTAR GOES HERE");
                    System.out.println("O cliente escolheu Registar");
                }

                // out.close();
                // ligacao_st.close();
                // break;

            } catch (IOException e) {
                System.out.println("Erro na execucao do servidor: " + e);
                System.exit(1);
            }
        }
    }
}
