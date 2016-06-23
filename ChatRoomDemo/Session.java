package ChatRoomDemo;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class Session extends Thread {

	ChatRoomClient cr;
	public DataInputStream dis;
	public DataOutputStream dos;
	public Session(ChatRoomClient cr)
	{
		this.cr = cr;
		try
		{
			this.dis = new DataInputStream(cr.soc.getInputStream());
			this.dos = new DataOutputStream(cr.soc.getOutputStream());
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
			while(true)
			{
				ch = dis.readUTF();
				String cmd = ch.substring(0,ch.indexOf(","));
				String msg = ch.substring(ch.indexOf(",") + 1);
				if(cmd.equals("Msg"))
					this.cr.Room.setText(cr.Room.getText() + "\n" + msg);
				else
					if(cmd.equals("Out"))
					{
						ChatRoomClient.setJoiners("@@@@@   " + msg + " out");
					}
					else
						if(cmd.equals("Ok"))
						{
							ChatRoomClient.setJoiners("@@@@@   " + msg + " online");
						}
			}
		}catch(Exception e)
		{
			cr.frame.dispose();
		}
	}
}
