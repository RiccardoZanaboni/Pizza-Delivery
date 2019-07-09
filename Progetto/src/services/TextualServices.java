package services;

import javafx.scene.paint.Color;
import pizzeria.Pizzeria;

public class TextualServices {

	/** In interfaces.TextualInterface, elenca tutte gli ingredienti che l'utente può scegliere, per modificare una pizza.
	 * */
	public static String possibleAddictions(Pizzeria pizzeria) {
		StringBuilder possibiliIngr = new StringBuilder();
		int i = 0;
		for (String ingr : pizzeria.getIngredientsPizzeria().values()) {
			if (i % 10 == 0)
				possibiliIngr.append("\n\t");
			possibiliIngr.append(ingr.toLowerCase().replace("_", " ")).append(", ");
			i++;
		}
		return possibiliIngr.toString();
	}

	/** Su interfaces.TextualInterface dà il benvenuto al cliente, fornendo le informazioni essenziali della pizzeria.
	 * @param pizzeria*/
	public static String helloThere(Pizzeria pizzeria){
		String opTime = TimeServices.timeStamp(pizzeria.getOpeningToday().getHours(), pizzeria.getOpeningToday().getMinutes());
		String clTime = TimeServices.timeStamp(pizzeria.getClosingToday().getHours(), pizzeria.getClosingToday().getMinutes());
		StringBuilder hello = new StringBuilder("\n");
		hello.append(TextualColorServices.colorSystemOut("\nBenvenuto!\n", Color.GREEN,true,true));
		hello.append(TextualColorServices.colorSystemOut("\nPIZZERIA ", Color.ORANGE,false,false));
		hello.append(TextualColorServices.colorSystemOut("\"" + pizzeria.getName() + "\"\n\t",Color.RED,true,false));
		hello.append(TextualColorServices.colorSystemOut(pizzeria.getAddress(),Color.ORANGE,false,false));
		if(pizzeria.getOpeningToday().equals(pizzeria.getClosingToday()))
			hello.append(TextualColorServices.colorSystemOut("\n\tOGGI CHIUSO", Color.RED, true, false));
		else {
			hello.append(TextualColorServices.colorSystemOut("\n\tApertura oggi: ", Color.ORANGE, false, false));
			hello.append(TextualColorServices.colorSystemOut(opTime + " - " + clTime, Color.RED, true, false));
		}
		hello.append("\n").append(TextualColorServices.getLine());
		return hello.toString();
	}

	/** Da interfaces.TextualInterface, permette di stampare a video il menu completo.
	 * @param pizzeria*/
	public static String printMenu(Pizzeria pizzeria) {
		String line = TextualColorServices.getLine();
		TextualColorServices.paintMenuString();
		StringBuilder s = new StringBuilder();
		for (String a : pizzeria.getMenu().keySet()) {
			s.append("\n").append(pizzeria.getMenu().get(a).toString());
		}
		return s.toString() + "\n" + line;
	}
}
