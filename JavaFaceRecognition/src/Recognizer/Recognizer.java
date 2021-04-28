package Recognizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	
	private Recognizer.DaemonThread myThread = null;
	
	//JavaCV
		VideoCapture webSource = null;
		Mat cameraImage = new Mat();
		CascadeClassifier cascade = new CascadeClassifier("D:\\SnapshotsTaken\\haarcascade_frontalface_alt.xml");
		BytePointer mem = new BytePointer();
		RectVector detectedFaces = new RectVector();
		LBPHFaceRecognizer recognizer;
		
		//Vars
		String root , usernamePerson, mailPerson, dateOfBirthPerson, addressPerson;
		int numSamples = 25, sample = 1;
		
		//Utils
		ConDatabase connected = new ConDatabase();
	
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
							
							RectVector detectedFace = new RectVector();
							cascade.detectMultiScale(imageGray, detectedFace, 1.1, 2, 0, new org.bytedeco.opencv.opencv_core.Size(150,150), new org.bytedeco.opencv.opencv_core.Size(500,500));
							
							for(int i = 0 ; i< detectedFaces.size(); i++) {
								Rect faceData = detectedFace.get(i);
								org.bytedeco.opencv.global.opencv_imgproc.rectangle(cameraImage, faceData, new Scalar(0,255,0,0));
								Mat capturedFace = new Mat(imageGray, faceData);
								opencv_imgproc.resize(capturedFace, capturedFace, new Size(160,160));
								
								IntPointer tag = new IntPointer(1);
								DoublePointer trust = new DoublePointer(1);
								recognizer.predict(capturedFace, tag, trust);
								int prediction = tag.get(0);
								String name = null;
								
								if(prediction == -1) {
								}else {
									System.out.println(trust.get(0));
									rec();
								}
								
								org.bytedeco.opencv.global.opencv_imgcodecs.imencode(".bmp", cameraImage, mem);
								Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
								
								BufferedImage buff = (BufferedImage) im;
								
								if(g.drawImage(buff, 0, 0, getWidth(), getHeight() - 100, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
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
		
	}
	
}
