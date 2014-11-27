package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;

import model.Hosts;
import model.HostsTableModel;

public class HostTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TIME_FORMAT = "HH:mm:ss.SSS";
	
/*************************************************************************
 * 
 * Конструктор и слушатель для добавления/удаления строк
 *
 *************************************************************************/
	
	public HostTable() {
		super();
		
		//Добавление новой строки в таблице
		this.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				int selectedRow = getSelectedRow();
				int lastRow = getRowCount()-1;
		
				if((selectedRow == lastRow) && (evt.getNewValue() != null)){
					addLastRow();
				}
			}
		});
		
		//Удаление строки в таблице
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				int selectedRow = getSelectedRow();
				int lastRow = getRowCount() - 1;
				
				if((key == KeyEvent.VK_DELETE) && selectedRow != lastRow){
					removeRow(selectedRow);
				}
			}

			
		});
		
		//XXX ширина столбца
		//getColumnModel().getColumn(0).setPreferredWidth(10);
		
		
	}
	
/*************************************************************************
 * 
 * Получаем список всех хостов
 *
 *************************************************************************/
	public List<String> getHosts(){
		List<String> hostsList = new ArrayList<String>();
		
		for(int i = 0; i < getRowCount()-1; i++){
			hostsList.add(getHostName(i));
		}
		
		return hostsList;
	}
	
/*************************************************************************
 * 
 * Запускать-ли i-ый хост
 *
 *************************************************************************/
	public Boolean isRunnable(int i) {
		return (Boolean) getValueAt(i, HostsTableModel.RUNNABLE_COLUMN);
	}

	
/*************************************************************************
 * 
 * Методы для добавления/удаления строк
 *
 *************************************************************************/
	public void addRow(String hostName){
		((HostsTableModel) getModel()).addRow(new Hosts(hostName));
		revalidate();
	}
	
	public void addLastRow() {
		((HostsTableModel) getModel()).addLastRow();
		revalidate();
	}
	
	public void removeRow(int selectedRow) {
		((HostsTableModel) getModel()).removeRow(selectedRow);
		revalidate();	
	}
	
/*************************************************************************
 * 
 * Очистка статуса и времени начала/завершения
 *
 *************************************************************************/
	public void clearAllStatus() {
		
		for(int i = 0; i < getRowCount(); i++){
			setValueAt("", i, HostsTableModel.STATUS_COLUMN);
		}
		
	}

	public String getHostName(int rowIndex) {
		return (String) getValueAt(rowIndex, HostsTableModel.HOST_NAME_COLUMN);
	}

	
/*************************************************************************
 * 
 * Задаём значение статуса, времени начал и времени завершения
 *
 *************************************************************************/
	
	public void setThreadState(String state, Integer listItem) {
		setValueAt(state, listItem, HostsTableModel.STATUS_COLUMN);
		
		if(state.equals("Working...")){
			setStartTime(listItem);
		}else{
			setEndTime(listItem);
		}
		
		repaint();		
	}
	
	public void setStartTime(int rowIndex){
		setValueAt(new SimpleDateFormat((TIME_FORMAT)).format(Calendar.getInstance().getTime()), rowIndex, HostsTableModel.START_TIME_COLUMN);
		repaint();		
	}
	
	public void setEndTime(int rowIndex){
		setValueAt(new SimpleDateFormat((TIME_FORMAT)).format(Calendar.getInstance().getTime()), rowIndex, HostsTableModel.END_TIME_COLUMN);
		repaint();		
	}
	
/*************************************************************************
 * 
 * Загрузка из файла
 *
 *************************************************************************/
	
	public void loadHostsFromFile(String fileName){
		List<String> hostsList = readFromCSV(fileName);

		//создаём список для модели таблицы
		List<Hosts> hostModelList = new ArrayList<Hosts>();
		
		for (int i = 0; i < hostsList.size(); i++){
			hostModelList.add(new Hosts(hostsList.get(i), false, ""));
		}

		setModel(new HostsTableModel(hostModelList));
		addLastRow();
	}

/*************************************************************************
 * 
 * Импорт из файла
 *
 *************************************************************************/
	
	public void importHostsFromFile(){
		String fileName = null;
		List<String> hostList;
		 
		JFileChooser chooser = new JFileChooser();
			 		 chooser.setFileFilter(new CSVFilter());

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			fileName = chooser.getSelectedFile().getPath();
			hostList = readFromCSV(fileName);
				 
			removeRow(getRowCount()-1);
				 
			for (int i = 0; i < hostList.size(); i++){
				addRow(hostList.get(i));
			}
				 
			addLastRow();
		}
	}
	
/*************************************************************************
 * 
 * Запись в файл csv
 *
 *************************************************************************/	
	public void writeToCSV(){
		
		//сохраняем файл с хостами
		try {
			FileOutputStream out = new FileOutputStream(GUI.options.getProperty("path.hosts"));
			
			List<String> list = getHosts();
			
			for(int i = 0; i < list.size(); i++){
				out.write((list.get(i) + "\n").getBytes());
			}
			
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

/*************************************************************************
 * 
 * Чтение в список из файла csv
 *
 *************************************************************************/
	public List<String> readFromCSV(String fileName){
		BufferedReader in = null;
		String inputLine;
		List<String> hostList = new ArrayList<String>();
		
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			
			while ((inputLine = in.readLine()) != null){
				hostList.add(inputLine);
			}
			in.close();
		} catch (FileNotFoundException e) {	
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return hostList;
	}
}

/*************************************************************************
 * 
 * Фильтр для диалогового окна
 *
 *************************************************************************/
class CSVFilter extends FileFilter{

	@Override
	public boolean accept(File f) {
		return f.getName().toLowerCase().endsWith(".csv") || f.isDirectory();
	}

	@Override
	public String getDescription() {
		return "CSV (разделители - запятые) (*.csv)";
	}	
}
