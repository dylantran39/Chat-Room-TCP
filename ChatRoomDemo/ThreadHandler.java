package ChatRoomDemo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class ThreadHandler extends Thread {

	ChatRoomServer crsv;
	public Socket incoming;
	public DataInputStream dis;
	public DataOutputStream dos;
	public String name;
	public ThreadHandler(ChatRoomServer crsv,Socket i)
	{
		this.crsv = crsv;
		this.incoming = i;
		try
		{
			this.dis = new DataInputStream(incoming.getInputStream());
			this.dos = new DataOutputStream(incoming.getOutputStream());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		String ch = "";
		try
		{
			ch = dis.readUTF();
			String cmd = ch.substring(0,ch.indexOf(","));
			String msg = ch.substring(ch.indexOf(",")+1);
			if(!cmd.equals("Join"))
				incoming.close();
			System.out.println("Hello: " + msg);
			this.name = msg;
			this.crsv.listClient.add(this);
			for(int i=0;i<this.crsv.listClient.size();i++)
			{
				ThreadHandler temp = this.crsv.listClient.get(i);
				if(temp != this)
				{
					temp.dos.writeUTF("Ok," + this.name);
				}
			}
			while(true)
			{
				ch = dis.readUTF();
				cmd = ch.substring(0,ch.indexOf(","));
				msg = ch.substring(ch.indexOf(",") + 1);
				if(cmd.equals("Msg"))
				{
					for(int i=0;i<this.crsv.listClient.size();i++)
					{
						ThreadHandler temp = this.crsv.listClient.get(i);
						if(temp != this)
						{
							temp.dos.writeUTF("Msg," + this.name + " >>  " + msg);
						}
					}
				}
				else
					if(cmd.equals("Out"))
				{
						for(int i=0;i<this.crsv.listClient.size();i++)
						{
							ThreadHandler temp = this.crsv.listClient.get(i);
							if(temp != this)
							{
								temp.dos.writeUTF("Out," + msg);
							}
						}
					incoming.close();
					this.crsv.listClient.remove(this);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			crsv.listClient.remove(this);
		}
	}
	
}
