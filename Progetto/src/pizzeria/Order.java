package pizzeria;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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
    private boolean isCompleted;
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
        num+=1000;
        this.orderCode = "ORD-" + num;
        this.customerAddress = "";
        this.time = null;
        this.orderedPizze = new ArrayList<>();
        this.isCompleted = false;
        this.countModifiedPizze = 0;
        this.numTemporaryPizze = 0;
    }

    public int getNumTemporaryPizze() {
        return this.numTemporaryPizze;
    }

    /** aggiorna il numero temporaneo di pizze dell'ordine, in seguito ad una aggiunta o rimozione.
	 * Due possibilità: i=+1 oppure i=-1. */
    public void setNumTemporaryPizze(int i) {
    	this.numTemporaryPizze += i;
	}

    /** aggiunge la pizza all'ordine. */
    public void addPizza(Pizza pizza, int num) {
        for (int i = 0; i < num; i++) {
            this.orderedPizze.add(pizza);
        }
    }

	/** In Interfaces.TextInterface, stampa a video il riepilogo dell'ordine. */
	public String recapOrder(){
		String line = Services.getLine();
		StringBuilder recap = new StringBuilder();
		recap.append(Services.colorSystemOut("ORDINE N. ", Color.RED,true,false));
		recap.append(Services.colorSystemOut(this.orderCode,Color.RED,true,false));
		recap.append(Services.colorSystemOut("\nSIG.\t\t",Color.YELLOW,false,false));
		recap.append(Services.colorSystemOut(this.customer.getUsername(),Color.GREEN,true,false));
		recap.append(Services.colorSystemOut("\nCITOFONO:\t",Color.YELLOW,false,false));
		recap.append(Services.colorSystemOut(this.name,Color.GREEN,true,false));
		recap.append(Services.colorSystemOut("\nINDIRIZZO:\t",Color.YELLOW,false,false));
		recap.append(Services.colorSystemOut(this.customerAddress,Color.GREEN,true,false));
		recap.append(Services.colorSystemOut("\nORARIO:\t\t",Color.YELLOW,false,false));
		recap.append(Services.colorSystemOut(Services.timeStamp(time.getHours(),time.getMinutes()),Color.GREEN,true,false));
		recap.append(textRecapProducts());
		recap.append(Services.colorSystemOut("TOTALE: € ",Color.YELLOW,true,false));
		recap.append(Services.colorSystemOut(String.valueOf(getTotalPrice()),Color.RED,true,false));
		return line + recap + line;
	}

	/** Restituisce una stringa con i vari prodotti, per il riepilogo. */
	private String textRecapProducts() {
		StringBuilder prodotti = new StringBuilder("\n");
		ArrayList<Pizza> elencate = new ArrayList<>();
		for (int i = 0; i < getNumPizze(); i++) {
			Pizza p = this.orderedPizze.get(i);
			int num = 0;

			boolean contains = false;
			for (Pizza pizza : elencate) {
				if (p.getName(false).equals(pizza.getName(false)) && p.getToppings().equals(pizza.getToppings())) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				elencate.add(p);
				for (int j = 0; j < getNumPizze(); j++) {
					if (p.getName(false).equals(getOrderedPizze().get(j).getName(false)) && p.getToppings().equals(getOrderedPizze().get(j).getToppings()))
						num++;
				}
				prodotti.append("\t€ ").append(p.getPrice()).append("  x  ");
				prodotti.append(Services.colorSystemOut(String.valueOf(num),Color.WHITE,true,false));
				prodotti.append("  ").append(Services.colorSystemOut(p.getName(true).toUpperCase(),Color.WHITE,true,false));
				prodotti.append("\t\t").append(p.getDescription()).append("\n");
			}
		}
		return prodotti.toString();
	}

	/** Costruisce etichette per il riepilogo della versione grafica, in OrderPage3. */
	public GridPane graphRecap(ArrayList<Label> nomiLabels, ArrayList<Label> countPizzeLabels, ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels) {
		GridPane gridPane = new GridPane();
		Label label = new Label();
		label.setText(this.numTemporaryPizze+"");
		ArrayList<Pizza> elencate = new ArrayList<>();
		int numTipo = 0;
		for (int i = 0; i < getNumPizze(); i++) {
			Pizza p = this.orderedPizze.get(i);
			int num = 0;

			boolean contains = false;
			for (Pizza pizza : elencate) {
				if (p.getName(false).equals(pizza.getName(false)) && p.getToppings().equals(pizza.getToppings())) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				elencate.add(p);
				for (int j = 0; j < getNumPizze(); j++) {
					if (p.getName(false).equals(getOrderedPizze().get(j).getName(false)) && p.getToppings().equals(getOrderedPizze().get(j).getToppings()))
						// di quel "tipo di pizza" ce n'è una in più
						num++;
				}
				nomiLabels.add(numTipo, new Label(this.orderedPizze.get(i).getName(true)));
				//nomiLabels.get(numTipo).setStyle("-fx-font-color: yellow");
				ingrLabels.add(numTipo, new Label(this.orderedPizze.get(i).getDescription()));
				prezziLabels.add(numTipo, new Label((this.orderedPizze.get(i).getPrice()*num + " €")));
				countPizzeLabels.add(numTipo, new Label());
				countPizzeLabels.get(numTipo).setText("" + num);

				gridPane.getChildren().add(nomiLabels.get(numTipo));
				gridPane.getChildren().add(ingrLabels.get(numTipo));
				gridPane.getChildren().add(countPizzeLabels.get(numTipo));
				gridPane.getChildren().add(prezziLabels.get(numTipo));

				GridPane.setConstraints(countPizzeLabels.get(numTipo), 0, numTipo + 1);
				GridPane.setConstraints(nomiLabels.get(numTipo), 1, numTipo + 1);
				GridPane.setConstraints(ingrLabels.get(numTipo), 2, numTipo + 1);
				GridPane.setConstraints(prezziLabels.get(numTipo), 3, numTipo + 1);

				numTipo++;		// ho un "tipo di pizza" in piu
			}
		}
		return gridPane;
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
    public boolean isCompleted() {
    	return this.isCompleted;
    }

    /** Setta l'ordine come completo. */
    public void setCompleted(Pizzeria pizzeria) {
    	Date orario = this.getTime();
    	int tot = this.getNumPizze();
		pizzeria.updateOvenAndDeliveryMan(orario, tot);
        this.isCompleted = true;
    }

	public void setCompletedDb(Pizzeria pizzeria, int tot , Date orario ) {
		//Date orario = this.getTime();
		pizzeria.updateOvenAndDeliveryMan(orario, tot);
		this.isCompleted = true;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
    	String s="";
    	for(Pizza p:this.getOrderedPizze()){
    		s+="\n"+p.toString();
		}
		return this.orderCode+" "+this.name+" "+this.customerAddress +" "+this.time +s;
	}


	@Override
	public int compareTo(Order o) {
		if(this.time.getHours()>o.time.getHours()){
			return  1;
		}
		if(this.time.getHours()==o.time.getHours()){
			if(this.time.getMinutes()>o.time.getMinutes()){
				return  1;
			}else if(this.time.getMinutes()<o.time.getMinutes()){
				return  -1;
			}
		}
		if(this.time.getHours()<o.time.getHours()){
			return  -1;
		}else{
			return 0;
		}
	}
}