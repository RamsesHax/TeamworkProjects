package capture;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.bytedeco.javacpp.BytePointer;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imencode;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import org.bytedeco.opencv.global.opencv_imgproc;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.opencv.core.CvType;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import databaseMain.ConDatabase;
import databaseMain.ControlPerson;
import databaseMain.ModelPerson;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.IntBuffer;
import java.awt.event.ActionEvent;

public class CaptureFrame extends JFrame {
	private long total;
	private JFrame frame;
	private CaptureFrame.DaemonThread myThread = null;
	private JLabel cameraLabel;
	private JLabel counterLabel;
	private JPanel panel;
	private JButton captureButton;
	private JLabel topTextLabel;
	private JLabel bgdLabel;
	private ImageIcon image;
	
	//JavaCV
	VideoCapture webSource = null;
	Mat cameraImage = new Mat();
	CascadeClassifier cascade = new CascadeClassifier("");
	BytePointer mem = new BytePointer();
	RectVector detectedFaces = new RectVector();
	
	//Vars
	String root;
	int numSamples = 25, sample = 1;
	
	//Utils
	ConDatabase connected = new ConDatabase();
	
	class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;
        
        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    try {
                        if (webSource.grab()) {
                            webSource.retrieve(cameraImage);
                            Graphics g = cameraLabel.getGraphics();

                            Mat imageColor = new Mat(); 
                            imageColor = cameraImage;

                            Mat imageGray = new Mat(); 
                            cvtColor(imageColor, imageGray, COLOR_BGRA2GRAY);
//                            flip(cameraImage, cameraImage, +1);

                            RectVector detectedFaces = new RectVector(); //face detection
                            cascade.detectMultiScale(imageColor, detectedFaces, 1.1, 1, 1, new org.bytedeco.opencv.opencv_core.Size(150, 150),new  org.bytedeco.opencv.opencv_core.Size(500, 500));

                            for (int i = 0; i < detectedFaces.size(); i++) { //cate fete detecteaza
                                Rect dadosFace = detectedFaces.get(0);

                                rectangle(imageColor, dadosFace, new Scalar(255, 255, 0, 2), 3, 0, 0);

                                Mat face = new Mat(imageGray, dadosFace);
                                opencv_imgproc.resize(face, face, new org.bytedeco.opencv.opencv_core.Size(160, 160));

                                if (captureButton.getModel().isPressed()) { //when save button is pressed
                                    if (txt_first_name.getText().equals("") || txt_first_name.getText().equals(" ")) {
                                        JOptionPane.showMessageDialog(null, "Campo vazio");
                                    } else if (txt_first_name.getText().equals("") || txt_first_name.getText().equals(" ")) {
                                        JOptionPane.showMessageDialog(null, "Campo vazio");
                                    } else if (txt_last_name.getText().equals("") || txt_last_name.getText().equals(" ")) {
                                        JOptionPane.showMessageDialog(null, "Campo vazio");
                                    } else if (txt_office.getText().equals("") || txt_office.getText().equals(" ")) {
                                        JOptionPane.showMessageDialog(null, "Campo vazio");
                                    } else {
                                        if (sample <= numSamples) {                                     
                                            String cropped = "C:\\photos\\person." + txt_id_label.getText() + "." + sample + ".jpg";
                                            imwrite(cropped, face);

                                            //System.out.println("Foto " + sample + " capture\n");
                                            counterLabel.setText(String.valueOf(sample) + "/25");
                                            sample++;
                                        }
                                        if (sample > 25) {
                                            new TrainLBPH().trainPhotos();//cand sunt 25 se termina
                                            insertDatabase(); //insert database

                                            System.out.println("File Generated");
                                            stopCamera(); 
                                        }

                                    }
                                }
                            }

                            imencode(".bmp", cameraImage, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
                            BufferedImage buff = (BufferedImage) im;
                            try {
                                if (g.drawImage(buff, 0, 0, 360, 390, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                    if (runnable == false) {
                                        System.out.println("Salve a Foto");
                                        this.wait();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public void generate() {
    	File directory = new File("D:\\SnapshotsTaken");
    	FilenameFilter filter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(".jpg") || name.endsWith(".png");
			}
    		
    	};
    	
    	File[] files = directory.listFiles(filter); // only our filter
    	MatVector photos = new MatVector();
    	Mat labels = new Mat(files.length, 1, CvType.CV_32SC1);
    	IntBuffer labelsBuffer = labels.createBuffer();
        
    	int counter = 0;
    	for(File image : files) {
    	Mat photo = imread(image.getAbsolutePath(), COLOR_BGRA2GRAY);//problema cu importul la imread simplu respectiv GRAYSCALE
    	int idPerson = Integer.parseInt(image.getName().split("\\.")[1]);
    	org.bytedeco.opencv.opencv_core.Size a = new org.bytedeco.opencv.opencv_core.Size(160,160);
    	opencv_imgproc.resize(photo, photo, a);
    	
    	photos.put(counter,photo);
    	labelsBuffer.put(counter, idPerson);
    	counter++;
    	
    	}
    	
    }

    /**
     * This method turns off the software connection with your web cam.
     */
    public void stopCamera() {
        myThread.runnable = false;
        webSource.release();
        dispose();
    }

    /**
     * This method connects the software to the web cam.
     * <br><br>
     * VideoCapture(0); is the default camera on your computer.
     */
    public void startCamera() {
        new Thread() {
            @Override
            public void run() {
                webSource = new VideoCapture(0);
                myThread = new CaptureFrame.DaemonThread(); // vom avea nevoie de o clasa RegisterFace
                Thread t = new Thread(myThread);
                t.setDaemon(true);
                myThread.runnable = true;
                t.start();
            }
        }.start();
    }

    
    public void insertDatabase() {
    	
    		ModelPerson mPerson ;
    		ControlPerson cPerson;
    	
			mPerson = new ModelPerson();
			cPerson = new ControlPerson();
			
			mPerson.setUsername(usernameField.getText());
			mPerson.setEmail(mailField.getText());
			mPerson.setDateOfBirth(dateOfBirthField.getText());
			mPerson.setAddress(addressField.getText());
			
			cPerson.insert(mPerson);
    }


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
	 * @throws IOException 
	 */
	public CaptureFrame() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		setFrame(new JFrame());
		getFrame().setResizable(false);
		getFrame().setBounds(100, 100, 412, 550);
		getFrame().getContentPane().setLayout(null);
		
				
		cameraLabel = new JLabel("");
		cameraLabel.setBounds(67, 73, 271, 287);
		getFrame().getContentPane().add(cameraLabel);
		
		counterLabel = new JLabel("00");
		counterLabel.setHorizontalAlignment(JLabel.CENTER);
		counterLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		counterLabel.setForeground(Color.WHITE);
		counterLabel.setBorder(BorderFactory.createBevelBorder(1));
		counterLabel.setBounds(173, 420, 63, 25);
		getFrame().getContentPane().add(counterLabel);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 434, 511);
		getFrame().getContentPane().add(panel);
		panel.setLayout(null);
		
			
		captureButton = new JButton("CAPTURE");
		captureButton.setBounds(161, 456, 89, 23);
		captureButton.setFocusable(false);
		captureButton.setBorder(BorderFactory.createLineBorder(new Color(29, 192, 242)));
		captureButton.setBackground(new Color(7, 56, 71));
		captureButton.setForeground(Color.WHITE);
		panel.add(captureButton);
		captureButton.addActionListener(new ActionListener() { //de fiecare data cand e apasat se incrementeaza nr de capturi
			public void actionPerformed(ActionEvent e) {
				total ++;
				String counter = String.format("%02d", total); //converteste 1 in 01
					  counterLabel.setText(""+counter);
			}
		});

		
		topTextLabel = new JLabel("CAPTURE 25 SNAPSHOTS");
		topTextLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 21));
		topTextLabel.setForeground(Color.WHITE);
		topTextLabel.setBounds(64, 11, 276, 57);
		topTextLabel.setBorder(BorderFactory.createLineBorder(new Color(29, 192, 242)));
		panel.add(topTextLabel);

		
		
		bgdLabel = new JLabel("");
		image = new ImageIcon();
		bgdLabel.setIcon(image);
		image.setImage(ImageIO.read(getClass().getResource("/img/bgdcapture.jpg"))); 
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