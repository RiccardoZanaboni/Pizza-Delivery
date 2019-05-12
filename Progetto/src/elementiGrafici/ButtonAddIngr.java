package elementiGrafici;

import javafx.scene.control.Button;
import pizzeria.Ingredienti;
import pizzeria.Pizza;

public class ButtonAddIngr extends Button {
	public ButtonAddIngr(Ingredienti ingr, Pizza nuovaPizza) {
		this.setText("Add ✔︎");
		this.setOnAction(e-> {
			nuovaPizza.addIngredienti(ingr);
			nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
			System.out.println(nuovaPizza.getIngredienti());
			System.out.println(ingr.name());
		});
	}
}
