package chatting.application;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Client implements ActionListener {
	
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	
	JPanel p1;
	static JPanel a1;
	static JFrame f1 = new JFrame();
	JTextField t1;
	JButton b1;
	static Box vertical = Box.createVerticalBox();
	
	Boolean typing;
	
	Client(){
		
		f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(Color.decode("#009688"));
		p1.setBounds(0, 0, 450, 70);
		f1.add(p1);
		
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
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/bunty.png"));
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
		
		JLabel l3 = new JLabel("Bunty");
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
		
		a1 = new JPanel();
		a1.setBounds(5, 75, 440, 575);
		//a1.setBackground(Color.PINK);
		a1.setFont(new Font("SANS_SERIF", Font.PLAIN, 15));
		f1.add(a1);
		
		t1 = new JTextField();
		t1.setBounds(5, 655, 340, 40);
		t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f1.add(t1);
		
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
		f1.add(b1);
		
		f1.setLayout(null);
		f1.setSize(450,700);
		f1.setLocation(1000, 200);
		f1.setUndecorated(true);
		f1.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		try {
			String out = t1.getText();
			
			JPanel p2 = formatLabel(out);
			
			a1.setLayout(new BorderLayout());
			
			JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            
            a1.add(vertical, BorderLayout.PAGE_START);
            
            t1.setText("");
			dout.writeUTF(out);
		} catch (Exception e) {System.out.println(e);}
		
	}
	public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        
        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        l1.setBackground(Color.decode("#DCF8C6"));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        p3.add(l1);
        p3.add(l2);
        return p3;
    }
	
	public static void main(String[] args) {
		new Client().f1.setVisible(true);
		
		
		try {
			
			s = new Socket("127.0.0.1",6001);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			String msginput = "";
			
			while(true) {
				a1.setLayout(new BorderLayout());
		        msginput = din.readUTF();
            	JPanel p2 = formatLabel(msginput);
                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);
                
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f1.validate();
			}
			
		}catch(Exception e) {System.out.println(e);}
	}

}
