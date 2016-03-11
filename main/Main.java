package main;

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
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
				@SuppressWarnings("unused")
				MainWindow w = new MainWindow(600, 600);
		    }
		});
		
	}
}
