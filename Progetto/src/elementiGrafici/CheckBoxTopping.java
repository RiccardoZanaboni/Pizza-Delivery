package elementiGrafici;

import javafx.scene.control.CheckBox;
import pizzeria.Ingredients;
import pizzeria.Pizza;

public class CheckBoxTopping extends CheckBox {

    private Ingredients ingr;
    private boolean b;

    public CheckBoxTopping(Ingredients ingr, Pizza nuovaPizza) {
        this.ingr = ingr;

        if(nuovaPizza.getIngredients().containsKey(ingr.name()))
        {
            this.b = true;
            setSelected(true);
        }else {
            this.b = false;
            setSelected(false);
        }
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public boolean isB() {
        return b;
    }

    public Ingredients getIngr() {
        return ingr;
    }
}