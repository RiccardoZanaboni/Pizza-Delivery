package pizzeria;

import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.*;
@SuppressWarnings("deprecation")

public class Pizzeria {
	private String name;
	private String address;
	private LocalTime[] openings = new LocalTime[7];    // orari di apertura in tutti i giorni della settimana
	private LocalTime[] closings = new LocalTime[7];    // orari di chiusura in tutti i giorni della settimana
	private Oven[] ovens;
	private ArrayList<DeliveryMan> deliveryMen;
	private HashMap<String, Pizza> menu;
	private HashMap<String, String> pizzeriaIngredients;
	private HashMap<String,Order> orders;
	private int availablePlaces;
	private int numDailyOrders;
	private final int OVEN_MINUTES = 5;      // ogni 5 minuti
	private final int DELIVERYMAN_MINUTES = 10;   // ogni 10 minuti
	private final double SUPPL_PRICE;
	private final String userPizzeria;
	private final String pswPizzeria;

	/**
	 * La Pizzeria è il locale che riceve le ordinazioni e le evade nei tempi richiesti.
	 * @param name: nome identificativo della Pizzeria
	 * @param address: indirizzo della Pizzeria
	 *
	 * Inizializza anche il forno, con tutte le possibili infornate,
	 * una ArrayList di fattorini e una di ordini del giorno.
	 */

	public Pizzeria(String name, String address,
					LocalTime op1, LocalTime op2, LocalTime op3, LocalTime op4, LocalTime op5, LocalTime op6, LocalTime op7,
					LocalTime cl1, LocalTime cl2, LocalTime cl3, LocalTime cl4, LocalTime cl5, LocalTime cl6, LocalTime cl7) {
		this.userPizzeria = "pizzeria".toUpperCase();
		this.pswPizzeria = "password".toUpperCase();
		this.menu = new HashMap<>();
		this.pizzeriaIngredients = new HashMap<>();
		this.name = name;
		this.numDailyOrders = 0;
		this.orders = new LinkedHashMap<>();
		this.address = address;
		setDayOfTheWeek(op1,op2,op3,op4,op5,op6,op7,cl1,cl2,cl3,cl4,cl5,cl6,cl7);  // 1 = domenica, 2 = lunedi, ... 7 = sabato.
		this.deliveryMen = new ArrayList<>();
		this.SUPPL_PRICE = 0.5;
		this.availablePlaces = 8;
		/* Apre la connessione con il database */
		Database.openDatabase();
		updatePizzeriaToday();
		addDeliveryMan(new DeliveryMan("Musi", this));
		//addDeliveryMan(new DeliveryMan("Zanzatroni", this));
	}

	/**
	 * Riempie i vettori della pizzeria contenenti gli orari
	 * di apertura e di chiusura per ogni giorno della settimana.
	 * Utilizzato nel costruttore della pizzeria, ma riutilizzabile in caso di cambiamenti.
	 * */
	private void setDayOfTheWeek(LocalTime op1, LocalTime op2, LocalTime op3, LocalTime op4, LocalTime op5, LocalTime op6, LocalTime op7, LocalTime cl1, LocalTime cl2, LocalTime cl3, LocalTime cl4, LocalTime cl5, LocalTime cl6, LocalTime cl7) {
		this.openings[0] = op1;
		this.openings[1] = op2;
		this.openings[2] = op3;
		this.openings[3] = op4;
		this.openings[4] = op5;
		this.openings[5] = op6;
		this.openings[6] = op7;
		this.closings[0] = cl1;
		this.closings[1] = cl2;
		this.closings[2] = cl3;
		this.closings[3] = cl4;
		this.closings[4] = cl5;
		this.closings[5] = cl6;
		this.closings[6] = cl7;
	}

