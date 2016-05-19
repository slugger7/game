
//QuintonWeenink u13176545
//Kevin David Heritage 13044924

import java.net.*;
import java.io.*;
import java.util.*;

public class HttpServer {

	public int Answ;
	public int ins_Score;
	public int ins_total;
	public static Battleship player1;
	public static Battleship player2;
	public boolean turn;

	HttpServer(Battleship p1, Battleship p2) {
		player1 = p1;
		player2 = p2;
		Answ = 0;
		ins_Score = 0;
		ins_total = 0;
	}

	private static class Threader extends Thread {

		private Socket socket; // create socket for each connected
		private BufferedReader in; // get input from the clients
		private BufferedWriter out; // output to clients


		public HttpServer instance;

		private Threader(Socket accept, HttpServer i) throws FileNotFoundException {
			this.socket = accept;
			instance = i;
		}

		public void run() {
			boolean boom = false;
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
				// Get response from server before sending HTML
				String a;
				String answ;
				int x = 0;
				boolean set = false;
				boolean hit = false;

				System.out.println("**********Start of Request*************");
				while ((a = in.readLine()) != null) {
					System.out.println(a);
					if (a.contains("n=") && !set) {
						// get answer from http request;
						answ = a.substring(a.indexOf('n') + 2);
						answ = answ.substring(0, answ.indexOf(' '));
						System.out.println(answ);

						boom = player2.incoming(answ);
						player1.outgoing(answ, boom);
						
;
						set = true;
					}
					if (a.contains("e=")) {
						//String adress = a.substring(a.indexOf("=") + 1, a.indexOf("HTTP"));
						//String rep = adress.replace("%40", "@");
						//String scorestr = instance.ins_Score + " out of " + instance.ins_total;
						//email(rep, scorestr);

					}
					if (a.isEmpty()) {
						break;
					}
				}

				// Sending output to server

				out.write("HTTP/1.0 200 OK\r\n");
				out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
				out.write("Server: Apache/0.8.4\r\n");
				out.write("Content-Type: text/html\r\n");
				out.write("Content-Length: 59\r\n");
				out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
				out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
				out.write("\r\n");
				out.write("<TITLE>Battleship</TITLE>");
				out.write("<h1>Battleship</h1>");
				out.write("<P>");
				out.write("<fieldset>");

				if(boom) {
					out.write("<h4><font color=\"green\">HIT </font></h4>");
					instance.ins_Score++;

				} else// need to fix this
				{
					out.write("<h4><font color=\"red\">MISS </font></h4>");
				}

				out.write(player1.printMaps());
				//out.write("<h1>PLEASE WAIT FOR THE OTHER PLAYER TO COMPLETE HIS TURN</h1>");

				out.write("<form method=\"get\" action=\"\" >" + " Answer:<input  name=\"n\" >"
						+ "<input type=\"submit\" value=\"Send Answer\">" + "</form>");
				out.write("</fieldset>");
				out.write("<b>Score:</b> " + instance.ins_Score + " out of " + instance.ins_total);
				out.write("</P>");
				out.write("<form method=\"get\" action=\"\" >" + " Answer:<input type=\"email\"  name=\"e\" >"
						+ "<input type=\"submit\" value=\"Email results\">" + "</form>");

				out.flush();
				
				
			} catch (IOException e) {
				System.err.println("Threading error");
			} finally {
				try {
					socket.close();
					System.out.println("**********End of Request*************");
				} catch (IOException e) {
					System.err.println("Error closing sockets");
				}
			}
		}

		private void email(String _to, String score) throws IOException {
			Mail mail = new Mail(_to);
			mail.sendMail("Your score was " + score);
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Starting server on port 55555");
		ServerSocket server = new ServerSocket(55555);
		Battleship player1 = new Battleship();
		Battleship player2 = new Battleship();
		HttpServer host1 = new HttpServer(player1, player2);
		HttpServer host2 = new HttpServer(player2, player1);
		try {
			while (true) {
				System.out.println("waiting for player 1");
				new Threader(server.accept(), host1).start();
				System.out.println("Player 1 connected, waiting for player 2");
				new Threader(server.accept(), host2).start();
				System.out.println("Player 2 connected");
			}
		} finally {
			server.close();
		}
	}
}
