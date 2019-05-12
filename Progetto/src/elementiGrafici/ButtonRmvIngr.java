package elementiGrafici;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pizzeria.Ingredienti;
import pizzeria.Pizza;

public class ButtonRmvIngr extends Button {
	/*public ButtonRmvIngr(Ingredienti ingr, Pizza nuovaPizza) {
		this.setText("Remove");
		this.setOnAction(e-> {
			nuovaPizza.rmvIngredienti(ingr);
			//nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() - 0.50);
			System.out.println(nuovaPizza.getIngredienti());
			System.out.println(ingr.name());
		});
	}
}
*/

	//FIXME:   --- IMPORSTARE GLI INGREDIENTI DA ✘(anInt =1) A ✔(anInt =0) DELLA PIZZA CHE SI STA MODIFICANDO  ---
    //FIXME: SPIEGAZIONE-->la prima volta che schiacci aggiungi, la seconda rimuovi, la terza aggiungi.. etc

	static private int anInt=0;
    public ButtonRmvIngr(Ingredienti ingr, Pizza nuovaPizza) {
        this.setText("✘");
        this.setOnAction(e-> {
            if(anInt%2==0) {
                nuovaPizza.addIngredienti(ingr);
                nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
                System.out.println(nuovaPizza.getIngredienti());
                System.out.println(ingr.name());}
            else{
                nuovaPizza.rmvIngredienti(ingr);
                System.out.println(nuovaPizza.getIngredienti());
                System.out.println(ingr.name());}
            anInt++;
        });
    }

    @Override
    public void fire() {
        if (!isDisabled()) {
            if(anInt%2==0) {
                fireEvent(new ActionEvent());
                this.setText("✔");
            }
            else {
                fireEvent(new ActionEvent());
                this.setText("✘");
            }
        }
    }
}

