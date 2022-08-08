
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GuestsList implements Serializable{
	
	private final int participantsNo;
	private ArrayList <Guest> listOfParticipants;
	
	private static final long serialVersionUID = 1L;
	
	public GuestsList(ArrayList <Guest> newListOfParticipants, int participantsNo) {
		this.listOfParticipants = newListOfParticipants;
		this.participantsNo = participantsNo;
	}
	
	//Exceptie 1
	public GuestsList(Scanner sc) {
		this.listOfParticipants = new ArrayList<Guest> ();
		int participants = 0;
		while (true) {
			try {
				System.out.print("Bine ati venit! Introduceti numarul de participanti pentru eveniment: ");
				participants = sc.nextInt();
				break;
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Reincercati...");
			}
		}
		this.participantsNo = participants;
	}
	
	public int getParticipantsNo() {
		return this.participantsNo;
	}
	
	
	public ArrayList<Guest> getListOfParticipants() {
		return this.listOfParticipants;
	}
	
	public void setListOfParticipants(ArrayList<Guest> list) {
		this.listOfParticipants = list;
	}
	
	int add(Guest newGuest) {
		
		if (this.onTheListByName(newGuest) || this.onTheListByEmail(newGuest) || this.onTheListByPhoneNumber(newGuest))  {
			return -1;
		} 
		
		if (this.listOfParticipants.size() < this.participantsNo) {
			
			this.listOfParticipants.add(newGuest);
			System.out.println("[" + newGuest.getName() + "] Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
			return 0;
			
		} else {
			this.listOfParticipants.add(newGuest);
			System.out.println("[" + newGuest.getName() + "] Te-ai inscris cu succes in lista de asteptare si ai primit numarul de ordine <" 
					+ (this.listOfParticipants.size() - this.participantsNo) + ">. Te vom notifica daca un loc devine disponibil.");
			return this.listOfParticipants.size() - this.participantsNo;
		}
		
	}
	
	boolean onAnyList(String option, Guest newGuest) {
		
		switch(option) {
		case "1":
			return onTheListByName(newGuest);
		case "2":
			return onTheListByEmail(newGuest);
		case "3":
			return onTheListByPhoneNumber(newGuest);
		default:
			return false;
		}
	}
	
	//Exceptie 2
	private boolean onTheListByName(Guest newGuest) {

		try {
			for (Guest guest: this.listOfParticipants) {
				if (guest.equals(newGuest)) {
					return true;
				}
			}
		} catch (NullPointerException e) {
			return false;
		}
		
		return false;
	}
	
	Guest guestOnTheListByName(String lastName, String firstName) {
		
		lastName = lastName.toLowerCase();
		firstName = firstName.toLowerCase();
		
		Guest thisGuest = new Guest();
		
		for (Guest guest: this.listOfParticipants) {
			
			if (guest.getFirstName().toLowerCase().equals(firstName)  && guest.getLastName().toLowerCase().equals(lastName)) {
				thisGuest = guest;
				return thisGuest;
			}
		}
		
		return thisGuest;
	}
	
	//Exceptie 3
	private boolean onTheListByEmail(Guest newGuest) {
		try {
			for (Guest guest: this.listOfParticipants) {
				if (guest.equals(newGuest)) {
					return true;
				}
			}
		} catch (NullPointerException e) {
			return false;
		}
		
		return false;
	}
		
	Guest guestOnTheListByEmail(String email) {
		
		Guest thisGuest = new Guest();
		
		for (Guest guest: this.listOfParticipants) {
			if (guest.getEmail().equals(email)) {
				thisGuest = guest;
				return thisGuest;
			}
		}
		
		return thisGuest;
	}
	
	//Exceptie 4
	private boolean onTheListByPhoneNumber(Guest newGuest) {
		try {
			for (Guest guest: this.listOfParticipants) {
				if (guest.equals(newGuest)) {
					return true;
				}
			}
		} catch (NullPointerException e) {
			return false;
		}
		
		return false;
	}
	
	Guest guestOnTheListByPhone(String phoneNumber) {
		
		Guest thisGuest = new Guest();
		
		for (Guest guest: this.listOfParticipants) {
			
			if (guest.getPhoneNumber().equals(phoneNumber)) {
				thisGuest = guest;
				return thisGuest;
			}
		}
		
		return thisGuest;
	}
	
	//Exceptie 5
	boolean removeByName(String lastName, String firstName) {
		
		Guest toBeRemoved = this.guestOnTheListByName(lastName, firstName);
		
		if (this.onTheListByName(toBeRemoved))  {
			int position = this.listOfParticipants.indexOf(toBeRemoved);
			this.listOfParticipants.remove(toBeRemoved);
					
			if (position < this.participantsNo) {
				try {
					System.out.println("[" + this.listOfParticipants.get(this.participantsNo - 1).getName() + "]" 
								+ "Felicitari! Locul tau la eveniment este confirmat. Te asteptam!.");
					System.out.println("Stergerea persoanei s-a realizat cu succes.");
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Stergerea persoanei s-a realizat cu succes.");
				}
			} 
			
			return true;
		}
			
		return false;
	}
	
	//Exceptie 6
	boolean removeByPhoneNumber(String phoneNumber) {
		
		Guest toBeRemoved = guestOnTheListByPhone(phoneNumber);

		if (this.onTheListByPhoneNumber(toBeRemoved))  {
			int position = this.listOfParticipants.indexOf(toBeRemoved);
			this.listOfParticipants.remove(toBeRemoved);
			
			if (position < this.participantsNo) {
				try {
					System.out.println("[" + this.listOfParticipants.get(this.participantsNo - 1).getName() + "]" 
								+ "Felicitari! Locul tau la eveniment este confirmat. Te asteptam!.");
					System.out.println("Stergerea persoanei s-a realizat cu succes.");
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Stergerea persoanei s-a realizat cu succes.");
				}
			} 
			return true;
		}
			
		return false;
	}
	
	//Exceptie 7
	boolean removeByEmail(String email) {
		
		Guest toBeRemoved = this.guestOnTheListByEmail(email);

		if (this.onTheListByEmail(toBeRemoved))  {
			int position = this.listOfParticipants.indexOf(toBeRemoved);
			this.listOfParticipants.remove(toBeRemoved);
			if (position < this.participantsNo) {
				try {
					System.out.println("[" + this.listOfParticipants.get(this.participantsNo - 1).getName() + "]" 
								+ "Felicitari! Locul tau la eveniment este confirmat. Te asteptam!.");
					System.out.println("Stergerea persoanei s-a realizat cu succes.");
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Stergerea persoanei s-a realizat cu succes.");
				}
			} 
			
			return true;
		}

		return false;
	}
		
	void updateFirstName (Guest guest, String firstName) {
		if (this.onTheListByName(guest)) {
			
			guest.setFirstName(firstName);
		}
	}
	
	void updateLastName (Guest guest, String lastName) {
		
		if (this.onTheListByName(guest)) {
			
			guest.setLastName(lastName);
		}
		
	}
	
	void updateEmail(Guest guest, String email) {
		
		if (this.onTheListByName(guest)) {
			
			guest.setEmail(email);
		}
	}
	
	void updatePhoneNumber(Guest guest, String phoneNumber) {
		
		if (this.onTheListByName(guest)) {
			
			guest.setPhoneNumber(phoneNumber);
		}
	}
	
	int availableSpots() {

		return this.listOfParticipants.size() < this.participantsNo ? 
				this.participantsNo - this.listOfParticipants.size() : 0;
	}
	
	int participants() {
		return this.listOfParticipants.size() < this.participantsNo ? 
				this.listOfParticipants.size() : this.participantsNo;
	}
	
	int participantsInWaiting() {
		return this.listOfParticipants.size() > this.participantsNo ? 
				this.listOfParticipants.size() - this.participantsNo : 0;
	}
	
	int totalParticipants() {
		return this.listOfParticipants.size();
	}
	
	ArrayList <String> searchBySubstring (String str) {
		
		str = str.toLowerCase();
		
		ArrayList <String> matchSearch = new ArrayList <String> ();
		
		for (Guest guest: this.listOfParticipants) {
			
			if (guest.getLastName().toLowerCase().contains(str)) {
				matchSearch.add(guest.toString());
			} else if (guest.getFirstName().toLowerCase().contains(str)) {
				matchSearch.add(guest.toString());
			} else if (guest.getPhoneNumber().toLowerCase().contains(str)) {
				matchSearch.add(guest.toString());
			} else if (guest.getEmail().toLowerCase().contains(str)) {
				matchSearch.add(guest.toString());
			}
		}
		
		return matchSearch;
	}
	
	void printList() {
		
		for(int i = 0; i < this.participantsNo; i++) {
			if(i == this.listOfParticipants.size()) {
				return;
			}
			System.out.println((i + 1) + ". " + this.listOfParticipants.get(i));
		}
	}
	
	void printWaitingList() {
		int j = 1;
		for(int i = this.participantsNo; i < this.listOfParticipants.size(); i++) {
			System.out.println(j + ". " + this.listOfParticipants.get(i));
			j++;
		}
	}
	
	
	
}
