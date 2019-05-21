package elementiGrafici;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import pizzeria.Ingredients;
import pizzeria.Pizza;

public class ButtonAddRmvIngr extends Button {
    /** --> la prima volta che schiacci aggiungi, la seconda rimuovi, la terza aggiungi.. etc
     * */

    private int anInt;
    ButtonAddRmvIngr(Ingredients ingr, Pizza nuovaPizza) {
        this.anInt=0;
        int cr=0;
        if(nuovaPizza.getIngredients().containsKey(ingr.name()))
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
                { nuovaPizza.setPrice(nuovaPizza.getPrice() + 0.50);}
                nuovaPizza.addIngredients(ingr);
            }
            else if(this.anInt%2==1){
                if(finalCr==0)
                { nuovaPizza.setPrice(nuovaPizza.getPrice() - 0.50);}
                nuovaPizza.rmvIngredients(ingr);
            }
            System.out.println(nuovaPizza.getIngredients());
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

