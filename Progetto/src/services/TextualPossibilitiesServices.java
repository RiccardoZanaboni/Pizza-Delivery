package services;

import javafx.scene.paint.Color;

public class TextualPossibilitiesServices {

	/** In interfaces.TextualInterface.whatDoYouWant(), chiede quali siano le intenzioni del cliente per procedere. */
	public static String whatDoYouWantPossibilities(boolean isOpen){
		String intro = TextualColorServices.colorSystemOut("\nEcco che cosa puoi fare:\n", Color.YELLOW,false,false);
		String con = "\t- con '";
		String newOrd = TextualColorServices.colorSystemOut("N", Color.ORANGE,true,false);
		String newOrdS = "' puoi effetturare un nuovo ordine;\n";
		String last = TextualColorServices.colorSystemOut("L",Color.ORANGE,true,false);
		String lastS = "' puoi visualizzare il tuo ultimo ordine;\n";
		String off = TextualColorServices.colorSystemOut("M",Color.ORANGE,true,false);
		String offS = "' puoi modificare i tuoi dati;\n";
		String hist = TextualColorServices.colorSystemOut("H",Color.ORANGE,true,false);
		String histS = "' puoi sapere di più sulla nostra attività;\n";
		String video = TextualColorServices.colorSystemOut("V",Color.ORANGE,true,false);
		String videoS = "' puoi visualizzare il video di presentazione del progetto;\n";
		String exit = TextualColorServices.colorSystemOut("E",Color.ORANGE,true,false);
		String exitS = "' puoi uscire dalla tua area riservata.\n";

		StringBuilder string = new StringBuilder(TextualColorServices.getLine());
		string.append(intro);
		if(isOpen) {
			string.append(con).append(newOrd).append(newOrdS);
		}
		string.append(con).append(last).append(lastS);
		string.append(con).append(off).append(offS);
		string.append(con).append(hist).append(histS);
		string.append(con).append(video).append(videoS);
		string.append(con).append(exit).append(exitS);

		return string.toString();
	}

	/** In interfaces.TextualInterface.whatDoYouWant(), chiede quali siano le intenzioni della pizzeria, per procedere. */
	public static String whatDoesPizzeriaWantPossibilities(){
		String intro = TextualColorServices.colorSystemOut("\nPizzeria, ecco che cosa puoi fare:\n",Color.YELLOW,false,false);
		String con = "\t- con '";
		String ordini = TextualColorServices.colorSystemOut("V", Color.ORANGE,true,false);
		String ordiniS = "' puoi visualizzare l'elenco degli ordini da evadere;\n";
		String modMenu = TextualColorServices.colorSystemOut("M",Color.ORANGE,true,false);
		String modMenuS = "' puoi modificare voci del menu;\n";
		String mail = TextualColorServices.colorSystemOut("S",Color.ORANGE,true,false);
		String mailS = "' puoi inviare una mail ad un utente;\n";
		String exit = TextualColorServices.colorSystemOut("E",Color.ORANGE,true,false);
		String exitS = "' puoi uscire dalla tua area privilegiata.\n";

		return (TextualColorServices.getLine() + intro + con + ordini + ordiniS + con + modMenu + modMenuS
				+ con + mail + mailS + con + exit + exitS);
	}

	public static String textModifyMenuPossibilities() {
		String intro = TextualColorServices.colorSystemOut("\nQuesto è ciò che puoi fare:\n",Color.YELLOW,false,false);
		String con = "\t- con '";
		String add = TextualColorServices.colorSystemOut("A", Color.ORANGE,true,false);
		String addS = "' puoi aggiungere una pizza al menu;\n";
		String rmv = TextualColorServices.colorSystemOut("R",Color.ORANGE,true,false);
		String rmvS = "' puoi rimuovere una pizza dal menu;\n";
		String addIng = TextualColorServices.colorSystemOut("AI",Color.ORANGE,true,false);
		String addIngS = "' puoi aggiungere un ingrediente possibile;\n";
		String rmvIng = TextualColorServices.colorSystemOut("RI",Color.ORANGE,true,false);
		String rmvIngS = "' puoi rimuovere un ingrediente da quelli possibili;\n";
		String ret = TextualColorServices.colorSystemOut("B",Color.ORANGE,true,false);
		String retS = "' puoi tornare indietro.\n";

		return (intro + con + add + addS + con + rmv + rmvS + con + addIng + addIngS + con + rmvIng + rmvIngS
				+ con + ret + retS);
	}

}