	public HashMap<String,Order> getOrders() {
		try {
			orders=Database.getOrdersDB(this, this.orders); //FIXME @ZANA SENZA QUESTO UGUALE NON FUNZIONA NON CAPISCO
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.orders;
	}

	/** Aggiunge l'ordine, completato, a quelli che la pizzeria deve evadere. */
	public void addInfoOrder(Order order) {
		Database.putOrder(order);
		this.orders.remove(order.getOrderCode());
		this.orders.put(order.getOrderCode(),order);
	}

	/** Aggiunge la pizza specificata al menu della pizzeria. */
	private void addPizza(Pizza pizza){
		this.menu.put(pizza.getName(false),pizza);
	}

	public void addDeliveryMan(DeliveryMan deliveryMan){
		this.deliveryMen.add(deliveryMan);
	}

	/** Aggiorna quotidianamente il menu e ripristina il vettore di infornate, ad ogni apertura della pizzeria */
	public void updatePizzeriaToday() {
		// FIXME: E' GIA PRONTO; SOLO DA INSERIRE.
		//  creare in db una tabella con alcuni dati della pizzeria (orari di apertura/chiusura? indirizzo?...):
		//  in particolare una data di ultimo aggiornamento: ogni volta che la pizzeria vuole visualizzare gli ordini o
		//  che un cliente vuole effettuare un nuovo ordine, si controlla se la data di ultimo aggiornamento corrisponde:
		//  se non corrisponde, si aggiorna tutto (si richiama questo metodo update()) e si aggiorna la data nel DB.

		/*Date today = new Date();
		Date last = Database.getLastUpdate();
		// System.out.println(last);
		*/ //if (today.getDate() != last.getDate()) {
			setIngredientsPizzeria();
			createMenu();
			int closeMinutes = Services.getMinutes(getClosingToday());
			int openMinutes = Services.getMinutes(getOpeningToday());
			this.ovens = new Oven[(closeMinutes - openMinutes) / this.OVEN_MINUTES];    // minutiTotali/5
			for (int i = 0; i < this.ovens.length; i++) {
				this.ovens[i] = new Oven(this.availablePlaces);
			}
			// setLastUpdate(last);
		//}
	}

	public Date getOpeningToday(){
		Calendar cal = new GregorianCalendar();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);  // oggi
		Date op = new Date();
		op.setHours(this.openings[dayOfWeek-1].getHour());
		op.setMinutes(this.openings[dayOfWeek-1].getMinute());
		return op;
	}

