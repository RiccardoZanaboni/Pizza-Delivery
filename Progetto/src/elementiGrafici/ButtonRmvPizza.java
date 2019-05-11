package elementiGrafici;

import avvisiGrafici.AlertNumPizzeMax;
import avvisiGrafici.AlertNumeroPizzeMin;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pizzeria.Order;
import pizzeria.Pizzeria;

public class ButtonRmvPizza extends Button {
	public ButtonRmvPizza(Order order, Pizzeria pizzeria, Label countPizza, String pizza){
		//int tot = order.getNumeroPizze();
		this.setText("Rimuovi dal carrello");
		this.setOnAction(e-> {if(order.searchPizza(pizzeria.getMenu().get(pizza))){
			order.getPizzeordinate().remove(pizzeria.getMenu().get(pizza));
			pizzeria.getMenu().get(pizza).resetCount();
			countPizza.setText(""+pizzeria.getMenu().get(pizza).getCount());
		}else
			AlertNumeroPizzeMin.display("MARGHERITA");
		});
	}

}
