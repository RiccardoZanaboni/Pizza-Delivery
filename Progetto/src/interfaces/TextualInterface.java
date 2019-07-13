package interfaces;

import database.*;
import javafx.scene.paint.Color;
import pizzeria.*;
import pizzeria.pizzeriaSendMail.SendJavaMail;
import pizzeria.services.*;
import pizzeria.services.TextColorServices;
import textualElements.TextCustomerSide;
import textualElements.TextPizzeriaSide;

import java.sql.SQLException;
import java.util.*;

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

	/** Richiama il costruttore della pizzeria. */
	private Pizzeria wolf = new Pizzeria("Wolf Of Pizza", "Via della Mozzarella, Pavia");
	private Scanner scan = new Scanner(System.in);

	/** Chiede se si vuole procedere con il login o con la creazione di un nuovo account, agendo di conseguenza. */
	public void askAccess() throws SQLException {

		/* Domanda di accesso */
		String log = TextColorServices.colorSystemOut("L",Color.ORANGE,true,false);
		String newAcc = TextColorServices.colorSystemOut("N",Color.ORANGE,true,false);
		String recPsw = TextColorServices.colorSystemOut("R",Color.ORANGE,true,false);
		System.out.println(TextColorServices.colorSystemOut("\t>> Digitare:\n",Color.YELLOW,false,false)
				+ "\t\t'" + log + "' per eseguire il login,\n\t\t'" + newAcc + "' per creare un nuovo account,\n\t\t'"
				+ recPsw + "' per recuperare i dati del tuo account.");
		System.out.print(TextColorServices.colorSystemOut("\t>>\t",Color.YELLOW,false,false));

		/* Lettura della risposta */
		String answer = scan.nextLine().toUpperCase();
		switch (answer) {
			case "L":
				/* login: vengono richiesti username e password */
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
						/* l'utente ha correttamente effettuato il login */
						Customer c = new Customer(user);
						System.out.println("\nBenvenuto: " + user);
						TextCustomerSide customerSide = new TextCustomerSide();
						customerSide.whatDoYouWant(c,wolf);
						break;
					case PIZZERIA:
						/* la pizzeria si è correttamente autenticata come tale */
						System.out.println("\nBenvenuto: " + user + " (utente privilegiato)");
						TextPizzeriaSide pizzeriaSide = new TextPizzeriaSide();
						pizzeriaSide.whatDoesPizzeriaWant(this,wolf);
						break;
					case NO:
						/* la combinazione username-password non corrisponde ad alcun utente salvato nel DB */
						System.out.println(TextColorServices.colorSystemOut("\nUsername o password errati: riprovare.\n",Color.RED,false,false));
						askAccess();
						break;
				}
				break;
			case "N":
				/* Viene richiesta la creazione di un nuovo account */
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
						/* tutti i dati sono stati correttamente inseriti */
						SendJavaMail newMail = new SendJavaMail();
						if (newMail.welcomeMail(newUser, newPsw, mail)) {
							/* se l'indirizzo e-mail risulta valido */
							CustomerDB.putCustomer(newUser,newPsw,mail);
							System.out.println("\nBenvenuto: " + newUser.toUpperCase() + ". Hai creato un nuovo account.\n");
							/* login automatico */
							Customer c = new Customer(newUser);
							TextCustomerSide customerSide = new TextCustomerSide();
							customerSide.whatDoYouWant(c,wolf);
						} else {
							/* se indirizzo e-mail non valido */
							System.out.println(TextColorServices.colorSystemOut("Errore: indirizzo e-mail non valido. Riprovare.",Color.RED,false,false));
							askAccess();
						}
						break;
					case SHORT:
						/* Username o password non sono lunghi almeno TOT caratteri */
						System.out.println(TextColorServices.colorSystemOut("\nUsername o password troppo breve: riprovare.\n",Color.RED,false,false));
						askAccess();
						break;
					case EXISTING:
						/* Un account con stesso username o indirizzo e-mail risulta già presente nel DB */
						System.out.println(TextColorServices.colorSystemOut("\nDati già presenti nel Database: riprovare con indirizzo e-mail o username differente.\nSe già registrato, effettuare il login.\n",Color.RED,false,false));
						askAccess();
						break;
					case DIFFERENT:
						/* La password non è stata confermata correttamente */
						System.out.println(TextColorServices.colorSystemOut("\nPassword non coincidente: riprovare.\n",Color.RED,false,false));
						askAccess();
						break;
				}
				break;
			case "R":
				/* viene richiesta una e-mail, in caso di smarrimento dei dati di accesso */
				String recoverMailQuestion = TextColorServices.colorSystemOut("\n\tInserisci l'indirizzo e-mail dell'account:\t", Color.YELLOW, false, false);
				System.out.print(recoverMailQuestion);
				String recMail = scan.nextLine().toUpperCase();
				if(Database.checkMail(recMail)) {
					/* Se l'indirizzo e-mail risulta presente nel DB */
					SendJavaMail newMail = new SendJavaMail();
					newMail.recoverPassword(recMail);
					System.out.println(TextColorServices.colorSystemOut("\nUna e-mail ti è stata inviata.\n", Color.YELLOW, false, false));
					askAccess();
				} else {
					/* Indirizzo e-mail non presente */
					System.out.println(TextColorServices.colorSystemOut("\nSpiacenti: indirizzo e-mail non presente nel Database.\n", Color.RED, false, false));
					askAccess();
				}
				break;
			default:
				/* Carattere non valido */
				String spiacenti = "\nSpiacenti: carattere inserito non valido. Riprovare:\n";
				System.out.println(TextColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
				askAccess();
				break;
		}
	}

	/** Viene presentata la pizzeria e subito richiamato il metodo di accesso all'area riservata. */
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