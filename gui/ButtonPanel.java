package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/** Nyomogombok es az esemneykezelojuk */
public class ButtonPanel extends JPanel implements ActionListener{
	private JButton btnSave=new JButton("Változtatások elmentése");
	private JButton btnDelete=new JButton("Kiválasztott törlése");
	private JButton btnNew=new JButton("Új dokumentum felvétel");
	
	public static String CMD_SAVE="SAVE";
	public static String CMD_NEW="NEW";
	public static String CMD_DELETE="DELETE";
	/** Konsturktora */
	public ButtonPanel(){
		add(btnSave);		
		add(btnDelete);		
		add(btnNew);
		
		btnSave.setActionCommand(CMD_SAVE);
		btnNew.setActionCommand(CMD_NEW);
		btnDelete.setActionCommand(CMD_DELETE);
		
		btnSave.addActionListener(this);
		btnDelete.addActionListener(this);
		btnNew.addActionListener(this);
	}

	/**
	 * Gomb megnyomásakor hívódik meg, töröl, módosít, vagy újat ad hozzá
	 */
	public void actionPerformed(ActionEvent e) {
		/* Ha a mentes gombra kattintottunk, menti a frissiteseket */
		if(e.getActionCommand().equals(CMD_SAVE)){
			GameWindow wnd=GameWindow.getInstance();
	
			
			FieldPanel pnlFields = wnd.getFieldPanel();
			 String id = pnlFields.getTxtDocId().getText();
			 String name = pnlFields.getTxtDocName().getText();
			 String type = pnlFields.getTxtDocType().getText();
			 String pageNum = pnlFields.getTxtPageNum().getText();
			 String status = pnlFields.getTxtStatus().getText();
			 
			//Intekke konvertalas
			 int intId=Integer.parseInt(id);
			 int intPageNum=Integer.parseInt(pageNum);
			 int intStatus=Integer.parseInt(status);

			// Megerosites:
			int answer=JOptionPane.showConfirmDialog(wnd,"Biztosan ezt szeretned?");
			if (answer==1 || answer==2){
				JOptionPane.showMessageDialog(wnd,"Nem lett valtoztatava."," ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			try {
				//megvaltoztatja az adatokat es frissiti a tablat
				//t.refresh();
				JOptionPane.showMessageDialog(wnd,"Sikeres modositas!","Siker!", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(wnd, "Hiba a updatekor("+e1.getMessage()+")", "Hiba", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
			
			
		/* Ha a torles gombra kattintottunk*/
		}else if(e.getActionCommand().equals(CMD_DELETE)){
			
			GameWindow wnd=GameWindow.getInstance();

			int rowIndx=1;
			// Megerosites:
			int answer=JOptionPane.showConfirmDialog(wnd,"Biztosan ezt szeretned?");
			if (answer==1 || answer==2){
				JOptionPane.showMessageDialog(wnd,"Nem lett valtoztatava."," ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			try {
			//	dao.delete(p);
			//	t.refresh();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(wnd, "Hiba a törléskor("+e1.getMessage()+")", "Hiba", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		/* Ha az uj gombra kattintottunk, insert*/
		} else if(e.getActionCommand().equals(CMD_NEW)){
			
			GameWindow wnd=GameWindow.getInstance();
	
			
			FieldPanel pnlFields = wnd.getFieldPanel();
			 String id = pnlFields.getTxtDocId().getText();
			 String name = pnlFields.getTxtDocName().getText();
			 String doc_type = pnlFields.getTxtDocType().getText();
			 String pageNum = pnlFields.getTxtPageNum().getText();
			 String status = pnlFields.getTxtStatus().getText();
			//Intekke konvertalas
			 int intId=Integer.parseInt(id);
			 int intPageNum=Integer.parseInt(pageNum);
			 int intStatus=Integer.parseInt(status);
			 
			 //Ha a nev ures, figyelmeztetes!
			 if (name.equals("") || name.equals(" ")){
				 JOptionPane.showMessageDialog(wnd,"A név ne maradjon üresen!");
				 return;
			 }
			 

			try {
				//t.refresh();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(wnd, "Hiba a insertkor("+e1.getMessage()+")", "Hiba", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
		
	}
}
