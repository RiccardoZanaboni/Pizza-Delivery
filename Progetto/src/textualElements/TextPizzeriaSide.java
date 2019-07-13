package textualElements;

import database.PizzaDB;
import database.ToppingDB;
import interfaces.TextualInterface;
import javafx.scene.paint.Color;
import pizzeria.Pizzeria;
import pizzeria.pizzeriaSendMail.SendJavaMail;
import pizzeria.services.TextColorServices;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class TextPizzeriaSide {
	private Scanner scan = new Scanner(System.in);

	public void whatDoesPizzeriaWant(TextualInterface textualInterface, Pizzeria pizzeria) throws SQLException {
		String risposta;
		System.out.println(TextPizzeriaSide.whatDoesPizzeriaWantPossibilities());
		System.out.print(TextColorServices.colorSystemOut("\t>> ", Color.YELLOW,false,false));
		risposta = scan.nextLine().toUpperCase();
		switch (risposta){
			case "V":
				pizzeria.updatePizzeriaToday();
				System.out.println(TextColorServices.colorSystemOut("Questi sono gli ordini da evadere:\n", Color.YELLOW, false, false));
				/* Visualizza solo gli ordini di oggi */
				for(String code : pizzeria.getOrders().keySet()){
					if(pizzeria.getOrders().get(code).getTime().getDate()==(new Date().getDate()))
						System.out.println(TextCustomerSide.recapOrder(pizzeria.getOrders().get(code)));
				}
				whatDoesPizzeriaWant(textualInterface,pizzeria);
				break;
			case "M":
				System.out.println(TextPizzeriaSide.modifyMenuPossibilities());
				System.out.print(TextColorServices.colorSystemOut("\t>> ", Color.YELLOW,false,false));
				risposta = scan.nextLine().toUpperCase();
				howModifyMenuAnswer(risposta,pizzeria,textualInterface);
				whatDoesPizzeriaWant(textualInterface,pizzeria);
				break;
			case "S":
				sendTextualMail();
				whatDoesPizzeriaWant(textualInterface,pizzeria);
				break;
			case "E":
				System.out.println(TextColorServices.colorSystemOut("Uscendo dall'area riservata...\n", Color.YELLOW, false, false));
				/* logout */
				textualInterface.askAccess();
				break;
			default:
				System.out.println(TextColorServices.colorSystemOut("Spiacenti: inserito carattere non valido. Riprovare: ", Color.RED, false, false));
				whatDoesPizzeriaWant(textualInterface,pizzeria);
				break;
		}
	}

	/** In interfaces.TextualInterface.whatDoYouWant(), chiede quali siano le intenzioni della pizzeria, per procedere. */
	public static String whatDoesPizzeriaWantPossibilities(){
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

	public void sendTextualMail() {
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

	public void askConfirmSendMail(String address, String subject, String txt){
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

	public static String modifyMenuPossibilities() {
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

	public void howModifyMenuAnswer(String risposta, Pizzeria pizzeria, TextualInterface textualInterface) throws SQLException {
		switch (risposta){
			case "A":
				PizzaDB.putPizzaText(pizzeria);
				break;
			case "R":
				PizzaDB.removePizzaText(pizzeria);
				break;
			case "AI":
				ToppingDB.putToppingText(pizzeria);
				break;
			case "RI":
				ToppingDB.removeToppingText(pizzeria);
				break;
			case "B":
				System.out.println(TextColorServices.colorSystemOut("Nessuna modifica effettuata al menu.\n", Color.YELLOW, false, false));
				// logout
				whatDoesPizzeriaWant(textualInterface,pizzeria);
				break;
			default:
				System.out.println(TextColorServices.colorSystemOut("Spiacenti: inserito carattere non valido. Riprovare: ", Color.RED, false, false));
				/* torna a "ecco cosa puoi fare" */
				break;
		}
	}
}