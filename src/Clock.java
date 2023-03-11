package src;

import java.time.Duration;

public class Clock {

	private static Duration systemTime;
	
	public Clock() {
		systemTime = Duration.ZERO;
	}
	
	/**
	 * Setter to advance the internal system time.
	 * @pre can only be called by a Project Manager.
	 * @pre this Project Manager is logged in.
	 * 
	 * @param time to add to the system time in MINUTES
	 */
	public static void advanceTime(Duration time) {
		Clock.systemTime = Clock.systemTime.plus(time);
	}
	
	public static Duration getSystemTime() { return systemTime;}
}
