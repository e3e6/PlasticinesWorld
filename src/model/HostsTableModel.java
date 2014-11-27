package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class HostsTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
	
	private List<Hosts> tableContentList;

	public static final int HOST_NAME_COLUMN = 0;
	public static final int RUNNABLE_COLUMN = 1;
	public static final int STATUS_COLUMN = 2;
	public static final int START_TIME_COLUMN = 3;
	public static final int END_TIME_COLUMN = 4;
	
	private static final String[]    HEADER = {"Host", "Runnable", "Status", "Start time", "End time"};
	private static final Boolean[] EDITABLE = {true, true, false, false, false};
	private static final Boolean[] EDITABLE_LAST_ROW = {true, false, true, true, true};
	
	public HostsTableModel(List<Hosts> aHostList) {
		super();
		this.tableContentList = aHostList;
	}

	@Override
	public int getColumnCount() {
		return HEADER.length;
	}

	@Override
	public int getRowCount() {
		return tableContentList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Hosts host = tableContentList.get(rowIndex);
		
		switch (columnIndex) {
		case HOST_NAME_COLUMN:
			return host.getHostName();

		case RUNNABLE_COLUMN:
			return host.getHostSelected();
		
		case STATUS_COLUMN:
			return host.getHostStatus();
		
		case START_TIME_COLUMN:
			return host.getStartTime();
		
		case END_TIME_COLUMN:
			return host.getEndTime();
		}
		
		return "";
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Hosts host = new Hosts();
		
		switch (columnIndex) {  
    	case HOST_NAME_COLUMN:  
    		return host.getHostName().getClass();
    	case RUNNABLE_COLUMN:  
    		return host.getHostSelected().getClass();
    	case STATUS_COLUMN:  
    		return host.getHostStatus().getClass();
    	case 3:  
    		return String.class;
    	case 4:  
    		return String.class;
	    }
    	
	    return null;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {

		return (rowIndex == getRowCount()-1)?EDITABLE_LAST_ROW[columnIndex]:EDITABLE[columnIndex];
	}


	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case HOST_NAME_COLUMN:
			tableContentList.get(rowIndex).setHostName((String) aValue);
			break;

		case RUNNABLE_COLUMN:
			tableContentList.get(rowIndex).setHostSelected((Boolean) aValue);
			break;
		
		case STATUS_COLUMN:
			tableContentList.get(rowIndex).setHostStatus((String) aValue);
			break;
			
		case START_TIME_COLUMN:
			tableContentList.get(rowIndex).setStartTime((String) aValue);
			break;
		
		case END_TIME_COLUMN:
			tableContentList.get(rowIndex).setEndTime((String) aValue);
			break;
		default:
			break;
		}
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return HEADER[columnIndex];
	}
	
	public void addRow(Hosts aValue){
		tableContentList.add(aValue);
	}
	
	public void removeRow(int rowIndex){
		tableContentList.remove(rowIndex);
	}

	public void addLastRow() {
		addRow(new Hosts("(new)"));
	}
	
	@Override
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l); 
	}
	
	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}
}
