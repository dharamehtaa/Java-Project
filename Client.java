//FINAL CLIENT
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;  // Import for File
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.util.*;
import java.text.*;
import java.io.*;
import java.net.*;


 //class Client extends JFrame implements ActionListener{		//also works
 	class Client implements ActionListener{

 	JTextField text;
 	static JPanel a1;
 	static Box vertical = Box.createVerticalBox();
 	static DataOutputStream dout;
 	static JFrame f = new JFrame();

	Client(){
		f.setLayout(null);
		f.setSize(450,700);
		f.setLocation(200,50);
		f.setUndecorated(true);	//removes the minimise, exit button from top
		f.setBackground(Color.WHITE);


		JPanel p1 = new JPanel();
		p1.setBounds(0,0,450,70);
		p1.setBackground(new Color(7,94,84));
		p1.setLayout(null);
		f.add(p1);

		//ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		//Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);

		//ImageIcon i3 = new ImageIcon(i2);

		try{
		String path = "3.png";
		//URL url = new URL(path);

		BufferedImage image = ImageIO.read(new File(path));

		Image resizedImage = image.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon resizedIcon = new ImageIcon(resizedImage);
            
        JLabel back = new JLabel(resizedIcon);

        back.setBounds(11,25,30,25);
		p1.add(back);

		back.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent ae){
					System.exit(0);
				}
		});

		}
	catch(IOException e){}

	try{
		String path2 = "2.png";

		BufferedImage image2 = ImageIO.read(new File(path2));

		Image resizedImage2 = image2.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
		ImageIcon resizedIcon2 = new ImageIcon(resizedImage2);
            
        JLabel pf = new JLabel(resizedIcon2);

        pf.setBounds(15,15,110,40);
		p1.add(pf);

		}
	catch(IOException e){}

	try{
		String path3 = "video.png";

		BufferedImage image3 = ImageIO.read(new File(path3));

		Image resizedImage3 = image3.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon resizedIcon3 = new ImageIcon(resizedImage3);
            
        JLabel vid = new JLabel(resizedIcon3);

        vid.setBounds(300,15,110,40);
		p1.add(vid);

		}
	catch(IOException e){}

	try{
		String path4 = "phone.png";

		BufferedImage image4 = ImageIO.read(new File(path4));

		Image resizedImage4 = image4.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon resizedIcon4 = new ImageIcon(resizedImage4);
            
        JLabel phone = new JLabel(resizedIcon4);

        phone.setBounds(340,15,110,40);
		p1.add(phone);

		}
	catch(IOException e){}

	try{
		String path5 = "3icon.png";

		BufferedImage image5 = ImageIO.read(new File(path5));

		Image resizedImage5 = image5.getScaledInstance(10, 20, Image.SCALE_DEFAULT);
		ImageIcon resizedIcon5 = new ImageIcon(resizedImage5);
            
        JLabel more = new JLabel(resizedIcon5);

        more.setBounds(375,15,110,40);
		p1.add(more);

		}
	catch(IOException e){}


	JLabel name = new JLabel("Mr. Shah");
	name.setBounds(110,15,100,18);
	name.setForeground(Color.WHITE);
	name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
	p1.add(name);

	JLabel status = new JLabel("Online");
	status.setBounds(110,35,100,18);
	status.setForeground(Color.WHITE);
	status.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
	p1.add(status);

	a1 = new JPanel();
	a1.setBounds(5,75,440,570);
	f.add(a1);

	text = new JTextField();
	text.setBounds(5,655,310,40);
	text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
	f.add(text);

	JButton send  = new JButton("Send");
	send.setBounds(320,655,123,40);
	send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
	send.setBackground(new Color(7,94,84));
	send.setForeground(Color.WHITE);
	send.addActionListener(this);
	f.add(send);
	

	f.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae){
		try{
		String out = text.getText();

		//JLabel output = new JLabel(out);
		//JPanel p2 = new JPanel();
		//p2.add(output);

		JPanel p2 = formatLabel(out);
		
		a1.setLayout(new BorderLayout());

		JPanel right = new JPanel(new BorderLayout());
		right.add(p2, BorderLayout.LINE_END);
		vertical.add(right); 		//output will be displayed in a box to the right
		vertical.add(Box.createVerticalStrut(15));

		a1.add(vertical, BorderLayout.PAGE_START);

		dout.writeUTF(out);		//to send text

		text.setText("");

		//once the text is sent, panel a1 needs to be repainted to display the sent text(output)
		f.repaint();
		f.invalidate();
		f.validate();
	}
	catch(Exception e){
		//e.printStackTrace();
	}

}

	public static JPanel formatLabel(String out){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel output = new JLabel(out);
		output.setFont(new Font("Tahoma", Font.PLAIN, 16));
		output.setBackground(new Color(189,212,184));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15,15,15,50));

		panel.add(output);
			//---------------
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		JLabel time  = new JLabel();
		time.setText(sdf.format(cal.getTime()));

		panel.add(time);
			//--------------

		return panel;

	}


	public static void main(String[] args) {
		new Client();

		try{
			Socket s = new Socket("127.0.0.1", 6001);	//6001 is a port number which listens to connections
			//127.0.0.1 is the server's IP address used to maintain socket connection 

			while(true){
				DataInputStream din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());

				while(true){		//FOR INCOMING MSGS
					a1.setLayout(new BorderLayout());
					String msg = din.readUTF();		//Reads a UTF-encoded message ie 'din' (the message sent by the client)
					JPanel panel = formatLabel(msg);

					JPanel left = new JPanel(new BorderLayout());
					left.add(panel, BorderLayout.LINE_START);
					vertical.add(left);

					vertical.add(Box.createVerticalStrut(15));
					a1.add(vertical, BorderLayout.PAGE_START);

					f.validate();

				}
			}
		}
		catch(Exception e){
			//e.printStackTrace();
		}
	}
}