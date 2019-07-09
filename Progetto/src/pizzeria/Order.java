package pizzeria;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import services.TextualColorServices;
import services.TimeServices;

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
    private int countModifiedPizze;
	private int numTemporaryPizze;

    /**
	 * L'ordine è identificato con un orderCode univoco.
	 * I suoi attributi vengono man mano riempiti, durante l'avanzamento
	 * delle procedure di ordinazione.
	 * Viene preso in carico dalla pizzeria solo una volta confermato dal cliente.
	 * */

    public Order(int num) {
        this.customer = null;
        num += 1000;
        this.orderCode = "ORD-" + num;
        this.customerAddress = "";
        this.time = null;
        this.orderedPizze = new ArrayList<>();
        this.countModifiedPizze = 0;
        this.numTemporaryPizze = 0;
    }

    public int getNumTemporaryPizze() {
        return this.numTemporaryPizze;
    }

    /** aggiorna il numero temporaneo di pizze dell'ordine, in seguito ad una aggiunta o rimozione.
	 * Due possibilità: true = +1 oppure false = -1. */
    public void setNumTemporaryPizze(boolean isPlus) {
    	if (isPlus)
    		this.numTemporaryPizze ++;
    	else
    		this.numTemporaryPizze --;
	}

    /** aggiunge la pizza all'ordine. */
    public void addPizza(Pizza pizza, int num) {
        for (int i = 0; i < num; i++) {
            this.orderedPizze.add(pizza);
        }
    }

    /** Calcola e restituisce la spesa totale. */
	public double getTotalPrice() {
		double totale = 0;
		for(int i = 0; i< getNumPizze(); i++){
			totale += this.orderedPizze.get(i).getPrice();
		}
		return totale;
	}

	/** Restituisce true se la pizza specificata è stata ordinata. */
	public boolean searchPizza(Pizza pizza){
		for (Pizza pizza1 : this.orderedPizze) {
			if (pizza1.equals(pizza))
				return true;
		}
		return false;
	}

    public boolean searchModificata(Pizza pizza){   //ricerca se esiste una pizza con le stesse modifiche alla pizza, non é uguale a searchPizza
        for (Pizza pizza1 : this.orderedPizze) {
            if (pizza1.getDescription().equals(pizza.getDescription()))
                return true;
        }
        return false;
    }
    public int countPizzaModificata(Pizza pizza){ // conta quante pizze modificate dello stesso tipo ci sono
	    int i=0;
        for (Pizza pizza1 : this.orderedPizze) {
            if (pizza1.getName(false).equals(pizza.getName(false)) && pizza1.getToppings().equals(pizza.getToppings()))
                i++;
        }
        return i;
    }

    /** Il server-pizzeria inizia a preparare le pizze solo se isCompleted = true. */
    /*public boolean isCompleted() {
    	return this.isCompleted;
    }*/

    /** Setta l'ordine come completo e aggiorna le disponibilità. */
    // todo: probabilmente va sistemato/spostato
	public void setCompletedDb(Pizzeria pizzeria, int tot, Date orario) {
		Date oggi = new Date();
		if(oggi.getDate() == orario.getDate())		// controllo nel caso sia scattata la mezzanotte
			pizzeria.updateOvenAndDeliveryMan(orario, tot, this);
		//this.isCompleted = true;		// TODO: isCompleted da togliere
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

	public int getCountModifiedPizze() {
		return this.countModifiedPizze;
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

	public void increaseCountModifiedPizze() {
		this.countModifiedPizze++;
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