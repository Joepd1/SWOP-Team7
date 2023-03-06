package src;

public class Status {

	private String status;
	private boolean available;
	
	
	public Status (String status) {
		this.available = true;
		this.status = "Created";
	}

	public String getStatus()  {return this.status;}

	public boolean isAvailable() {return this.available;}
	
	public void setStatus(String status) {
		// Must be a developer
		
		this.status = status;
	}
}
