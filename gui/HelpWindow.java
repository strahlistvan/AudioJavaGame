package gui;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;;

@SuppressWarnings("serial")
public class HelpWindow extends JFrame implements HyperlinkListener
{
	private int width = 500, height = 500;
	private String title = "AudioJavaGame súgója";
	private String helpHTML = "helpme.html";
	private JTextPane helpText = new JTextPane();			//To contain HTML text
	private JScrollPane scrollPane = new JScrollPane(helpText);	//Scrollable helpText
	
	public HelpWindow() 
	{
		setTitle(title);
		setSize(width, height);
		
		Container cont = this.getContentPane();
		
		helpText.setContentType("text/html");
		helpText.addHyperlinkListener(this);
		
		try 
		{
			String text = readTextFromFile(helpHTML);
			helpText.setText(text);
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(null, "HIBA! Fájl nem található: "+helpHTML);
			helpText.setText("<h1>Nem érhetõ el leírás</h1>");
			ex.printStackTrace();
		}
		helpText.setBounds(width/5, height/5, 4*width/4, 4*height/5);
		helpText.setEditable(false);
		helpText.setCaretPosition(0);  //show the beginning of file
		
	//	scrollPane.add(helpText);
		cont.add(scrollPane);
		
		setResizable(false);
		setVisible(true);
	}
	
	/** Read everything from the given text file
	 *	@param String fileName - name of the text file
	 *	@return String Content of fileName
	 *  @throws IOException 
	 **/
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
		reader.close();
		return result;
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent event) {
		
        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) 
        {
            String desc = event.getDescription();
            System.out.println(desc);
            if (desc == null || !desc.startsWith("#")) 
            	return;
            desc = desc.substring(1);
            System.out.println(desc);
            helpText.scrollToReference(desc);
        }
		
	}
}
