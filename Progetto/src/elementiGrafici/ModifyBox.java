package elementiGrafici;

import avvisiGrafici.AlertNumPizzeMax;
import avvisiGrafici.AlertTopping;
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

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModifyBox{
	private static boolean answer = false;

	static void setAnswer() {
		ModifyBox.answer = !ModifyBox.answer;
    }

    public static boolean display(Order order, Pizzeria pizzeria, String pizza) {
	    
		Stage window = new Stage();

		Pizza pizzaMenu = new Pizza(
				pizzeria.getMenu().get(pizza).getCamelName(),
				pizzeria.getMenu().get(pizza).getIngredients(),
				pizzeria.getMenu().get(pizza).getPrice());

		HashMap<String, Ingredients> ingr = new HashMap<>(pizzaMenu.getIngredients());
		Pizza nuovaPizza = new Pizza(pizzaMenu.getMaiuscName(), ingr, pizzaMenu.getPrice());

		ArrayList<Label> ingrLabels = new ArrayList<>();
		ArrayList<ButtonAddRmvIngr> ingrButtons = new ArrayList<>();
		ArrayList<HBox> hBoxes = new ArrayList<>();
		ArrayList<CheckBoxTopping> checkBoxes = new ArrayList<>();

		fillLabelsAndCheclBoxes(pizzeria, nuovaPizza, ingrLabels, checkBoxes);
		fillHBoxes(hBoxes, checkBoxes);
		GridPane gridPane = setGridPaneContraints(ingrLabels, hBoxes);
		gridPane.getColumnConstraints().add(new ColumnConstraints(210));
		gridPane.getColumnConstraints().add(new ColumnConstraints(70));

		Button confirmButton = new Button("Conferma le modifiche");
		confirmButton.setOnAction(e -> {
			handleOptions(checkBoxes, nuovaPizza);
			if (!checkCheckBoxTopping(checkBoxes))
					AlertTopping.display();
				else {
					handleOptions(checkBoxes, nuovaPizza);
					order.addPizza(nuovaPizza, 1);
					answer = true;
					window.close();
				}
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

	private static void fillHBoxes(ArrayList<HBox> hBoxes, ArrayList<CheckBoxTopping> checkBoxToppings) {
		for (CheckBoxTopping checkBoxTopping : checkBoxToppings) {
			HBox hBox = new HBox(4);
			hBox.getChildren().add(checkBoxTopping);
			hBoxes.add(hBox);
		}
	}

	private static void fillLabelsAndCheclBoxes(Pizzeria pizzeria, Pizza nuovaPizza, ArrayList<Label> ingrLabels, ArrayList<CheckBoxTopping> checkBoxes) {
		for (Ingredients ingr : pizzeria.getIngredientsPizzeria().values()) {
			ingrLabels.add(new Label(ingr.name().toUpperCase().replace("_"," ")));
			checkBoxes.add(new CheckBoxTopping(ingr, nuovaPizza));
		}
	}


	private static void handleOptions(ArrayList<CheckBoxTopping> checkBoxToppings, Pizza pizza){

		for (int i = 0; i<checkBoxToppings.size(); i++) {
			if (checkBoxToppings.get(i).isSelected()) {
				if (!checkBoxToppings.get(i).isB()) {
					checkBoxToppings.get(i).setB(true);
					pizza.setPrice(pizza.getPrice() + 0.50);
					pizza.addIngredients(checkBoxToppings.get(i).getIngr());
				}
			} else {
					checkBoxToppings.get(i).setB(false);
					pizza.rmvIngredients(checkBoxToppings.get(i).getIngr());
			}
		}
	}

	private static boolean checkCheckBoxTopping (ArrayList<CheckBoxTopping> checkBoxToppings) {
		boolean b;
		int m=0;
		for (int i=0; i<checkBoxToppings.size(); i++) {
			if (!checkBoxToppings.get(i).isB()) {
				m++;
			}
		}
		if (m==checkBoxToppings.size())
			b=false;
		else
			b=true;
		return b;
	}



}
