import java.net.*;
import java.io.*;

public class Server {
	static int DEFAULT_PORT=2000;
	static int port=DEFAULT_PORT;
	
	public static void main(String[] args) {

		SI identificacao = new SI();
		ST ticketing = new ST();
		
		identificacao.run();
		System.out.println("SI terminado.");
		ticketing.run();	
	}	
}