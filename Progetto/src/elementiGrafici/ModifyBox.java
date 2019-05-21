package elementiGrafici;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import pizzeria.Ingredients;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;

import java.util.ArrayList;
import java.util.HashMap;

public class ModifyBox{
	private static boolean answer = false;

	static void setAnswer() {
		ModifyBox.answer = !ModifyBox.answer;
    }

    public static boolean display(Order order, Pizzeria pizzeria, String pizza) {

		Pizza pizzaMenu = new Pizza(
				pizzeria.getMenu().get(pizza).getCamelName(),
				pizzeria.getMenu().get(pizza).getIngredients(),
				pizzeria.getMenu().get(pizza).getPrice());

		HashMap<String, Ingredients> ingr = new HashMap<>(pizzaMenu.getIngredients());
		Pizza nuovaPizza = new Pizza(pizzaMenu.getMaiuscName(), ingr, pizzaMenu.getPrice());

		Stage window = new Stage();

		ArrayList<Label> ingrLabels = new ArrayList<>();
		ArrayList<ButtonAddRmvIngr> ingrButtons = new ArrayList<>();
		ArrayList<HBox> hBoxes = new ArrayList<>();

		fillLabelsAndButtons(pizzeria, nuovaPizza, ingrLabels, ingrButtons);
		fillHBoxes(hBoxes, ingrButtons);
		GridPane gridPane = setGridPaneContraints(ingrLabels, hBoxes);
		gridPane.getColumnConstraints().add(new ColumnConstraints(210));
		gridPane.getColumnConstraints().add(new ColumnConstraints(70));

		Button confirmButton = new Button("Conferma le modifiche");
		confirmButton.setOnAction(e -> {
			order.addPizza(nuovaPizza, 1);
			answer = true;
			window.close();
		});

		ScrollPane scrollPane = new ScrollPane(gridPane);
		VBox layout = new VBox();
		layout.getChildren().addAll(scrollPane, confirmButton);
		layout.setAlignment(Pos.CENTER);

		// Impedisce di fare azioni sulle altre finestre
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Modifica la pizza " + nuovaPizza.getMaiuscName());
		window.setMinWidth(330);
		window.setMaxWidth(400);
		window.setMaxHeight(300);
		// Mostra la finestra e attende di essere chiusa
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		return answer;
	}

	private static GridPane setGridPaneContraints(ArrayList<Label> ingrLabels, ArrayList<HBox> hBoxes) {
		GridPane gridPane = new GridPane();
		for (int i=0; i<ingrLabels.size(); i++) {
			gridPane.getChildren().addAll(ingrLabels.get(i), hBoxes.get(i));
			GridPane.setConstraints(ingrLabels.get(i), 0, i);
			GridPane.setConstraints(hBoxes.get(i), 1, i);
		}
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);
		return gridPane;
	}

	private static void fillHBoxes(ArrayList<HBox> hBoxes, ArrayList<ButtonAddRmvIngr> ingrButtons) {
		for (ButtonAddRmvIngr ingrButton : ingrButtons) {
			HBox hBox = new HBox(4);
			hBox.getChildren().add(ingrButton);
			hBoxes.add(hBox);
		}
	}

	private static void fillLabelsAndButtons(Pizzeria pizzeria, Pizza nuovaPizza, ArrayList<Label> ingrLabels, ArrayList<ButtonAddRmvIngr> ingrButtons) {
		for (Ingredients ingr : pizzeria.getIngredientsPizzeria().values()) {
			ingrLabels.add(new Label(ingr.name().toUpperCase().replace("_"," ")));
			ingrButtons.add(new ButtonAddRmvIngr(ingr, nuovaPizza));
		}
	}
}
