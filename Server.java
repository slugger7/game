import java.net.*;
import java.io.*;
import java.util.*;

public class Server extends Thread
{
	private Socket socket;
	private ChatServer server;
	private DataInputStream streamIn;
	private DataOutputStream streamOut;
	private int ID;
	
	public ServerThread(ChatServer cs, Socket s)
	{
		super();
		server = cs;
		socket = s;
		ID = socket.getPort();
	}
	
	public void send(String msg)
	{
		try
		{
			//Pipe and flush - lol
			streamOut.writeUTF(msg);
			streamOut.flush();
		}
		catch(IOException ex)
		{
			System.out.println("Error: " + ex);
			//Faulty
			server.remove(ID);
			stop();
		}
	}
	
	public void run()
	{
		System.out.println("Client Thread " + ID + " is running");
		while(true)
		{
			try
			{
				//read from pipe
				server.handle(ID, streamIn.readUTF());
			}
			catch(IOException ex)
			{
				System.out.println("Error: " + ex);
				//Faulty
				server.remove(ID);
				stop();
			}
		}
	}
	
	
	public void open() throws IOException
	{  
		System.out.println("Attempting to open connection");
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		System.out.println("Connection opened");
	}
	
	public void close() throws IOException
	{  
		System.out.println("Attempting to close connection");
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
		if (streamOut != null)
			streamOut.close();
		System.out.println("Connection closed");
	}
	

}
