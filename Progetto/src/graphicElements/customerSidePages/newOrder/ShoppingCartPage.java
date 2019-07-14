package graphicElements.customerSidePages.newOrder;

import graphicElements.elements.ButtonRmvPizza;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.services.SettleStringsServices;

import java.util.ArrayList;


public class ShoppingCartPage {
	private Stage window = new Stage();

	/** Lo Stage mostra il carrello dell'ordine, con i prodotti fino ad allora selezionati. */
	public void display(Order order, Button shoppingCartButton) {
		window.setTitle("Wolf of Pizza - Il tuo Carrello");

		HBox totalBox = new HBox();
		Label label = new Label("Totale: ");
		Label totLabel = new Label(order.getTotalPrice() + " €");
		totalBox.getChildren().addAll(label,totLabel);
		totalBox.setMinHeight(50);
		totalBox.setAlignment(Pos.CENTER);

		GridPane gridPane = createGridPane(shoppingCartButton, totLabel, order);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setHgap(10);
		gridPane.setVgap(30);

		ScrollPane scrollPane = new ScrollPane(gridPane);
		scrollPane.setMinSize(600, 350);
		VBox layout = new VBox();
		layout.getChildren().addAll(scrollPane,totalBox);
		Scene scene = new Scene(layout);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setScene(scene);
		window.show();
	}

	/** Crea tutti gli elementi necessari per la Scene del carrello e li riunisce nel GridPane. */
	private GridPane createGridPane(Button shoppingCartButton, Label total, Order order) {
		GridPane gridPane = new GridPane();
		ArrayList<Label> nomiLabels = new ArrayList<>();
		ArrayList<Label> ingrLabels = new ArrayList<>();
		ArrayList<Label> prezziLabels = new ArrayList<>();
		ArrayList<Label> countPizzeLabels = new ArrayList<>();
		ArrayList<ButtonRmvPizza> buttonRmvPizzas = new ArrayList<>();

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
					if (p.getName(false).equals(order.getOrderedPizze().get(j).getName(false)) && p.getToppings().equals(order.getOrderedPizze().get(j).getToppings()))
						num++;		// di quel "tipo di pizza" ce n'è una in più
				}

				nomiLabels.add(numTipo, new Label(order.getOrderedPizze().get(i).getName(true)));
				ingrLabels.add(numTipo, new Label(order.getOrderedPizze().get(i).getDescription()));
				prezziLabels.add(numTipo, new Label((SettleStringsServices.settlePriceDecimal(order.getOrderedPizze().get(i).getPrice() * num) + " €")));
				countPizzeLabels.add(numTipo, new Label());
				countPizzeLabels.get(numTipo).setText("" + num);
				buttonRmvPizzas.add(new ButtonRmvPizza(nomiLabels.get(numTipo), ingrLabels.get(numTipo), prezziLabels.get(numTipo), shoppingCartButton, total, order, order.getOrderedPizze().get(i), countPizzeLabels.get(numTipo)));

				gridPane.getChildren().add(nomiLabels.get(numTipo));
				gridPane.getChildren().add(ingrLabels.get(numTipo));
				gridPane.getChildren().add(countPizzeLabels.get(numTipo));
				gridPane.getChildren().add(prezziLabels.get(numTipo));
				gridPane.getChildren().add(buttonRmvPizzas.get(numTipo));

				GridPane.setConstraints(countPizzeLabels.get(numTipo), 0, numTipo + 1);
				GridPane.setConstraints(nomiLabels.get(numTipo), 1, numTipo + 1);
				GridPane.setConstraints(ingrLabels.get(numTipo), 2, numTipo + 1);
				GridPane.setConstraints(prezziLabels.get(numTipo), 3, numTipo + 1);
				GridPane.setConstraints(buttonRmvPizzas.get(numTipo), 4, numTipo + 1);

				numTipo++;		// ho un "tipo di pizza" in piu
			}
		}
		return gridPane;
	}
}