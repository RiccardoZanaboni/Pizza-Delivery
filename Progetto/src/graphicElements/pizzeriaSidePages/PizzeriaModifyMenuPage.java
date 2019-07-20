package graphicElements.pizzeriaSidePages;

import database.PizzaDB;
import database.ToppingDB;
import graphicAlerts.GenericAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pizzeria.*;

import java.util.HashMap;

@SuppressWarnings("ALL")
public class PizzeriaModifyMenuPage {

	/**
	 * Lo Stage mostra la pagina atta a effettuare modifiche al menu, da parte della pizzeria.
	 * Nella parte superiore, la tabella "table" mostra il menu attuale, con nomi, descrizione e prezzo delle pizze.
	 * Nella parte inferiore si hanno gli strumenti per la modifica:
	 * - inserendo nome e prezzo di una nuova pizza, selezionandone gli ingredienti dalla lista (tenendo premuto
	 *   il tasto CTRL), è possibile aggiungere una pizza al menu ("Add Pizza").
	 * - selezionando una pizza dal menu, cliccando "Delete Pizza", è possibile rimuoverla dal menu.
	 * - inserendo il nome di un nuovo ingrediente, è possibile aggiungerlo a quelli disponibili.
	 * - selezionando un ingrediente dalla lista, è possibile rimuoverlo ("Delete Topping").
	 * In entrambi i casi di inserimento, viene visualizzato errore se un elemento con lo stesso nome
	 * risulta già salvato nel DB.
	 * */
    public void display(Pizzeria pizzeria, Stage window) {
		window.setTitle("Wolf of Pizza - Modifica Menu");

		TableView<Pizza> table = new TableView<>();

        /* Name column */
        TableColumn<Pizza, String> nameColumn = new TableColumn<>("PIZZAS");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));		/* richiama Pizza.getName() */

        /* Topping column */
        TableColumn<Pizza, String> toppingColumn = new TableColumn<>("TOPPINGS");
        toppingColumn.setMinWidth(540);
        toppingColumn.setCellValueFactory(new PropertyValueFactory<>("description"));	/* richiama Pizza.getDescription() */

        /* Price column */
        TableColumn<Pizza, Double> priceColumn = new TableColumn<>("PRICE");
        priceColumn.setMinWidth(70);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));	/* richiama Pizza.getPrice() */

		/* Campi di inserimento */
        TextField nameInput = new TextField();
        nameInput.setPromptText("New Pizza Name");
        nameInput.setMinWidth(100);

        TextField priceInput = new TextField();
        priceInput.setPromptText("New Pizza Price");

		TextField newToppingInput = new TextField();
		newToppingInput.setPromptText("New Topping Name");
		newToppingInput.setMinWidth(100);

        ListView toppingsList = new ListView<>();
        toppingsList.getItems().addAll(pizzeria.getIngredientsPizzeria().values());
        toppingsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Text infoNewPizzaLabel = new Text();
        infoNewPizzaLabel.setText("Per selezionare piú ingredienti \n tenere premuto CTRL.\n");

        /* Bottoni di inserimento/eliminazione */
        Button addPizzaButton = new Button("Add Pizza");
        addPizzaButton.setOnAction(e ->
                addPizzaButtonClicked(pizzeria, nameInput, toppingsList, priceInput, table)
        );
        Button deletePizzaButton = new Button("Delete Pizza");
        deletePizzaButton.setOnAction(e ->
                deletePizzaButtonClicked(pizzeria, table)
        );
		Button addToppingButton = new Button("Add Topping");
		addToppingButton.setOnAction(e ->
				addToppingButtonClicked(pizzeria, newToppingInput, toppingsList)
		);
		Button deleteToppingButton = new Button("Delete Topping");
		deleteToppingButton.setOnAction(e -> {
			deleteToppingButtonClicked(pizzeria, toppingsList);
			/* Ricarica la pagina, in modo da visualizzare se alcune pizze sono state automaticamente rimosse. */
			display(pizzeria,window);
		});

		table.setItems(getMenu(pizzeria));
		table.getColumns().addAll(nameColumn, toppingColumn, priceColumn);

        VBox newPizzaVBox = new VBox(15);
        newPizzaVBox.setPadding(new Insets(10,80,10,10));
        newPizzaVBox.getChildren().addAll(nameInput, priceInput, infoNewPizzaLabel, addPizzaButton, deletePizzaButton);
        newPizzaVBox.setAlignment(Pos.BASELINE_LEFT);

		VBox newToppingHBox = new VBox(15);
		newToppingHBox.setPadding(new Insets(10,10,10,80));
		newToppingHBox.getChildren().addAll(newToppingInput,addToppingButton,deleteToppingButton);
		newToppingHBox.setAlignment(Pos.BASELINE_RIGHT);

		HBox hBoxInferiore = new HBox(10);
		hBoxInferiore.setPadding(new Insets(10,10,10,10));
		hBoxInferiore.getChildren().addAll(newPizzaVBox,toppingsList,newToppingHBox);

		/* Bottone per tornare indietro */
		Button backButton = new Button("← Return");
		backButton.setMinHeight(35);
		backButton.setOnAction(e -> {
			PizzeriaHomePage pizzeriaHomePage = new PizzeriaHomePage();
			pizzeriaHomePage.display(pizzeria, window);
		});

        HBox returnHBox = new HBox(10);
        returnHBox.getChildren().add(backButton);
        returnHBox.setMinHeight(60);
        returnHBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox();
        layout.getChildren().addAll(table, hBoxInferiore, returnHBox);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setOnKeyPressed(ke -> {
            if(ke.getCode() == KeyCode.BACK_SPACE)
                backButton.fire();
        });
        Scene scene = new Scene(layout,800,600);
		//scene.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/ButtonPizza.css").toExternalForm());
		window.setScene(scene);
        window.show();
    }

    /** @return la lista delle pizze presenti sul Menu. */
	private ObservableList<Pizza> getMenu(Pizzeria pizzeria){
        ObservableList<Pizza> pizze = FXCollections.observableArrayList();
        pizze.addAll(pizzeria.getMenu().values());
        return pizze;
    }

    /** Gestisce la richiesta di aggiungere una nuova pizza */
    private void addPizzaButtonClicked(Pizzeria pizzeria, TextField nameInput, ListView toppingInput, TextField priceInput, TableView table){
		try {
			ObservableList<String> toppings;
        	toppings = toppingInput.getSelectionModel().getSelectedItems();
        	String name = nameInput.getText().toUpperCase();
        	if(name.length() == 0 || priceInput.getText().length() == 0){
				GenericAlert.display("Attenzione: riempire entrambi i campi 'nome' e 'prezzo'.");
				return;
			}
        	if(pizzeria.getMenu().containsKey(name)) {
				GenericAlert.display("Attenzione: la nuova pizza\nnon deve essere già presente nel Database.");
				return;
			}
			double price = Double.parseDouble(priceInput.getText());
        	if(price <= 0){
        		GenericAlert.display("Attenzione: inserito prezzo non valido.");
        		return;
			}
        	if(toppings.size() > 0) {
            	StringBuilder ingr = new StringBuilder();
            	HashMap<String, String> h = new HashMap <>();
            	for (String topping : toppings) {
                	h.put(topping, topping);
                	ingr.append(topping).append(",");
            	}
            	String ingredienti = ingr.toString();
            	ingredienti = ingredienti.substring(0,ingr.length()-1);
            	Pizza pizza = new Pizza(nameInput.getText().toUpperCase(), h, price);
            	pizzeria.getMenu().put(pizza.getName(false).toUpperCase(), pizza);
            	table.getItems().add(pizza);
            	PizzaDB.putPizza(nameInput.getText(), ingredienti, Double.parseDouble(priceInput.getText()));
				nameInput.clear();
				priceInput.clear();
        	} else {
				GenericAlert.display("Attenzione: selezionare almeno un ingrediente.");
			}
		} catch (NumberFormatException nfe){
			GenericAlert.display("Attenzione: inserito prezzo non valido.");
		}
    }

    /** Gestisce la richiesta di eliminare una pizza */
	private void deletePizzaButtonClicked(Pizzeria pizzeria, TableView table) {
        ObservableList<Pizza> pizzaSelected, allPizzas;
        allPizzas = table.getItems();
        pizzaSelected = table.getSelectionModel().getSelectedItems();
        if(pizzaSelected.size() > 0) {
			for (Pizza pizza : pizzaSelected) {
				pizzeria.getMenu().remove(pizza.getName(false).toUpperCase());
				PizzaDB.removePizza(pizza.getName(false).toUpperCase());
				allPizzas.remove(pizza);
			}
		} else {
        	GenericAlert.display("Attenzione: selezionare una pizza da rimuovere.");
		}
    }

    /** Gestisce la richiesta di aggiungere un nuovo topping */
	private void addToppingButtonClicked(Pizzeria pizzeria, TextField newToppingInput, ListView toppingsList) {
		String topping = newToppingInput.getText().toUpperCase();
		if (topping.length() == 0) {
			GenericAlert.display("Attenzione: riempire il campo richiesto.");
		} else if(pizzeria.getIngredientsPizzeria().containsKey(topping)){
			GenericAlert.display("Attenzione: il nuovo ingrediente\nnon deve essere già presente nel Database.");
		} else {
			pizzeria.getIngredientsPizzeria().put(topping, topping);
			toppingsList.getItems().add(topping);
			ToppingDB.putTopping(topping);
			newToppingInput.clear();
		}
	}

	/** Gestisce la richiesta di eliminare un topping */
	private void deleteToppingButtonClicked(Pizzeria pizzeria, ListView table) {
		ObservableList<String> toppingsSelected, allToppings;
		allToppings = table.getItems();
		toppingsSelected = table.getSelectionModel().getSelectedItems();
		if (toppingsSelected.size() > 0) {
			for (String topping : toppingsSelected) {
				pizzeria.getIngredientsPizzeria().remove(topping);
				ToppingDB.removeTopping(pizzeria,topping);
				allToppings.remove(topping);
			}
		} else {
			GenericAlert.display("Attenzione: selezionare un ingrediente da rimuovere.");
		}
	}
}