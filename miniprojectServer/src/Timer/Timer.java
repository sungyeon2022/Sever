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
	public Timer() {
		timerString = "SOLO";
	}

	public void checkPassedTimeThread() {};
}
