package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.GameSettings;

/** Beviteli mezok panele */

@SuppressWarnings("serial")
public class InfoPanel extends JPanel implements ActionListener
{
	private JLabel scoreTextLabel = new JLabel("Pontszám: ");
	private JLabel scoreLabel = new JLabel("0");
	private JLabel lifeTextLabel = new JLabel("Életek száma: ");
	private JLabel lifeLabel = new JLabel("3");
	private JButton incLife = new JButton("+");
	private JButton decLife = new JButton("-");
	private JButton helpButton = new JButton("Játék leírása");
	private boolean changable = true;
	
	public InfoPanel()
	{
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 0));
		
		scoreTextLabel.setForeground(Color.GREEN);
		scoreLabel.setForeground(Color.GREEN);
		lifeLabel.setForeground(Color.RED);
		
		incLife.addActionListener(this);
		decLife.addActionListener(this);
		helpButton.addActionListener(this);
		incLife.setActionCommand("INC");
		decLife.setActionCommand("DEC");
		helpButton.setActionCommand("HELP");
		incLife.setToolTipText("Kezdeti életek számának növelése");
		decLife.setToolTipText("Kezdeti életek számának csökkentése");
		
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		if (!changable)
		{
			incLife.setEnabled(false);
			decLife.setEnabled(false);
		}
		
		this.add(scoreTextLabel);
		this.add(scoreLabel);
		this.add(decLife);
		this.add(lifeTextLabel);
		this.add(lifeLabel);
		this.add(incLife);
		this.add(helpButton);
	}
	
	public int getLife()
	{
		int life = 0;
		try 
		{
			life = Integer.parseInt(lifeLabel.getText());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return life;
	}
	
	public void setLife(Integer life)
	{
		String text = life.toString();
		lifeLabel.setText(text);
	}
	
	public int getScore()
	{
		int score = 0;
		try 
		{
			score = Integer.parseInt(scoreLabel.getText());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return score;
	}
	
	public void setScore(Integer score)
	{
		String text = score.toString();
		scoreLabel.setText(text);
	}

	public boolean isChangable() {
		return changable;
	}

	public void setChangable(boolean changable) {
		this.changable = changable;
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (!changable)
			return;
		
		if (event.getActionCommand().equals("INC"))
		{
			if (GameSettings.starterLifeCount < 20)
				++GameSettings.starterLifeCount;
		}
		else if (event.getActionCommand().equals("DEC"))
		{
			if (GameSettings.starterLifeCount > 1)
				--GameSettings.starterLifeCount;
		}
		else if (event.getActionCommand().equals("HELP"))
		{
			@SuppressWarnings("unused")
			HelpWindow helper = new HelpWindow();
		}
		else 
			System.out.println("No action command: "+event.getActionCommand());
		
		String text = GameSettings.starterLifeCount.toString();
		lifeLabel.setText(text);		
	}
}
