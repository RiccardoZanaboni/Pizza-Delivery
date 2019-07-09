package interfaces;

import database.Database;
import enums.OpeningPossibilities;
import exceptions.RestartOrderExc;
import exceptions.TryAgainExc;
import javafx.scene.paint.Color;
import pizzeria.*;
import pizzeria.pizzeriaSendMail.SendJavaMail;
import services.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static enums.OpeningPossibilities.CLOSE;
import static enums.OpeningPossibilities.CLOSING;

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
	public Pizzeria wolf = new Pizzeria("Wolf Of Pizza", "Via Bolzano 10, Pavia",
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
	private void whatDoYouWant(Customer customer) throws SQLException {
		String risposta;
		OpeningPossibilities isOpen = TimeServices.checkTimeOrder(wolf);
		/* se la pizzeria è aperta */
		if (isOpen == OpeningPossibilities.OPEN) {
			System.out.println(TextualServices.whatDoYouWantPossibilities(true));
			System.out.print(TextualColorServices.colorSystemOut("\t>> ", Color.YELLOW, false, false));
			risposta = scan.nextLine().toUpperCase();
			whatDoYouWantAnswers(true, risposta, customer);
			/* se la pizzeria è chiusa o in chiusura */
		} else {
			String chiusura;
			if (isOpen == CLOSING)
				chiusura = "\nAttenzione: la pizzeria è in chiusura. Impossibile effettuare ordini al momento.";
			else
				chiusura = "\nAttenzione: la pizzeria per oggi è chiusa. Impossibile effettuare ordini al momento.";
			System.out.println(TextualColorServices.colorSystemOut(chiusura, Color.RED, false, false));
			System.out.println(TextualServices.whatDoYouWantPossibilities(false));
			System.out.print(TextualColorServices.colorSystemOut("\t>> ", Color.YELLOW, false, false));
			risposta = scan.nextLine().toUpperCase();
			whatDoYouWantAnswers(false, risposta, customer);
		}
	}

	/** In whatDoYouWant(), gestisce le possibili risposte alla domanda. */
	private void whatDoYouWantAnswers(boolean isOpen, String risposta, Customer customer) throws SQLException {
		switch (risposta){
			case "L":
				Order last = PizzeriaServices.CustomerLastOrder(customer,wolf);
				if(last != null){
				System.out.println("\n" + customer.getUsername() + ", questo è l'ultimo ordine che hai effettuato:");
				System.out.println(TextualServices.recapOrder(last));
				} else System.out.println(TextualColorServices.colorSystemOut("\n" + customer.getUsername() + ", non hai ancora effettuato nessun ordine!\n",Color.RED,false,false));
				whatDoYouWant(customer);
				break;
			case "M":
				modifyAccount(customer);
				whatDoYouWant(customer);
				break;
			case "H":
				System.out.println(PizzeriaServices.getHistory(false));
				whatDoYouWant(customer);
				break;
			case "E":
				System.out.println(TextualColorServices.colorSystemOut("Uscendo dall'area riservata...\n", Color.YELLOW, false, false));
				/* logout */
				askAccess();
				break;
			default:
				if(isOpen && risposta.equals("N")){
					makeOrderText(customer);
				} else {
					System.out.println(TextualColorServices.colorSystemOut("\nSpiacenti: inserito carattere non valido. Riprovare:", Color.RED, false, false));
					whatDoYouWant(customer);
				} break;
		}
	}

	private void modifyAccount(Customer customer) {
		String user = customer.getUsername();
		System.out.print("\nInserisci il tuo nome: ");
		String nome = scan.nextLine();
		System.out.print("Inserisci il tuo cognome: ");
		String cognome = scan.nextLine();
		System.out.print("Inserisci il tuo indirizzo principale: ");
		String indirizzo = scan.nextLine();
		if(Database.addInfoCustomer(user,nome,cognome,indirizzo)) {
			System.out.println(TextualColorServices.colorSystemOut("\nGrazie! Dati aggiornati.", Color.YELLOW, false, false));
			customer.setName(nome);
			customer.setSurname(cognome);
			customer.setAddress(indirizzo);
		} else System.out.println(TextualColorServices.colorSystemOut("\nErrore nell'aggiornamento dei dati.",Color.RED,false,false));
	}

	/** Effettua tutte le operazioni necessarie ad effettuare un nuovo ordine.
	 * Utilizzo la sigla-chiave "OK" una volta terminata la scelta delle pizze, per continuare.
	 * Utilizzo ovunque la sigla-chiave "F" per l'annullamento dell'ordine: si torna all'inizio. */
	private void makeOrderText(Customer customer) throws SQLException {
		wolf.updatePizzeriaToday();
		System.out.println(TextualServices.printMenu(wolf));
		Order order = wolf.initializeNewOrder();
		order.setCustomer(customer);
		int num;
		String nomePizza;
		int tot = 0;
		boolean isPrimaRichiesta = true;
		try {
			do {
				nomePizza = whichPizza(isPrimaRichiesta);
				if (nomePizza.equals("OK")) {
					break;      // smette di chiedere pizze
				}
				isPrimaRichiesta = false;
				num = howManySpecificPizza(order, nomePizza);
				if (order.getNumPizze() == 16) {
					tot += num;
					break;      // hai chiesto esattamente 16 pizze in totale: smette di chiedere pizze
				}
				tot += num;
			} while (true);

			Date orario;
			orario = orderTime(order, tot);
			System.out.println(orario);
			if (insertNameAndAddress(order)) {
				System.out.println(TextualServices.recapOrder(order));
				askConfirm(order, orario);
			}
		} catch (RestartOrderExc e) {
			String annullato = TextualColorServices.colorSystemOut("L'ordine è stato annullato.",Color.ORANGE,true,false);
			System.out.println("\t>> " + annullato);
			System.out.println(TextualColorServices.getLine());
			whatDoYouWant(customer);
		}
	}

	/** Esegue i controlli dovuti sulla stringa relativa all'orario e,
	 * in caso di successo, restituisce il Date "orarioScelto". */
	private Date orderTime(Order order, int tot) throws RestartOrderExc {
		String orarioScelto = insertTime(tot);
		Date d;
		try {
			if (orarioScelto.toUpperCase().equals("F")) {
				throw new RestartOrderExc();
			}
			if (TimeServices.checkValidTime(orarioScelto)) {
				d = TimeServices.stringToDate(orarioScelto);
				assert d != null : "Error"; // se la condizione non è verificata, il programma viene terminato
				int ora = d.getHours();
				int minuti = d.getMinutes();
				if (!wolf.isOpen(d)) {
					throw new ArrayIndexOutOfBoundsException();
				}
				if (!checkNotTooLate(d,tot) | !wolf.checkTimeBoxOven(ora,minuti,tot) | wolf.aFreeDeliveryMan(ora,minuti)==null) {
					throw new TryAgainExc();
				}
				else {
					order.setTime(d);
				}
			} else {
				throw new TryAgainExc();
			}
		} catch (ArrayIndexOutOfBoundsException obe) {
			String spiacenti = "Spiacenti: la pizzeria è chiusa nell'orario inserito. Riprovare: ";
			System.out.println(TextualColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
			d = orderTime(order, tot);
		} catch (TryAgainExc re) {
			String spiacenti = "Spiacenti: inserito orario non valido. Riprovare: ";
			System.out.println(TextualColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
			d = orderTime(order, tot);
		}
		return d;
	}

	/** Stampa a video tutti gli orari disponibili per la consegna
	 * e ritorna la stringa inserita dall'utente. */
	private String insertTime (int tot) throws RestartOrderExc {
		String domanda = TextualColorServices.colorSystemOut("A che ora vuoi ricevere la consegna? [formato HH:mm]",Color.YELLOW,false,false);
		System.out.println(domanda + " \t\t(Inserisci 'F' per annullare.)");
		System.out.println(TextualColorServices.colorSystemOut("\tEcco gli orari disponibili:",Color.YELLOW,false,false));
		int c = 0;
		System.out.print("\t");
		try {
			for (String s : TimeServices.availableTimes(wolf, tot)) {
				System.out.print(s);
				c++;
				if (c % 18 == 0) System.out.print("\n\t");      // stampa 18 orari su ogni riga
			}
			System.out.print("\n");
		} catch (NullPointerException npe) {
			throw new RestartOrderExc();
		}
		return scan.nextLine();
	}

	/** Assicura che all'orario attuale della richiesta
	 * sia effettivamente possibile portare a termine un ordine */
	private boolean checkNotTooLate(Date orarioScelto, int tot) {
		int chosenTime = TimeServices.getMinutes(orarioScelto);
		int nowTime = TimeServices.getNowMinutes();
		if(tot < wolf.getOvens()[0].getAvailablePlaces())
			nowTime += 14;       // tengo conto dei tempi minimi di una infornata e consegna.
		else
			nowTime += 19;       // tengo conto dei tempi minimi di due infornate e consegna.
		return (chosenTime >= nowTime);
	}

	/** Restituisce il nome della pizza desiderata, dopo avere effettuato i dovuti controlli:
	 * - che non sia stato inserito "OK" oppure "F";
	 * - che la stringa inserita corrisponda ad una pizza valida. */
	private String whichPizza(boolean isPrimaRichiesta) throws RestartOrderExc {
		String nomePizza;
		boolean ok = false;
		do {
			System.out.print(TextualColorServices.colorSystemOut("Quale pizza desideri?",Color.YELLOW,false,false));
			if(isPrimaRichiesta)
				System.out.print("\t\t(Inserisci 'F' per annullare)\n");
			else
				System.out.print("\t\t(Inserisci 'OK' se non desideri altro, oppure 'F' per annullare)\n");
			nomePizza = scan.nextLine().toUpperCase();
			try {
				if (nomePizza.equals("F")) {
					ok = true;
					throw new RestartOrderExc();
				}
				String err;
				if (nomePizza.equals("OK") && isPrimaRichiesta) {
					err = "Numero di pizze non valido. Riprovare:";
					System.out.println(TextualColorServices.colorSystemOut(err, Color.RED, false, false));
				} else if (nomePizza.equals("OK"))
					ok = true;
				else if (!(wolf.getMenu().containsKey(nomePizza)))
					throw new TryAgainExc();
				else    // pizza inserita correttamente
					ok = true;
			} catch (TryAgainExc tae) {
				String spiacenti = "Spiacenti: \"" + nomePizza + "\" non presente sul menu. Riprovare:";
				System.out.println(TextualColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
			}
		} while (!ok);
		return nomePizza;
	}

	/** Ritorna il numero desiderato della pizza specifica richiesta.
	 * Il numero di pizze complessivamente ordinate non deve superare
	 * il valore massimo consentito. */
	private int howManySpecificPizza(Order order, String nomePizza) {
		boolean ok = false;
		int num = 0;
		int totOrdinate = order.getNumPizze();
		do {
			String domanda = TextualColorServices.colorSystemOut("Quante " + nomePizza + " vuoi?",Color.YELLOW,false,false);
			System.out.println(domanda + "\t[1.." + (2*(wolf.getAvailablePlaces())-totOrdinate) + "]");
			String line = scan.nextLine();
			try {
				num = Integer.parseInt(line);
				if (num <= 0)
					throw new NumberFormatException();
				else if (totOrdinate + num > 2*wolf.getAvailablePlaces())    // max 16 pizze per ordine
					throw new TryAgainExc();
				else {
					ok = true;
					askModifyPizza(order, nomePizza, num);
				}
			} catch (NumberFormatException e) {
				String spiacenti = "Spiacenti: inserito numero non valido. Riprovare:";
				System.out.println(TextualColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
			} catch (TryAgainExc e) {
				String spiacenti = "Spiacenti: massimo numero di pizze superato. Riprovare:";
				System.out.println(TextualColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
			}
		} while (!ok);
		return num;
	}

	/** Gestisce la possibilità che la pizza desiderata necessiti di aggiunte o rimozioni
	 * di ingredienti, rispetto ad una specifica presente sul menu. */
	private void askModifyPizza(Order order, String nomePizza, int num) {
		String domanda = TextualColorServices.colorSystemOut("Vuoi apportare modifiche alle " + num + " " + nomePizza + "?",Color.YELLOW,false,false);
		System.out.println(domanda + "\t[S/N]: ");
		String answer = scan.nextLine().toUpperCase();
		switch (answer) {
			case "S":
				System.out.println(possibleAddictions(wolf));
				String advise = TextualColorServices.colorSystemOut("(Attenzione: è prevista una maggiorazione di 0.50 € per ogni ingrediente aggiunto)",Color.YELLOW,false,false);
				System.out.println(advise);
				String adding = TextualColorServices.colorSystemOut("Inserisci gli ingredienti da AGGIUNGERE, separati da virgola, poi invio:",Color.YELLOW,false,false);
				System.out.println(adding);
				String aggiunte = scan.nextLine().toUpperCase();
				String rmving = TextualColorServices.colorSystemOut("Inserisci gli ingredienti da RIMUOVERE, separati da virgola, poi invio:",Color.YELLOW,false,false);
				System.out.println(rmving);
				String rimozioni = scan.nextLine().toUpperCase();
				Pizza modPizza = addAndRmvToppingsText(wolf.getMenu().get(nomePizza), aggiunte, rimozioni, wolf.getSUPPL_PRICE());
				for (int i = 0; i < num; i++) {
					order.getOrderedPizze().add(modPizza);
				}
				String conf1 = TextualColorServices.colorSystemOut("Aggiunte " + num + " pizze " + modPizza.getName(true).toUpperCase(),Color.YELLOW,false,false);
				System.out.println("\t> " + conf1 + " (" + modPizza.getDescription() + ").");
				break;
			case "N":
				order.addPizza(wolf.getMenu().get(nomePizza), num);
				String conf2 = TextualColorServices.colorSystemOut("Aggiunte " + num + " pizze " + nomePizza.toUpperCase(),Color.YELLOW,false,false);
				System.out.println("\t> " + conf2);
				break;
			default:
				String spiacenti = "Spiacenti: inserito carattere non corretto. Riprovare: ";
				System.out.println(TextualColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
				askModifyPizza(order, nomePizza, num);
				break;
		}
	}

	/** Gestisce l'inserimento del nome del cliente e dell'indirizzo di spedizione. */
	private boolean insertNameAndAddress(Order order) throws RestartOrderExc {
		String qst = TextualColorServices.colorSystemOut("Nome sul citofono:",Color.YELLOW,false,false);
		System.out.println(qst + "\t\t(Inserisci 'F' per annullare l'ordine)");
		String nome = scan.nextLine();
		if (nome.toUpperCase().equals("F")) {
			throw new RestartOrderExc();
		}
		order.setName(nome);
		String addr = TextualColorServices.colorSystemOut("Indirizzo di consegna:",Color.YELLOW,false,false);
		System.out.println(addr + "\t\t(Inserisci 'F' per annullare l'ordine)");
		String indirizzo = scan.nextLine();
		if (indirizzo.toUpperCase().equals("F")) {
			throw new RestartOrderExc();
		}
		order.setAddress(indirizzo);
		return true;
	}

	/** Aggiunge la pizza all'Order, nella quantità inserita.
	 * Effettua, nel caso, tutte le modifiche richieste, aggiornando il prezzo. */
	private Pizza addAndRmvToppingsText(Pizza pizza, String aggiunte, String rimozioni, double prezzoSuppl) {
		HashMap<String, String> ingr = new HashMap<>(pizza.getToppings());
		Pizza p = new Pizza(pizza.getName(false), ingr, pizza.getPrice());
		int suppl = 0;
		StringTokenizer stAgg = new StringTokenizer(aggiunte);
		while (stAgg.hasMoreTokens()) {
			try {
				String ingredienteAggiuntoString = SettleStringsServices.arrangeIngredientString(stAgg);
				p.addIngredients(ingredienteAggiuntoString);
				if(!pizza.getToppings().containsKey(ingredienteAggiuntoString))
					suppl++;
			} catch (Exception ignored) { }
		}
		StringTokenizer stRmv = new StringTokenizer(rimozioni);
		while (stRmv.hasMoreTokens()) {
			try {
				String ingredienteRimossoString = SettleStringsServices.arrangeIngredientString(stRmv);
				if(!pizza.getToppings().containsKey(ingredienteRimossoString) && p.getToppings().containsKey(ingredienteRimossoString))
					suppl--;
				p.rmvIngredients(ingredienteRimossoString);
			} catch (Exception ignored) { }
		}
		p.setPrice(p.getPrice() + (suppl * prezzoSuppl));        // aggiunto 0.50 per ogni ingrediente
		if(!p.getToppings().equals(pizza.getToppings()))
			p.setName(pizza.getName(false) + "*");
		return p;
	}

	/** Chiede conferma dell'ordine e lo salva tra quelli completati
	 * (pronti all'evasione), aggiornando il vettore orario del forno e del fattorino. */
	private void askConfirm(Order order, Date orario) throws SQLException {
		Customer customer = order.getCustomer();
		String domanda = TextualColorServices.colorSystemOut("Confermi l'ordine?",Color.YELLOW,false,false);
		String s = TextualColorServices.colorSystemOut("S",Color.ORANGE,true,false);
		String n = TextualColorServices.colorSystemOut("N",Color.ORANGE,true,false);
		System.out.println(domanda + "  Digitare '" + s + "' per confermare, '" + n + "' per annullare: ");
		String risp = scan.nextLine().toUpperCase();
		switch (risp) {
			case "S":
				/* Conferma l'ordine e lo aggiunge a quelli della pizzeria. */
				Database.putCompletedOrder(order);
				order.setCompletedDb(wolf,order.getNumPizze(),order.getTime());
				String confirm = "\nGrazie! L'ordine è stato effettuato correttamente.";
				System.out.println(TextualColorServices.colorSystemOut(confirm, Color.GREEN,true,false));
				String confirmedTime = TimeServices.dateTimeStamp(orario);
				confirmedTime = TextualColorServices.colorSystemOut(confirmedTime,Color.GREEN,true,false);
				System.out.println("\t>> Consegna prevista: " + confirmedTime + ".");
				System.out.println(TextualColorServices.getLine());
				whatDoYouWant(customer);
				break;
			case "N":
				/* Annulla l'ordine. */
				try {
					throw new RestartOrderExc();
				} catch (RestartOrderExc roe) {
					String annullato = TextualColorServices.colorSystemOut("L'ordine è stato annullato.",Color.ORANGE,true,false);
					System.out.println("\t>> " + annullato);
					System.out.println(TextualColorServices.getLine());
					whatDoYouWant(customer);
				}
				break;
			default:
				/* è stato inserito un carattere diverso da 'S' o 'N'. */
				try {
					throw new TryAgainExc();
				} catch (TryAgainExc re) {
					String spiacenti = "Spiacenti: carattere inserito non valido. Riprovare: ";
					System.out.println(TextualColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
					askConfirm(order, orario);
				}
				break;
		}
	}

	/** Elenca tutte gli ingredienti che l'utente può scegliere, per modificare una pizza. */
	public static String possibleAddictions(Pizzeria pizzeria) {
		StringBuilder possibiliIngr = new StringBuilder();
		possibiliIngr.append(TextualColorServices.colorSystemOut("\tPossibili aggiunte: ",Color.ORANGE,false,false));
		possibiliIngr.append(TextualServices.possibleAddictions(pizzeria));
		return possibiliIngr.substring(0, possibiliIngr.lastIndexOf(",")); // elimina ultima virgola
	}

	/** Chiede se si vuole procedere con il login o con la creazione di un nuovo account. */
	public void askAccess() throws SQLException {
		String log = TextualColorServices.colorSystemOut("L",Color.ORANGE,true,false);
		String newAcc = TextualColorServices.colorSystemOut("N",Color.ORANGE,true,false);
		String recPsw = TextualColorServices.colorSystemOut("R",Color.ORANGE,true,false);
		System.out.println(TextualColorServices.colorSystemOut("\t>> Digitare:\n",Color.YELLOW,false,false)
				+ "\t\t'" + log + "' per eseguire il login,\n\t\t'" + newAcc + "' per creare un nuovo account,\n\t\t'"
				+ recPsw + "' per recuperare i dati del tuo account.");
		System.out.print(TextualColorServices.colorSystemOut("\t>>\t",Color.YELLOW,false,false));
		String answer = scan.nextLine().toUpperCase();
		switch (answer) {
			case "L":
				String userQuestion = TextualColorServices.colorSystemOut("\n\tUsername:\t", Color.YELLOW, false, false);
				System.out.print(userQuestion);
				String user = scan.nextLine().toUpperCase();
				String pswQuestion = TextualColorServices.colorSystemOut("\tPassword:\t", Color.YELLOW, false, false);
				System.out.print(pswQuestion);
				String psw = scan.nextLine().toUpperCase();
				String working1 = TextualColorServices.colorSystemOut("\n\tAccedendo al database...\n", Color.GREENYELLOW, false, false);
				System.out.print(working1);
				switch(PizzeriaServices.checkLogin(wolf, user,psw)){
					case OK:
						Customer c = new Customer(user,psw);
						System.out.println("\nBenvenuto: " + user);
						whatDoYouWant(c);
						break;
					case PIZZERIA:
						System.out.println("\nBenvenuto: " + user + " (utente privilegiato)");
						whatDoesPizzeriaWant();
						break;
					case NO:
						System.out.println(TextualColorServices.colorSystemOut("\nUsername o password errati: riprovare.\n",Color.RED,false,false));
						askAccess();
						break;
				}
				break;
			case "N":
				String newUserQuestion = TextualColorServices.colorSystemOut("\n\tNuovo username:\t\t", Color.YELLOW, false, false);
				System.out.print(newUserQuestion);
				String newUser = scan.nextLine().toUpperCase();
				String mailQuestion = TextualColorServices.colorSystemOut("\tIndirizzo e-mail:\t", Color.YELLOW, false, false);
				System.out.print(mailQuestion);
				String mail = scan.nextLine().toLowerCase();
				String newPswQuestion = TextualColorServices.colorSystemOut("\tNuova password:\t\t", Color.YELLOW, false, false);
				System.out.print(newPswQuestion);
				String newPsw = scan.nextLine().toUpperCase();
				String confPswQuestion = TextualColorServices.colorSystemOut("\tConferma psw:\t\t", Color.YELLOW, false, false);
				System.out.print(confPswQuestion);
				String confPsw = scan.nextLine().toUpperCase();
				String working2 = TextualColorServices.colorSystemOut("\n\tAccedendo al database...\n", Color.GREENYELLOW, false, false);
				System.out.print(working2);
				switch(PizzeriaServices.canCreateAccount(mail,newUser,newPsw,confPsw)) {
					case OK:
						SendJavaMail newMail = new SendJavaMail();
						if(!newMail.welcomeMail(newUser,newPsw,mail)) {    // se indirizzo mail non valido
							System.out.println(TextualColorServices.colorSystemOut("Errore: indirizzo e-mail non valido. Riprovare.",Color.RED,false,false));
							askAccess();
						}
						else {
							Database.putCustomer(newUser,newPsw,mail);
							System.out.println("\nBenvenuto: " + newUser.toUpperCase() + ". Hai creato un nuovo account.\n");
							/* login automatico */
							Customer c = new Customer(newUser, newPsw);
							whatDoYouWant(c);
						}
						break;
					case SHORT:
						System.out.println(TextualColorServices.colorSystemOut("\nUsername o password troppo breve: riprovare.\n",Color.RED,false,false));
						askAccess();
						break;
					case EXISTING:
						System.out.println(TextualColorServices.colorSystemOut("\nDati già presenti nel Database: riprovare con indirizzo e-mail o username differente.\nSe già registrato, effettuare il login.\n",Color.RED,false,false));
						askAccess();
						break;
					case DIFFERENT:
						System.out.println(TextualColorServices.colorSystemOut("\nPassword non coincidente: riprovare.\n",Color.RED,false,false));
						askAccess();
						break;
				}
				break;
			case "R":
				String recoverMailQuestion = TextualColorServices.colorSystemOut("\n\tInserisci l'indirizzo e-mail dell'account:\t", Color.YELLOW, false, false);
				System.out.print(recoverMailQuestion);
				String recMail = scan.nextLine().toUpperCase();
				if(Database.checkMail(recMail)) {
					SendJavaMail newMail = new SendJavaMail();
					newMail.recoverPassword(recMail);
					System.out.println(TextualColorServices.colorSystemOut("\nUna e-mail ti è stata inviata.\n", Color.YELLOW, false, false));
					askAccess();
				} else {
					System.out.println(TextualColorServices.colorSystemOut("\nSpiacenti: indirizzo e-mail non presente nel Database.\n", Color.RED, false, false));
					askAccess();
				}
				break;
			default:
				String spiacenti = "\nSpiacenti: carattere inserito non valido. Riprovare:\n";
				System.out.println(TextualColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
				askAccess();
				break;
		}
	}

	private void whatDoesPizzeriaWant() throws SQLException {
		String risposta;
		System.out.println(TextualServices.whatDoesPizzeriaWantPossibilities());
		System.out.print(TextualColorServices.colorSystemOut("\t>> ", Color.YELLOW,false,false));
		risposta = scan.nextLine().toUpperCase();
		switch (risposta){
			case "V":
				wolf.updatePizzeriaToday();
				System.out.println(TextualColorServices.colorSystemOut("Ecco gli ordini da evadere...\n", Color.YELLOW, false, false));
				/* Visualizza solo gli ordini di oggi */
				for(String code : wolf.getOrders().keySet()){
					if(wolf.getOrders().get(code).getTime().getDate()==(new Date().getDate()))
						System.out.println(TextualServices.recapOrder(wolf.getOrders().get(code)));
				}
				whatDoesPizzeriaWant();
				break;
			case "M":
				System.out.println(TextualServices.textModifyMenuPossibilities());
				System.out.print(TextualColorServices.colorSystemOut("\t>> ", Color.YELLOW,false,false));
				risposta = scan.nextLine().toUpperCase();
				howModifyMenuAnswer(risposta);
				whatDoesPizzeriaWant();
				break;
			case "S":
				sendTextualMail();
				whatDoesPizzeriaWant();
				break;
			case "E":
				System.out.println(TextualColorServices.colorSystemOut("Uscendo dall'area riservata...\n", Color.YELLOW, false, false));
				/* logout */
				askAccess();
				break;
			default:
				System.out.println(TextualColorServices.colorSystemOut("Spiacenti: inserito carattere non valido. Riprovare: ", Color.RED, false, false));
				whatDoesPizzeriaWant();
				break;
		}
	}

	/** Mando mail per:
	 * - confermare nuovo account alla creazione
	 * - inviare mensilmente newsletter con aggiornamento account, pizza del mese, ecc
	 * - ...
	 * */
	private void sendTextualMail() {
		String newAddQuestion = TextualColorServices.colorSystemOut("Inserire indirizzo e-mail:\t\t\t", Color.YELLOW, false, false);
		System.out.print(newAddQuestion);
		String address = scan.nextLine().toLowerCase();
		String newSubjQuestion = TextualColorServices.colorSystemOut("Inserire l'oggetto della mail:\t\t", Color.YELLOW, false, false);
		System.out.print(newSubjQuestion);
		String subject = scan.nextLine();
		String newTxtQuestion = TextualColorServices.colorSystemOut("Inserire testo della mail (inserire linea vuota per concludere):\n", Color.YELLOW, false, false);
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

	private void askConfirmSendMail(String address, String subject, String txt){
		String confAddrQuestion = TextualColorServices.colorSystemOut("Confermi l'invio a:\t\t", Color.YELLOW, false, false);
		System.out.print(confAddrQuestion);
		String confAddr = TextualColorServices.colorSystemOut(address,Color.WHITE,false,false);
		System.out.print(confAddr);
		String confSubjQuestion = TextualColorServices.colorSystemOut("\ndell'email con oggetto:\t", Color.YELLOW, false, false);
		System.out.print(confSubjQuestion);
		String confSubj = TextualColorServices.colorSystemOut(subject,Color.WHITE,false,false);
		System.out.print(confSubj);
		String confTxtQuestion = TextualColorServices.colorSystemOut("\ncon testo:\n", Color.YELLOW,false,false);
		System.out.print(confTxtQuestion);
		String confTxt = TextualColorServices.colorSystemOut(txt, Color.WHITE,false,false);
		System.out.print(confTxt);
		String confQuestion = TextualColorServices.colorSystemOut("\n\t?\t(S/N):\t", Color.YELLOW,false,false);
		System.out.print(confQuestion);
		switch(scan.nextLine().toUpperCase()){
			case "S":
				SendJavaMail mail = new SendJavaMail();
				mail.sendMail(address,subject,txt);
				break;
			case "N":
				String err = "\tMessaggio eliminato.";
				System.out.println(TextualColorServices.colorSystemOut(err,Color.YELLOW,true,false));
				break;
			default:
				String notValid = "Spiacenti: inserito carattere non valido. Riprovare:";
				System.out.println(TextualColorServices.colorSystemOut(notValid,Color.RED,false,false));
				askConfirmSendMail(address,subject,txt);
				break;
		}
	}

	private void howModifyMenuAnswer(String risposta) throws SQLException {
		switch (risposta){
			case "A":
				Database.putPizza(wolf);
				break;
			case "R":
				Database.removePizzaText(wolf);
				break;
			case "AI":
				Database.putTopping(wolf);
				break;
			case "RI":
				Database.removeToppingText(wolf);
				break;
			case "B":
				System.out.println(TextualColorServices.colorSystemOut("Nessuna modifica effettuata al menu.\n", Color.YELLOW, false, false));
				// logout
				whatDoesPizzeriaWant();
				break;
			default:
				System.out.println(TextualColorServices.colorSystemOut("Spiacenti: inserito carattere non valido. Riprovare: ", Color.RED, false, false));
				/*  torna a "ecco cosa puoi fare" */
				break;
		}
	}

	public static void main(String[] args) {
		TextualInterface textInterface = new TextualInterface();
		System.out.println(TextualServices.helloThere(textInterface.wolf));
		try {
            textInterface.askAccess();
        } catch (SQLException e) {
			e.printStackTrace();
        }
	}
}