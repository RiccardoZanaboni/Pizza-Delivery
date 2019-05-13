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

	//
    /** SPIEGAZIONE-->la prima volta che schiacci aggiungi, la seconda rimuovi, la terza aggiungi.. etc
*/
    static private int anInt;
    public ButtonAddRmvIngr(Ingredienti ingr, Pizza nuovaPizza) {
        anInt=0;
        int cr=0;
        if(nuovaPizza.getIngredienti().containsKey(ingr.name()))
        {
            this.setText("✔");
            cr=7;                            //SE APPARTIENE GIÁ ALLA PIZZA SETTATO A ✔
            anInt++;
        }else {
            this.setText("✘");
        }
        int finalCr = cr;
        this.setOnAction(e-> {
            if(anInt%2==0) {
                if(finalCr !=7)
                { nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);}
                nuovaPizza.addIngredienti(ingr);
            }
            else if(anInt%2==1){
                //FIXME: NON SO PERCHE' NON FUNZIONA, SERVIREBBE A TOGLIERE 0.50 AGGIUNTI PER UN INGREDIENTE NON DELLA
                 //FIXME PIZZA DOPO CHE HAI TOLTO SUCCESSIVAMENTE, DA PRIORITA AL if di CR
                //if(finalCr==0)
                //{ nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() - 0.50);}
                nuovaPizza.rmvIngredienti(ingr);
            }
            System.out.println(nuovaPizza.getIngredienti());
            System.out.println(ingr.name());
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

