package graphicElements.elements;

import javafx.beans.NamedArg;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.services.SettleStringsServices;

public class ButtonRmvPizza extends Button {

	/**
	 * Bottone utile, nel carrello dell'interfaccia grafica, per rimuovere dall'ordine
	 * un'istanza della pizza selezionata. I dati vengono aggiornati real-time nel carrello.
	 */

	public ButtonRmvPizza(Label nomeLabels, Label toppingLabel, Label prezzoLabel, Button shoppingCartButton, Label total, Order order, Pizza pizza, Label countPizza) {
	    this.setGraphic(createImageView("/graphicElements/images/cestino.png",20,20));
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
		    prezzoLabel.setText("  x  " + (SettleStringsServices.settlePriceDecimal(pizza.getPrice())) + " €");
		    total.setText(order.getTotalPrice() + " €");
		    order.setNumTemporaryPizze(false);
		    shoppingCartButton.setText(String.valueOf(order.getNumTemporaryPizze()));

			/* se eliminate tutte le istanze di una data pizza, la linea corrispondente viene tolta dal carrello */
			if (pizza.getCount()==0) {
		        nomeLabels.setText("");
		        prezzoLabel.setText("");
		        toppingLabel.setText("");
		        countPizza.setText("");
		        this.setVisible(false);
		    }
		});
    }
    private ImageView createImageView(@NamedArg("url") String url, double Height, double Width) {
        Image image1 = new Image(url);
        ImageView imageView = new ImageView(image1);
        imageView.setFitHeight(Height);
        imageView.setFitWidth(Width);
        imageView.autosize();
        return imageView;
    }
}