package src;


/**
 * 
 * @author vincent
 */
public class User {
	
	/**
	 * @contains the name of the associated user.
	 * @contains a boolean indicating if this user is logged in or not.
	 */
	protected String name;
	protected boolean loggedIn;
	
	/**
	 * Setter that indicates the loggedIn boolean as true; i.e. the user is logged in
	 * @pre The user must be logged out.
	 */
	public void logIn() {
		if (this.loggedIn) {
			throw new IllegalArgumentException();
		}
		else {
			this.loggedIn = true;
		}
	}
	
	/**
	 * Setter that indicates the loggedIn boolean as false; i.e. the user is logged out
	 * @pre The user must be logged in.
	 */
	public void logOut() {
		if (!this.loggedIn) {
			throw new IllegalArgumentException();
		}
		else {
			this.loggedIn = false;
		}
	}
	
	/**
	 * Getter that returns the state of the user; Is he logged in or not?
	 */
	public boolean isLoggedIn() {return this.loggedIn;}
	
}
