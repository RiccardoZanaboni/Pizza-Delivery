package graphicElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.Services;

import javax.xml.ws.Service;
import java.util.ArrayList;
import java.util.Date;

public class ShoppingCart {
    Stage window = new Stage();


    public void display(Order order, Pizzeria pizzeria, Button shoppingCartButton) {

        VBox layout = new VBox();

        Label label = new Label("Il tuo carrello");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(label);
        hBox.setAlignment(Pos.CENTER);

        //GridPane gridPane = addEverythingToGridPane(pizzasInCart, order, pizzeria, nomiLabels, countPizzeLabels, ingrLabels, prezziLabels);
        GridPane gridPane = createGridPane(shoppingCartButton, order, pizzeria);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setMinSize(600, 400);
        layout.getChildren().addAll(hBox, scrollPane);
        Scene scene = new Scene(layout);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.show();

    }


    private static GridPane createGridPane(Button shoppingCartButton, Order order, Pizzeria pizzeria) {
        GridPane gridPane = new GridPane();
        ArrayList<Label> nomiLabels = new ArrayList<>();
        ArrayList<Label> ingrLabels = new ArrayList<>();
        ArrayList<Label> prezziLabels = new ArrayList<>();
        ArrayList<Label> countPizzeLabels = new ArrayList<>();
        ArrayList<ButtonRmvPizza> buttonRmvPizzas = new ArrayList<>();

        ArrayList<Pizza> elencate = new ArrayList<>();
        int numTipo = 0;
        for (int i = 0; i < order.getNumPizze(); i++) {
            Pizza p = order.getOrderedPizze().get(i);
            int num = 0;
            boolean contains = false;
            for (Pizza pizza : elencate) {
                if (p.getMaiuscName().equals(pizza.getMaiuscName()) && p.getToppings().equals(pizza.getToppings())) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                elencate.add(p);
                for (int j = 0; j < order.getNumPizze(); j++) {
                    if (p.getMaiuscName().equals(order.getOrderedPizze().get(j).getMaiuscName()) && p.getToppings().equals(order.getOrderedPizze().get(j).getToppings()))
                        num++;		// di quel "tipo di pizza" ce n'è una in più
                }

                nomiLabels.add(numTipo, new Label(Services.getCamelName(order.getOrderedPizze().get(i))));
                ingrLabels.add(numTipo, new Label(order.getOrderedPizze().get(i).getDescription()));
                prezziLabels.add(numTipo, new Label((order.getOrderedPizze().get(i).getPrice()*num + " €")));
                countPizzeLabels.add(numTipo, new Label());
                countPizzeLabels.get(numTipo).setText("" + num);
                buttonRmvPizzas.add(new ButtonRmvPizza(shoppingCartButton, order, pizzeria, order.getOrderedPizze().get(i), countPizzeLabels.get(numTipo)));

                gridPane.getChildren().add(nomiLabels.get(numTipo));
                gridPane.getChildren().add(ingrLabels.get(numTipo));
                gridPane.getChildren().add(countPizzeLabels.get(numTipo));
                gridPane.getChildren().add(prezziLabels.get(numTipo));
                gridPane.getChildren().add(buttonRmvPizzas.get(numTipo));

                GridPane.setConstraints(countPizzeLabels.get(numTipo), 0, numTipo + 1);
                GridPane.setConstraints(nomiLabels.get(numTipo), 1, numTipo + 1);
                GridPane.setConstraints(ingrLabels.get(numTipo), 2, numTipo + 1);
                GridPane.setConstraints(prezziLabels.get(numTipo), 3, numTipo + 1);
                GridPane.setConstraints(buttonRmvPizzas.get(numTipo), 4, numTipo + 1);

                numTipo++;		// ho un "tipo di pizza" in piu


            }


        }
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(30);
        return gridPane;
    }

}
