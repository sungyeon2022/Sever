package Timer;

import java.text.SimpleDateFormat;

import miniprojectServer.ServerControl;

public class TimerControl extends Timer {
	public TimerControl() {
	}

	@Override
	public void checkPassedTimeThread() {
		new Thread(() -> {
			setStartTime((int) System.currentTimeMillis()/10);
			while (true) {
				setCurrentTime((int) System.currentTimeMillis()/10);
				setTimeData(getCurrentTime() - getStartTime());
				setMmsec(getTimeData() % 100);
				setSec(String.format("%02d", getTimeData()/100%60));
				setMin(String.format("%02d", getTimeData()/6000%60));
				setHour(String.format("%02d", getTimeData()/360000%60));
				setTimerString(getHour()+":"+getMin()+":"+getSec()+":"+getMmsec());
			}
		}).start();
	}
}
