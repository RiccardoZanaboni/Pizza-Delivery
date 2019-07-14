package graphicElements.elements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.services.SettleStringsServices;
import pizzeria.services.TimeServices;

import java.util.ArrayList;

/** Il Recap grafico è presente in OrderPage3, LastOrderPage e in PizzeriaVisualizeOrdersPage. */
public class GraphicRecap {

	/** Riempie il GridPane con tutti gli elementi necessari (prezzo totale incluso) */
	private static GridPane addEverythingToGridPane(Order order, ArrayList<Label> nomiLabels) {
		HBox totalBox = new HBox();
		Label label = new Label("Totale: ");
		Label totLabel = new Label(order.getTotalPrice() + " €");
		totalBox.getChildren().addAll(label,totLabel);
		totalBox.setAlignment(Pos.CENTER);

		GridPane gridPane;
		gridPane = graphicRecap(nomiLabels, order);
		gridPane.getChildren().add(totalBox);
		GridPane.setConstraints(totalBox, 1, nomiLabels.size()+2);
		return gridPane;
	}

	/** Costruisce le etichette per il Recap e restituisce il GridPane così completato. */
	public static GridPane graphicRecap(ArrayList<Label> nomiLabels, Order order) {
		GridPane gridPane = new GridPane();
		ArrayList<Label> ingrLabels = new ArrayList<>();
		ArrayList<Label> prezziLabels = new ArrayList<>();
		ArrayList<Label> countPizzeLabels = new ArrayList<>();

		ArrayList<Pizza> elencate = new ArrayList<>();
		int numTipo = 0;
		for (int i = 0; i < order.getNumPizze(); i++) {
			Pizza p = order.getOrderedPizze().get(i);
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
				for (int j = 0; j < order.getNumPizze(); j++) {
					if (p.getName(false).equals(order.getOrderedPizze().get(j).getName(false)) && p.getToppings().equals(order.getOrderedPizze().get(j).getToppings())) {
						/* di quel "tipo di pizza" ce n'è una in più */
						num++;
					}
				}
				nomiLabels.add(numTipo, new Label(order.getOrderedPizze().get(i).getName(true)));
				ingrLabels.add(numTipo, new Label(order.getOrderedPizze().get(i).getDescription()));
				prezziLabels.add(numTipo, new Label((SettleStringsServices.settlePriceDecimal(order.getOrderedPizze().get(i).getPrice() * num) + " €")));
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

				numTipo++;	/* ho un "tipo di pizza" in più */
			}
		}
		return gridPane;
	}

	public static GridPane setGridPane(Order order, ArrayList<Label> nomiLabels){
		GridPane gridPane = addEverythingToGridPane(order,nomiLabels);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setHgap(10);
		gridPane.setVgap(30);
		return gridPane;
	}

	public static VBox setIntro(Order order) {
		VBox recapBox = new VBox(20);
		Label userLabel = new Label("USERNAME:\t" + order.getCustomer().getUsername().toLowerCase());
		Label surnameLabel = new Label("COGNOME:\t" + order.getName());
		Label yourAddressLabel = new Label("INDIRIZZO:\t" + order.getAddress());
		Label yourOrderTimeLabel = new Label ("ORARIO:\t\t" + TimeServices.dateTimeStamp(order.getTime()));
		recapBox.getChildren().addAll(userLabel, surnameLabel, yourAddressLabel, yourOrderTimeLabel);
		recapBox.setId("recapBox");
		return recapBox;
	}

	public static HBox setIntestation(Label yourOrder) {
		HBox titleBox = new HBox();
		titleBox.getChildren().add(yourOrder);
		titleBox.setId("yourOrderBox");
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setMinSize(600, 50);
		return titleBox;
	}
}