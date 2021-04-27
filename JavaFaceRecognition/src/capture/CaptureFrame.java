package capture;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import org.bytedeco.javacpp.BytePointer;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imencode;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.opencv.core.CvType;

import databaseMain.ConDatabase;
import databaseMain.ControlPerson;
import databaseMain.ModelPerson;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.io.IOException;
import java.nio.IntBuffer;

public class CaptureFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	CascadeClassifier cascade = new CascadeClassifier("D:\\SnapshotsTaken\\haarcascade_frontalface_alt.xml");
	BytePointer mem = new BytePointer();
	RectVector detectedFaces = new RectVector();
	
	//Vars
	String root , usernamePerson, mailPerson, dateOfBirthPerson, addressPerson;
	int numSamples = 25, sample = 1;
	public static boolean test;
	
	//Utils
	ConDatabase connected = new ConDatabase();
	
	class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;
        
        @SuppressWarnings("resource")
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
                            opencv_core.flip(cameraImage, cameraImage, +1);

                            RectVector detectedFaces = new RectVector(); //face detection
                            cascade.detectMultiScale(imageColor, detectedFaces, 1.1, 1, 1, new org.bytedeco.opencv.opencv_core.Size(150, 150),new  org.bytedeco.opencv.opencv_core.Size(500, 500));

                            for (int i = 0; i < detectedFaces.size(); i++) { //cate fete detecteaza
                                Rect faceData = detectedFaces.get(0);

                                rectangle(imageColor, faceData, new Scalar(255, 255, 0, 2), 3, 0, 0); 

                                Mat face = new Mat(imageGray, faceData);
                                opencv_imgproc.resize(face, face, new org.bytedeco.opencv.opencv_core.Size(160, 160));

                                if (captureButton.getModel().isPressed()) { //when save button is pressed
                                	if (sample <= numSamples) {                                     
                                            String cropped = "D:\\SnapshotsTaken\\"+ usernamePerson + "." + sample + ".jpg";
                                            imwrite(cropped, face);

                                            //System.out.println("Foto " + sample + " capture\n");
                                            counterLabel.setText(String.valueOf(sample) + "/25");
                                            sample++;
                                        }
                                        if (sample > 25) {
                                            generate();//cand sunt 25 se termina
                                            insertDatabase(); //insert database

                                            System.out.println("File Generated");
                                            stopCamera(); 
                                        }

                                    
                                }
                            }

                            imencode(".bmp", cameraImage, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
                            BufferedImage buff = (BufferedImage) im;
                            try {
                                if (g.drawImage(buff, 0, 0, 360, 390, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                    if (runnable == false) {
                                        System.out.println("All good!");
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
				return name.endsWith(".jpg") || name.endsWith(".png");
			}
    		
    	};
    	
    	File[] files = directory.listFiles(filter); // only our filter
    	MatVector photos = new MatVector(files.length);
    	Mat labels = new Mat(files.length, 1, CvType.CV_32SC1);
    	IntBuffer labelsBuffer = labels.createBuffer();
         
    	int counter = 0;
    	for(File image : files) {
    		Mat photo = imread(image.getAbsolutePath(), COLOR_BGRA2GRAY);
    		int idPerson = Integer.parseInt(image.getName().split("\\.")[1]);
    		org.bytedeco.opencv.opencv_core.Size a = new org.bytedeco.opencv.opencv_core.Size(160,160);
    		opencv_imgproc.resize(photo, photo, a);
    	
    		photos.put(counter,photo);
    		labelsBuffer.put(counter, idPerson);
    		counter++;
    	
    	}
    	FaceRecognizer lbph = LBPHFaceRecognizer.create();
    	lbph.train(photos, labels);
    	lbph.save("D:\\SnapshotsTaken\\classifierLBPH.yml");
    }

    /**
     * This method turns off the software connection with your web cam.
     */
    
    public void stopCamera() {
        myThread.runnable = false;
        webSource.release();
        this.getFrame().dispose();
        test = false;
    }

    /**
     * This method connects the software to the web cam.
     * <br><br>
     * VideoCapture(0); is the default camera on your computer.
     */
    public void startCamera() {
    	test = true;
        new Thread() {
            @Override
            public void run() {
            	
            	webSource = new VideoCapture(0);
                myThread = new CaptureFrame.DaemonThread(); 
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
			
			mPerson.setUsername(usernamePerson); 
			mPerson.setEmail(mailPerson);
			mPerson.setDateOfBirth(dateOfBirthPerson);
			mPerson.setAddress(addressPerson);
			
			cPerson.insert(mPerson);
    }


	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 * @throws IOException 
	 */
    public class WarningFrame {  
		JFrame warning;  
		WarningFrame(){  
		    warning = new JFrame();  
		    JOptionPane.showMessageDialog(warning,"All the fields have to be valid","Warning",JOptionPane.WARNING_MESSAGE);     
		}
	}
	public CaptureFrame() {
		
	}
	
	public CaptureFrame(String username, String mail , String dateOfBirth, String addressField) throws IOException {
		if(username.isBlank() || mail.isBlank() || dateOfBirth.isBlank() || addressField.isBlank() || !mail.contains("@")){
			new WarningFrame();
			stopCamera();
		}else {
			initialize();
		}
		
		
		usernamePerson = username;
		mailPerson = mail;
		dateOfBirthPerson = dateOfBirth;
		addressPerson = addressField;
		
		startCamera();
	}

	private void initialize() throws IOException {
		setFrame(new JFrame());
		getFrame().setResizable(false);
		getFrame().setBounds(100, 100, 823, 330);
		getFrame().getContentPane().setLayout(null);
		
		JLabel bkgCameraLabel = new JLabel();
		bkgCameraLabel.setBounds(0 , 0 , 380 , 300);
		bkgCameraLabel.setBackground(new Color(5,20,31));
		bkgCameraLabel.setOpaque(true);
		getFrame().getContentPane().add(bkgCameraLabel);
		
		cameraLabel = new JLabel();
		cameraLabel.setBounds(15, 17, 350, 260);
		getFrame().getContentPane().add(cameraLabel);
		
		counterLabel = new JLabel("00");
		counterLabel.setHorizontalAlignment(JLabel.CENTER);
		counterLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		counterLabel.setForeground(Color.WHITE);
		counterLabel.setBounds(436, 150, 200, 100);
		getFrame().getContentPane().add(counterLabel);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 823, 300);
		getFrame().getContentPane().add(panel);
		panel.setLayout(null);
		
			
		captureButton = new JButton("CAPTURE");
		captureButton.setBounds(478, 100, 120, 45);
		captureButton.setFocusable(false);
		captureButton.setBorder(BorderFactory.createLineBorder(new Color(29, 192, 242)));
		captureButton.setBackground(new Color(7, 56, 71));
		captureButton.setForeground(Color.WHITE);
		panel.add(captureButton);

		
		topTextLabel = new JLabel("CAPTURE 25 SNAPSHOTS");
		topTextLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 21));
		topTextLabel.setForeground(Color.WHITE);
		topTextLabel.setBounds(400, 11, 276, 60);
		panel.add(topTextLabel);

		
		
		bgdLabel = new JLabel("");
		image = new ImageIcon();
		bgdLabel.setIcon(image);
		image.setImage(ImageIO.read(getClass().getResource("/img/CaptureFrame.jpg"))); 
		bgdLabel.setBounds(0, 0, 823, 300);
		panel.add(bgdLabel);

		
		getFrame().setVisible(true);
		
	}

	public JFrame getFrame() {
		return frame;
	}
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
}