	public Date getClosingToday(){
		Calendar cal = new GregorianCalendar();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);  // oggi
		Date cl = new Date();
		cl.setHours(this.closings[dayOfWeek-1].getHour());
		cl.setMinutes(this.closings[dayOfWeek-1].getMinute());
		return cl;
	}

	/** Una tantum: vengono aggiunti a "pizzeriaIngredients" tutti gli ingredienti utilizzabili. */
	private void setIngredientsPizzeria(){
		try {
			for(String s : Database.getToppings(this.pizzeriaIngredients).keySet()){
				this.pizzeriaIngredients.put(s,s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** Una tantum: viene creato il menu della pizzeria; ad ogni pizza vengono aggiunti i rispettivi toppings. */
	private void createMenu() {
		try {
			for(String s : Database.getPizze(menu).keySet()){
				addPizza(menu.get(s));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Pizza> getMenu() {
		return this.menu;
	}

	public HashMap<String, String> getIngredientsPizzeria() {
		return this.pizzeriaIngredients;
	}

	/** Crea un nuovo ordine e aggiorna il numero di ordini giornalieri. */
	public Order initializeNewOrder() {
		Order order;
		order = new Order(Database.countOrdersDB());
		Database.addNewVoidOrderToDB(order);
		return order;
	}

	/** Su Interfaces.TextInterface dà il benvenuto al cliente, fornendo le informazioni essenziali della pizzeria. */
	public String helloThere(){
		String opTime = Services.timeStamp(getOpeningToday().getHours(), getOpeningToday().getMinutes());
		String clTime = Services.timeStamp(getClosingToday().getHours(), getClosingToday().getMinutes());
		StringBuilder hello = new StringBuilder("\n");
		hello.append(Services.colorSystemOut("\nBenvenuto!\n", Color.GREEN,true,true));
		hello.append(Services.colorSystemOut("\nPIZZERIA ", Color.ORANGE,false,false));
		hello.append(Services.colorSystemOut("\"" + this.name + "\"\n\t",Color.RED,true,false));
		hello.append(Services.colorSystemOut(this.address,Color.ORANGE,false,false));
		if(getOpeningToday().equals(getClosingToday()))
			hello.append(Services.colorSystemOut("\n\tOGGI CHIUSO", Color.RED, true, false));
		else {
			hello.append(Services.colorSystemOut("\n\tApertura oggi: ", Color.ORANGE, false, false));
			hello.append(Services.colorSystemOut(opTime + " - " + clTime, Color.RED, true, false));
		}
		hello.append("\n").append(Services.getLine());
		return hello.toString();
	}

	/** Da Interfaces.TextInterface, permette di stampare a video il menu completo. */
	public String printMenu() {
		String line = Services.getLine();
		Services.paintMenuString();
		StringBuilder s = new StringBuilder();
		for (String a : this.menu.keySet()) {
			s.append("\n").append(this.menu.get(a).toString());
		}
		return s+"\n"+line;
	}

	/** Controlla che la pizzeria sia aperta in un determinato orario, nella giornata odierna. */
	public boolean isOpen(Date d){
		int openTime = Services.getMinutes(getOpeningToday());
		int closeTime = Services.getMinutes(getClosingToday());
		int requestTime = Services.getMinutes(d);

		return (requestTime >= openTime && requestTime < closeTime);
	}

	/** ritorna l'indice della casella temporale (forno) desiderata. */
	public int findTimeBoxOven(int oraDesiderata, int minutiDesiderati){
		int openMinutes = Services.getMinutes(getOpeningToday());
		int desiredMinutes = Services.getMinutes(oraDesiderata,minutiDesiderati);
		return (desiredMinutes - openMinutes)/this.OVEN_MINUTES;
	}

	/** ritorna l'indice della casella temporale (fattorino) desiderata. */
	public int findTimeBoxDeliveryMan(int oraDesiderata, int minutiDesiderati){
		int openMinutes = Services.getMinutes(getOpeningToday());
		int desiredMinutes = Services.getMinutes(oraDesiderata,minutiDesiderati);
		return (desiredMinutes - openMinutes)/this.DELIVERYMAN_MINUTES;
	}

	/** restituisce il primo fattorino della pizzeria che sia disponibile all'orario indicato. */
	public DeliveryMan aFreeDeliveryMan(int oraDesiderata, int minutiDesiderati){
		for(DeliveryMan a : this.deliveryMen){
			if(a.getDeliveryManTimes()[findTimeBoxDeliveryMan(oraDesiderata,minutiDesiderati)].isFree()){
				return a;
			}
		}
		return null;
	}

	/** Restituisce tutti gli orari in cui la pizzeria potrebbe garantire la consegna di "tot" pizze.
	 * la var "scarto" risponde all'eventualità che la pizzeria sia già aperta al momento attuale. */
	public ArrayList<String> availableTimes(int tot){
		ArrayList<String> availables = new ArrayList<>();
		int now = Services.getNowMinutes();
		int restaAperta = Services.calculateOpeningMinutesPizzeria(this);
		int esclusiIniziali = Services.calculateStartIndex(this, now, tot);     // primo orario da visualizzare (in minuti)

		for(int i = esclusiIniziali; i < restaAperta; i++) {    // considera i tempi minimi di preparazione e consegna
			if(i % 5 == 0) {
				if (this.ovens[i / 5].getPostiDisp() + this.ovens[(i / 5) - 1].getPostiDisp() >= tot) {
					for (DeliveryMan a : this.deliveryMen) {
						if (a.getDeliveryManTimes()[i / 10].isFree()) {
							int newMinutes = Services.getMinutes(getOpeningToday()) + i;   // NON POSSO PARTIRE DA TROVACASELLA MENO 1: RISCHIO ECCEZIONE
							int ora = newMinutes / 60;
							int min = newMinutes % 60;
							String nuovoOrario = Services.timeStamp(ora,min);
							availables.add(nuovoOrario + "  ");
							break;
						}
					}
				}
			}
		}
		if(availables.size() > 0) {
			return availables;
		} else {
			/* se l'ordine inizia in un orario ancora valido, ma impiega troppo tempo e diventa troppo tardi: */
			String spiacenti = "\nSpiacenti: si è fatto tardi, la pizzeria è ormai in chiusura. Torna a trovarci!\n";
			System.out.println(Services.colorSystemOut(spiacenti,Color.RED,false,false));
			return null;
		}
	}

	/** Controlla che la pizzeria possa garantire la consegna di "tot" pizze all'orario "d",
	 * in base alla disponibilità di forno e fattorini. */
	public void updateOvenAndDeliveryMan(Date d, int tot, Order order) {
		// PRIMA CONDIZIONE PER LE INFORNATE, SUCCESSIVA SUI FATTORINI
		if(this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].getPostiDisp()<tot){
			int disp = this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].getPostiDisp();
			this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].inserisciInfornate(disp);
			this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())-1].inserisciInfornate(tot-disp);
		} else {
			this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].inserisciInfornate(tot);
		}
		if(aFreeDeliveryMan(d.getHours(), d.getMinutes()) != null)
			aFreeDeliveryMan(d.getHours(), d.getMinutes()).assignDelivery(findTimeBoxDeliveryMan(d.getHours(), d.getMinutes()));
		else System.out.println("PROBLEMA IN PIZZERIA.UPDATEOVENANDDELIVERYMAN() per " + order.getOrderCode());    //TODO non ho fattorini disponibili!
	}

	public double getSUPPL_PRICE() {
		return this.SUPPL_PRICE;
	}

	public Oven[] getOvens() {
		return this.ovens;
	}

	/** In Interfaces.TextInterface, elenca tutte gli ingredienti che l'utente può scegliere, per modificare una pizza. */
	public String possibleAddictions() {
		StringBuilder possibiliIngr = new StringBuilder();
		int i = 0;
		for (String ingr : getIngredientsPizzeria().values()) {
			if (i % 10 == 0)
				possibiliIngr.append("\n\t");
			possibiliIngr.append(ingr.toLowerCase().replace("_", " ")).append(", ");
			i++;
		}
		return possibiliIngr.toString();
	}



	/** Verifica che sia possibile cuocere le pizze nell'infornata richiesta e in quella appena precedente. */
	public boolean checkTimeBoxOven(int ora, int minuti, int tot) {
		if (this.ovens[findTimeBoxOven(ora, minuti)].getPostiDisp() + this.ovens[findTimeBoxOven(ora, minuti) - 1].getPostiDisp() < tot)
			return false;
		else
			return true;
	}

	public int getAvailablePlaces() {
		return this.availablePlaces;
	}

	public int getOVEN_MINUTES() {
		return this.OVEN_MINUTES;
	}

	public int getDELIVERYMAN_MINUTES() {
		return this.DELIVERYMAN_MINUTES;
	}

	public String checkLogin(String user, String psw) throws SQLException {
		if(user.equals(this.userPizzeria) && psw.equals(this.pswPizzeria)){
			/* se è la pizzeria, allora accede come tale */
			return "P";
		} else if (Database.getCustomers(user,psw)){
			/* se è un utente identificato, accede come tale */
			return "OK";
		} else {
			/* se la combinazione utente-password è errata */
			return "NO";
		}
	}

	public String canCreateAccount(String mailAddress, String newUser, String newPsw, String confPsw) {
		if(newPsw.equals(confPsw)){
			if(newUser.length()>2 && newPsw.length()>2) {
				/* se si registra correttamente, va bene */
				try {
					if (Database.getCustomers(newUser.toUpperCase(),newPsw) || checkMail(mailAddress))
						return "EXISTING";
					else
						return "OK";
				} catch (SQLException e) {
					return "OK";	// è sicuro ???????????????
				}
			} else
				/* password troppo breve */
				return "SHORT";
		} else {
			/* se la password non viene confermata correttamente */
			return "DIFFERENT";
		}
	}

	public boolean checkMail(String mail){
		return Database.getUsernameCustomer(mail) != null;
	}
}