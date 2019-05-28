package graphicElements;

import graphicAlerts.MaxPizzasAlert;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;

class ButtonAddPizza extends Button {

	/**
	 * Bottone utile, nell'interfaccia grafica, per aggiungere all'ordine
	 * un'istanza della pizza desiderata.
	 */

	ButtonAddPizza(Button shoppingCartButton, Order order, Pizzeria pizzeria, Pizza pizza){
		this.setId("addpizza");
		this.setShape(new Circle(1000));
		this.setText("✚");//("Aggiungi al carrello ✔︎");
		this.setOnAction(e-> {
			if (order.getNumPizze()<16) {
				order.addPizza(pizza, 1);
				pizza.increaseCount();
				//countPizza.setText(""+pizzeria.getMenu().get(pizza).getCount());
				order.increaseNumPizzeProvvisorie();
				shoppingCartButton.setText(order.getNumPizzeProvvisorie()+"");
			} else
				MaxPizzasAlert.display();
		});
	}

}
