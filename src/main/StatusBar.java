package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel {

	JPanel textPanel;
	JPanel progressPanel;
	
	public StatusBar() {
		super(new GridBagLayout());
		//setBorder(BorderFactory.createLoweredBevelBorder());
		
		textPanel = new TextPanel();
		progressPanel = new ProgressPanel();
		
		add(textPanel, new GridBagConstraints(0, 0, 1, 1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, createInsets(0), 6, 0));
		add(progressPanel, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH, createInsets(0), 0, 0));
	}

	private Insets createInsets(int i) {
		return new Insets(i,i,i,i);
	}
	
/*****************************************************
 * 
 * Тескт в левой части строки состояния
 * 
 *****************************************************/
	
	public void setStatusDescription(String text){
		((TextPanel) textPanel).getLabel().setText(text);
		revalidate();
		repaint();
	}
	
	public String getStatusDescription(){
		return ((TextPanel) textPanel).getLabel().getText();
	}
}

class TextPanel extends JPanel{
	JLabel gStatusText;
	
	public TextPanel() {
		super(new GridBagLayout());
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		gStatusText = new JLabel("Status.");
		
		add(gStatusText, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0, 0));
	}
	
	public JLabel getLabel(){
		return gStatusText;
	}
}

class ProgressPanel extends JPanel{
	public ProgressPanel() {
		super(new GridBagLayout());
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
}