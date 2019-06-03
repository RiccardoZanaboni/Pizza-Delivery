package graphicElements;

import graphicAlerts.ToppingsAlert;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import pizzeria.*;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Finestra che si attiva se viene premuto un qualunque bottone "modifica" in OrderPage1.
 * Dà all'utente la possibilità di selezionare gli ingredienti desiderati alla propria pizza.
 * Blocca l'utilizzo della pagina OrderPage1, fino all'attivamento del confirmButton.
 */

public class ModifyBox{
    private static boolean answer = false;  // answer = true se la pizza ha subìto modifiche

    public static boolean display(Order order, Pizzeria pizzeria, String pizza) {
        Stage window = new Stage();

        Pizza pizzaMenu = new Pizza(
                pizza,//pizzeria.getMenu().get(pizza).getName(false),
                pizzeria.getMenu().get(pizza).getToppings(),
                pizzeria.getMenu().get(pizza).getPrice()
        );
        HashMap<String, Toppings> ingr = new HashMap<>(pizzaMenu.getToppings());
        Pizza nuovaPizza = new Pizza(pizzaMenu.getName(false), ingr, pizzaMenu.getPrice());

        ArrayList<Label> ingrLabels = new ArrayList<>();
        //ArrayList<ButtonAddRmvIngr> ingrButtons = new ArrayList<>();
        ArrayList<CheckBoxTopping> checkBoxes = new ArrayList<>();
        ArrayList<HBox> hBoxes = new ArrayList<>();
        fillLabelsAndCheckBoxes(pizzeria, nuovaPizza, ingrLabels, checkBoxes);
        fillHBoxes(hBoxes, checkBoxes);

        GridPane gridPane = setGridPaneContraints(ingrLabels, hBoxes);
        gridPane.getColumnConstraints().add(new ColumnConstraints(210));
        gridPane.getColumnConstraints().add(new ColumnConstraints(70));

        Button confirmButton = new Button("Conferma le modifiche");
        confirmButton.setOnAction(e -> {
            handleOptions(checkBoxes, nuovaPizza);
            if (!checkCheckBoxTopping(checkBoxes))
                ToppingsAlert.display();
            else {
                handleOptions(checkBoxes, nuovaPizza);
                nuovaPizza.setName("PizzaModificata");
                order.addPizza(nuovaPizza, 1);
                nuovaPizza.setCount(true);
                answer = true;
                window.close();
            }
        });

        ScrollPane scrollPane = new ScrollPane(gridPane);
        VBox layout = new VBox();
        layout.getChildren().addAll(scrollPane, confirmButton);
        layout.setAlignment(Pos.CENTER);

        window.initModality(Modality.APPLICATION_MODAL);    // Impedisce di fare azioni sulle altre finestre
        window.setTitle("Modifica la pizza \"" + nuovaPizza.getName(true) + "\"");
        window.setMinWidth(330);
        window.setMaxWidth(400);
        window.setMaxHeight(300);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }

    /** riempie il GridPane con tutti gli elementi necessari */
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

    /** riempie ogni HBox con il relativo CheckBox */
    private static void fillHBoxes(ArrayList<HBox> hBoxes, ArrayList<CheckBoxTopping> checkBoxToppings) {
        for (CheckBoxTopping checkBoxTopping : checkBoxToppings) {
            HBox hBox = new HBox(4);
            hBox.getChildren().add(checkBoxTopping);
            hBoxes.add(hBox);
        }
    }

    /** costruisce la lista dei vari Labels, CheckBoxes */
    private static void fillLabelsAndCheckBoxes(Pizzeria pizzeria, Pizza nuovaPizza, ArrayList<Label> ingrLabels, ArrayList<CheckBoxTopping> checkBoxes) {
        for (Toppings ingr : pizzeria.getIngredientsPizzeria().values()) {
            ingrLabels.add(new Label(ingr.name().toUpperCase().replace("_"," ")));
            checkBoxes.add(new CheckBoxTopping(ingr, nuovaPizza));
        }
    }

    /** setta gli ingredienti come presenti o assenti sulla pizza */
    private static void handleOptions(ArrayList<CheckBoxTopping> checkBoxToppings, Pizza pizza){
        for (CheckBoxTopping checkBoxTopping : checkBoxToppings) {
            if (checkBoxTopping.isSelected()) {
                if (!checkBoxTopping.isPresent()) {
                    checkBoxTopping.setPresent(true);
                    pizza.setPrice(pizza.getPrice() + 0.50);
                    pizza.addIngredients(checkBoxTopping.getIngr());
                }
            } else {
                checkBoxTopping.setPresent(false);
                pizza.rmvIngredients(checkBoxTopping.getIngr());
            }
        }
    }

    /** controlla che sia stato selezionato almeno un ingrediente per la pizza modificata */
    private static boolean checkCheckBoxTopping (ArrayList<CheckBoxTopping> checkBoxToppings) {
        for (CheckBoxTopping checkBoxTopping : checkBoxToppings) {
            if (checkBoxTopping.isPresent()) {
                return true;
            }
        }
        return false;
    }

	static void setAnswer() {
		ModifyBox.answer = !ModifyBox.answer;
	}
}


