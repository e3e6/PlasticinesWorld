package thread;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import main.GUI;
import main.HostTable;

public class WorkingThread extends Thread {
	private HostTable hostsTable;
	private JFrame mainFrame;

	private List<Thread> workingThreadList;
	
	private static final int TIMEOUT_BETWEEN_EXECUTE = 400;
	private static final int TIMEOUT_CHECK_STATE = 100;
	
	public WorkingThread(HostTable hostTable, JFrame aMainFrame) {
		super();
		this.hostsTable = hostTable;
		this.mainFrame = aMainFrame;
	}



	@Override
	public void run() {
		workingThreadList = new ArrayList<Thread>();

		//������ � ��������� ��� ������
		for(int i = 0; i < hostsTable.getRowCount(); i++){
			
			if(hostsTable.isRunnable(i)){
				//�������� ������� ��� �������
				String commandLine = ((GUI) mainFrame).parseCommandLine(hostsTable.getHostName(i));

				//������ �����
				CMD thread = new CMD(commandLine);
			
				//XXX: ������������� � ������� ������ ������
				hostsTable.setThreadState("Working...", i);
			
				//��������� ������ �� ����� � ������
				workingThreadList.add(thread);
			
				//��������� ����� �� ����������
				thread.start();
	
				//�����
				try {
					Thread.sleep(TIMEOUT_BETWEEN_EXECUTE);
				} catch (InterruptedException e) {e.printStackTrace();}
			}
		}

		//���� �� �������� �������� ���������� ������� � ���������� �� � �������
		int working = 2; //����� ��������� ���� while ��� ���, ����� ��������� ���� �������

		
		while(working > 0){
			int threadsListItem = 0;

			for(int i = 0; i < hostsTable.getRowCount(); i++){
				if(hostsTable.isRunnable(i)){

					//���� ����� ����			
					if(!workingThreadList.get(threadsListItem).isAlive()){
						//XXX: ������� � ������� ExitValue
						hostsTable.setThreadState("Exit value = " + ((CMD) workingThreadList.get(threadsListItem)).getExitValue(), i);	
					}
				
					try {
						Thread.sleep(TIMEOUT_CHECK_STATE);
					} catch (InterruptedException e) {e.printStackTrace();}
					
					threadsListItem++;
				}
				
				
			}
			
			if (getActiveThreadsCount() == 0){
				working--;
			}
		}
	}



	private int getActiveThreadsCount() {
		int activeCount = 0;
		
		for(int i = 0; i < workingThreadList.size(); i++){
			if(workingThreadList.get(i).isAlive()){
				activeCount++;
			}
		}
		
		return activeCount;
	}
}
