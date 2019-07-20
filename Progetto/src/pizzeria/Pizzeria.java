package pizzeria;

import database.*;
import pizzeria.services.TimeServices;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static database.Database.openDatabase;
import static database.UpdateDB.setLastUpdate;
import static pizzeria.services.TimeServices.getMinutes;

@SuppressWarnings("deprecation")

public class Pizzeria {
	private String name;
	private String address;
	private LocalTime[] openings = new LocalTime[7];    /* orari di apertura in tutti i giorni della settimana */
	private LocalTime[] closings = new LocalTime[7];    /* orari di chiusura in tutti i giorni della settimana */
	private Oven[] ovens;
	private ArrayList<DeliveryMan> deliveryMen;
	private HashMap<String, Pizza> menu;
	private HashMap<String, String> pizzeriaIngredients;
	private HashMap<String,Order> orders;
	private final int availablePlaces = 8;
	private final int OVEN_MINUTES = 5;      /* ogni 5 minuti */
	private final int DELIVERYMAN_MINUTES = 10;   /* ogni 10 minuti */
	private final double SUPPL_PRICE = 0.5;
	private final String userPizzeria;
	private final String pswPizzeria;

	/**
	 * La Pizzeria è il locale che riceve le ordinazioni e le evade nei tempi richiesti.
	 * @param name: nome identificativo della Pizzeria
	 * @param address: indirizzo della Pizzeria
	 *
	 * E' responsabile della inizializzazione del forno, con tutte le possibili infornate,
	 * di una ArrayList di fattorini e una di ordini del giorno.
	 */

	public Pizzeria(String name, String address){
		this.userPizzeria = "pizzeria".toUpperCase();
		this.pswPizzeria = "password".toUpperCase();
		this.menu = new HashMap<>();
		this.pizzeriaIngredients = new HashMap<>();
		this.name = name;
		this.address = address;
		this.orders = new LinkedHashMap<>();
		this.deliveryMen = new ArrayList<>();
		/* Apre la connessione con il database */
		openDatabase();
		setIngredientsPizzeria();
		createMenu();
		setDayOfTheWeek();
		addDeliveryMan(new DeliveryMan("Musi", this));
		//addDeliveryMan(new DeliveryMan("Zana", this)); per aggiungere un altro fattorino
		updatePizzeriaToday();
	}

