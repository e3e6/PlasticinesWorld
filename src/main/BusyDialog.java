package main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class BusyDialog extends Thread{
	MainWindow window;
	
	
	public BusyDialog(JFrame parent, String textMessage) {
		window = new MainWindow(parent, textMessage);			   
	}

	@Override
	public void run() {
		window.doBusy();	
		
	}
	
	public void exit(){
		window.dispose();
	}
}

class MainWindow extends JWindow {

	private static final long serialVersionUID = 1L;
	private int i = 20;
	private JLabel waiting = new JLabel("...");


	public MainWindow(JFrame parent, String textMessage){
		super();
		
		JPanel panel = new JPanel(new GridBagLayout());
		
		panel.add(new JLabel(textMessage), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, 
																										   GridBagConstraints.NONE, new Insets(i, i, i, 0), 0, 0));
		panel.add(waiting, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST, 
				   								  					GridBagConstraints.NONE, new Insets(i, 0, i, i), 0, 0));
		
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));		

		getContentPane().add(panel);
		pack();
		setLocation(parent.getX() + ((parent.getWidth() - getWidth())/2), parent.getY() + ((parent.getHeight() - getHeight())/2));
		setVisible(true);
	}

	public void doBusy(){
		
		
		Thread createReport = new Thread() {
			public void run() {
				while (true){
					waiting.setText("");
					
					for(i = 0; i < 4; i++){
						waiting.setText(waiting.getText() + ".");
						
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
					}
				}
			}
		};

		createReport.start();
	}
}
