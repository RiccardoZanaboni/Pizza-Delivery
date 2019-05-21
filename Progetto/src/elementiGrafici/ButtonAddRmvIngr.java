package elementiGrafici;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pizzeria.Ingredienti;
import pizzeria.Pizza;

import java.util.HashMap;

public class ButtonAddRmvIngr extends Button {
    /** SPIEGAZIONE-->la prima volta che schiacci aggiungi, la seconda rimuovi, la terza aggiungi.. etc
*/

    private int anInt;
    ButtonAddRmvIngr(Ingredienti ingr, Pizza nuovaPizza) {
        this.anInt=0;
        int cr=0;
        if(nuovaPizza.getIngredienti().containsKey(ingr.name()))
        {
            this.setText("✔");
            cr=7;                            //SE APPARTIENE GIÁ ALLA PIZZA SETTATO A ✔
            this.anInt++;
        }else {
            this.setText("✘");
        }
        int finalCr = cr;
        this.setOnAction(e-> {
            if(this.anInt%2==0) {
                if(finalCr !=7)
                { nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);}
                nuovaPizza.addIngredienti(ingr);
            }
            else if(this.anInt%2==1){
                if(finalCr==0)
                { nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() - 0.50);}
                nuovaPizza.rmvIngredienti(ingr);
            }
            System.out.println(nuovaPizza.getIngredienti());
            System.out.println(ingr.name());
            this.anInt++;
        });
    }

    @Override
    public void fire() {
        if (!isDisabled()) {
            if(this.anInt%2==0) {
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

