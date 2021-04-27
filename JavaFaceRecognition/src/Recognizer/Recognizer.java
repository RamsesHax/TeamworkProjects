package Recognizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Recognizer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	JPanel bkgPanel;
	JLabel bkgLabel;
	ImageIcon bkgImage ;
	JLabel cameraLabel;
	
	public Recognizer() throws IOException {
		frame = new JFrame();
		getFrame().setLayout(null);
		getFrame().setBounds(250,250, 800, 480);
		
		bkgPanel = new ImagePanel();
		bkgPanel.setBounds(0,0,800,480);
		bkgPanel.setLayout(null);
		
		cameraLabel = new JLabel("");
		cameraLabel.setBounds(262,54,276,342);
		cameraLabel.setBackground(Color.red);
		cameraLabel.setOpaque(true);
		
		bkgPanel.add(cameraLabel);
		getFrame().add(bkgPanel);
		getFrame().setVisible(true);
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

}
