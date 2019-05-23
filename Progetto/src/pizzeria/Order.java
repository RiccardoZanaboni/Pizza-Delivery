package pizzeria;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private Customer customer;
    private String orderCode;
    private String customerAddress;
    private Date time;
    private ArrayList<Pizza> orderedPizze;
    private boolean isFull;
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
        this.orderCode = "ORD.00" + num;
        this.customerAddress = "";
        this.time = null;
        this.orderedPizze = new ArrayList<>();
        this.isFull = false;
        this.countModifiedPizze = 0;
        this.numTemporaryPizze = 0;
    }

    public int getNumPizzeProvvisorie() {
        return numTemporaryPizze;
    }

    public void increaseNumPizzeProvvisorie() {
        this.numTemporaryPizze ++;
    }

    public void decreaseNumPizzeProvvisorie() {
        this.numTemporaryPizze --;
    }

    /** aggiunge la pizza all'ordine. */
    public void addPizza(Pizza pizza, int num) {
        for (int i = 0; i < num; i++) {
            orderedPizze.add(pizza);
        }
    }

    /** Restituisce una stringa con i vari prodotti, per il riepilogo. */
	String textRecap() {
		StringBuilder prodotti = new StringBuilder();
		ArrayList<Pizza> elencate = new ArrayList<>();
		for (int i = 0; i < getNumPizze(); i++) {

			Pizza p = orderedPizze.get(i);
			
			int num = 0;
			if (!(elencate.contains(p))) {
				elencate.add(p);
				for (int j = 0; j < getNumPizze(); j++) {
					if (p.equals(getOrderedPizze().get(j)))
						num++;
				}
				prodotti.append("\t").append(num).append("\t").append(p.getMaiuscName()).append("\t\t").append(p.getDescription()).append("\t\t-->\t").append(num * p.getPrice()).append("€\n");
			}
		}
		return prodotti.toString();
	}

	/** costruisce etichette per il riepilogo della versione grafica, in OrderPage3. */
	public GridPane graphRecap(ArrayList<Label> nomiLabels, ArrayList<Label> countPizzeLabels, ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels) {
    	GridPane gridPane = new GridPane();
		ArrayList<Pizza> elencate = new ArrayList<>();
		int numTipo = 0;
		for (int i = 0; i < getNumPizze(); i++) {
			Pizza p = orderedPizze.get(i);
			int num = 0;

			boolean contains = false;
			for (Pizza pizza : elencate) {
				if (p.getMaiuscName().equals(pizza.getMaiuscName()) && p.getToppings().equals(pizza.getToppings())) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				elencate.add(p);
				for (int j = 0; j < getNumPizze(); j++) {
					if (p.getMaiuscName().equals(getOrderedPizze().get(j).getMaiuscName()) && p.getToppings().equals(getOrderedPizze().get(j).getToppings()))
						num++;		// di quel "tipo di pizza" ce n'è una in più
				}
				nomiLabels.add(numTipo, new Label(orderedPizze.get(i).getCamelName()));
				ingrLabels.add(numTipo, new Label(orderedPizze.get(i).getDescription()));
				prezziLabels.add(numTipo, new Label((orderedPizze.get(i).getPrice()*num + " €")));
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

    /** calcola e restituisce la spesa totale. */
	public double getTotalPrice() {
		double totale = 0;
		for(int i = 0; i< getNumPizze(); i++){
			totale += orderedPizze.get(i).getPrice();
		}
		return totale;
	}

	/** restituisce true se la pizza specificata è stata ordinata. */
	public boolean searchPizza(Pizza pizza){
		for (Pizza pizza1 : orderedPizze) {
			if (pizza1.equals(pizza))
				return true;
		}
		return false;
	}

    /** il server-pizzeria inizia a preparare le pizze solo se isFull = true. */
    public boolean isFull() {
    	return isFull;
    }

    public void setFull() {
        this.isFull = true;
        System.out.println("\nGrazie! L'ordine è stato effettuato correttamente.");
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
		return countModifiedPizze;
	}

	public Customer getCustomer() {
		return customer;
	}

	public String getAddress() {
		return customerAddress;
	}

	public Date getTime() {
		return time;
	}

	public ArrayList<Pizza> getOrderedPizze() {
		return orderedPizze;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public int getNumPizze() {
        return orderedPizze.size();
    }

	public void increaseCountModifiedPizze() {
		this.countModifiedPizze++;
	}
}