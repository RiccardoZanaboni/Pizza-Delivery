package graphicElements.elements;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import pizzeria.Order;
import pizzeria.Pizza;

public class ButtonRmvPizza extends Button {

	/**
	 * Bottone utile, nel carrello dell'interfaccia grafica, per rimuovere dall'ordine
	 * un'istanza della pizza selezionata.
	 */

	public ButtonRmvPizza(Label nomeLabels, Label toppingLabel, Label prezzoLabel, Button shoppingCartButton, Order order, Pizza pizza, Label countPizza) {
		Image image1 = new Image("/graphicElements/images/cestino.png");
		ImageView imageView = new ImageView(image1);
		imageView.setFitHeight(20);
		imageView.setFitWidth(20);
		this.setGraphic(imageView);
		setId("rmvpizza");
        getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/orderPage1.css").toExternalForm());
		this.setShape(new Circle(100000));
		this.setOnAction(e-> {
		    if(pizza.getName(false).endsWith("*")) {
                for(int j = 0; j < order.getNumPizze(); j++) {
                    if(order.getOrderedPizze().get(j).getToppings().equals(pizza.getToppings()) && order.getOrderedPizze().get(j).getName(false).endsWith("*")) {
                        order.getOrderedPizze().remove(j);
                        break;
                    }
                }
		        int i = order.countPizzaModificata(pizza);
		        pizza.setCount(i);
		    } else {
                order.getOrderedPizze().remove(pizza);
		        pizza.setCount(false);
		    }

		    countPizza.setText("" + pizza.getCount());
		    prezzoLabel.setText("" + (pizza.getPrice() * pizza.getCount()));
		    order.setNumTemporaryPizze(false);
		    shoppingCartButton.setText(order.getNumTemporaryPizze() + "");

		    if (pizza.getCount()==0) {
		    	/* se eliminate tutte le istanze di una data pizza, la linea corrispondente viene tolta dal carrello */
		        nomeLabels.setText("");
		        prezzoLabel.setText("");
		        toppingLabel.setText("");
		        countPizza.setText("");
		        this.setVisible(false);
		    }
		});
    }
}