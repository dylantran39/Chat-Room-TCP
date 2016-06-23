package ChatRoomDemo;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class ChatRoomServer {

	public final static int DEFAULT_PORT = 5679;
	public Vector<ThreadHandler> listClient = new Vector<ThreadHandler>();
	public ChatRoomServer()
	{
		ServerSocket theServer;
		Socket theConnection;
		try
		{
			theServer = new ServerSocket(DEFAULT_PORT);
			while(true)
			{
				theConnection = theServer.accept();
				System.out.println("The client conect");
				new ThreadHandler(this,theConnection).start();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String [] args)
	{
		new ChatRoomServer();
	}
}
