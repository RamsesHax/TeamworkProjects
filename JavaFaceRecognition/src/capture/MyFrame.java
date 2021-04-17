package capture;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.Border;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

public class MyFrame extends JFrame implements ActionListener {
	
	ImageIcon image;
	JButton button;
	JMenuBar menuBar;
	//

	
	MyFrame() {
		
		button = new JButton("Start");
		image = new ImageIcon("logo.jpg");
		menuBar = new JMenuBar();	
		
		this.setTitle("Face Recognition");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(500,550);
		this.setLayout(null);
		this.setIconImage(image.getImage()); // logo-ul aplicatiei
		this.getContentPane().setBackground(Color.LIGHT_GRAY);		
		this.setJMenuBar(menuBar);
		
		button.setBounds(200,5,75,25);
		button.setFocusable(false);
		button.setBorder(BorderFactory.createBevelBorder(1));
		button.setBackground(new Color(66, 245, 209));
		button.setFont(new Font("MV Boli", Font.BOLD, 15));
		
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem loadItem = new JMenuItem("Load");
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem exitItem = new JMenuItem("Exit");
		
		fileMenu.add(loadItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		
		
		this.add(button);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}