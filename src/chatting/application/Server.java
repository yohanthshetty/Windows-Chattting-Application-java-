package chatting.application;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

import javax.swing.*;

public class Server extends JFrame implements ActionListener {
	
	static ServerSocket skt;
	static Socket s;
	
	static DataInputStream din;
	static DataOutputStream dout;
	
	JPanel p1;
	JTextField t1;
	JButton b1;
	static JTextArea a1;
	
	Boolean typing;
	
	Server(){
		
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(Color.decode("#009688"));
		p1.setBounds(0, 0, 450, 70);
		add(p1);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/back_arrow.png"));
		Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l1 = new JLabel(i3);
		l1.setBounds(5, 17, 30, 30);
		p1.add(l1);
		
		l1.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/nawaz.png"));
		Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel l2 = new JLabel(i6);
		l2.setBounds(40, 5, 60, 60);
		p1.add(l2);
		
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/menu.png"));
		Image i8 = i7.getImage().getScaledInstance(11, 25, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel l5 = new JLabel(i9);
		l5.setBounds(408, 20, 11, 25);
		p1.add(l5);
		
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/video.png"));
		Image i11 = i10.getImage().getScaledInstance(22, 35, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel l6 = new JLabel(i12);
		l6.setBounds(350, 15, 22, 35);
		p1.add(l6);
		
		ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/phone.png"));
		Image i14 = i13.getImage().getScaledInstance(27, 35, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel l7 = new JLabel(i15);
		l7.setBounds(300, 16, 27, 35);
		p1.add(l7);
		
		JLabel l3 = new JLabel("Nawaz");
		l3.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
		l3.setForeground(Color.WHITE);
		l3.setBounds(110, 20, 100, 20);
		p1.add(l3);
		
		JLabel l4 = new JLabel("Active Now");
		l4.setFont(new Font("SANS_SERIF", Font.PLAIN, 15));
		l4.setForeground(Color.WHITE);
		l4.setBounds(110, 45, 100, 15);
		p1.add(l4);
		
		Timer t = new Timer(1, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!typing) {
					l4.setText("Active Now");
				}
			}
		});
		
		t.setInitialDelay(2000);
		
		a1 = new JTextArea();
		a1.setBounds(5, 75, 440, 575);
		//a1.setBackground(Color.PINK);
		a1.setFont(new Font("SANS_SERIF", Font.PLAIN, 15));
		a1.setEditable(false);
		a1.setLineWrap(true);
		a1.setWrapStyleWord(false);
		add(a1);
		
		t1 = new JTextField();
		t1.setBounds(5, 655, 340, 40);
		t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		add(t1);
		
		t1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				l4.setText("typing...");
				
				t.stop();
				
				typing = true;
			}
			public void keyReleased(KeyEvent ke) {
				typing = false;
				if(!t.isRunning()) t.start();
			}
		});
		
		
		b1 = new JButton("Send");
		b1.setBounds(350, 657, 90, 35);
		b1.setFont(new Font("SANS_SERIF", Font.PLAIN, 16));
		b1.setBackground(Color.decode("#009688"));
		b1.setForeground(Color.WHITE);
		b1.addActionListener(this);
		add(b1);
		
		setLayout(null);
		setSize(450,700);
		setUndecorated(true);
		setVisible(true);
		setLocation(400, 200);
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		try {
			String out = t1.getText();
			a1.setText(a1.getText()+"\n\t\t\t"+out);
			t1.setText("");
			dout.writeUTF(out);
		} catch(Exception e) {System.out.println(e);}
	}
	
	public static void main(String[] args) {
		new Server().setVisible(true);
		
		String msginput = "";
		while(true) {
			try {
				skt = new ServerSocket(6001);
				s = skt.accept();
				
				din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());
				
				msginput = din.readUTF();
				a1.setText(a1.getText()+"\n"+msginput);
				
				skt.close();
				s.close();
				
			}catch(Exception e) {System.out.println(e);}
		}
	}

}
