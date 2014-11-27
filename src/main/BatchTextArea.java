package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

public class BatchTextArea extends JTextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;

	public BatchTextArea() {
		fileName = GUI.options.getProperty("path.bat");

		//загрузка файла при запуске программы
		loadBatchFromFile(fileName);
	}

	public String loadBatchFromFile(String fileName){
		//Очищаем текстовое поле
		setText("");

		if(fileName.length() == 0){
			JFileChooser chooser = new JFileChooser();
						 chooser.setCurrentDirectory(new File(GUI.options.getProperty("path.bat")));
						 chooser.setFileFilter(new MyBatFilter());
		
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				fileName = chooser.getSelectedFile().getPath();
				this.fileName = fileName;
				GUI.options.setProperty("path.bat", fileName);
			
				//оповещаем об изменении fileName
				firePropertyChange("fileName", "oldValue", fileName);
			}
		}
		
		if(fileName.length() != 0){
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
					String inputLine;
				
					while ((inputLine = in.readLine()) != null){
						setText(getText() + inputLine + "\n");
					}
								
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		
		return fileName;
	}
	
	public void saveBatch() {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
			
			out.write(getText());
			
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


class MyBatFilter extends FileFilter{

	@Override
	public boolean accept(File f) {
		return f.getName().toLowerCase().endsWith(".bat") || f.isDirectory();
	}

	@Override
	public String getDescription() {
		return "Command File (*.bat)";
	}	
}