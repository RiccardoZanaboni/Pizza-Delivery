package graphicElements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.Toppings;

import java.util.ArrayList;
import java.util.HashMap;

public class PizzeriaMenuPage {

    public void display(Pizzeria pizzeria, Stage window) {

        //Name column
        TableColumn<Pizza, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(300);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Quantity column
        TableColumn<Pizza,HashMap <String, Toppings>> toppingColumn = new TableColumn<>("Topping");
        toppingColumn.setMinWidth(300);
        toppingColumn.setCellValueFactory(new PropertyValueFactory<>("topping"));

        //Price column
        TableColumn<Pizza, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));



        TableView<Pizza> table = new TableView<>();
        table.setItems(getMenu(pizzeria));
        table.getColumns().addAll(nameColumn,  toppingColumn, priceColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }

    private ObservableList<Pizza> getMenu(Pizzeria pizzeria){
        ObservableList<Pizza> pizze = FXCollections.observableArrayList();
        pizze.addAll(pizzeria.getMenu().values());
        return pizze;
    }
}

