package src;


/**
 * Each instance of this class represents a User. A user can be a Project Manager or a Developer.
 * @invar | this.getName() != null
 * @author vincent
 */
public abstract class User {
	
	/**
	 * @invar | name != null
	 */
	protected String name;
	protected boolean loggedIn;
	
	/**
	 * Setter that indicates the loggedIn boolean as true; i.e. the user is logged in
	 * @throws IllegalArgumentException | isLoggedIn() == true
	 * @post | isLoggedIn() == true
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
	 * @throws IllegalArgumentException | isLoggedIn() == false
	 * @post | isLoggedIn() == false
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
	 * @basic
	 */
	public boolean isLoggedIn() {return this.loggedIn;}
	
	/**
	 * @basic
	 */
	public String getName() {return this.name;}
}
