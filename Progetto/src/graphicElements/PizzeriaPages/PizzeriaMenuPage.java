package graphicElements.PizzeriaPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.Toppings;
import java.util.HashMap;

public class PizzeriaMenuPage {

    public void display(Pizzeria pizzeria, Stage window) {

        TableView<Pizza> table = new TableView<>();

        //Name column
        TableColumn<Pizza, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(300);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Topping column
        TableColumn<Pizza,HashMap <String, Toppings>> toppingColumn = new TableColumn<>("Topping");
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
        toppingInput.getItems().addAll(Toppings.values());
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

        VBox layout = new VBox();
        layout.getChildren().addAll(table, hBox);

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

        ObservableList<Toppings> toppings;
        toppings = toppingInput.getSelectionModel().getSelectedItems();

        HashMap<String, Toppings> h = new HashMap <>();
        for (int i=0; i<toppings.size(); i++) {
                h.put(toppings.get(i).name(), toppings.get(i));
                System.out.println(toppings.get(i).name()+ toppings.get(i));
        }
        Pizza pizza = new Pizza(nameInput.getText(), h, Double.parseDouble(priceInput.getText()));
        pizzeria.getMenu().put(pizza.getName(false), pizza);
        table.getItems().add(pizza);

        nameInput.clear();
        priceInput.clear();
    }


    public void deleteButtonClicked(Pizzeria pizzeria, TableView table){     // FIXME non cancella la pizza dal menu

        ObservableList<Pizza> productSelected, allProducts;

        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);

        pizzeria.getMenu().remove(table.getSelectionModel().getSelectedItems());
        System.out.println(pizzeria.getMenu());
    }
}

