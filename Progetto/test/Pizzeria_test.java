//import org.junit.*;
//import org.junit.Test;
//import static org.junit.Assert.*;
/*import org.junit.Test;
import pizzeria.Pizzeria;
import pizzeria.Services;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class Pizzeria_test {

    @Test
    public void testFindTimeBoxOven(){
        Pizzeria pizzeria = new Pizzeria("Wolf of Pizza","Via Bolzano 12",// orari di apertura, da domenica a sabato
                LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
                // orari di chiusura, da domenica a sabato
                LocalTime.MIN.plus(Services.getMinutes(23,59), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(23,59), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(23,59), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(23,59), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(23,59), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(23,59), ChronoUnit.MINUTES),
                LocalTime.MIN.plus(Services.getMinutes(23,59), ChronoUnit.MINUTES));
        //pizzeria.findTimeBoxOven(19,35);

        assertEquals(120,pizzeria.findTimeBoxOven(10,0));
        assertEquals(60, pizzeria.findTimeBoxDeliveryMan(10, 0));
        /**Problema : l'assert restituisce che il findBoxOven divide gli indici di 10 minuti in 10 minuti*/
/*    }


}*/
