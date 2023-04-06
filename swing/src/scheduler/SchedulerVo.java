package scheduler;

public class SchedulerVo {
	private String day;
	private int pno;
	private String task;
	private String fromTime;
	private String toTime;
	private String username;

	public SchedulerVo() {
		super();

	}

	public SchedulerVo(String day, int pno, String task, String fromTime, String toTime, String username) {
		super();
		this.day = day;
		this.pno = pno;
		this.task = task;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.username = username;
	}

	public SchedulerVo(String day, int pno, String task, String fromTime, String toTime) {
		super();
		this.day = day;
		this.pno = pno;
		this.task = task;
		this.fromTime = fromTime;
		this.toTime = toTime;
	}

	public String getDay() {return day;}
	public void setDay(String day) {this.day = day;}
	public int getPno() {return pno;}
	public void setPno(int pno) {this.pno = pno;}
	public String getTask() {return task;}
	public void setTask(String task) {this.task = task;}
	public String getFromTime() {return fromTime;}
	public void setFromTime(String fromTime) {this.fromTime = fromTime;}
	public String getToTime() {return toTime;}
	public void setToTime(String toTime) {this.toTime = toTime;}
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}

	@Override
	public String toString() {
		return "SchedulerVo [day=" + day + ", pno=" + pno + ", task=" + task + ", fromTime=" + fromTime + ", toTime="
				+ toTime + ", username=" + username + "]";
	}

}