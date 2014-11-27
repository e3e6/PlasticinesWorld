package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.sun.java.swing.plaf.motif.MotifBorders.BevelBorder;


import thread.WorkingThread;

public class GUI extends JFrame implements ActionListener, PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	
	public static Properties options;
	
	private JTextField commandLineField;

	private HostTable hostTable;
	private JButton saveBatch;

	private BatchTextArea batchArea;

/****************************************************************
 * 
 * Конструктор окна
 * 
 ***************************************************************/	
	
	public GUI(String title) throws HeadlessException {
		super(title);
		
		/****************************************************************
		 * 
		 * Создаём все элементы
		 * 
		 ***************************************************************/			
		setLayout(new GridBagLayout());

		//стартовая строка в TextField
		commandLineField = new JTextField(getCommandLine());
		commandLineField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		commandLineField.setBackground(new Color(0xFFFFCC));

		hostTable = new HostTable();
		hostTable.loadHostsFromFile(GUI.options.getProperty("path.hosts"));

		batchArea = new BatchTextArea();
		batchArea.addPropertyChangeListener("fileName", this);
		
		/*batchArea.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent e) {
					saveBatch.setEnabled(true);
				}
		
				@Override
				public void insertUpdate(DocumentEvent e) {
					saveBatch.setEnabled(true);
				}
		
				@Override
				public void changedUpdate(DocumentEvent e) {
					saveBatch.setEnabled(true);
				}
		});*/

		JScrollPane hostTableScroll = new JScrollPane(hostTable);
					hostTableScroll.setPreferredSize(new Dimension((int) (hostTableScroll.getPreferredSize().width*1), 
																   (int) (hostTableScroll.getPreferredSize().height*0.5)));
	
		JScrollPane batchScroll = new JScrollPane(batchArea);					
					/*batchScroll.setPreferredSize(new Dimension((int) (batchScroll.getPreferredSize().width*0.8),
														      (int) (batchScroll.getPreferredSize().height*2)));*/		   
	
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, hostTableScroll, batchScroll);
			  	   split.setBorder(BorderFactory.createLoweredBevelBorder());
		
		/****************************************************************
		 * 
		 * Меню
		 * 
		 ***************************************************************/		
		MyMenuBar menuBar = new MyMenuBar();
				  menuBar.setActionListener(this);
		
		 /****************************************************************
		  * 
		  * Панель с кнопками
		  * 
		  ***************************************************************/		  
		
				  ButtonPanel buttonPanel = new ButtonPanel();
				  			  buttonPanel.setActionListener(this);
		
		/****************************************************************
		 * 
		 * Помещаем всё во фрейм и готовим к выводу
		 * 
		 ***************************************************************/
			  	   
		add(commandLineField, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST, 
																	   GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 10, 10));
		add(split, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST, 
														    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

				
		add(buttonPanel, new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
	
		add(new StatusBar(), new GridBagConstraints(0, 3, 1, 1, 1, 0, GridBagConstraints.SOUTH, 
																	  GridBagConstraints.BOTH, new Insets(0,0,0,0), 0, 0));
		//setJMenuBar(menuBar);	
		
		pack();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
/****************************************************************
 * 
 * Реакция на закрытие окна
 * 
 ***************************************************************/
		addWindowListener(new WindowAdapter() {
					
			@Override
			public void windowClosing(WindowEvent e) {
				//сохраняем файл с настройками
				
				//XXX Создаём окно «Busy» с текстом "Сохраняем настройки"
				//XXX Устанавливаем индикатор прогресса в 0%
				
				try {
					options.store(new FileOutputStream(new File("path.properties")), "Updated:");
				} catch (FileNotFoundException e1) {e1.printStackTrace();
				} catch (IOException e1) {e1.printStackTrace();}
				
				//XXX Устанавливаем индикатор прогресса в 100%
				//XXX Уничтожаем окно

			hostTable.writeToCSV();
					/*	try {
							Thread.sleep(10000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}*/
				
				System.exit(1);
			}
		});
	}

	/****************************************************************
	 * 
	 * Реакция на нажатие кнопок
	 * 
	 ***************************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if(action.equals("Execute")){
			hostTable.clearAllStatus();
			new WorkingThread(hostTable, this).start();	 

			/*BusyDialog dialog = new BusyDialog(this, "textMessage");
					   dialog.start();*/
					   
				  

		}else if(action.equals("Open")){
			batchArea.loadBatchFromFile("");
		}else if(action.equals("Save")){
			batchArea.saveBatch();
			((JButton) e.getSource()).setEnabled(false);
		}else if(action.equals("Import")){
			hostTable.importHostsFromFile();
		}
	}	
	

	/****************************************************************
	 * 
	 * Отлавливаем изменение у кого-то свойств
	 * 
	 ***************************************************************/
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		//XXX system.out
		System.out.println("Property Changed");
		commandLineField.setText(getCommandLine());
	}

	/****************************************************************
	 * 
	 * Получаем команду на запуск для заданного имени хоста
	 * 
	 ***************************************************************/	
	public String parseCommandLine(String replacement){
		return commandLineField.getText().replace("%hostname%", "\\\\" + replacement);
	}	

	/****************************************************************
	 * 
	 * Получаем шаблон команду на запуск из файла параметров
	 * 
	 ***************************************************************/	
	public String getCommandLine() {
		String execPath = GUI.options.getProperty("path.psexec");
		String params = GUI.options.getProperty("psexec.params");
		String batPath = GUI.options.getProperty("path.bat");
		
		return execPath + " %hostname% " + params + " " + batPath;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		options = new Properties();
		final File configfile = new File("path.properties");
		
		try {
			 FileInputStream in = new FileInputStream(configfile);
			options.load(in); // Load properties from the file
				in.close();
			} catch (IOException e) {
				System.out.println("Properties file is missing");
			}
			
		JFrame frame = new GUI("Многопоточный исполнитель. Beta");
			   frame.setVisible(true);	
	}
}

