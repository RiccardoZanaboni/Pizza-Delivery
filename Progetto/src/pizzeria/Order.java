package pizzeria;

import pizzeria.services.SettleStringsServices;

import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("deprecation")
public class Order implements Comparable<Order> {
    private Customer customer;
    private String name;
    private String orderCode;
    private String customerAddress;
    private Date time;
    private ArrayList<Pizza> orderedPizze;
	private int numTemporaryPizze;

    /**
	 * L'ordine è identificato con un orderCode univoco, costituito da una sigla ed un seriale.
	 * I suoi attributi vengono man mano riempiti, durante l'avanzamento delle procedure di ordinazione.
	 * Viene salvato nel DB e preso in carico dalla pizzeria solo quando confermato dal cliente.
	 * */

    public Order(int num) {
        this.customer = null;
        num += 1000;
        this.orderCode = "ORD-" + num;
        this.customerAddress = "";
        this.time = null;
        this.orderedPizze = new ArrayList<>();
        this.numTemporaryPizze = 0;
    }

    /** Aggiorna il numero temporaneo di pizze dell'ordine, in seguito ad una aggiunta o rimozione.
	 * @param isPlus prevede due possibilità: true = +1 oppure false = -1. */
    public void setNumTemporaryPizze(boolean isPlus) {
    	if (isPlus)
    		this.numTemporaryPizze ++;
    	else
    		this.numTemporaryPizze --;
	}

    /** @param pizza: tipo di pizza.
	 * @param num: quante pizze aggiungere all'ordine. */
    public void addPizza(Pizza pizza, int num) {
        for (int i = 0; i < num; i++) {
            this.orderedPizze.add(pizza);
        }
    }

    /** @return la spesa complessiva per l'ordine, già formattata come String. */
	public String getTotalPrice() {
		double totale = 0;
		for(int i = 0; i< getNumPizze(); i++){
			totale += this.orderedPizze.get(i).getPrice();
		}
		return SettleStringsServices.settlePriceDecimal(totale);
	}

	/** @return quante pizze modificate dello stesso tipo ci sono */
    public int countPizzaModificata(Pizza pizza){
	    int i=0;
        for (Pizza pizza1 : this.orderedPizze) {
            if (pizza1.getName(false).equals(pizza.getName(false)) && pizza1.getToppings().equals(pizza.getToppings()))
                i++;
        }
        return i;
    }

    /** Richiama l'aggiornamento delle disponibilità di forni e fattorini. */
	public void updateAvailability(Pizzeria pizzeria, int tot, Date orario) {
		Date oggi = new Date();
		if(oggi.getDate() == orario.getDate())	/* controllo che non sia scattata la mezzanotte */
			pizzeria.updateOvenAndDeliveryMan(orario, tot);
	}

	public void setTime(Date orario) {
		this.time = orario;
    }

	public void setCustomer(Customer c) {
		this.customer = c;
	}

	public void setAddress(String indirizzo) {
		this.customerAddress = indirizzo;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public String getAddress() {
		return this.customerAddress;
	}

	public Date getTime() {
		return this.time;
	}

	public ArrayList<Pizza> getOrderedPizze() {
		return this.orderedPizze;
	}

	public String getOrderCode() {
		return this.orderCode;
	}

	public int getNumPizze() {
        return this.orderedPizze.size();
    }

	public int getNumTemporaryPizze() {
		return this.numTemporaryPizze;
	}

	public String getCustomerAddress(){
		return this.customerAddress;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
    	StringBuilder elenco = new StringBuilder();
    	for(Pizza p : this.getOrderedPizze()){
    		elenco.append("\n").append(p.toString());
		}
		return this.orderCode + " " + this.name + " " + this.customerAddress + " " + this.time + elenco.toString();
	}

	/** Ordina gli Orders in base all'orario. */
	@Override
	public int compareTo(Order o) {
		if(this.time.getHours() > o.time.getHours()){
			return 1;
		}
		if(this.time.getHours() == o.time.getHours()){
			if(this.time.getMinutes() > o.time.getMinutes()){
				return 1;
			} else if(this.time.getMinutes() < o.time.getMinutes()){
				return -1;
			}
		}
		if(this.time.getHours() < o.time.getHours()){
			return -1;
		} else {
			return 0;
		}
	}
}