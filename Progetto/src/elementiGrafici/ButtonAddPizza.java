package elementiGrafici;

import avvisiGrafici.AlertNumPizzeMax;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pizzeria.Order;
import pizzeria.Pizzeria;

class ButtonAddPizza extends Button {
	ButtonAddPizza(Order order, Pizzeria pizzeria, Label countPizza, String pizza){
		this.setText("Aggiungi al carrello ✔︎");
		this.setOnAction(e-> {
			if (order.getNumeroPizze()<16) {
				order.addPizza(pizzeria.getMenu().get(pizza), 1);
				pizzeria.getMenu().get(pizza).setCount();
				countPizza.setText(""+pizzeria.getMenu().get(pizza).getCount());
			} else
				AlertNumPizzeMax.display();
		});
	}

}
