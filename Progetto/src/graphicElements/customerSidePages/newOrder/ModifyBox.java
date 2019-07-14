package graphicElements.customerSidePages.newOrder;

import graphicAlerts.GenericAlert;
import graphicElements.elements.CheckBoxTopping;
import javafx.scene.input.KeyCode;
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

public class ModifyBox {
    private static boolean answer = false;  /* answer = true se la pizza ha effettivamente subìto modifiche */

    /** In un nuovo Stage viene visualizzato il Box in cui selezionare o deselezionare
     * gli ingredienti per la pizza desiderata.
     * Questa nuova finestra compare se viene premuto un qualunque bottone "modifica" in OrderPage1.
     * Blocca l'utilizzo della pagina OrderPage1, fino alla pressione del confirmButton.
     * E' possibile confermare l'aggiunta di una "pizza modificata" solo se sono state
     * effettivamente apportate modifiche, altrimenti viene visualizzato un messaggio di errore.
     * */
    public boolean display(Order order, Pizzeria pizzeria, String pizzaName) {
        Stage window = new Stage();

        Pizza pizzaMenu = new Pizza(pizzaName, pizzeria.getMenu().get(pizzaName).getToppings(), pizzeria.getMenu().get(pizzaName).getPrice());
        HashMap<String, String> ingr = new HashMap<>(pizzaMenu.getToppings());
        Pizza nuovaPizza = new Pizza(pizzaMenu.getName(false), ingr, pizzaMenu.getPrice());

        ArrayList<Label> ingrLabels = new ArrayList<>();
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
                GenericAlert.display("Attenzione: inserire almeno un ingrediente!");
            else {
                handleOptions(checkBoxes, nuovaPizza);
                if(!pizzaMenu.getToppings().equals(nuovaPizza.getToppings())){
                    nuovaPizza.setName(pizzaName + "*");    /* aggiungo un asterisco al nome della pizza modificata */
                    order.addPizza(nuovaPizza, 1);
                    nuovaPizza.setCount(true);
                    answer = true;
                    window.close();
                } else {
                    GenericAlert.display("Attenzione: nessuna modifica effettuata!");
                }
            }
        });

        ScrollPane scrollPane = new ScrollPane(gridPane);
        VBox layout = new VBox();
        layout.getChildren().addAll(scrollPane, confirmButton);
        layout.setAlignment(Pos.CENTER);

        window.initModality(Modality.APPLICATION_MODAL);    /* Impedisce di fare azioni sulle altre finestre */
        window.setTitle("Modifica la pizza (+0.50 € per aggiunta)");
        window.setMinWidth(350);
        window.setMaxWidth(400);
        window.setMaxHeight(300);
        layout.setOnKeyPressed(ke -> {
            if(ke.getCode()== KeyCode.ENTER)
                confirmButton.fire();
        });

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }

    /** Riempie il GridPane con tutti gli elementi necessari */
    private GridPane setGridPaneContraints(ArrayList<Label> ingrLabels, ArrayList<HBox> hBoxes) {
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

    /** Riempie ogni HBox con il relativo CheckBox */
    private static void fillHBoxes(ArrayList<HBox> hBoxes, ArrayList<CheckBoxTopping> checkBoxToppings) {
        for (CheckBoxTopping checkBoxTopping : checkBoxToppings) {
            HBox hBox = new HBox(4);
            hBox.getChildren().add(checkBoxTopping);
            hBoxes.add(hBox);
        }
    }

    /** Costruisce la lista dei vari Labels, CheckBoxes */
    private static void fillLabelsAndCheckBoxes(Pizzeria pizzeria, Pizza nuovaPizza, ArrayList<Label> ingrLabels, ArrayList<CheckBoxTopping> checkBoxes) {
        for (String ingr : pizzeria.getIngredientsPizzeria().values()) {
            ingrLabels.add(new Label(ingr.toUpperCase().replace("_"," ")));
            checkBoxes.add(new CheckBoxTopping(ingr, nuovaPizza));
        }
    }

    /** Setta gli ingredienti come presenti o assenti sulla pizza */
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

    /** Controlla che sia stato selezionato almeno un ingrediente per la pizza modificata */
    private static boolean checkCheckBoxTopping(ArrayList<CheckBoxTopping> checkBoxToppings) {
        for (CheckBoxTopping checkBoxTopping : checkBoxToppings) {
            if (checkBoxTopping.isPresent()) {
                return true;
            }
        }
        return false;
    }

	public static void setAnswer() {
		ModifyBox.answer = !ModifyBox.answer;
	}
}


