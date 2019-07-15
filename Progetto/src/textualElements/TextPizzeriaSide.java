package textualElements;

import database.PizzaDB;
import database.ToppingDB;
import interfaces.TextualInterface;
import javafx.scene.paint.Color;
import pizzeria.Pizzeria;
import pizzeria.services.sendMail.SendJavaMail;
import pizzeria.services.TextColorServices;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

@SuppressWarnings("deprecation")

     /** Gestisce ciò che la Pizzeria può fare, attraverso interfaccia testuale, dopo essersi autenticata. **/
public class TextPizzeriaSide {
	private Scanner scan = new Scanner(System.in);

	/** Comunica ciò che la pizzeria può fare, una volta autenticata. */
	public void whatDoesPizzeriaWant(TextualInterface textualInterface, Pizzeria pizzeria) throws SQLException {
		String risposta;
		System.out.println(TextPizzeriaSide.whatDoesPizzeriaWantPossibilities());
		System.out.print(TextColorServices.colorSystemOut("\t>> ", Color.YELLOW,false,false));
		risposta = scan.nextLine().toUpperCase();
		whatDoesPizzeriaWantAnswers(textualInterface,risposta,pizzeria);
	}

	/** Gestisce l epossibili azioni che la pizzeria può intraprendere */
	private void whatDoesPizzeriaWantAnswers(TextualInterface textualInterface, String risposta, Pizzeria pizzeria) throws SQLException {
		switch (risposta){
			case "V":
				/* Visualizza gli ordini da evadere oggi */
				pizzeria.updatePizzeriaToday();
				System.out.println(TextColorServices.colorSystemOut("Questi sono gli ordini da evadere:\n", Color.YELLOW, false, false));
				boolean almostOne = false;
				for(String code : pizzeria.getOrders().keySet()) {
					if (pizzeria.getOrders().get(code).getTime().getDate() == (new Date().getDate())){
						System.out.println(TextCustomerSide.recapOrder(pizzeria.getOrders().get(code)));
						almostOne = true;
					}
				}
				if(!almostOne)
					System.out.println(TextColorServices.colorSystemOut("\nAttualmente nessun ordine da evadere.\n", Color.YELLOW, false, false));
				whatDoesPizzeriaWant(textualInterface,pizzeria);
				break;
			case "M":
				/* Modifica il menu */
				System.out.println(TextPizzeriaSide.modifyMenuPossibilities());
				System.out.print(TextColorServices.colorSystemOut("\t>> ", Color.YELLOW,false,false));
				risposta = scan.nextLine().toUpperCase();
				howModifyMenuAnswer(risposta,pizzeria,textualInterface);
				whatDoesPizzeriaWant(textualInterface,pizzeria);
				break;
			case "S":
				/* Invia una e-mail personalizzata */
				sendTextualMail();
				whatDoesPizzeriaWant(textualInterface,pizzeria);
				break;
			case "E":
				/* Esce dall'area riservata */
				System.out.println(TextColorServices.colorSystemOut("Uscendo dall'area riservata...\n", Color.YELLOW, false, false));
				/* logout */
				textualInterface.askAccess();
				break;
			default:
				/* Inserito carattere errato */
				System.out.println(TextColorServices.colorSystemOut("Spiacenti: inserito carattere non valido. Riprovare: ", Color.RED, false, false));
				whatDoesPizzeriaWant(textualInterface,pizzeria);
				break;
		}
	}

	/** Specifica quali siano le possibilità della pizzeria per procedere. */
	private static String whatDoesPizzeriaWantPossibilities(){
		String intro = TextColorServices.colorSystemOut("\nPizzeria, ecco che cosa puoi fare:\n", Color.YELLOW,false,false);
		String con = "\t- con '";
		String ordini = TextColorServices.colorSystemOut("V", Color.ORANGE,true,false);
		String ordiniS = "' puoi visualizzare l'elenco degli ordini da evadere;\n";
		String modMenu = TextColorServices.colorSystemOut("M",Color.ORANGE,true,false);
		String modMenuS = "' puoi modificare voci del menu;\n";
		String mail = TextColorServices.colorSystemOut("S",Color.ORANGE,true,false);
		String mailS = "' puoi inviare una mail ad un utente;\n";
		String exit = TextColorServices.colorSystemOut("E",Color.ORANGE,true,false);
		String exitS = "' puoi uscire dalla tua area privilegiata.\n";

		return (TextColorServices.getLine() + intro + con + ordini + ordiniS + con + modMenu + modMenuS
				+ con + mail + mailS + con + exit + exitS);
	}

