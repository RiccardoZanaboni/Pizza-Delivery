package pizzeria;

import database.Database;
import database.OrderDB;
import database.PizzaDB;
import database.ToppingDB;
import pizzeria.services.TimeServices;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static database.Database.openDatabase;
import static database.Database.setLastUpdate;
import static pizzeria.services.TimeServices.getMinutes;

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

	public Pizzeria(String name, String address/*,
					LocalTime op1, LocalTime op2, LocalTime op3, LocalTime op4, LocalTime op5, LocalTime op6, LocalTime op7,
					LocalTime cl1, LocalTime cl2, LocalTime cl3, LocalTime cl4, LocalTime cl5, LocalTime cl6, LocalTime cl7*/) {
		this.userPizzeria = "pizzeria".toUpperCase();
		this.pswPizzeria = "password".toUpperCase();
		this.menu = new HashMap<>();
		this.pizzeriaIngredients = new HashMap<>();
		this.name = name;
		this.orders = new LinkedHashMap<>();
		this.address = address;
		setDayOfTheWeek(/*op1,op2,op3,op4,op5,op6,op7,cl1,cl2,cl3,cl4,cl5,cl6,cl7*/);  // 1 = domenica, 2 = lunedi, ... 7 = sabato.
		this.deliveryMen = new ArrayList<>();
		this.SUPPL_PRICE = 0.5;
		this.availablePlaces = 8;
		openDatabase();		/* Apre la connessione con il database */
		addDeliveryMan(new DeliveryMan("Musi", this));
		//addDeliveryMan(new DeliveryMan("Zanzatroni", this));
		updatePizzeriaToday();
	}

	/**
	 * Riempie i vettori della pizzeria contenenti gli orari
	 * di apertura e di chiusura per ogni giorno della settimana.
	 * Utilizzato nel costruttore della pizzeria, ma riutilizzabile in caso di cambiamenti.
	 * */
	private void setDayOfTheWeek() {
		/* orari di apertura, da domenica a sabato */
		this.openings[0] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		this.openings[1] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		this.openings[2] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		this.openings[3] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		this.openings[4] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		this.openings[5] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		this.openings[6] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		/* orari di apertura, da domenica a sabato */
		this.closings[0] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
		this.closings[1] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
		this.closings[2] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
		this.closings[3] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
		this.closings[4] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
		this.closings[5] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
		this.closings[6] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
	}

	public HashMap<String,Order> getOrders() {
		try {
			this.orders = OrderDB.getOrders(this.orders);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.orders;
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
		// FIXME:	(RISOLTO: SI PUO TOGLIERE)
		//  creare in db una tabella con alcuni dati della pizzeria (orari di apertura/chiusura? indirizzo?...):
		//  in particolare una data di ultimo aggiornamento: ogni volta che la pizzeria vuole visualizzare gli ordini o
		//  che un cliente vuole effettuare un nuovo ordine, si controlla se la data di ultimo aggiornamento corrisponde:
		//  se non corrisponde, si aggiorna tutto (si richiama questo metodo update()) e si aggiorna la data nel DB.

		setIngredientsPizzeria();
		createMenu();
		int closeMinutes = getMinutes(getClosingToday());
		int openMinutes = getMinutes(getOpeningToday());
		this.ovens = new Oven[(closeMinutes - openMinutes) / this.OVEN_MINUTES];    // minutiTotali/5
		for (int i = 0; i < this.ovens.length; i++) {
			this.ovens[i] = new Oven(this.availablePlaces);
		}
		Date last = Database.getLastUpdate();
		Date today = new Date();
		if (last.getDate() != today.getDate()) {
			setLastUpdate(last);
		}
		getOrders();
	}

	/** Una tantum: vengono aggiunti a "pizzeriaIngredients" tutti gli ingredienti utilizzabili. */
	private void setIngredientsPizzeria(){
		try {
			for(String s : ToppingDB.getToppings(this.pizzeriaIngredients).keySet()){
				this.pizzeriaIngredients.put(s,s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** Una tantum: viene creato il menu della pizzeria; ad ogni pizza vengono aggiunti i rispettivi toppings. */
	private void createMenu() {
		try {
			for(String s : PizzaDB.getPizzeDB(menu).keySet()){
				addPizza(menu.get(s));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** Crea un nuovo ordine e aggiorna il numero di ordini giornalieri. */
	public Order initializeNewOrder() {
		Order order;
		getOrders();
		order = new Order(Database.countOrdersDB());
		Database.addNewVoidOrderToDB(order);
		return order;
	}

	/** Controlla che la pizzeria sia aperta in un determinato orario, nella giornata odierna. */
	public boolean isOpen(Date d){
		int openTime = getMinutes(getOpeningToday());
		int closeTime = getMinutes(getClosingToday());
		int requestTime = getMinutes(d);

		return (requestTime >= openTime && requestTime < closeTime);
	}

	/** ritorna l'indice della casella temporale (forno) desiderata. */
	public int findTimeBoxOven(int oraDesiderata, int minutiDesiderati){
		return findTimeBox(oraDesiderata,minutiDesiderati,OVEN_MINUTES);
	}

	/** ritorna l'indice della casella temporale (fattorino) desiderata. */
	public int findTimeBoxDeliveryMan(int oraDesiderata, int minutiDesiderati){
		return findTimeBox(oraDesiderata,minutiDesiderati,DELIVERYMAN_MINUTES);
	}

	private int findTimeBox(int oraDesiderata, int minutiDesiderati, int parameter){
		int openMinutes = getMinutes(getOpeningToday());
		int desiredMinutes = getMinutes(oraDesiderata,minutiDesiderati);
		return (desiredMinutes - openMinutes)/parameter;
	}

	/** restituisce il primo fattorino della pizzeria che sia disponibile all'orario indicato. */
	public DeliveryMan aFreeDeliveryMan(int oraDesiderata, int minutiDesiderati){
		for(DeliveryMan man : this.deliveryMen){
			if(man.getDeliveryManTimes()[findTimeBoxDeliveryMan(oraDesiderata,minutiDesiderati)].isFree()){
				return man;
			}
		}
		return null;
	}

	/** Controlla che la pizzeria possa garantire la consegna di "tot" pizze all'orario "d",
	 * in base alla disponibilità di forno e fattorini. */
	public void updateOvenAndDeliveryMan(Date d, int tot, Order order) {
		/* PRIMA CONDIZIONE PER LE INFORNATE, SUCCESSIVA SUI FATTORINI */
		int disp = this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].getAvailablePlaces();
		if(disp < tot){
			this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].updateAvailablePlaces(disp);
			this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())-1].updateAvailablePlaces(tot-disp);
		} else {
			this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].updateAvailablePlaces(tot);
		}
		if(aFreeDeliveryMan(d.getHours(), d.getMinutes()) != null)
			aFreeDeliveryMan(d.getHours(), d.getMinutes()).assignDelivery(findTimeBoxDeliveryMan(d.getHours(), d.getMinutes()));
		else System.out.println("problema critico per l'ordine " + order.getOrderCode());    //fixme: questo significa problema GRAVE
	}

	/** Verifica che sia possibile cuocere le pizze nell'infornata richiesta e in quella appena precedente. */
	public boolean checkTimeBoxOven(int ora, int minuti, int tot) {
		int postiDisponibiliQuestaInfornata = this.ovens[findTimeBoxOven(ora, minuti)].getAvailablePlaces();
		int postiDisponibiliPrecedenteInfornata = this.ovens[findTimeBoxOven(ora, minuti) - 1].getAvailablePlaces();
		return (postiDisponibiliQuestaInfornata + postiDisponibiliPrecedenteInfornata >= tot);
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public double getSUPPL_PRICE() {
		return this.SUPPL_PRICE;
	}

	public String getUserPizzeria() {
		return userPizzeria;
	}

	public String getPswPizzeria() {
		return pswPizzeria;
	}

	public Oven[] getOvens() {
		return this.ovens;
	}

	public ArrayList<DeliveryMan> getDeliveryMen() {
		return this.deliveryMen;
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

	public HashMap<String, Pizza> getMenu() {
		return this.menu;
	}

	public HashMap<String, String> getIngredientsPizzeria() {
		return this.pizzeriaIngredients;
	}

	public Date getOpeningToday(){
		return getToday(this.openings);
	}

	public Date getClosingToday(){
		return getToday(this.closings);
	}

	private Date getToday(LocalTime[] vector){
		Calendar cal = new GregorianCalendar();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);  // oggi
		Date date = new Date();
		date.setHours(vector[dayOfWeek-1].getHour());
		date.setMinutes(vector[dayOfWeek-1].getMinute());
		return date;
	}
}