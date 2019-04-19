public class OutOfTIme extends Exception {
    public OutOfTIme(){
        super("Ora non valida"); //usata per evitare chiamata ricorsiva pericolosa che da errore
    }

}
