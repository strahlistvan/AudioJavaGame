package main;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.SwingUtilities;

import gui.MainWindow;

/**
 * @author Istvan
 *
 */
public class Main 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try 
		{
			PrintStream logfile = new PrintStream("logfile.txt");
			System.setOut(logfile);
			System.setErr(logfile);
		} 
		catch (FileNotFoundException ex) 
		{
			System.out.println("Can not create log file:");
			ex.printStackTrace();
		}
	
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
				@SuppressWarnings("unused")
				MainWindow w = new MainWindow(600, 600);
		    }
		});
	}
}
