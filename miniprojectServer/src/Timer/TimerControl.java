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
				setSec((getTimeData()/100));
				setMin(getSec()/60%60);
				setTimerString(getSec()+"."+getMmsec());
			}
		}).start();
	}
}
