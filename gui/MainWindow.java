package gui;

import java.io.*;
import java.net.URL;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import audio.MusicFiles;
import main.GameSettings;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener 
{
	
	private static MainWindow instance;
	
	private InfoPanel infoPanel = new InfoPanel();
	private String title = "Music Game - Be�ll�t�sok";
	private JLabel welcomeLabel = new JLabel("�dv�z�llek, J�t�kos!");
	private JLabel musicFolderInfoLabel = new JLabel(
			"<html>A j�t�k megkezd�s�hez ki kell v�lasztani <br>a digit�lis zen�ket tartalmaz� mapp�t.</html>", JLabel.CENTER);
	private JButton chooseFileButton = new JButton("Tall�z�s...");
	private JTextField chooseFilePathTxt = new JTextField(new File(".").getAbsolutePath());
	private JButton playGameButton = new JButton("Kezd�dj�n a j�t�k!");
	private JButton readMusicButton = new JButton("V�lasztott k�nyvt�r figyel�se");
	private String imgFilePath = "http://progmatinfo.netii.net/disc.jpg";
	private BufferedImage myPicture;
	private JLabel pleaseWaitLabel = new JLabel("K�rem v�rjon...");
	
	public MainWindow() {
		createMainWindow(600, 600);
	}

	public MainWindow(int width, int height) {
		createMainWindow(width, height);
	}

	public void createMainWindow(int width, int height) {
		instance = this;
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		renderElements(width, height);
		setResizable(false);
		setVisible(true);
	}

	public void renderElements(int width, int height) {
		// clean Container
		Container cont = this.getContentPane();
		cont.setLayout(null);
		cont.removeAll();
		cont.repaint();

		// positioning + actionListener
		infoPanel.setBounds(0, 0, width, height/5);
		welcomeLabel.setBounds(width / 4, 20, width / 2, height / 10);
		musicFolderInfoLabel.setBounds(width / 4, 2 * height / 3, width / 2, height / 10);

		chooseFileButton.setBounds(width / 10, 2 * height / 3 + height / 10, width / 4, height / 15);
		chooseFilePathTxt.setBounds(width / 10 + width / 4, 2 * height / 3 + height / 10, width / 4, height / 15);
		chooseFilePathTxt.setEditable(false);
		
		readMusicButton.setBounds(width / 10 + width / 2, 2 * height / 3 + height / 10, width / 3, height / 15);
		playGameButton.setBounds(width / 3, height / 2, width / 3, height / 15);

		pleaseWaitLabel.setBounds(width / 4, 9 * height / 10, width / 2, height / 20);
		pleaseWaitLabel.setForeground(Color.RED);
		pleaseWaitLabel.setVisible(false);

		chooseFileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		readMusicButton.setBackground(Color.GREEN);

		chooseFileButton.setActionCommand("choose file");
		readMusicButton.setActionCommand("read music");
		playGameButton.setActionCommand("play game");
		chooseFileButton.addActionListener(this);
		readMusicButton.addActionListener(this);
		playGameButton.addActionListener(this);
		
		try 
		{
			// add image to the top
			myPicture = ImageIO.read(new URL(imgFilePath));
		//	URL url = this.getClass().getResource("disc.jpg");
			JLabel picLabel = new JLabel((Icon) new ImageIcon(myPicture));
			picLabel.setBounds(width / 3, height / 10, height / 3, height / 3);
			cont.add(picLabel);
		} 
		catch (IOException e) 
		{
			System.out.println("Can not find URL location: " + imgFilePath);
			e.printStackTrace();
		}

		// add to context
		cont.add(infoPanel);
		cont.add(welcomeLabel);
		cont.add(musicFolderInfoLabel);
		cont.add(chooseFileButton);
		cont.add(chooseFilePathTxt);
		cont.add(readMusicButton);
		cont.add(playGameButton);
		cont.add(pleaseWaitLabel);
	}

	public static MainWindow getInstance() {
		return instance;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if (event.getActionCommand().equals("choose file")) 
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(GameSettings.folderName)); // current folder
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			// disable the "All files" option
			chooser.setAcceptAllFileFilterUsed(false);

			int returnVal = chooser.showOpenDialog(getParent());
			if (returnVal == JFileChooser.APPROVE_OPTION) 
			{
				GameSettings.folderName = chooser.getCurrentDirectory().toString() + "/" + chooser.getSelectedFile().getName();
				chooseFilePathTxt.setText(GameSettings.folderName);
				System.out.println("You choose to open this folder: " + GameSettings.folderName);
			} 
			else
				System.out.println("Nothing selected...");
		} 
		else if (event.getActionCommand().equals("read music")) 
		{
			pleaseWaitLabel.setText(GameSettings.folderName+" k�nyvt�r olvas�sa folyamatban...");
			pleaseWaitLabel.setVisible(true);
			playGameButton.setEnabled(false);
			chooseFileButton.setEnabled(false);
			new Thread(new Runnable() {
				@Override
				public void run() 
				{
					//read all data from selected folder
					GameSettings.audioFiles = MusicFiles.getallMusicObjects(GameSettings.folderName);
					GameSettings.artists = MusicFiles.getallArtists(GameSettings.folderName);
					GameSettings.albums = MusicFiles.getAllAlbum(GameSettings.folderName);
					System.out.println(GameSettings.artists);
					
					JOptionPane.showMessageDialog(null, "Elk�sz�lt: " + GameSettings.audioFiles.size() + " audiofile beolvasva.",
							"Feladat elk�sz�lt", JOptionPane.INFORMATION_MESSAGE);
					pleaseWaitLabel.setVisible(false);
					playGameButton.setEnabled(true);
					chooseFileButton.setEnabled(true);
				}
			}).start();
		} 
		else if (event.getActionCommand().equals("play game")) 
		{
			if (GameSettings.audioFiles.size() < GameSettings.answersCount) 
			{
				JOptionPane.showMessageDialog(null,
					"HIBA: Az adatb�zis m�rete kisebb ("+GameSettings.audioFiles.size()+"), mint a minim�lis ("
					+GameSettings.answersCount+")! K�rem t�ltse fel az ablak alj�n l�that� �rlap seg�ts�g�vel.",
					"HIBA!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			@SuppressWarnings("unused")
			GameWindow gameWindow = new GameWindow();
		} 
		else
			System.out.println("No action command: " + event.getActionCommand());
	}
}
