package elementiGrafici;

import avvisiGrafici.AlertNumeroPizzeMin;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pizzeria.Order;
import pizzeria.Pizzeria;

class ButtonRmvPizza extends Button {
	ButtonRmvPizza(Label pizzasInCart, Order order, Pizzeria pizzeria, Label countPizza, String pizza){
		this.setId("rmvpizza");
		this.setText("Rimuovi dal carrello ✘");
		this.setOnAction(e-> {
			if(order.searchPizza(pizzeria.getMenu().get(pizza))){
				order.getOrderedPizze().remove(pizzeria.getMenu().get(pizza));
				pizzeria.getMenu().get(pizza).decreaseCount();
				countPizza.setText(""+pizzeria.getMenu().get(pizza).getCount());
				order.decreaseNumPizzeProvvisorie();
				pizzasInCart.setText(order.getNumPizzeProvvisorie()+"");
			}else
				AlertNumeroPizzeMin.display(pizza);
		});
	}

}
