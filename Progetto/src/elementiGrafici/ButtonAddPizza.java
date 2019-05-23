package elementiGrafici;

import avvisiGrafici.AlertNumPizzeMax;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pizzeria.Order;
import pizzeria.Pizzeria;

class ButtonAddPizza extends Button {
	ButtonAddPizza(Order order, Pizzeria pizzeria, Label countPizza, String pizza){
		this.setId("addpizza");
		this.setText("Aggiungi al carrello ✔︎");
		this.setOnAction(e-> {
			if (order.getNumPizze()<16) {
				order.addPizza(pizzeria.getMenu().get(pizza), 1);
				pizzeria.getMenu().get(pizza).increaseCount();
				countPizza.setText(""+pizzeria.getMenu().get(pizza).getCount());
			} else
				AlertNumPizzeMax.display();
		});
	}

}
