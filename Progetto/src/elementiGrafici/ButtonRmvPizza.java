package elementiGrafici;

import avvisiGrafici.AlertNumeroPizzeMin;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pizzeria.Order;
import pizzeria.Pizzeria;

class ButtonRmvPizza extends Button {
	ButtonRmvPizza(Order order, Pizzeria pizzeria, Label countPizza, String pizza){
		setId("rmvpizza");
		this.setText("Rimuovi dal carrello ✘");
		this.setOnAction(e-> {
			if(order.searchPizza(pizzeria.getMenu().get(pizza))){
				order.getOrderedPizze().remove(pizzeria.getMenu().get(pizza));
				pizzeria.getMenu().get(pizza).decreaseCount();
				countPizza.setText(""+pizzeria.getMenu().get(pizza).getCount());
			}else
				AlertNumeroPizzeMin.display(pizza);
		});
	}

}
