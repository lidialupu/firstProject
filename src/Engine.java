//import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Engine implements Serializable{
	
	private static GuestsList firstList;
	
	private static final long serialVersionUID = 1L;
	
	
	public static void startMethod (Scanner sc) {
		try {
			firstList = new GuestsList(readFromBinaryFile(), readGuestsNumber());
		} catch (IOException e){

		} 
		
		try {
			firstList.getListOfParticipants();
		} catch (NullPointerException e) {
			firstList = new GuestsList(sc);
			sc.nextLine();
		}
		
		boolean keyword = true;
		
		while (keyword) {
			
			System.out.print("Asteapta comanda(help pentru meniu):");
			String option = sc.nextLine();
			
			switch(option) {
				case "help":
					System.out.println(Engine.printMenu());
					break;
				case "add":
					Guest newGuest = Engine.createGuest(sc);
					if (firstList.add(newGuest) < 0) {
						System.out.println("Persoana este deja inscrisa!");
					} 
					break;
				case "check":
					if (Engine.check(sc)) {
						System.out.println("Persoana se afla pe lista una dintre liste!");
						break;
					} else {
						System.out.println("Persoana nu a fost gasita!");
						break;
					}
				case "remove":
					Engine.removeGuest(sc);
					break;
				case "update":
					Engine.updateGuest(sc);
					break;
				case "guests":
					try {
						if (firstList.getListOfParticipants().size() < 1) {
							System.out.println("Niciun participant inscris...");
							break;
						} else {
							firstList.printList();
							break;
						}
					} catch  (NullPointerException e)  {
						System.out.println("Niciun participant inscris...");
						break;
					}
				case "waitlist":
					try {
						if (firstList.getListOfParticipants().size() <= firstList.getParticipantsNo()) {
							System.out.println("Lista de asteptare este goala...");
							break;
						} else {
							firstList.printWaitingList();
							break;
						}	
					} catch (NullPointerException e) {
						System.out.println("Lista de asteptare este goala...");
						break;
					}
				case "available":
					try {
						System.out.println("Numarul de locuri ramase: " + firstList.availableSpots());
						break;
					} catch (NullPointerException e) {
						System.out.println("Numarul de locuri ramase: " + firstList.getParticipantsNo());
						break;
					}
					
				case "guests_no":
					System.out.println("Numarul de participanti: " + firstList.participants());
					break;
				case "waitlist_no":
					System.out.println("Dimensiunea listei de astepare: " + firstList.participantsInWaiting());
					break;
				case "subscribe_no":
					System.out.println("Numarul total de persoane: " + firstList.totalParticipants());
					break;
				case "search":
					System.out.println(Engine.searchGuests(sc));
					break;
				case "quit":
					keyword = false;
					try {
						writeToBinaryFile(firstList.getListOfParticipants());
						writeGuestNumber(firstList.getParticipantsNo());
					} catch (IOException e) {
						System.out.println("Ceva nu a functionat bine...");
					}
					break;
				case "reset":
					firstList = new GuestsList(sc);
					sc.nextLine();
					break;
				default:
					System.out.println("Comanda introdusa nu este valida.");
					System.out.println("Incercati inca o data.");
					break;
			}
		}
	}
	private static Guest createGuest (Scanner sc) {
		System.out.println("Se adauga o noua persoana...");
		
		System.out.print("Introduceti numele: ");
		String nume = sc.nextLine();
		
		System.out.print("Introduceti prenumele: ");
		String prenume = sc.nextLine();

		System.out.print("Introduceti email: ");
		String email = sc.nextLine();

		System.out.print("Introduceti nr de tel: ");
		String nrTel = sc.nextLine();

		Guest newGuest = new Guest (nume, prenume, email, nrTel);
		
		return newGuest;
	}
	
	
	private static boolean check (Scanner sc) {
		
		System.out.print("Introduceti criteriul de cautare: \n\t" + "Nume si prenume: 1\n\t" + "Email: 2\n\t" + "Numar de telefon: 3\n\t");
		String option = sc.nextLine();
		
		switch (option) {
		
			case "1":
				System.out.print("Introduceti numele: ");
				String nume = sc.nextLine();
				System.out.print("Introduceti prenumele: ");
				String prenume = sc.nextLine();
				Guest toCheck = firstList.guestOnTheListByName(nume, prenume);
				return firstList.onAnyList(option, toCheck);
				
			case "2":
				System.out.print("Introduceti email: ");
				String mail = sc.nextLine();
				toCheck = firstList.guestOnTheListByEmail(mail);
				return firstList.onAnyList(option, toCheck);
				
			case "3":
				System.out.print("Introduceti nr de tel: ");
				String phone = sc.nextLine();
				toCheck = firstList.guestOnTheListByPhone(phone);
				return firstList.onAnyList(option, toCheck);
				
			default:
				return false;
		}
		
	}
	
	private static void removeGuest(Scanner sc) {
		System.out.println("Se sterge o persoana  existenta din lista...");
		System.out.print("Alege modul de autentificare, tastand: \n\t" + "Nume si prenume: \"1\"\n\t" + "Email: \"2\"\n\t" + "Numar de telefon: \"3\"\n\t");
		String option = sc.nextLine();
		
		switch (option) {
			case "1":
				System.out.print("Introduceti numele: ");
				String nume = sc.nextLine();
				
				System.out.print("Introduceti prenumele: ");
				String prenume = sc.nextLine();
				
				if (!firstList.removeByName(nume, prenume)) {
					System.out.println("Eroare: Persoana nu este inscrisa la eveniment.");
				}
				return;
			case "2":
				System.out.print("Introduceti email: ");
				String mail = sc.nextLine();
				
				if (!firstList.removeByEmail(mail)) {
					System.out.println("Eroare: Persoana nu este inscrisa la eveniment.");
				}
				return;
			case "3":
				System.out.print("Introduceti nr de tel: ");
				String phone = sc.nextLine();
				
				if (!firstList.removeByPhoneNumber(phone)) {
					System.out.println("Eroare: Persoana nu este inscrisa la eveniment.");
				}
				return;
			default:
				return;
		}
	}
	
	private static String printMenu() {
		return 	"help " + "- Afiseaza aceasta lista de comenzi\n" +
				"add " + "- Adauga o noua persoana (inscriere)\n" +
				"check " + "- Verifica daca o persoana este inscrisa la eveniment\n" + 
				"remove " + "- Sterge o persoana existenta din lista\n" +
				"update " + "- Actualizeaza detaliile unei persoane\n" +
				"guests " + "- Lista de persoane care participa la eveniment\n" +
				"waitlist " + "- Persoanele din lista de asteptare\n" +
				"available " + "- Numarul de locuri libere\n" +
				"guests_no " + "- Numarul de persoane care participa la eveniment\n" +
				"waitlist_no " + "- Numarul de persoane din lista de asteptare\n" +
				"subscribe_no " + "- Numarul total de persoane inscrise\n" +
				"search " + "- Cauta toti invitatii conform sirului de caractere introdus\n" +
				"reset " + "- Reseteaza lista de invitati" +
				"quit " + "- Inchide aplicatia";
	}
	
	private static Guest returnGuest (Scanner sc) {
		
		System.out.println("Alege modul de autentificare, tastand:: \n\t" + "Nume si prenume: \"1\"\n\t" + "Email: \"2\"\n\t" + "Numar de telefon: \"3\"\n\t");
		String option = sc.nextLine();
		
		switch (option) {
			case "1":
				System.out.println("Introduceti numele: ");
				String lastName = sc.nextLine();
				System.out.println("Introduceti prenumele: ");
				String firstName = sc.nextLine();
				Guest toUpdate = firstList.guestOnTheListByName(lastName, firstName);
				return toUpdate;
			case "2":
				System.out.println("Introduceti email: ");
				String email = sc.nextLine();
				toUpdate = firstList.guestOnTheListByEmail(email);
				return toUpdate;
			case "3":
				System.out.println("Introduceti numarul de telefon: ");
				String phone = sc.nextLine();
				toUpdate = firstList.guestOnTheListByPhone(phone);
				return toUpdate;
			default:
				return null;
		}
	}
	
	private static void updateGuest (Scanner sc) {
		System.out.println("Se actualizeaza detaliile unei persoane...");
		Guest toUpdate = Engine.returnGuest(sc);
		
		System.out.println("Alege campul de actualizat, tastand: \n\t" + "Nume: \"1\"\n\t"  + "Prenume: \"2\"\n\t" + "Email: \"3\"\n\t"
					+ "Numar de telefon: \"4\"\n\t");
		String option = sc.nextLine();
		
		switch (option) {
			case "1":
				System.out.println("Introduceti numele: ");
				String nume = sc.nextLine();	
				firstList.updateLastName(toUpdate, nume);
				break;
			case "2":
				System.out.println("Introduceti prenumele: ");
				String prenume = sc.nextLine();	
				firstList.updateFirstName(toUpdate, prenume);
				break;
			case "3":
				System.out.println("Introduceti email: ");
				String mail = sc.nextLine();
				firstList.updateEmail(toUpdate, mail);
				return;
			case "4":
				System.out.println("Introduceti numarul de telefon: ");
				String phone = sc.nextLine();
				firstList.updatePhoneNumber(toUpdate, phone);
				return;
			default:
				return;
		}
		
	}
	
	private static ArrayList <String> searchGuests (Scanner sc) {
		System.out.print("Introduceti subsirul pentru cautare: ");
		String str = sc.nextLine();
		return firstList.searchBySubstring(str);

		
	}
	
	public static void writeToBinaryFile(ArrayList<Guest> data) throws IOException{
		try(ObjectOutputStream binaryFileOut = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("./guestList.dat")))) {
			binaryFileOut.writeObject(data);
		}
		  
	}
	
	public static void writeGuestNumber(int data) throws IOException{
		try(DataOutputStream binaryFileOut = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream("./guestsNo.dat")))) {
			binaryFileOut.writeInt(data);
		}
		  
	}
		 
	 @SuppressWarnings("unchecked")
	public static ArrayList<Guest> readFromBinaryFile() throws IOException {
		 ArrayList<Guest> data = null;
		 
		 try(ObjectInputStream binaryFileIn = new ObjectInputStream(
		        new BufferedInputStream(new FileInputStream("./guestList.dat")))) {
			 data = (ArrayList<Guest>) binaryFileIn.readObject();
		 } catch (ClassNotFoundException e) {
		      System.out.println("A class not found exception: " + e.getMessage());
		 }
		 
		 return data;
	 }
	

	public static int readGuestsNumber() throws IOException {
		 int data = 0;
			 
		 try(DataInputStream binaryFileIn = new DataInputStream(
				 new BufferedInputStream(new FileInputStream("./guestsNo.dat")))) {
				 data = (int) binaryFileIn.readInt();
		 }
			 
		 return data;
	 }
	
	
}