class ButtonPanel extends JPanel{
	JButton openBatch;
	JButton saveBatch;
	JButton importHosts;
	JButton startBtn;
	
	public ButtonPanel(){
		super(new GridBagLayout());
		setBorder(BorderFactory.createBevelBorder(1));
		
		openBatch = new JButton("Open");
		saveBatch = new JButton("Save");
		importHosts = new JButton("Import");
		startBtn = new JButton("Execute");
	
		add(openBatch,   new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, createInsets(5), 0, 0));
		add(saveBatch,   new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, createInsets(5), 0, 0));
		add(importHosts, new GridBagConstraints(2, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, createInsets(5), 0, 0));
		add(startBtn,    new GridBagConstraints(3, 0, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, createInsets(5), 0, 0));
	}
	
	public void setActionListener(ActionListener al){
			openBatch.addActionListener(al);
			saveBatch.addActionListener(al);
			importHosts.addActionListener(al);
			startBtn.addActionListener(al);
	}
	
	private Insets createInsets(int i){
		return new Insets(i,i,i,i);
	}
}

class MyMenuBar extends JMenuBar{
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem importItem;
	private JMenuItem exitItem;
	private JMenuItem executeItem;
	
	public MyMenuBar(){
		JMenu menuFile = new JMenu("File");

				openItem = new JMenuItem("Open (*.bat)");
				openItem.setActionCommand("Open");
			
				saveItem = new JMenuItem("Save");
				saveItem.setActionCommand("Save");
			
				importItem = new JMenuItem("Import (*.csv)");
				importItem.setActionCommand("Import");
			
				exitItem = new JMenuItem("Exit");
	
			menuFile.add(openItem);
			menuFile.add(saveItem);
			menuFile.add(importItem);
			menuFile.addSeparator();
			menuFile.add(exitItem);
		
		JMenu menuRun = new JMenu("Run");	
			
				executeItem = new JMenuItem("Execute");
			
			menuRun.add(executeItem);
		
		add(menuFile);
		add(menuRun);
	}
	
	public void setActionListener(ActionListener al){
		openItem.addActionListener(al);
		saveItem.addActionListener(al);
		importItem.addActionListener(al);
		executeItem.addActionListener(al);
	}
}