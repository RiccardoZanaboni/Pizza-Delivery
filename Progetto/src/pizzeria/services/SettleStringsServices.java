package pizzeria.services;

import java.util.StringTokenizer;

public class SettleStringsServices {

	/** In interfaces.TextualInterface, gestisce eventuali errori di inserimento da tastiera (spazi/virgole) degli ingredienti. */
	public static String arrangeIngredientString(StringTokenizer st){
		String ingred = st.nextToken(",");
		while (ingred.startsWith(" "))		// elimina tutti gli spazi prima della stringa
			ingred = ingred.substring(1);
		while (ingred.endsWith(" "))		// elimina tutti gli spazi dopo la stringa
			ingred = ingred.substring(0,ingred.length()-1);
		ingred = ingred.replace("'"," ");
		//ingred = ingred.replace(" ","_");
		ingred = ingred.toUpperCase();
		return ingred;
	}

	/** Restituisce il nome della pizza, sistemato con tutte le iniziali maiuscole. */
	public static String getSettledName(String nome) {
		nome = nome.charAt(0) + nome.substring(1).toLowerCase();
		for(int i=1; i<nome.length(); i++){
			if(nome.substring(i,i+1).equals("_") || nome.substring(i,i+1).equals(" ")){
				nome = nome.replace(nome.substring(i,i+1)," ");
				nome = nome.replace(nome.substring(i+1,i+2),nome.substring(i+1,i+2).toUpperCase());
			}
		}
		return nome;
	}
}
