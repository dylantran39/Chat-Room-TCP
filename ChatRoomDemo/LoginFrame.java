package ChatRoomDemo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginFrame {

	public static LoginFrame it;
	public static String ipAddress;
	public static String mName;
	public JFrame frame;
	public LoginFrame(String ms)
	{
		frame = new JFrame("Login Account");
		frame.setSize(350, 250);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(null);
		
		//username
		JLabel lname = new JLabel("Username");
		lname.setBounds(20, 32, 80, 50);
		frame.add(lname);

		final JTextField Name = new JTextField("");
		Name.setBounds(120,40, 200, 30);
		frame.add(Name);
		
		//ip address
		JLabel lIP = new JLabel("IP Address");
		lIP.setBounds(20, 81, 80, 50);
		frame.add(lIP);
		
		final JTextField IP = new JTextField("");
		IP.setBounds(120,90, 200, 30);
		frame.add(IP);
		
		final JLabel msg = new JLabel("" + ms);
		msg.setBounds(20,170, 200, 50);
		msg.setForeground(Color.red);
		frame.add(msg);
		
		JButton OK = new JButton("Login");
		OK.setBounds(120,150,100,30);
		
		OK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				ipAddress = IP.getText();
				mName = Name.getText();
				new ChatRoomClient(Name.getText());
				frame.dispose();
			}
		});
		
		frame.add(OK);
		frame.setVisible(true);
		
		it = this;
	}
	
	public static String getIDAddress()
	{
		return ipAddress;
	}
	
	public static String getName()
	{
		return mName;
	}
	
	public static void main(String [] args)
	{
		new LoginFrame("");
	}
}
