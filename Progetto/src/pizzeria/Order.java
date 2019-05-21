package pizzeria;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private Customer customer;
    private String codice;
    private String indirizzo;
    private Date orario;
    private ArrayList<Pizza> pizzeordinate;
    private boolean completo;
    private int countPizzeModificate;

    public Order(int num) {
        this.customer = null;
        this.codice = "WOLF.00" + num;
        this.indirizzo = "";
        this.orario = null;
        this.pizzeordinate = new ArrayList<>();
        this.completo = false;
        this.countPizzeModificate = 0;
    }

    public int getCountPizzeModificate() {
        return countPizzeModificate;
    }

    public void incrementaCountPizzeModificate() {
        this.countPizzeModificate++;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public Date getOrario() {
        return orario;
    }

    public ArrayList<Pizza> getPizzeordinate() {
        return pizzeordinate;
    }

    public String getCodice() {
        return codice;
    }

    public void addPizza(Pizza pizza, int num) {
        for (int i = 0; i < num; i++) {
            pizzeordinate.add(pizza);
        }
        System.out.println("\t> Aggiunte " + num + " pizze " + pizza.getNomeMaiusc());
    }

	public String recapTextual() {
		StringBuilder prodotti = new StringBuilder();
		ArrayList<Pizza> elencate = new ArrayList<>();
		for (int i = 0; i < getNumeroPizze(); i++) {

			Pizza p = pizzeordinate.get(i);
			
			int num = 0;
			if (!(elencate.contains(p))) {
				elencate.add(p);
				for (int j = 0; j < getNumeroPizze(); j++) {
					if (p.equals(getPizzeordinate().get(j)))
						num++;
				}
				prodotti.append("\t").append(num).append("\t").append(p.getNomeMaiusc()).append("\t\t").append(p.getDescrizione()).append("\t\t-->\t").append(num * p.getPrezzo()).append("€\n");
			}
		}
		return prodotti.toString();
	}

	public GridPane recapGraphic(ArrayList<Label> nomiLabels, ArrayList<Label> countPizzeLabels, ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels) {
    	GridPane gridPane = new GridPane();
		ArrayList<Pizza> elencate = new ArrayList<>();
		int numTipo = 0;
		for (int i = 0; i < getNumeroPizze(); i++) {
			Pizza p = pizzeordinate.get(i);
			int num = 0;

			boolean contains = false;
			for (Pizza pizza : elencate) {
				if (p.getNomeMaiusc().equals(pizza.getNomeMaiusc()) && p.getIngredienti().equals(pizza.getIngredienti())) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				elencate.add(p);
				for (int j = 0; j < getNumeroPizze(); j++) {
					if (p.getNomeMaiusc().equals(getPizzeordinate().get(j).getNomeMaiusc()) && p.getIngredienti().equals(getPizzeordinate().get(j).getIngredienti()))
						num++;
				}
				nomiLabels.add(numTipo, new Label(pizzeordinate.get(i).getNomeCamel()));
				ingrLabels.add(numTipo, new Label(pizzeordinate.get(i).getDescrizione()));
				prezziLabels.add(numTipo, new Label((pizzeordinate.get(i).getPrezzo()*num + " €")));
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

				numTipo++;
			}
		}
		return gridPane;
    }
	
    public void setCustomer(Customer c) {
        this.customer = c;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompleto() {
        this.completo = true;
        System.out.println("\nGrazie! L'ordine è stato effettuato correttamente.");
    }

    public void setOrario(Date orario) {
        this.orario = orario;
    }

    public int getNumeroPizze() {
        return pizzeordinate.size();
    }

    public double getTotaleCosto() {
        double totale = 0;
        for(int i=0; i<getNumeroPizze(); i++){
            totale += pizzeordinate.get(i).getPrezzo();
        }
        return totale;
    }

    public boolean searchPizza(Pizza pizza){
        for (int i=0; i<pizzeordinate.size();i++){
           if(pizzeordinate.get(i)==pizza)
               return true;
        }
    return false;
    }
}