import java.net.*;
import java.io.*;

public class Server {
	static String DEFAULT_HOST;
	static int port;
	
	public static void main(String[] args) {

		DEFAULT_HOST = args[0];
		port =Integer.valueOf(args[1]);
		SI identificacao = new SI();
		ST ticketing = new ST();
		
		identificacao.run(DEFAULT_HOST,port);
		System.out.println("SI terminado.");
		ticketing.run();	
	}	
}