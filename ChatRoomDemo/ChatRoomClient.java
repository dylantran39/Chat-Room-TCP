package ChatRoomDemo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatRoomClient {

	public JFrame frame;
	public JTextArea Room;
	public JTextField msg;
	JTextArea Joiners;
	public String nickName;

	public Socket soc;
	public DataInputStream dis;
	public DataOutputStream dos;
	public static JTextArea txtJoin = new JTextArea("");
	public ChatRoomClient(final String nickName)
	{
		this.nickName = nickName;
		this.frame = new JFrame("Chat Room!");
		this.frame.setSize(650, 400);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		this.frame.setLayout(null);
		JLabel lr = new JLabel("Username: " + this.nickName);
		lr.setBounds(80,25,300,25);
		this.frame.add(lr);
		this.Room = new JTextArea("");
		this.Room.setBounds(20, 80, 410, 220);
		this.Room.setLineWrap(true);
		this.Room.setEditable(false);
		JScrollPane scroll = new JScrollPane(this.Room);
		scroll.setBounds(20, 80, 410, 220);
		this.frame.add(scroll);
		JLabel lsd = new JLabel("Message");
		lsd.setBounds(10, 325, 80, 25);
		this.frame.add(lsd);
		
		JLabel lbl = new JLabel("Joiners");
		lbl.setBounds(510, 10, 50, 20);
		this.frame.add(lbl);
		
		txtJoin.setBounds(450, 35, 180, 325);
		txtJoin.setLineWrap(true);
		txtJoin.setBackground(Color.gray);
		txtJoin.setForeground(Color.white);
		txtJoin.setEditable(false);
		JScrollPane scrollJoin = new JScrollPane(txtJoin);
		scrollJoin.setBounds(450, 35, 180, 325);
		this.frame.add(scrollJoin);
		
		//JLabel lblImage = new JLabel("");
		//lblImage.setIcon(new ImageIcon(RegisterAccount.class.getResource(LoginFrame.getMyAccount().getImage())));
		//lblImage.setBounds(10, 10, 66, 54);
		//this.frame.add(lblImage);
		
		this.msg = new JTextField("");
		this.msg.setBounds(70, 325, 220, 25);
		this.frame.add(msg);
		JButton OK = new JButton("Send");
		OK.setBounds(295, 325, 65, 25);
		
		JButton Exit = new JButton("Exit");
		Exit.setBounds(370, 325, 65, 25);
		Exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					dos.writeUTF("Out," + nickName);
					soc.close();
					frame.dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		this.frame.add(Exit);
		
		OK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!msg.getText().equals(""))
				{
					Room.setText(Room.getText() + "\n" + nickName + " >>  " + msg.getText());
					try
					{
						dos.writeUTF("Msg," + msg.getText());
					}catch(Exception e)
					{
						frame.dispose();
					}
					msg.setText("");
				}
			}
		});
		
		this.frame.add(OK);
		this.Joiners = new JTextArea("");
		this.Joiners.setEditable(false);
		this.frame.add(Joiners);
		frame.setVisible(true);
	try
		{
			soc = new Socket(LoginFrame.getIDAddress(),5679);
			this.dis = new DataInputStream(soc.getInputStream());
			this.dos = new DataOutputStream(soc.getOutputStream());
			new Session(this).start();
			this.dos.writeUTF("Join," + this.nickName);
		}catch(Exception e)
		{
			e.printStackTrace();
			this.frame.dispose();
		}
	}
	
	public static void main(String [] args)
	{
		new ChatRoomClient(LoginFrame.getName());
	}
	
	public static void setJoiners(String text)
	{
		txtJoin.setText(txtJoin.getText() + "\n" + text);
	}
}
