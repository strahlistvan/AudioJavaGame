package gui;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;;

@SuppressWarnings("serial")
public class HelpWindow extends JFrame 
{
	private int width = 500, height = 500;
	private String title = "AudioJavaGame súgója";
	private String helpHTML = "helpme.html";
	private JTextPane helpText = new JTextPane();
	
	public HelpWindow() 
	{
		setTitle(title);
		setSize(width, height);
		
		Container cont = this.getContentPane();
		
		helpText.setContentType("text/html");
		try 
		{
			String text = readTextFromFile(helpHTML);
			helpText.setText(text);
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(null, "HIBA! Fájl nem található: "+helpHTML);
			helpText.setText("<h1>Leírás</h1> <br />habla habla");
			ex.printStackTrace();
		}
		helpText.setBounds(width/5, height/5, 4*width/4, 4*height/5);
		helpText.setEditable(false);
		cont.add(helpText);
		
		setResizable(false);
		setVisible(true);
	}
	
	private String readTextFromFile(String fileName) throws IOException
	{
		String result = "";
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		
		String line = reader.readLine();
		while(line!=null)
		{
			result+=line;
			line = reader.readLine();
		}
		System.out.println(result);
		return result;
	}
}
