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

		//Создаём и запускаем все потоки
		for(int i = 0; i < hostsTable.getRowCount(); i++){
			
			if(hostsTable.isRunnable(i)){
				//Получаем команду для запуска
				String commandLine = ((GUI) mainFrame).parseCommandLine(hostsTable.getHostName(i));

				//Создаём поток
				CMD thread = new CMD(commandLine);
			
				//XXX: Устанавливаем в таблице статус строки
				hostsTable.setThreadState("Working...", i);
			
				//Сохраняем ссылку на поток в списке
				workingThreadList.add(thread);
			
				//Запускаем поток на выполнение
				thread.start();
	
				//пауза
				try {
					Thread.sleep(TIMEOUT_BETWEEN_EXECUTE);
				} catch (InterruptedException e) {e.printStackTrace();}
			}
		}

		//цикл по проверке статусов запущенных потоков и обновление их в таблице
		int working = 2; //чтобы выполнить цикл while ещё раз, после остановки всех потоков

		
		while(working > 0){
			int threadsListItem = 0;

			for(int i = 0; i < hostsTable.getRowCount(); i++){
				if(hostsTable.isRunnable(i)){

					//если поток умер			
					if(!workingThreadList.get(threadsListItem).isAlive()){
						//XXX: Выводим в таблицу ExitValue
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
