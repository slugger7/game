
//Charl Jansen van Vuuren 13054903

import java.net.*;
import java.io.*;
import java.util.*;

public class HttpServer {

	public int Answ;
	public int ins_Score;
	public int ins_total;
	public static Battleship battle = new Battleship();
	public boolean turn;

	HttpServer() {
		Answ = 0;
		ins_Score = 0;
		ins_total = 0;
	}

	private static class Threader extends Thread {

		private Socket socket; // create socket for each connected
		private BufferedReader in; // get input from the clients
		private BufferedWriter out; // output to clients

		private Socket socket2; // create socket for each connected
		private BufferedReader in2; // get input from the clients
		private BufferedWriter out2; // output to clients
		public int Score;

		public HttpServer instance;

		private Threader(Socket accept, Socket accept2, HttpServer i) throws FileNotFoundException {
			this.socket = accept;
			this.socket2 = accept2;
			instance = i;
		}

		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				BufferedReader in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
				BufferedWriter out2 = new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream()));

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

						battle.outgoing(answ);

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
				out.write("<TITLE>Question Server</TITLE>");
				out.write("<h1>Question Server</h1>");
				out.write("<P>");
				out.write("<fieldset>");

				if (hit) {
					out.write("<h4><font color=\"green\">HIT </font></h4>");
					instance.ins_Score++;

				} else// need to fix this
				{
					out.write("<h4><font color=\"red\">MISS </font></h4>");
				}

				out.write(battle.printMaps());
				out.write("<h1>PLEASE WAIT FOR THE OTHER PLAYER TO COMPLETE HIS TURN</h1>");

				out.write("<form method=\"get\" action=\"\" >" + " Answer:<input  name=\"n\" >"
						+ "<input type=\"submit\" value=\"Send Answer\">" + "</form>");
				out.write("</fieldset>");
				out.write("<b>Score:</b> " + instance.ins_Score + " out of " + instance.ins_total);
				out.write("</P>");
				out.write("<form method=\"get\" action=\"\" >" + " Answer:<input type=\"email\"  name=\"e\" >"
						+ "<input type=\"submit\" value=\"Email results\">" + "</form>");

				out.flush();
				
				// Get response from server before sending HTML
				a = "";
				answ = "";
				x = 0;
				set = false;
				hit = false;

				System.out.println("**********Start of Request*************");
				while ((a = in2.readLine()) != null) {
					System.out.println(a);
					if (a.contains("n=") && !set) {
						// get answer from http request;
						answ = a.substring(a.indexOf('n') + 2);
						answ = answ.substring(0, answ.indexOf(' '));
						System.out.println(answ);

						battle.incoming(answ);

						set = true;
					}
					if (a.contains("e=")) {
						String adress = a.substring(a.indexOf("=") + 1, a.indexOf("HTTP"));
						String rep = adress.replace("%40", "@");
						String scorestr = instance.ins_Score + " out of " + instance.ins_total;
						email(rep, scorestr);

					}
					if (a.isEmpty()) {
						break;
					}
				}

				// Sending output to server

				out2.write("HTTP/1.0 200 OK\r\n");
				out2.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
				out2.write("Server: Apache/0.8.4\r\n");
				out2.write("Content-Type: text/html\r\n");
				out2.write("Content-Length: 59\r\n");
				out2.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
				out2.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
				out2.write("\r\n");
				out2.write("<TITLE>Question Server</TITLE>");
				out2.write("<h1>Question Server</h1>");
				out2.write("<P>");
				out2.write("<fieldset>");

				if (hit) {
					out2.write("<h4><font color=\"green\">HIT </font></h4>");
					instance.ins_Score++;

				} else// need to fix this
				{
					out2.write("<h4><font color=\"red\">MISS </font></h4>");
				}

				out2.write(battle.printMaps());
				out2.write("<h1>PLEASE WAIT FOR THE OTHER PLAYER TO COMPLETE HIS TURN</h1>");

				out2.write("<form method=\"get\" action=\"\" >" + " Answer:<input  name=\"n\" >"
						+ "<input type=\"submit\" value=\"Send Answer\">" + "</form>");
				out2.write("</fieldset>");
				out2.write("<b>Score:</b> " + instance.ins_Score + " out of " + instance.ins_total);
				out2.write("</P>");
				out2.write("<form method=\"get\" action=\"\" >" + " Answer:<input type=\"email\"  name=\"e\" >"
						+ "<input type=\"submit\" value=\"Email results\">" + "</form>");

				out2.flush();

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
		System.out.println("Starting server on port 44444");
		ServerSocket server2 = new ServerSocket(44444);
		HttpServer i = new HttpServer();
		try {
			while (true) {
				new Threader(server.accept(), server2.accept(), i).start();
			}
		} finally {
			server.close();
		}
	}
}
