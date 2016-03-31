package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Beviteli mezok panele */

@SuppressWarnings("serial")
public class FieldPanel extends JPanel
{
	private JTextField txtDocId=new JTextField(5);
	private JTextField txtDocName=new JTextField(5);
	private JTextField txtDocType=new JTextField(5);
	private JTextField txtPageNum=new JTextField(5);
	private JTextField txtStatus=new JTextField(5);
	
	public FieldPanel(){
		add(new JLabel("Dokumentum ID"));
		add(txtDocId);
		add(new JLabel("Dokumentum név"));
		add(txtDocName);
		add(new JLabel("Dokumentum típusa"));
		add(txtDocType);
		add(new JLabel("Oldalak száma"));
		add(txtPageNum);
		add(new JLabel("Státusz"));
		add(txtStatus);
	}
	

	public JTextField getTxtDocId() {
		return txtDocId;
	}

	public void setTxtDocId(JTextField txtDocId) {
		this.txtDocId = txtDocId;
	}

	public JTextField getTxtDocName() {
		return txtDocName;
	}

	public void setTxtDocName(JTextField txtDocName) {
		this.txtDocName = txtDocName;
	}

	public JTextField getTxtDocType() {
		return txtDocType;
	}

	public void setTxtDocType(JTextField txtDocType) {
		this.txtDocType = txtDocType;
	}

	public JTextField getTxtPageNum() {
		return txtPageNum;
	}

	public void setTxtPageNum(JTextField txtPageNum) {
		this.txtPageNum = txtPageNum;
	}

	public JTextField getTxtStatus() {
		return txtStatus;
	}

	public void setTxtStatus(JTextField txtStatus) {
		this.txtStatus = txtStatus;
	}

}
