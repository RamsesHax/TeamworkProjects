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
	
	private JFrame frame = new JFrame();
	JPanel bkgPanel = new JPanel();
	JLabel bkgImage = new JLabel();
	ImageIcon bkg ;
	
	public Recognizer() throws IOException {
		getFrame().setLayout(null);
		getFrame().setBounds(250,250, 800, 480);
		
		bkgPanel.setBounds(0,0,800,450);
		
		bkgImage.setBounds(0,0,800,450);
		bkg = new ImageIcon();
		bkg.setImage(ImageIO.read(getClass().getResource("/img/recog.jpg")));
		bkgImage.setIcon(bkg);
		
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
