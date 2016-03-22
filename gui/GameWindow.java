package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import audio.Music;
import main.GameSettings;
import main.Question;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener, MouseListener
{
	private static GameWindow instance;
	
	private InfoPanel infoPanel = new InfoPanel();
	private String title = "Music Game!";
	private JLabel questionLabel = new JLabel();
	private JButton[] buttons = new JButton[GameSettings.answersCount];
	private Question quest;
	private Thread musicThread;
	private String altFile = "disc.jpg";
	private BufferedImage coverImage = null;
	private JLabel picLabel = new JLabel();
	
	private boolean multiClicked = false;
	
	public GameWindow() 
	{
		createGameWindow(600, 600);
		game();		
	}
	
	public GameWindow(int width, int height)
	{	
		createGameWindow(width, height);
		game();
	}
	
	/** Create a question, set the button labels.
	 *	plays a random part from the music (in other Tread)
	 **/
	public void game()
	{	
		infoPanel.setLife(GameSettings.lifeCount);
		infoPanel.setChangable(false);
		
		quest = new Question();
		questionLabel.setText(quest.getQuestionLine());
		
		//Set cover image
		coverImage = quest.getCurrentMusic().getImage();
		picLabel.setBounds(getWidth()/3, getHeight()/10, getHeight()/3, getHeight()/3);
		setPicLabel(coverImage);
		
		for (int i=0; i<buttons.length; ++i)
		{
			buttons[i].setText(quest.getAnswers().get(i));
			//good answer action command:
			if (i==quest.getGoodAnswerIndex())
				buttons[i].setActionCommand("Good answer");
			else 
				buttons[i].setActionCommand("Bad answer");
		}
		
		musicThread = new Thread(new Runnable(){
			@Override
			public void run() 
			{
				Music currentMusic = quest.getCurrentMusic();
				currentMusic.playRandomPart(20);
			}		
		});

		renderElements(getWidth(), getHeight());
		musicThread.start();
	}
	
	
	/** Create and set the Game Window. Used in constructors.
	 *  @param int width - Window width 
	 *  @param int height - Window height 
	 **/
	public void createGameWindow(int width, int height) 
	{
		instance=this;
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameSettings.lifeCount = GameSettings.starterLifeCount;
		setPicLabel(null);
		
		for (int i=0; i<buttons.length; ++i)
		{
			buttons[i] = new JButton();
			buttons[i].addActionListener(this);
			buttons[i].addMouseListener(this);
		}
		renderElements(width, height);
		
		 //Resize window listener
		 this.getRootPane().addComponentListener(new ComponentAdapter() {
	            public void componentResized(ComponentEvent e) {
	                // This is only called when the user releases the mouse button.
	                renderElements(getWidth(), getHeight());
	            }
	        });

		setVisible(true);	
	}
	
	/** Clean the container, set bounds, and add new Elements to the container
	 *  @param int width - Window width 
	 *  @param int height - Window height 
	 **/
	public void renderElements(int width, int height)
	{
		//Clean the container:
		Container cont = this.getContentPane();
		cont.setLayout(null);
		cont.removeAll();
		cont.repaint();
		
		infoPanel.setBounds(0, 0, width, height/5);
		cont.add(infoPanel);
		
		//Buttons: set bounds and add to container
		for (int i=0; i<buttons.length; ++i)
		{
			buttons[i].setBackground(Color.GRAY);
			buttons[i].setBounds(width/3, (5+i)*height/10, width/3, height/10);
			cont.add(buttons[i]);
		}
		
		questionLabel.setBounds(width/3, height/2-50, 2*width/3, 50);
		questionLabel.setFont(new Font("Serif", Font.PLAIN, 28));
		cont.add(questionLabel);
		
		picLabel.setBounds(width/2-height/8, height/5, height/4, height/4);
		cont.add(picLabel);	
	}
		
   /** Resize an image using a Graphics2D object backed by a BufferedImage.
	*  @param srcImg - source image to scale
	*  @param width - desired width
	*  @param height - desired height
	*  @return - the new resized image
	*/
	public BufferedImage scaleImage(BufferedImage image, int width, int height) 
	{
       int type = (image.getType() == 0)? BufferedImage.TYPE_INT_ARGB : image.getType();
       BufferedImage resizedImage = new BufferedImage(width, height, type);
       Graphics2D graphics = resizedImage.createGraphics();
       graphics.drawImage(image, 0, 0, width, height, null);
       graphics.dispose();
       return resizedImage;
    }
	
	public static GameWindow getInstance(){
		return instance;
	}
	
	public InfoPanel getInfoPanel(){
		return infoPanel;
	}
	
	
	/** Set the Picture Label to the Music's Cover Image if exist. 
	 *  If not exists, use a default image file.
	*   @param BufferedImage coverImage - Image to add to Label.
	*/
	public void setPicLabel(BufferedImage coverImage)
	{
		if (coverImage!=null) //have cover image
		{
			//coverImage = scaleImage(coverImage, picLabel.getWidth()+1, picLabel.getHeight()+1);
			picLabel.setIcon(new ImageIcon(coverImage));
		}
		else //no cover image -> default
		{
			try 
			{
				coverImage = ImageIO.read(new File(altFile));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				return;
			}
			//coverImage = scaleImage(coverImage, picLabel.getWidth()+1, picLabel.getHeight()+1);
			picLabel.setIcon(new ImageIcon(coverImage));
		}
	}

	/** Event Handler method */
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if (multiClicked)
			return;
		
		try 
		{
			//New timer: Show new Question after answer, with 2 sec delay
			Timer timer = new Timer(2000, new ActionListener() {
			    public void actionPerformed(ActionEvent evt) 
			    {
					game();
					((Timer)evt.getSource()).stop();
			    }    
			});	
			
			//Good answer is green
			int index = quest.getGoodAnswerIndex();
			buttons[index].setBackground(Color.GREEN);
			
			//if it's a bad answer
			if (!event.getActionCommand().equals("Good answer"))
			{
				for (int i=0; i<buttons.length; ++i)
				{
					//Bad answer is red
					if (buttons[i].equals(event.getSource()))
					{
						buttons[i].setBackground(Color.RED);
						buttons[i].repaint();
					}
				}
				--GameSettings.lifeCount;
				System.out.println(GameSettings.lifeCount+" life remaining.");
			}
			else
			{
				GameSettings.score+=100;
				infoPanel.setScore(GameSettings.score);
			}

			if (GameSettings.lifeCount == 0) //no more life
			{
				JOptionPane.showMessageDialog(null, 
						"Vége a játéknak! Elfogyott az életed. Pontszámod: "+GameSettings.score);
				musicThread.stop();
				musicThread = null;
				GameSettings.lifeCount = GameSettings.starterLifeCount;
				GameSettings.score = 0;
				this.dispose(); //exit from the window
			}
			
			//GENERAL:
			musicThread.stop(); //I know it's deprected... but currently works...
			timer.start();  //Start timer = Show new question, after 2 seconds
			
		//	Thread.sleep(1000);
						
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
	    if (e.getClickCount() < 2) 
	    {
	        System.out.println("double click");
	        multiClicked = false;
	    } 
	    else 
	    {
	        multiClicked = true;
	        try 
	        {
				Thread.sleep(500);
			} catch (InterruptedException e1) 
	        {
				e1.printStackTrace();
			}
	    }
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		multiClicked = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
