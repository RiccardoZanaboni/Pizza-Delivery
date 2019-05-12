package elementiGrafici;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pizzeria.Ingredienti;
import pizzeria.Pizza;

import java.util.HashMap;

public class ButtonAddRmvIngr extends Button {
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
    public ButtonAddRmvIngr(Ingredienti ingr, Pizza nuovaPizza) {
        int cr=0;
        if(nuovaPizza.getIngredienti().containsKey(ingr.name()))
        {
            this.setText("✔");
            cr=7;
            anInt++;
        }else {
            this.setText("✘");}
        int finalCr = cr;
        this.setOnAction(e-> {
            if(anInt%2==0) {
                if(finalCr !=7)
                { nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);}
                nuovaPizza.addIngredienti(ingr);
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

