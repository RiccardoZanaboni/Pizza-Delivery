package graphicElements.PizzeriaPages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.Order;
import pizzeria.Pizzeria;

import java.util.ArrayList;

public class PizzeriaOrderPage {

    public static void display(Pizzeria pizzeria, Stage window) {
        //ArrayList<Label> orderNameLabels = new ArrayList<>();
        ArrayList<Label> nameLabels = new ArrayList<>();
        ArrayList<Label> toppingLabels = new ArrayList<>();
        ArrayList<Label> priceLabels = new ArrayList<>();
        ArrayList<Label> countPizzeLabels = new ArrayList<>();
        //ArrayList<GridPane> gridPanes = new ArrayList<>();
        VBox layout = new VBox();

        for (int i=0; i<pizzeria.getOrders().size(); i++) {
            GridPane gridPane = addEverythingToGridPane(pizzeria.getOrders().get(i), nameLabels, countPizzeLabels, toppingLabels, priceLabels);
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            gridPane.setHgap(10);
            gridPane.setVgap(30);
            layout.getChildren().add(gridPane);
        }

        Scene scene = new Scene(layout, 880, 600);
        window.setScene(scene);
        window.show();
    }

    private static GridPane addEverythingToGridPane(Order order, ArrayList<Label> nomiLabels, ArrayList<Label> countPizzeLabels,  ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels) {
        HBox hBox = new HBox();
        Label orderLabel = new Label(order.getOrderCode()+"");
        Label timeLabel = new Label(order.getTime()+"");
        hBox.getChildren().addAll(orderLabel, timeLabel);
        hBox.setAlignment(Pos.CENTER);

        GridPane gridPane;
        gridPane = order.graphRecap(nomiLabels, countPizzeLabels, ingrLabels, prezziLabels);
        gridPane.getChildren().add(hBox);
        return gridPane;
    }

}

