package textualElements;

import database.OrderDB;
import exceptions.RestartOrderExc;
import exceptions.TryAgainExc;
import javafx.scene.paint.Color;
import pizzeria.Customer;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.services.SettleStringsServices;
import pizzeria.services.TextColorServices;
import pizzeria.services.TimeServices;

import java.sql.SQLException;
import java.util.*;
 @SuppressWarnings("all")

/**
 * Contiene tutti i metodi necessari al cliente per effettuare un nuovo ordine da interfaccia testuale.
 * Utilizzata la sigla-chiave "OK" una volta terminata la scelta delle pizze, per continuare.
 * Utilizzata ovunque la sigla-chiave "F" per l'annullamento dell'ordine: si torna all'inizio.
 * */
class TextNewOrder {
	private Scanner scan = new Scanner(System.in);

	/** Esegue tutte le operazioni necessarie per effettuare un nuovo ordine. */
	void makeOrderText(Customer customer, Pizzeria pizzeria, TextCustomerSide customerSide) throws SQLException {
		pizzeria.updatePizzeriaToday();
		System.out.println(TextCustomerSide.printMenu(pizzeria));
		Order order = pizzeria.initializeNewOrder();
		order.setCustomer(customer);
		int num;
		String nomePizza;
		int tot = 0;
		boolean isPrimaRichiesta = true;
		try {
			do {
				nomePizza = whichPizza(pizzeria, isPrimaRichiesta);
				if (nomePizza.equals("OK")) {
					break;      /* smette di chiedere pizze */
				}
				isPrimaRichiesta = false;
				num = howManySpecificPizza(pizzeria, order, nomePizza);
				if (order.getNumPizze() == 16) {
					tot += num;
					break;      /* hai chiesto esattamente 16 pizze in totale: smette di chiedere pizze */
				}
				tot += num;
			} while (true);

			Date orario;
			orario = orderTime(order,tot,pizzeria);
			System.out.println(orario);
			if (insertNameAndAddress(order)) {
				System.out.println(TextCustomerSide.recapOrder(order));
				askConfirm(pizzeria, order, orario, customerSide);
			}
		} catch (RestartOrderExc e) {
			String annullato = TextColorServices.colorSystemOut("L'ordine è stato annullato.", Color.ORANGE,true,false);
			System.out.println("\t>> " + annullato);
			System.out.println(TextColorServices.getLine());
			customerSide.whatDoYouWant(customer,pizzeria);
		}
	}

