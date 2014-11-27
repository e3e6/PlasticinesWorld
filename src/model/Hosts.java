package model;

public class Hosts {
	private String hostName;
	private Boolean hostSelected;
	private String hostStatus;
	private String startTime;
	private String endTime;
	
	public static int getSize(){
		return 5;
	}
	
	public Hosts(String hostName, Boolean hostSelected, String hostStatus) {
		super();
		this.hostName = hostName;
		this.hostSelected = hostSelected;
		this.hostStatus = hostStatus;
	}
	
	public Hosts(String hostName) {
		super();
		this.hostName = hostName;
		this.hostSelected = false;
		this.hostStatus = "";
	}
	
	public Hosts() {
		this.hostName = "";
		this.hostSelected = false;
		this.hostStatus = "";
	}

	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public Boolean getHostSelected() {
		return hostSelected;
	}
	public void setHostSelected(Boolean hostSelected) {
		this.hostSelected = hostSelected;
	}

	public String getHostStatus() {
		return hostStatus;
	}

	public void setHostStatus(String hostStatus) {
		this.hostStatus = hostStatus;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
