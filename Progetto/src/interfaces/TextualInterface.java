package interfaces;

import database.*;
import enums.OpeningPossibilities;
import javafx.scene.paint.Color;
import pizzeria.*;
import pizzeria.pizzeriaSendMail.SendJavaMail;
import pizzeria.services.*;
import pizzeria.services.TextColorServices;
import textualElements.TextCustomerSide;
import textualElements.TextNewOrder;
import textualElements.TextPizzeriaSide;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static enums.OpeningPossibilities.*;

/**
 * * @authors: Javengers, 2019
 *
 *  @author  Fecchio Andrea
 *  @author  Gobbo Matteo
 *  @author  Musitano Francesco
 *  @author  Rossanigo Fabio
 *  @author  Zanaboni Riccardo
 *
 * Avvia il programma tramite interfaccia testuale.
 */

@SuppressWarnings("deprecation")

public class TextualInterface {

	/**
	 * 16 parametri: nome, indirizzo, 7 orari di apertura (da domenica a sabato),
	 * 7 orari di chiusura (da domenica a sabato).
	 *
	 * Gli orari partono sempre da LocalTime.MIN, che corrisponde a mezzanotte.
	 * A questo si aggiunge (con il metodo plus()) ora e minuti desiderati.
	 *
	 * ATTENZIONE: Per lasciare la pizzeria chiusa in un particolare giorno, porre openTime = closeTime.
	 * PRESTARE PARTICOLARE ATTENZIONE: assicurarsi che ogni giorno la pizzeria rimanga aperta almeno 20 minuti.
	 *
	 * Per modificare gli orari successivamente, lavorerò con il metodo Pizzeria.setDayOfTheWeek().
	 * */
	private Pizzeria wolf = new Pizzeria("Wolf Of Pizza", "Via Bolzano 10, Pavia",
			/* orari di apertura, da domenica a sabato */
			LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES),
			/* orari di chiusura, da domenica a sabato */
			LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES)
	);
	private Scanner scan = new Scanner(System.in);

	/** Al lancio di interfaces.TextualInterface, inizia un nuovo ordine solo se richiesto. */
	public void whatDoYouWant(Customer customer) throws SQLException {
		String risposta;
		OpeningPossibilities isOpen = TimeServices.checkTimeOrder(wolf);
		/* se la pizzeria è aperta */
		if (isOpen == OpeningPossibilities.OPEN) {
			System.out.println(TextCustomerSide.whatDoYouWantPossibilities(true));
			System.out.print(TextColorServices.colorSystemOut("\t>> ", Color.YELLOW, false, false));
			risposta = scan.nextLine().toUpperCase();
			whatDoYouWantAnswers(true, risposta, customer);
			/* se la pizzeria è chiusa o in chiusura */
		} else {
			String chiusura;
			if (isOpen == CLOSING)
				chiusura = "\nAttenzione: la pizzeria è in chiusura. Impossibile effettuare ordini al momento.";
			else
				chiusura = "\nAttenzione: la pizzeria per oggi è chiusa. Impossibile effettuare ordini al momento.";
			System.out.println(TextColorServices.colorSystemOut(chiusura, Color.RED, false, false));
			System.out.println(TextCustomerSide.whatDoYouWantPossibilities(false));
			System.out.print(TextColorServices.colorSystemOut("\t>> ", Color.YELLOW, false, false));
			risposta = scan.nextLine().toUpperCase();
			whatDoYouWantAnswers(false, risposta, customer);
		}
	}

	/** In whatDoYouWant(), gestisce le possibili risposte alla domanda. */
	public void whatDoYouWantAnswers(boolean isOpen, String risposta, Customer customer) throws SQLException {
		switch (risposta){
			case "L":
				Order last = PizzeriaServices.CustomerLastOrder(customer,wolf);
				if(last != null){
				System.out.println("\n" + customer.getUsername() + ", questo è l'ultimo ordine che hai effettuato:");
				System.out.println(TextCustomerSide.recapOrder(last));
				} else System.out.println(TextColorServices.colorSystemOut("\n" + customer.getUsername() + ", non hai ancora effettuato nessun ordine!\n",Color.RED,false,false));
				whatDoYouWant(customer);
				break;
			case "M":
				TextCustomerSide customerSide = new TextCustomerSide();
				customerSide.modifyAccount(customer);
				whatDoYouWant(customer);
				break;
			case "H":
				System.out.println(PizzeriaServices.getHistory(false));
				whatDoYouWant(customer);
				break;
			case "E":
				System.out.println(TextColorServices.colorSystemOut("Uscendo dall'area riservata...\n", Color.YELLOW, false, false));
				/* logout */
				askAccess();
				break;
			default:
				if(isOpen && risposta.equals("N")){
					TextNewOrder newOrder = new TextNewOrder();
					newOrder.makeOrderText(customer, wolf, this);
				} else {
					System.out.println(TextColorServices.colorSystemOut("\nSpiacenti: inserito carattere non valido. Riprovare:", Color.RED, false, false));
					whatDoYouWant(customer);
				} break;
		}
	}

	/** Chiede se si vuole procedere con il login o con la creazione di un nuovo account. */
	public void askAccess() throws SQLException {
		String log = TextColorServices.colorSystemOut("L",Color.ORANGE,true,false);
		String newAcc = TextColorServices.colorSystemOut("N",Color.ORANGE,true,false);
		String recPsw = TextColorServices.colorSystemOut("R",Color.ORANGE,true,false);
		System.out.println(TextColorServices.colorSystemOut("\t>> Digitare:\n",Color.YELLOW,false,false)
				+ "\t\t'" + log + "' per eseguire il login,\n\t\t'" + newAcc + "' per creare un nuovo account,\n\t\t'"
				+ recPsw + "' per recuperare i dati del tuo account.");
		System.out.print(TextColorServices.colorSystemOut("\t>>\t",Color.YELLOW,false,false));
		String answer = scan.nextLine().toUpperCase();
		switch (answer) {
			case "L":
				String userQuestion = TextColorServices.colorSystemOut("\n\tUsername:\t", Color.YELLOW, false, false);
				System.out.print(userQuestion);
				String user = scan.nextLine().toUpperCase();
				String pswQuestion = TextColorServices.colorSystemOut("\tPassword:\t", Color.YELLOW, false, false);
				System.out.print(pswQuestion);
				String psw = scan.nextLine().toUpperCase();
				String working1 = TextColorServices.colorSystemOut("\n\tAccedendo al database...\n", Color.GREENYELLOW, false, false);
				System.out.print(working1);
				switch(PizzeriaServices.checkLogin(wolf, user,psw)){
					case OK:
						Customer c = new Customer(user);
						System.out.println("\nBenvenuto: " + user);
						whatDoYouWant(c);
						break;
					case PIZZERIA:
						System.out.println("\nBenvenuto: " + user + " (utente privilegiato)");
						TextPizzeriaSide pizzeriaSide = new TextPizzeriaSide();
						pizzeriaSide.whatDoesPizzeriaWant(this,wolf);
						break;
					case NO:
						System.out.println(TextColorServices.colorSystemOut("\nUsername o password errati: riprovare.\n",Color.RED,false,false));
						askAccess();
						break;
				}
				break;
			case "N":
				String newUserQuestion = TextColorServices.colorSystemOut("\n\tNuovo username:\t\t", Color.YELLOW, false, false);
				System.out.print(newUserQuestion);
				String newUser = scan.nextLine().toUpperCase();
				String mailQuestion = TextColorServices.colorSystemOut("\tIndirizzo e-mail:\t", Color.YELLOW, false, false);
				System.out.print(mailQuestion);
				String mail = scan.nextLine().toLowerCase();
				String newPswQuestion = TextColorServices.colorSystemOut("\tNuova password:\t\t", Color.YELLOW, false, false);
				System.out.print(newPswQuestion);
				String newPsw = scan.nextLine().toUpperCase();
				String confPswQuestion = TextColorServices.colorSystemOut("\tConferma psw:\t\t", Color.YELLOW, false, false);
				System.out.print(confPswQuestion);
				String confPsw = scan.nextLine().toUpperCase();
				String working2 = TextColorServices.colorSystemOut("\n\tAccedendo al database...\n", Color.GREENYELLOW, false, false);
				System.out.print(working2);
				switch(PizzeriaServices.canCreateAccount(mail,newUser,newPsw,confPsw)) {
					case OK:
						SendJavaMail newMail = new SendJavaMail();
						if(!newMail.welcomeMail(newUser,newPsw,mail)) {    // se indirizzo mail non valido
							System.out.println(TextColorServices.colorSystemOut("Errore: indirizzo e-mail non valido. Riprovare.",Color.RED,false,false));
							askAccess();
						}
						else {
							CustomerDB.putCustomer(newUser,newPsw,mail);
							System.out.println("\nBenvenuto: " + newUser.toUpperCase() + ". Hai creato un nuovo account.\n");
							/* login automatico */
							Customer c = new Customer(newUser);
							whatDoYouWant(c);
						}
						break;
					case SHORT:
						System.out.println(TextColorServices.colorSystemOut("\nUsername o password troppo breve: riprovare.\n",Color.RED,false,false));
						askAccess();
						break;
					case EXISTING:
						System.out.println(TextColorServices.colorSystemOut("\nDati già presenti nel Database: riprovare con indirizzo e-mail o username differente.\nSe già registrato, effettuare il login.\n",Color.RED,false,false));
						askAccess();
						break;
					case DIFFERENT:
						System.out.println(TextColorServices.colorSystemOut("\nPassword non coincidente: riprovare.\n",Color.RED,false,false));
						askAccess();
						break;
				}
				break;
			case "R":
				String recoverMailQuestion = TextColorServices.colorSystemOut("\n\tInserisci l'indirizzo e-mail dell'account:\t", Color.YELLOW, false, false);
				System.out.print(recoverMailQuestion);
				String recMail = scan.nextLine().toUpperCase();
				if(Database.checkMail(recMail)) {
					SendJavaMail newMail = new SendJavaMail();
					newMail.recoverPassword(recMail);
					System.out.println(TextColorServices.colorSystemOut("\nUna e-mail ti è stata inviata.\n", Color.YELLOW, false, false));
					askAccess();
				} else {
					System.out.println(TextColorServices.colorSystemOut("\nSpiacenti: indirizzo e-mail non presente nel Database.\n", Color.RED, false, false));
					askAccess();
				}
				break;
			default:
				String spiacenti = "\nSpiacenti: carattere inserito non valido. Riprovare:\n";
				System.out.println(TextColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
				askAccess();
				break;
		}
	}

	public static void main(String[] args) {
		TextualInterface textInterface = new TextualInterface();
		System.out.println(TextCustomerSide.helloThere(textInterface.wolf));
		try {
            textInterface.askAccess();
        } catch (SQLException e) {
			e.printStackTrace();
        }
	}
}