	/** Consente alla pizzeria di inviare una e-mail personalizzata all'indirizzo desiderato. */
	private void sendTextualMail() {
		String newAddQuestion = TextColorServices.colorSystemOut("Inserire indirizzo e-mail:\t\t\t", Color.YELLOW, false, false);
		System.out.print(newAddQuestion);
		String address = scan.nextLine().toLowerCase();
		String newSubjQuestion = TextColorServices.colorSystemOut("Inserire l'oggetto della mail:\t\t", Color.YELLOW, false, false);
		System.out.print(newSubjQuestion);
		String subject = scan.nextLine();
		String newTxtQuestion = TextColorServices.colorSystemOut("Inserire testo della mail (inserire linea vuota per concludere):\n", Color.YELLOW, false, false);
		System.out.print(newTxtQuestion);
		StringBuilder txt = new StringBuilder();
		do {
			String line = scan.nextLine();
			if (line.equals(""))
				break;
			else
				txt.append(line).append("\n");
		} while (true);
		askConfirmSendMail(address, subject, txt.toString());
	}

	/** Chiede conferma riguardo l'invio di una e-mail. */
	private void askConfirmSendMail(String address, String subject, String txt){
		String confAddrQuestion = TextColorServices.colorSystemOut("Confermi l'invio a:\t\t", Color.YELLOW, false, false);
		System.out.print(confAddrQuestion);
		String confAddr = TextColorServices.colorSystemOut(address,Color.WHITE,false,false);
		System.out.print(confAddr);
		String confSubjQuestion = TextColorServices.colorSystemOut("\ndell'email con oggetto:\t", Color.YELLOW, false, false);
		System.out.print(confSubjQuestion);
		String confSubj = TextColorServices.colorSystemOut(subject,Color.WHITE,false,false);
		System.out.print(confSubj);
		String confTxtQuestion = TextColorServices.colorSystemOut("\ncon testo:\n", Color.YELLOW,false,false);
		System.out.print(confTxtQuestion);
		String confTxt = TextColorServices.colorSystemOut(txt, Color.WHITE,false,false);
		System.out.print(confTxt);
		String confQuestion = TextColorServices.colorSystemOut("\n\t?\t(S/N):\t", Color.YELLOW,false,false);
		System.out.print(confQuestion);
		switch(scan.nextLine().toUpperCase()){
			case "S":
				SendJavaMail mail = new SendJavaMail();
				mail.sendMail(address,subject,txt);
				break;
			case "N":
				String err = "\tMessaggio eliminato.";
				System.out.println(TextColorServices.colorSystemOut(err,Color.YELLOW,true,false));
				break;
			default:
				String notValid = "Spiacenti: inserito carattere non valido. Riprovare:";
				System.out.println(TextColorServices.colorSystemOut(notValid,Color.RED,false,false));
				askConfirmSendMail(address,subject,txt);
				break;
		}
	}

	/** Specifica alla Pizzeria in che modo essa può apportare modifiche al menu. */
	private static String modifyMenuPossibilities() {
		String intro = TextColorServices.colorSystemOut("\nQuesto è ciò che puoi fare:\n",Color.YELLOW,false,false);
		String con = "\t- con '";
		String add = TextColorServices.colorSystemOut("A", Color.ORANGE,true,false);
		String addS = "' puoi aggiungere una pizza al menu;\n";
		String rmv = TextColorServices.colorSystemOut("R",Color.ORANGE,true,false);
		String rmvS = "' puoi rimuovere una pizza dal menu;\n";
		String addIng = TextColorServices.colorSystemOut("AI",Color.ORANGE,true,false);
		String addIngS = "' puoi aggiungere un ingrediente possibile;\n";
		String rmvIng = TextColorServices.colorSystemOut("RI",Color.ORANGE,true,false);
		String rmvIngS = "' puoi rimuovere un ingrediente da quelli possibili;\n";
		String ret = TextColorServices.colorSystemOut("B",Color.ORANGE,true,false);
		String retS = "' puoi tornare indietro.\n";

		return (intro + con + add + addS + con + rmv + rmvS + con + addIng + addIngS + con + rmvIng + rmvIngS
				+ con + ret + retS);
	}

	/** Gestisce le varie possibilità che la pizzeria ha di modificare il menu, in base alla risposta data. */
	private void howModifyMenuAnswer(String risposta, Pizzeria pizzeria, TextualInterface textualInterface) throws SQLException {
		switch (risposta){
			case "A":	/* Aggiungere una pizza al menu */
				PizzaDB.putPizzaText(pizzeria);
				break;
			case "R":	/* Rimuovere una pizza dal menu */
				PizzaDB.removePizzaText(pizzeria);
				break;
			case "AI":	/* Aggiungere un ingrediente a quelli possibili */
				ToppingDB.putToppingText(pizzeria);
				break;
			case "RI":	/* Rimuovere un ingrediente da quelli possibili */
				ToppingDB.removeToppingText(pizzeria);
				break;
			case "B":	/* Tornare indietro */
				System.out.println(TextColorServices.colorSystemOut("Nessuna modifica effettuata al menu.\n", Color.YELLOW, false, false));
				whatDoesPizzeriaWant(textualInterface,pizzeria);
				break;
			default:
				/* Carattere non valido: torna a "ecco cosa puoi fare" */
				System.out.println(TextColorServices.colorSystemOut("Spiacenti: inserito carattere non valido. Riprovare: ", Color.RED, false, false));
				break;
		}
	}
}