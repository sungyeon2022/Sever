package Timer;

import javax.swing.JLabel;

import lombok.Data;

@Data

public class Timer{
	private int startTime;
	private int currentTime;
	private int timeData;
	private int mmsec;
	private String sec;
	private String min;
	private String hour;
	private String timerString;
	JLabel timerLabel;
	private boolean isStart;
	public Timer() {
		this.timerString = "SOLO";
		this.isStart = false;
	}

	public void checkPassedTimeThread() {};
}
