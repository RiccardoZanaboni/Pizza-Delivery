package graphicElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;

import java.util.ArrayList;

public class ShoppingCart {
    Stage window = new Stage();


    public void display(Order order, Pizzeria pizzeria, Label pizzasInCart) {

        VBox layout = new VBox();

        ArrayList<Label> nomiLabels = new ArrayList<>();
        ArrayList<Label> ingrLabels = new ArrayList<>();
        ArrayList<Label> prezziLabels = new ArrayList<>();
        ArrayList<Label> countPizzeLabels = new ArrayList<>();
        //ArrayList<ButtonRmvPizza> rmvButton = new ArrayList<>();

        Label label = new Label("Il tuo carrello");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(label);
        hBox.setAlignment(Pos.CENTER);

        GridPane gridPane = addEverythingToGridPane(pizzasInCart, order, pizzeria, nomiLabels, countPizzeLabels, ingrLabels, prezziLabels);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setMinSize(600, 400);
        layout.getChildren().addAll(hBox, scrollPane);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }


    private static GridPane addEverythingToGridPane(Label pizzasInCart, Order order, Pizzeria pizzeria, ArrayList<Label> nomiLabels, ArrayList<Label> countPizzeLabels,  ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels) {
        HBox totalBox = new HBox();
        Label labelTot = new Label("Totale: ");
        Label label2 = new Label("" + order.getTotalPrice());
        totalBox.getChildren().addAll(labelTot,label2);
        totalBox.setAlignment(Pos.CENTER);

        ArrayList<ButtonRmvPizza> rmvButtons = new ArrayList<>();
        GridPane gridPane;
        gridPane = order.graphRecap(nomiLabels, countPizzeLabels, ingrLabels, prezziLabels);
        /*for (int i = 0; i < order.getNumPizze(); i++) {
            rmvButtons.add(new ButtonRmvPizza(pizzasInCart, order, pizzeria, countPizzeLabels.get(i), order.getOrderedPizze().get(i).getMaiuscName()));
        }*/
        for (int i=0; i<order.getNumPizze(); i++) {
            rmvButtons.add(new ButtonRmvPizza(pizzasInCart, order, pizzeria, countPizzeLabels.get(i), order.getOrderedPizze().get(i).getMaiuscName()));
            gridPane.getChildren().add(rmvButtons.get(i));
            GridPane.setConstraints(rmvButtons.get(i), 4, i + 1);
        }

        GridPane.setConstraints(totalBox, 1, nomiLabels.size()+2);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(30);
        return gridPane;
    }
}
