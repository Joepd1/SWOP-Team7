package src;

import java.time.Duration;

/**
 * Each instance of this class represents the internal system time of the task manager.
 * @invar | getSystemTime() != null
 * @author vincent
 */
public class Clock {

	/**
	 * @invar | systemTime != null
	 */
	private static Duration systemTime;
	
	/**
	 * Initializes this object so that it it can represent the system time.
	 * 	This time is set as 0.
	 * @post | getSystemTime() != null
	 * @post | getSystemTime().toSeconds() == 0
	 */
	public Clock() {
		systemTime = Duration.ZERO;
	}
	
	/**
	 * Set's the new internal system time. Calculates the sum of the old system time and adds it to the given time to represent the new system time. 
	 * @pre can only be called by a Project Manager.
	 * @pre this Project Manager is logged in.
	 * @param time is the time that must be added to the old time (-> put the clock forward)
	 * @post | new{getSystemTime().toMinutes()} == old{getSystemTime().toMinutes)} + time.toMinutes()
	 */
	public static void advanceTime(Duration time) {
		Clock.systemTime = Clock.systemTime.plus(time);
	}
	
	/**
	 * @basic
	 */
	public static Duration getSystemTime() { return systemTime;}
}