	/**
	 * Riempie i vettori della pizzeria contenenti 7 orari di apertura (da domenica a sabato) e
	 * 7 orari di chiusura (da domenica a sabato).
	 *
	 * Gli orari partono sempre da LocalTime.MIN, che corrisponde alla mezzanotte.
	 * A questo si aggiunge (con il metodo plus()) ora e minuti desiderati.
	 *
	 * ATTENZIONE: Per lasciare la pizzeria chiusa in un particolare giorno, porre openTime = closeTime.
	 * PRESTARE PARTICOLARE ATTENZIONE: assicurarsi che ogni giorno la pizzeria rimanga aperta almeno 20 minuti.
	 * */
	private void setDayOfTheWeek() {
		/* orari di apertura, da domenica a sabato */
		this.openings[0] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		this.openings[1] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		//fixme rimettere lunedí chiuso
		this.openings[2] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		this.openings[3] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		this.openings[4] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		this.openings[5] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		this.openings[6] = LocalTime.MIN.plus(TimeServices.getMinutes(0,0), ChronoUnit.MINUTES);
		/* orari di apertura, da domenica a sabato */
		this.closings[0] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
		this.closings[1] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
		// FIXME: 15/07/2019 rimettere lunedi chiuso
		this.closings[2] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
		this.closings[3] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
		this.closings[4] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
		this.closings[5] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
		this.closings[6] = LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES);
	}

	/** Aggiorna il vettore "locale" degli ordini, sincronizzandolo con gli ordini salvati nel DB. */
	public HashMap<String,Order> getOrders() {
		try {
			this.orders = OrderDB.getOrders(this,this.orders);
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

	/**
	 * Aggiorna quotidianamente il menu e ripristina il vettore di infornate, ad ogni apertura della pizzeria.
	 * Questo viene fatto confrontando la data odierna con la data di ultimo update, salvata nel DB:
	 * se non corrispondono, allora è il primo accesso di oggi all'applicazione, pertanto occorre aggiornare.
	 * */
	public void updatePizzeriaToday() {
		int closeMinutes = getMinutes(getClosingToday());
		int openMinutes = getMinutes(getOpeningToday());
		this.ovens = new Oven[(closeMinutes - openMinutes) / this.OVEN_MINUTES];    /* minutiTotali/5 */
		for (int i = 0; i < this.ovens.length; i++) {
			this.ovens[i] = new Oven(this.availablePlaces);
		}
		Date last = UpdateDB.getLastUpdate();
		Date today = new Date();
		if (last.getDate() != today.getDate()) {
			setLastUpdate(last);
		}
		if(isOpen(today)) {
			getOrders();
		}
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
			for(String s : PizzaDB.getPizzeDB(this.menu).keySet()){
				addPizza(this.menu.get(s));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** Crea un nuovo ordine e aggiorna il numero seriale degli ordini. */
	public Order initializeNewOrder() {
		Order order;
		order = new Order(OrderDB.countOrdersDB());
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

	/** Ritorna l'indice della casella temporale (forno) desiderata. */
	public int findTimeBoxOven(int oraDesiderata, int minutiDesiderati){
		return findTimeBox(oraDesiderata,minutiDesiderati,OVEN_MINUTES);
	}

	/** Ritorna l'indice della casella temporale (fattorino) desiderata. */
	public int findTimeBoxDeliveryMan(int oraDesiderata, int minutiDesiderati){
		return findTimeBox(oraDesiderata,minutiDesiderati,DELIVERYMAN_MINUTES);
	}

	/** Restituisce l'indice della casella temporale desiderata, in base al parametro. */
	private int findTimeBox(int oraDesiderata, int minutiDesiderati, int parameter){
		int openMinutes = getMinutes(getOpeningToday());
		int desiredMinutes = getMinutes(oraDesiderata,minutiDesiderati);
		return (desiredMinutes - openMinutes)/parameter;
	}

	/** Restituisce il primo fattorino che risulta disponibile all'orario indicato. */
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
	void updateOvenAndDeliveryMan(Date d, int tot) {
		int disp = this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].getAvailablePlaces();
		if(disp < tot){
			this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].updateAvailablePlaces(disp);
			this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())-1].updateAvailablePlaces(tot-disp);
		} else {
			this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].updateAvailablePlaces(tot);
		}
		if(aFreeDeliveryMan(d.getHours(), d.getMinutes()) != null)
			aFreeDeliveryMan(d.getHours(), d.getMinutes()).assignDelivery(findTimeBoxDeliveryMan(d.getHours(), d.getMinutes()));
	}

	/** Restituisce tutti gli orari in cui la pizzeria potrebbe garantire la consegna di "tot" pizze.
	 * Richiama altri metodi di questa classe, per gestire ogni eventualità. */
	public ArrayList<String> availableTimes(int tot){
		ArrayList<String> availables = new ArrayList<>();
		int now = TimeServices.getNowMinutes();
		int restaAperta = TimeServices.calculateOpeningMinutesPizzeria(this.getOpeningToday(), this.getClosingToday());
		int esclusiIniziali = TimeServices.calculateStartIndex(this.getAvailablePlaces(), this.getOpeningToday(), now, tot);     /* primo orario da visualizzare (in minuti) */

		for(int i = esclusiIniziali; i < restaAperta; i++) {    /* considera i tempi minimi di preparazione e consegna */
			if(i % 5 == 0) {
				if (this.getOvens()[i / 5].getAvailablePlaces() + this.getOvens()[(i / 5) - 1].getAvailablePlaces() >= tot) {
					for (DeliveryMan a : this.getDeliveryMen()) {
						if (a.getDeliveryManTimes()[i / 10].isFree()) {
							int newMinutes = getMinutes(this.getOpeningToday()) + i;
							int ora = newMinutes / 60;
							int min = newMinutes % 60;
							String nuovoOrario = TimeServices.timeStamp(ora,min);
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
			return null;
		}
	}

	/** Verifica che sia possibile cuocere le pizze nell'infornata richiesta
	 * e, al massimo, in quella appena precedente. */
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

	int getDELIVERYMAN_MINUTES() {
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
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);  /* oggi */
		Date date = new Date();
		date.setHours(vector[dayOfWeek-1].getHour());
		date.setMinutes(vector[dayOfWeek-1].getMinute());
		return date;
	}
}