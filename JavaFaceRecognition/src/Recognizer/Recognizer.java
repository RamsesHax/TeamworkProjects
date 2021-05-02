package Recognizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Array;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;

import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;

import capture.CaptureFrame;
import databaseMain.ConDatabase;

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
	JLabel usernameLabel;
	JLabel mailLabel;
	JLabel dateOfBirthLabel;
	JLabel addressLabel;
	
	private Recognizer.DaemonThread myThread = null;
	
	//JavaCV
		VideoCapture webSource = null;
		Mat cameraImage = new Mat();
		CascadeClassifier cascade = new CascadeClassifier("D:\\SnapshotsTaken\\haarcascade_frontalface_alt.xml");
		BytePointer mem = new BytePointer();
		RectVector detectedFaces = new RectVector();
		LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();;
		
		//Vars
		String root , usernamePerson, mailPerson, dateOfBirthPerson, addressPerson;
		int numSamples = 25, sample = 1;
		int idPerson;
		
		//Utils
		ConDatabase connected = new ConDatabase();
	
	public Recognizer() throws IOException, InterruptedException {
		frame = new JFrame();
		getFrame().setLayout(null);
		getFrame().setBounds(250,250, 800, 480);
		
		bkgPanel = new ImagePanel();
		bkgPanel.setBounds(0,0,800,480);
		bkgPanel.setLayout(null);

		cameraLabel = new JLabel("");
		cameraLabel.setBounds(262,54,276,342);
		
		
				
		usernameLabel = new JLabel("");
		usernameLabel.setBounds(30, 115, 200, 35);
		usernameLabel.setBackground(new Color(6,16,22));
		usernameLabel.setForeground(Color.white);
		usernameLabel.setBorder(BorderFactory.createEtchedBorder());
		usernameLabel.setOpaque(true);
		bkgPanel.add(usernameLabel);
			        
		
		bkgPanel.add(cameraLabel);
		getFrame().add(bkgPanel);
		getFrame().setVisible(true);
		
		
		recognizer.read("D:\\SnapshotsTaken\\classifierLBPH.yml");
		recognizer.setThreshold(80);
		
		startCamera();
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	class DaemonThread implements Runnable{
		
		protected volatile boolean runnable = false;
		
		@Override
		public void run() {
			synchronized(this) {
				while(runnable) {
					try {
						if(webSource.grab()) {
							webSource.retrieve(cameraImage);
							Graphics g = cameraLabel.getGraphics();
							
							Mat imageGray = new Mat();
							org.bytedeco.opencv.global.opencv_imgproc.cvtColor(cameraImage, imageGray, COLOR_BGRA2GRAY);
							//opencv_core.flip(cameraImage, cameraImage, +1);
							
							RectVector detectedFace = new RectVector();
							cascade.detectMultiScale(imageGray, detectedFace, 1.1, 2, 0, new org.bytedeco.opencv.opencv_core.Size(150,150), new org.bytedeco.opencv.opencv_core.Size(500,500));
							
							for(int i = 0 ; i< detectedFace.size(); i++) {
								Rect faceData = detectedFace.get(i);
								org.bytedeco.opencv.global.opencv_imgproc.rectangle(cameraImage, faceData, new Scalar(0,255,0,3), 3, 0, 0);
								Mat capturedFace = new Mat(imageGray, faceData);
								opencv_imgproc.resize(capturedFace, capturedFace, new Size(160,160));
								
								IntPointer tag = new IntPointer(1);
								DoublePointer trust = new DoublePointer(1);
								recognizer.predict(capturedFace, tag, trust);
								int prediction = tag.get(0);
								String name = usernamePerson;
								
								if(prediction == -1) {
									org.bytedeco.opencv.global.opencv_imgproc.rectangle(cameraImage, faceData, new Scalar(0,255,0,3), 3, 0, 0);
									usernameLabel.setText("nedescoperit");
									idPerson = 0 ;
									
								}else {
									org.bytedeco.opencv.global.opencv_imgproc.rectangle(cameraImage, faceData, new Scalar(0,255,0,3), 3, 0, 0);
									System.out.println(trust.get(0));
									idPerson = prediction;
									System.out.println(idPerson);
									rec();
								}
								
								org.bytedeco.opencv.global.opencv_imgcodecs.imencode(".bmp", cameraImage, mem);
								Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
								
								BufferedImage buff = (BufferedImage) im;
								
								if(g.drawImage(buff, 0, 0, 360, 390, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
									if(runnable == false) {
										this.wait();
									}
								}
							}
						}
					}catch(IOException | InterruptedException e) {
						
					}
				}
			}
			
		}
		
	}
	
	private void rec() {
		SwingWorker worker = new SwingWorker() {

			@Override
			protected Object doInBackground() throws Exception {
				connected.connect();
				try {
					String SQL = "SELECT * FROM accounts WHERE ID = " + idPerson ;
					connected.execSQL(SQL); 
					while(connected.resultSet.next()) {
						usernameLabel.setText(connected.resultSet.getString("user"));
						mailLabel.setText(connected.resultSet.getString("email"));
						dateOfBirthLabel.setText(connected.resultSet.getString("date"));
						addressLabel.setText(connected.resultSet.getString("address"));
						
						System.out.println("Person: " + connected.resultSet.getString("user"));
						
						Array ident = connected.resultSet.getArray(2);
						String[] person = (String[]) ident.getArray();
						
						for(int i = 0 ; i<person.length; i++) {
							System.out.println(person[i]);
						}
						
					}
					
				}catch(Exception e) {
					
				}
				
				connected.disconnect();
				return null;
			}
		};
		worker.execute();
	}
	
    public void stopCamera() {
        myThread.runnable = false;
        webSource.release();
        this.getFrame().dispose();
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
                myThread = new Recognizer.DaemonThread(); 
                Thread t = new Thread(myThread);
                t.setDaemon(true);
                myThread.runnable = true;
                t.start();
            	
            }
        }.start();
    }
}
