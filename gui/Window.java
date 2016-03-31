package gui;

import java.awt.Container;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import main.Main;

@SuppressWarnings("serial")
public class Window extends JFrame
{

	private static Window instance;
	//private int width, height;
	
	private ButtonPanel pnlButton;
	private FieldPanel pnlFields;
	
	private final String title = "Music Game!";
	
	public Window(int width, int height)
	{
		instance=this;
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		renderElements(width, height);
		
		//Resize window listener
		 this.getRootPane().addComponentListener(new ComponentAdapter() {
	            public void componentResized(ComponentEvent e) {
	                // This is only called when the user releases the mouse button.
	          
	               // System.out.println("componentResized:"+getWidth()+"x"+getHeight());
	                renderElements(getWidth(), getHeight());
	            }
	        });
		
	//	setResizable(false);
		setVisible(true);
	   
	/*	try {
			AudioInputStream ais =  AudioSystem.getAudioInputStream(
			        Main.class.getResourceAsStream("music1.mp3"));
			Clip clip = AudioSystem.getClip();
		    clip.open(ais);
		    clip.start();
			
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	*/
		//play("music1.mp3");
		
	}
	
	
	public void play(String filename)
	   {
	     // Instantiate BasicPlayer.
	    BasicPlayer player = new BasicPlayer();
	    // BasicPlayer is a BasicController.
	    BasicController control = (BasicController) player;
	    // Register BasicPlayerTest to BasicPlayerListener events.
	    // It means that this object will be notified on BasicPlayer
	    // events such as : opened(...), progress(...), stateUpdated(...)
	    player.addBasicPlayerListener((BasicPlayerListener) this);
	   

	try
	   { 
	    // Open file, or URL or Stream (shoutcast, icecast) to play.
	    control.open(new File(filename));

	    // control.open(new URL("http://yourshoutcastserver.com:8000"));

	    // Start playback in a thread.
	    control.play();
	    
	   

	    // If you want to pause/resume/pause the played file then
	    // write a Swing player and just call control.pause(),
	    // control.resume() or control.stop(). 
	    // Use control.seek(bytesToSkip) to seek file
	    // (i.e. fast forward and rewind). seek feature will
	    // work only if underlying JavaSound SPI implements
	    // skip(...). True for MP3SPI and SUN SPI's
	    // (WAVE, AU, AIFF).

	   // Set Volume (0 to 1.0).
	   // control.setGain(0.85);
	    // Set Pan (-1.0 to 1.0).
	   // control.setPan(0.0);
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  }
	}
	 
	
	
	
	
	public void renderElements(int width, int height)
	{
		//Kontener:
		Container cont = this.getContentPane();
		cont.setLayout(null);
		cont.removeAll();
		cont.repaint();

		
		JLabel questionLabel = new JLabel("What is the title of the music?");
		questionLabel.setBounds(width/3, height/3, 200, 50);
		JButton button1 = new JButton("What is love?");
		button1.setBounds(width/3, (int) (0.5*height), width/3, height/10);
		JButton button2 = new JButton("Baby don't Herz me?");
		button2.setBounds(width/3, (int) (0.6*height), width/3, height/10);
		JButton button3 = new JButton("Don't Herz me!");
		button3.setBounds(width/3, (int) (0.7*height), width/3, height/10);
		JButton button4 = new JButton("No Morse!");
		button4.setBounds(width/3, (int) (0.8*height), width/3, height/10);
		
		cont.add(questionLabel);
		cont.add(button1);
		cont.add(button2);
		cont.add(button3);
		cont.add(button4);
		//cont.add(pnlButton, BorderLayout.LINE_START);
		//cont.add(pnlButton);
	}
	

	public static Window getInstance(){
		return instance;
	}
	
	
	public FieldPanel getFieldPanel(){
		return pnlFields;
		
	}
}
