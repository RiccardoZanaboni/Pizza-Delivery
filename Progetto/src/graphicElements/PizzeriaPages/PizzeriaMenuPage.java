package graphicElements.PizzeriaPages;

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
import javafx.stage.Stage;
import pizzeria.*;

import java.sql.SQLException;
import java.util.HashMap;

public class PizzeriaMenuPage {

    public void display(Pizzeria pizzeria, Stage window) {

        TableView<Pizza> table = new TableView<>();

        //Name column
        TableColumn<Pizza, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(300);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Topping column
        TableColumn<Pizza,HashMap <String, String>> toppingColumn = new TableColumn<>("Topping");
        toppingColumn.setMinWidth(300);
        toppingColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));

        //Price column
        TableColumn<Pizza, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TextField nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);

        TextField priceInput = new TextField();
        priceInput.setPromptText("Price");

        ListView toppingInput = new ListView<>();
        toppingInput.getItems().addAll(pizzeria.getIngredientsPizzeria().values());
        toppingInput.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked(pizzeria, nameInput, toppingInput, priceInput, table));
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked(pizzeria, table));

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, priceInput, toppingInput, addButton, deleteButton);

        table.setItems(getMenu(pizzeria));
        table.getColumns().addAll(nameColumn, toppingColumn, priceColumn);

        Button backButton = new Button("← Torna indietro");
        backButton.setOnAction(e -> {
            PizzeriaHomePage pizzeriaHomePage=new PizzeriaHomePage();
            pizzeriaHomePage.display(pizzeria, window);
        });
        HBox hBox1=new HBox(10);
        hBox1.getChildren().add(backButton);
        hBox1.setAlignment(Pos.CENTER);

        VBox layout = new VBox();
        layout.getChildren().addAll(table, hBox,hBox1);
        layout.setOnKeyPressed(ke -> {
            if(ke.getCode()== KeyCode.BACK_SPACE)
                backButton.fire();
        });

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

    private ObservableList<Pizza> getMenu(Pizzeria pizzeria){
        ObservableList<Pizza> pizze = FXCollections.observableArrayList();
        pizze.addAll(pizzeria.getMenu().values());
        return pizze;
    }

    public void addButtonClicked(Pizzeria pizzeria, TextField nameInput, ListView toppingInput, TextField priceInput, TableView table){

        ObservableList<String> toppings;
        toppings = toppingInput.getSelectionModel().getSelectedItems();
        StringBuilder ingr =new StringBuilder();

        HashMap<String, String> h = new HashMap <>();
        for (int i=0; i<toppings.size(); i++) {
            h.put(toppings.get(i), toppings.get(i));
            ingr.append(toppings.get(i)).append(",");
        }
        String ingredienti=ingr.toString();
        ingredienti= ingredienti.substring(0,ingr.length()-1);
        Pizza pizza = new Pizza(nameInput.getText().toUpperCase(), h, Double.parseDouble(priceInput.getText()));
        pizzeria.getMenu().put(pizza.getName(false).toUpperCase(), pizza);
        table.getItems().add(pizza);
        Database.putPizza(nameInput.getText(), ingredienti, Double.parseDouble(priceInput.getText()));

        nameInput.clear();
        priceInput.clear();
    }


    public void deleteButtonClicked(Pizzeria pizzeria, TableView table) {

        ObservableList<Pizza> pizzaSelected, allPizzas;

        allPizzas = table.getItems();
        pizzaSelected = table.getSelectionModel().getSelectedItems();

        for (int i=0; i<pizzaSelected.size(); i++) {
            pizzeria.getMenu().remove(pizzaSelected.get(i).getName(false).toUpperCase());
            Database.removePizza(pizzaSelected.get(i).getName(false).toUpperCase());
        }
        pizzaSelected.forEach(allPizzas::remove);

    }
}