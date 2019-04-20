package exceptions;

public class OutOfTImeExc extends Exception {
    public OutOfTImeExc(){
        super("Ora non valida"); // usata per evitare chiamata ricorsiva pericolosa che dà errore
    }

}
