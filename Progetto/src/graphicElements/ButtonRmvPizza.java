package graphicElements;

import graphicAlerts.MinPizzasAlert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import pizzeria.Order;
import pizzeria.Pizza;

class ButtonRmvPizza extends Button {

	ButtonRmvPizza(Stage window, Label nomeLabels, Label prezzoLabel, Label toppingLabel, Button shoppingCartButton, Order order, Pizza pizza, Label countPizza) {
		Image image1 = new Image("graphicElements/images/cestino.png");
		ImageView imageView = new ImageView(image1);
		imageView.setFitHeight(20);
		imageView.setFitWidth(20);
		this.setGraphic(imageView);
		setId("rmvpizza");
        getStylesheets().addAll(this.getClass().getResource("cssStyle/buttonsAndLabelsAndBackgroundStyle.css").toExternalForm());
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

		    /* FIXME: con queste tre righe effettua il refresh del carrello ogni volta
		      che si rimuove una pizza, però graficamente si vede che chiude e riapre...
			  può essere un bene (rende "ben visibile" l'effettuato aggiornamento o può dare fastidio.
			  Comunque è un modo per risolvere l'aggiornamento del prezzo quando elimino una pizza!
		    */
		    window.hide();
		    ShoppingCart shoppingCart = new ShoppingCart();
		    shoppingCart.display(order, shoppingCartButton);


		    //countPizza.setText("" + pizza.getCount());
		    //prezzoLabel.setText("" + (pizza.getPrice() * pizza.getCount()));	// fixme: altrimenti così, ma non funziona bene (non so perchè)
		    order.setNumTemporaryPizze(false);
		    shoppingCartButton.setText(order.getNumTemporaryPizze() + "");
/*
		    if (pizza.getCount()==0) {		// se eliminate tutte, vengono nascoste nel carrello
		        nomeLabels.setText("");
		        prezzoLabel.setText("");
		        toppingLabel.setText("");
		        countPizza.setText("");
		        this.setVisible(false);
		    }

		    */

			/*else if(pizza.getCount()<0) {		// fixme: questo tecnicamente è inutile, non può verificarsi
		        MinPizzasAlert.display(pizza.getName(false));
		    }*/
		});
    }
}