	/** Esegue i controlli dovuti sulla stringa relativa all'orario e, in caso di successo,
	 * @return l'orario in formato Date. */
	private Date orderTime(Order order, int tot, Pizzeria pizzeria) throws RestartOrderExc {
		possibleTimes(pizzeria,tot);
		String orarioScelto = scan.nextLine();
		Date d;
		try {
			if (orarioScelto.toUpperCase().equals("F")) {
				throw new RestartOrderExc();
			}
			if (TimeServices.checkValidTime(orarioScelto)) {
				d = TimeServices.stringToDate(orarioScelto);
				assert d != null : "Error";
				int ora = d.getHours();
				int minuti = d.getMinutes();
				if (!pizzeria.isOpen(d)) {
					throw new ArrayIndexOutOfBoundsException();
				}
				if (!checkNotTooLate(pizzeria,d,tot) | !pizzeria.checkTimeBoxOven(ora,minuti,tot) | pizzeria.aFreeDeliveryMan(ora,minuti)==null) {
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
			System.out.println(TextColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
			d = orderTime(order, tot, pizzeria);
		} catch (TryAgainExc re) {
			String spiacenti = "Spiacenti: inserito orario non valido. Riprovare: ";
			System.out.println(TextColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
			d = orderTime(order, tot, pizzeria);
		}
		return d;
	}

	/** Stampa a video tutti gli orari disponibili per la consegna. */
	private void possibleTimes(Pizzeria pizzeria, int tot) throws RestartOrderExc {
		String domanda = TextColorServices.colorSystemOut("A che ora vuoi ricevere la consegna? [formato HH:mm]",Color.YELLOW,false,false);
		System.out.println(domanda + " \t\t(Inserisci 'F' per annullare.)");
		System.out.println(TextColorServices.colorSystemOut("\tEcco gli orari disponibili:",Color.YELLOW,false,false));
		int c = 0;
		System.out.print("\t");
		try {
			//noinspection ConstantConditions
			for (String time : TimeServices.availableTimes(pizzeria,tot)) {
				System.out.print(time);
				c++;
				if (c % 18 == 0) System.out.print("\n\t");      // stampa 18 orari su ogni riga
			}
			System.out.print("\n");
		} catch (NullPointerException npe) {
			/* se l'ordine inizia in un orario ancora valido, ma impiega troppo tempo e diventa troppo tardi: */
			String spiacenti = "\nSpiacenti: si è fatto tardi, la pizzeria è ormai in chiusura. Torna a trovarci!\n";
			System.out.println(TextColorServices.colorSystemOut(spiacenti, Color.RED, false, false));
			throw new RestartOrderExc();
		}
	}

	/** @return true se all'orario attuale della richiesta
	 * è ancora effettivamente possibile portare a termine un ordine */
	private boolean checkNotTooLate(Pizzeria pizzeria, Date orarioScelto, int tot) {
		int chosenTime = TimeServices.getMinutes(orarioScelto);
		int nowTime = TimeServices.getNowMinutes();
		if(tot < pizzeria.getOvens()[0].getAvailablePlaces())
			nowTime += 14;       /* tengo conto dei tempi minimi di una infornata e consegna. */
		else
			nowTime += 19;       /* tengo conto dei tempi minimi di due infornate e consegna. */
		return (chosenTime >= nowTime);
	}

	/**
	 * @return il nome della pizza desiderata, dopo avere effettuato i dovuti controlli:
	 * - che non sia stato inserito "OK" oppure "F";
	 * - che la stringa inserita corrisponda ad una pizza valida.
	 * */
	private String whichPizza(Pizzeria pizzeria, boolean isPrimaRichiesta) throws RestartOrderExc {
		String nomePizza;
		boolean ok = false;
		do {
			System.out.print(TextColorServices.colorSystemOut("Quale pizza desideri?",Color.YELLOW,false,false));
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
					System.out.println(TextColorServices.colorSystemOut(err, Color.RED, false, false));
				} else if (nomePizza.equals("OK"))
					ok = true;
				else if (!(pizzeria.getMenu().containsKey(nomePizza)))
					throw new TryAgainExc();
				else
					ok = true;	/* pizza inserita correttamente */
			} catch (TryAgainExc tae) {
				String spiacenti = "Spiacenti: \"" + nomePizza + "\" non presente sul menu. Riprovare:";
				System.out.println(TextColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
			}
		} while (!ok);
		return nomePizza;
	}

	/** @return il numero desiderato di pizze del tipo richiesto.
	 * Il numero di pizze complessivamente ordinate non deve superare
	 * il valore massimo consentito dalla pizzeria. */
	private int howManySpecificPizza(Pizzeria pizzeria, Order order, String nomePizza) {
		boolean ok = false;
		int num = 0;
		int totOrdinate = order.getNumPizze();
		do {
			String domanda = TextColorServices.colorSystemOut("Quante " + nomePizza + " vuoi?",Color.YELLOW,false,false);
			System.out.println(domanda + "\t[1.." + (2*(pizzeria.getAvailablePlaces())-totOrdinate) + "]");
			String line = scan.nextLine();
			try {
				num = Integer.parseInt(line);
				if (num <= 0)
					throw new NumberFormatException();
				else if (totOrdinate + num > 2* pizzeria.getAvailablePlaces())    /* max 16 pizze per ordine */
					throw new TryAgainExc();
				else {
					ok = true;
					askModifyPizza(pizzeria, order, nomePizza, num);
				}
			} catch (NumberFormatException e) {
				String spiacenti = "Spiacenti: inserito numero non valido. Riprovare:";
				System.out.println(TextColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
			} catch (TryAgainExc e) {
				String spiacenti = "Spiacenti: massimo numero di pizze superato. Riprovare:";
				System.out.println(TextColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
			}
		} while (!ok);
		return num;
	}

	/** Gestisce la possibilità che la pizza desiderata necessiti di aggiunte o rimozioni
	 * di ingredienti, rispetto ad una specifica presente sul menu. */
	private void askModifyPizza(Pizzeria pizzeria, Order order, String nomePizza, int num) {
		String domanda = TextColorServices.colorSystemOut("Vuoi apportare modifiche alle " + num + " " + nomePizza + "?",Color.YELLOW,false,false);
		System.out.println(domanda + "\t[S/N]: ");
		String answer = scan.nextLine().toUpperCase();
		switch (answer) {
			case "S":
				System.out.println(TextCustomerSide.possibleAddictions(pizzeria));
				String avviso = TextColorServices.colorSystemOut("(Attenzione: è prevista una maggiorazione di 0.50 € per ogni ingrediente aggiunto)",Color.YELLOW,false,false);
				System.out.println(avviso);
				String adding = TextColorServices.colorSystemOut("Inserisci gli ingredienti da AGGIUNGERE, separati da virgola, poi invio:",Color.YELLOW,false,false);
				System.out.println(adding);
				String aggiunte = scan.nextLine().toUpperCase();
				String rmving = TextColorServices.colorSystemOut("Inserisci gli ingredienti da RIMUOVERE, separati da virgola, poi invio:",Color.YELLOW,false,false);
				System.out.println(rmving);
				String rimozioni = scan.nextLine().toUpperCase();
				Pizza modPizza = addAndRmvToppingsText(pizzeria.getMenu().get(nomePizza), aggiunte, rimozioni, pizzeria.getSUPPL_PRICE());
				for (int i = 0; i < num; i++) {
					order.getOrderedPizze().add(modPizza);
				}
				String conf1 = TextColorServices.colorSystemOut("Aggiunte " + num + " pizze " + modPizza.getName(true).toUpperCase(),Color.YELLOW,false,false);
				System.out.println("\t> " + conf1 + " (" + modPizza.getDescription() + ").");
				break;
			case "N":
				order.addPizza(pizzeria.getMenu().get(nomePizza), num);
				String conf2 = TextColorServices.colorSystemOut("Aggiunte " + num + " pizze " + nomePizza.toUpperCase(),Color.YELLOW,false,false);
				System.out.println("\t> " + conf2);
				break;
			default:
				String spiacenti = "Spiacenti: inserito carattere non corretto. Riprovare: ";
				System.out.println(TextColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
				askModifyPizza(pizzeria, order, nomePizza, num);
				break;
		}
	}

	/** Gestisce l'inserimento del nome del cliente e dell'indirizzo di consegna. */
	private boolean insertNameAndAddress(Order order) throws RestartOrderExc {
		String qst = TextColorServices.colorSystemOut("Nome sul citofono:",Color.YELLOW,false,false);
		System.out.println(qst + "\t\t(Inserisci 'F' per annullare l'ordine)");
		String nome = scan.nextLine();
		if (nome.toUpperCase().equals("F")) {
			throw new RestartOrderExc();
		}
		order.setName(nome);
		String addr = TextColorServices.colorSystemOut("Indirizzo di consegna:",Color.YELLOW,false,false);
		System.out.println(addr + "\t\t(Inserisci 'F' per annullare l'ordine)");
		String indirizzo = scan.nextLine();
		if (indirizzo.toUpperCase().equals("F")) {
			throw new RestartOrderExc();
		}
		order.setAddress(indirizzo);
		return true;
	}

	/** Effettua tutte le modifiche richieste, aggiornando il prezzo della pizza richiesta.
	 * @return la pizza così modificata. */
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
		p.setPrice(p.getPrice() + (suppl * prezzoSuppl));        /* aggiunto 0.50 per ogni ingrediente */
		if(!p.getToppings().equals(pizza.getToppings()))
			p.setName(pizza.getName(false) + "*");
		return p;
	}

	/** Chiede conferma dell'ordine e lo salva nel DB tra quelli pronti all'evasione,
	 * aggiornando i vettori orari del forno e del fattorino. */
	private void askConfirm(Pizzeria pizzeria, Order order, Date orario, TextCustomerSide customerSide) throws SQLException {
		Customer customer = order.getCustomer();
		String domanda = TextColorServices.colorSystemOut("Confermi l'ordine?",Color.YELLOW,false,false);
		String s = TextColorServices.colorSystemOut("S",Color.ORANGE,true,false);
		String n = TextColorServices.colorSystemOut("N",Color.ORANGE,true,false);
		System.out.println(domanda + "  Digitare '" + s + "' per confermare, '" + n + "' per annullare: ");
		String risp = scan.nextLine().toUpperCase();
		switch (risp) {
			case "S":
				/* Conferma l'ordine e lo aggiunge a quelli della pizzeria. */
				OrderDB.putOrder(order);
				String confirm = "\nGrazie! L'ordine è stato effettuato correttamente.";
				System.out.println(TextColorServices.colorSystemOut(confirm, Color.GREEN,true,false));
				String confirmedTime = TimeServices.dateTimeStamp(orario);
				confirmedTime = TextColorServices.colorSystemOut(confirmedTime,Color.GREEN,true,false);
				System.out.println("\t>> Consegna prevista: " + confirmedTime + ".");
				System.out.println(TextColorServices.getLine());
				customerSide.whatDoYouWant(customer,pizzeria);
				break;
			case "N":
				/* Annulla l'ordine. */
				String annullato = TextColorServices.colorSystemOut("L'ordine è stato annullato.",Color.ORANGE,true,false);
				System.out.println("\t>> " + annullato);
				System.out.println(TextColorServices.getLine());
				customerSide.whatDoYouWant(customer,pizzeria);
				break;
			default:
				/* è stato inserito un carattere diverso da 'S' o 'N'. */
				String spiacenti = "Spiacenti: carattere inserito non valido. Riprovare: ";
				System.out.println(TextColorServices.colorSystemOut(spiacenti,Color.RED,false,false));
				askConfirm(pizzeria, order, orario, customerSide);
				break;
		}
	}
}