package elementiGrafici;

import javafx.scene.control.Button;
import pizzeria.Ingredienti;
import pizzeria.Pizza;

public class ButtonRmvIngr extends Button {
	public ButtonRmvIngr(Ingredienti ingr, Pizza nuovaPizza) {
		this.setText("Remove");
		this.setOnAction(e-> {
			nuovaPizza.rmvIngredienti(ingr);
			//nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() - 0.50);
			System.out.println(nuovaPizza.getIngredienti());
			System.out.println(ingr.name());
		});
	}
}
