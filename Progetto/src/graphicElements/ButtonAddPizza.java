package graphicElements;

import graphicAlerts.GenericAlert;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import pizzeria.Order;
import pizzeria.Pizza;

class ButtonAddPizza extends Button {

	/**
	 * Bottone utile, nell'interfaccia grafica, per aggiungere all'ordine
	 * un'istanza della pizza desiderata.
	 */

	ButtonAddPizza(Button shoppingCartButton, Order order, Pizza pizza){
		this.setId("addpizza");
		this.setShape(new Circle(1000));
		this.setText("✚");//("Aggiungi al carrello ✔︎");
		this.setOnAction(e-> {
			if (order.getNumPizze()<16) {
				order.addPizza(pizza, 1);
				pizza.setCount(true);
				//countPizza.setText(""+pizzeria.getMenu().get(pizza).getCount());
				order.setNumTemporaryPizze(1);
				shoppingCartButton.setText(order.getNumTemporaryPizze()+"");
			} else
				GenericAlert.display("Attenzione: numero massimo di pizze raggiunto!");
		});
	}

}
