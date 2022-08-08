
import java.io.Serializable;

import java.util.Objects;

public class Guest implements Serializable{

	private String lastName;
	private String firstName;
	private String email;
	private String phoneNumber;
	
	private static final long serialVersionUID = 1L;
	
	public String getLastName() {
		return this.lastName;
	}
	
	void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Guest() {
		
	}
	
	public Guest(String lastName, String firstName) {
		this.lastName = lastName;
		this.firstName = firstName;
	}
	
	public Guest(String lastName, String firstName, String email) {
		this(lastName, firstName);
		this.email = email;
	}
	
	public Guest(String lastName, String firstName, String email, String phoneNumber) {
		this(lastName, firstName, email);
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		return this.lastName + " " + this.firstName + " email: " + this.email + " phone: " + this.phoneNumber;
	}

	public String getName() {
		return this.lastName + " " + this.firstName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		Guest other = (Guest) obj;
		return  (Objects.equals(firstName.toLowerCase(), other.getFirstName().toLowerCase()) 
				&& Objects.equals(lastName.toLowerCase(), other.getLastName().toLowerCase())) || 
				 Objects.equals(phoneNumber, other.getPhoneNumber()) ||
						 Objects.equals(phoneNumber, other.getPhoneNumber());
	}
	
}
