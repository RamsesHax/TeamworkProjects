package capture;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CaptureFrame extends JFrame {
	private long total;
	private JFrame frame;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CaptureFrame window = new CaptureFrame();
					window.setUndecorated(isDefaultLookAndFeelDecorated());
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CaptureFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setResizable(false);
		getFrame().setBounds(100, 100, 412, 550);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		
				
				JLabel cameraLabel = new JLabel("");
				cameraLabel.setBounds(67, 73, 271, 287);
				getFrame().getContentPane().add(cameraLabel);
		
		JLabel counterLabel = new JLabel("00");
		counterLabel.setHorizontalAlignment(JLabel.CENTER);
		counterLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		counterLabel.setForeground(Color.WHITE);
		counterLabel.setBounds(173, 420, 63, 25);
		getFrame().getContentPane().add(counterLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 511);
		getFrame().getContentPane().add(panel);
		panel.setLayout(null);
		
			
				JButton captureButton = new JButton("CAPTURE");
				captureButton.setBounds(161, 456, 89, 23);
				panel.add(captureButton);
				captureButton.addActionListener(new ActionListener() { //de fiecare data cand e apasat se incrementeaza nr de capturi
					public void actionPerformed(ActionEvent e) {
						total ++;
						String counter = String.format("%02d", total); //converteste 1 in 01
					    counterLabel.setText(""+counter);
					}
				});

		
		JLabel topTextLabel = new JLabel("CAPTURE 25 SNAPSHOTS");
		topTextLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 21));
		topTextLabel.setForeground(Color.WHITE);
		topTextLabel.setBounds(67, 11, 296, 57);
		panel.add(topTextLabel);

		
		JLabel bgdLabel = new JLabel("");
		bgdLabel.setIcon(new ImageIcon("C:\\Users\\Alex\\OneDrive\\Desktop\\bgdccccccccp.jpg"));
		bgdLabel.setBounds(0, 0, 406, 521);
		panel.add(bgdLabel);

		
		
	}

	public JFrame getFrame() {
		return frame;
	}
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}