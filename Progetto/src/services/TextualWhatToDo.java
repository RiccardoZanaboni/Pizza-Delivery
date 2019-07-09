package services;

import javafx.scene.paint.Color;

public class TextualWhatToDo {

	/** In interfaces.TextualInterface.whatDoYouWant(), chiede quali siano le intenzioni del cliente per procedere. */
	public static String whatDoYouWantPossibilities(boolean isOpen){		//TODO: va in testuale!
		String ecco = TextualPrintServices.colorSystemOut("\nEcco che cosa puoi fare:\n", Color.YELLOW,false,false);
		String con = "\t- con '";
		String newOrd = TextualPrintServices.colorSystemOut("N", Color.ORANGE,true,false);
		String newOrdS = "' puoi effetturare un nuovo ordine;\n";
		String last = TextualPrintServices.colorSystemOut("L",Color.ORANGE,true,false);
		String lastS = "' puoi visualizzare il tuo ultimo ordine;\n";
		String off = TextualPrintServices.colorSystemOut("M",Color.ORANGE,true,false);
		String offS = "' puoi modificare i tuoi dati;\n";
		String hist = TextualPrintServices.colorSystemOut("H",Color.ORANGE,true,false);
		String histS = "' puoi sapere di più sulla nostra attività;\n";
		String video = TextualPrintServices.colorSystemOut("V",Color.ORANGE,true,false);
		String videoS = "' puoi visualizzare il video di presentazione del progetto;\n";
		String exit = TextualPrintServices.colorSystemOut("E",Color.ORANGE,true,false);
		String exitS = "' puoi uscire dalla tua area riservata.\n";

		StringBuilder string = new StringBuilder(TextualPrintServices.getLine());
		string.append(ecco);
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
		String intro = TextualPrintServices.colorSystemOut("\nPizzeria, ecco che cosa puoi fare:\n",Color.YELLOW,false,false);
		String con = "\t- con '";
		String ordini = TextualPrintServices.colorSystemOut("V", Color.ORANGE,true,false);
		String ordiniS = "' puoi visualizzare l'elenco degli ordini da evadere;\n";
		String pers = TextualPrintServices.colorSystemOut("P",Color.ORANGE,true,false);
		String persS = "' puoi gestire il personale della pizzeria;\n";
		String modMenu = TextualPrintServices.colorSystemOut("M",Color.ORANGE,true,false);
		String modMenuS = "' puoi modificare voci del menu;\n";
		String mail = TextualPrintServices.colorSystemOut("S",Color.ORANGE,true,false);
		String mailS = "' puoi inviare una mail ad un utente;\n";
		String exit = TextualPrintServices.colorSystemOut("E",Color.ORANGE,true,false);
		String exitS = "' puoi uscire dalla tua area privilegiata.\n";

		StringBuilder elenco = new StringBuilder(TextualPrintServices.getLine());
		elenco.append(intro);
		elenco.append(con).append(ordini).append(ordiniS);
		elenco.append(con).append(pers).append(persS);
		elenco.append(con).append(modMenu).append(modMenuS);
		elenco.append(con).append(mail).append(mailS);
		elenco.append(con).append(exit).append(exitS);

		return elenco.toString();
	}

	public static String textModifyMenuPossibilities() {
		String intro = TextualPrintServices.colorSystemOut("\nQuesto è ciò che puoi fare:\n",Color.YELLOW,false,false);
		String con = "\t- con '";
		String add = TextualPrintServices.colorSystemOut("A", Color.ORANGE,true,false);
		String addS = "' puoi aggiungere una pizza al menu;\n";
		String rmv = TextualPrintServices.colorSystemOut("R",Color.ORANGE,true,false);
		String rmvS = "' puoi rimuovere una pizza dal menu;\n";
		String addIng = TextualPrintServices.colorSystemOut("AI",Color.ORANGE,true,false);
		String addIngS = "' puoi aggiungere un ingrediente possibile;\n";
		String rmvIng = TextualPrintServices.colorSystemOut("RI",Color.ORANGE,true,false);
		String rmvIngS = "' puoi rimuovere un ingrediente da quelli possibili;\n";
		String ret = TextualPrintServices.colorSystemOut("B",Color.ORANGE,true,false);
		String retS = "' puoi tornare indietro.\n";

		StringBuilder elenco = new StringBuilder();
		elenco.append(intro);
		elenco.append(con).append(add).append(addS);
		elenco.append(con).append(rmv).append(rmvS);
		elenco.append(con).append(addIng).append(addIngS);
		elenco.append(con).append(rmvIng).append(rmvIngS);
		elenco.append(con).append(ret).append(retS);

		return elenco.toString();
	}
}
