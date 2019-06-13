import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import pizzeria.DeliveryMan;
import pizzeria.Pizzeria;
import pizzeria.Services;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class Pizzeria_test {

    @Test
    public void testFindTimeBoxOven_and_FindTimeBoxDeliveryman(){
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

   }

   @Test
   public void testAFreeDeliveryMan(){
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
       DeliveryMan musi = new DeliveryMan("musi",pizzeria);
       pizzeria.addDeliveryMan(musi);
       assertEquals(musi,pizzeria.aFreeDeliveryMan(10,00));
       /**Da sistemare controllano il nome easy easy**/
   }






}
