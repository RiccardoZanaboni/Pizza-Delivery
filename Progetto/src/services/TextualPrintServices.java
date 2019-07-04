package services;

import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.RED;

public class TextualPrintServices {

	/** Stringa-key a cui aggiungere il testo della stringa:
	 * consente di cambiare colore al background del testo. */
	private static String setBackgroundColorString(Color color){
		String background = "";
		if(color == Color.BLACK) {
			background = "\u001B[40m";
		}
		else if(color == RED) {
			background = "\u001B[41m";
		}
		else if(color == Color.GREEN) {
			background = "\u001B[42m";
		}
		else if(color == Color.YELLOW) {
			background = "\u001B[43m";
		}
		else if(color == Color.BLUE) {
			background = "\u001B[44m";
		}
		else if(color == Color.MAGENTA) {
			background = "\u001B[45m";
		}
		else if(color == Color.CYAN) {
			background = "\u001B[46m";
		}
		else if(color == Color.WHITE) {
			background = "\u001B[47m";
		}
		return background;
	}

	/** Consente di fornire caratteristiche aggiuntive (colore, bold, underlined) alla stringa richiesta. */
	public static String colorSystemOut(String text, Color color, boolean bold, boolean underlined) {
		StringBuilder cString = new StringBuilder("\033[");
		if(color == Color.WHITE) {
			cString.append("30");
		}
		else if(color == RED) {
			cString.append("31");
		}
		else if(color == Color.GREEN) {
			cString.append("32");
		}
		else if(color == Color.YELLOW) {
			cString.append("33");
		}
		else if(color == Color.BLUE) {
			cString.append("34");
		}
		else if(color == Color.MAGENTA) {
			cString.append("35");
		}
		else if(color == Color.CYAN) {
			cString.append("36");
		}
		else if(color == Color.GRAY) {
			cString.append("37");
		}
		else {
			cString.append("30");
		}
		if(bold) {
			cString.append(";1");
		}
		if(underlined) {
			cString.append(";4");
		}
		cString.append(";0m").append(text).append("\033[0m");
		return cString.toString();
	}

	/** Consente di disegnare, in interfaces.TextInterface, l'intestazione del menu. */
	public static void paintMenuString() {
		String l1 = "__________________________________________";
		l1 = colorSystemOut(l1,Color.WHITE,true,false);
		String v = "||";
		v = colorSystemOut(v,Color.WHITE,true,false);
		String colorTab = setBackgroundColorString(Color.CYAN) + "\t\t";
		String tab = "\t\t";
		String a = setBackgroundColorString(Color.CYAN) + "        _____              ";
		a = colorSystemOut(a, Color.WHITE,true,false);
		String b = setBackgroundColorString(Color.CYAN) + "|\\  /| |      |\\   | |    |";
		b = colorSystemOut(b, Color.WHITE,true,false);
		String c = setBackgroundColorString(Color.CYAN) + "| \\/ | |___   | \\  | |    |";
		c = colorSystemOut(c, Color.WHITE,true,false);
		String d = setBackgroundColorString(Color.CYAN) + "|    | |      |  \\ | |    |";
		d = colorSystemOut(d, Color.WHITE,true,false);
		String e = setBackgroundColorString(Color.CYAN) + "|    | |_____ |   \\| |____|";
		e = colorSystemOut(e, Color.WHITE,true,false);
		String l2 = setBackgroundColorString(Color.CYAN) + "______________________________________";
		l2 = setBackgroundColorString(Color.CYAN) + colorSystemOut(l2,Color.WHITE,true,false);
		l2 = v+l2+v;

		System.out.println("\n"+tab+l1+"\n"+tab+v+colorTab+a+colorTab+v+"\n"+tab+v+colorTab+b+colorTab+v+
				"\n"+tab+v+colorTab+c+colorTab+v+"\n"+tab+v+colorTab+d+colorTab+v+"\n"+tab+v+colorTab+e+colorTab+v+"\n"+tab+l2);
	}

	/** Restituisce una linea, utile per la stampa in interfaces.TextInterface. */
	public static String getLine(){
		return "\n---------------------------------------------------------------------------------------------------\n";
	}
}
