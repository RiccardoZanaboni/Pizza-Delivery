import enums.AccountPossibilities;
import enums.LoginPossibilities;
import enums.OpeningPossibilities;
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import pizzeria.DeliveryMan;
import pizzeria.Pizzeria;
import services.*;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class Pizzeria_test {
    Pizzeria pizzeria = new Pizzeria("Wolf of Pizza","Via Bolzano 12",
            LocalTime.MIN.plus(TimeServices.getMinutes(14,0), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(14,0), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(14,0), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(14,0), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(14,0), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(14,0), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(14,0), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(TimeServices.getMinutes(23,59), ChronoUnit.MINUTES));

    //Attraverso questo test verifico che in un orario prestabillito i Time box relativi
    //al forno in quell'orario e quelli relativi al Delivery man siano corretti l'uno deve inoltre
    //essere il doppio dell'altro affinche' il ragionamento utilizzato sia corretto
    @Test
    public void testFindTimeBoxOven_and_FindTimeBoxDeliveryman(){
        assertEquals(12,pizzeria.findTimeBoxOven(15,0));
        assertEquals(6, pizzeria.findTimeBoxDeliveryMan(15, 0));

   }
    //Con questo test verifico che il delivery man aggiunto alla pizzeria sia effettivamente reperibile
    //per la consegna
   @Test
   public void testAFreeDeliveryMan(){
       DeliveryMan fetch = new DeliveryMan("fetch",pizzeria);
       pizzeria.addDeliveryMan(fetch);
       //primo controllo del fattorino
       assertEquals("Musi",pizzeria.aFreeDeliveryMan(19,00).getName());
       pizzeria.aFreeDeliveryMan(19,00).assignDelivery(pizzeria.findTimeBoxDeliveryMan(19,00));
       //dopo aver assegnato un ordine a "musi" esso e' ancora libero perche puo' portare due ordini
       assertEquals("Musi",pizzeria.aFreeDeliveryMan(19,00).getName());
       pizzeria.aFreeDeliveryMan(19,00).assignDelivery(pizzeria.findTimeBoxDeliveryMan(19,00));
       //dopo aver assegnato due ordini a musi quello libero e' "fetch" ora
       assertEquals(fetch.getName(),pizzeria.aFreeDeliveryMan(19,00).getName());

   }

   @Test
    public void testCheckTimeBoxEven(){
        assertEquals(true,pizzeria.checkTimeBoxOven(19,00,3));
        assertEquals(true,pizzeria.checkTimeBoxOven(20,00,16));
        assertEquals(false,pizzeria.checkTimeBoxOven(20,00,23));
        assertEquals(true,pizzeria.checkTimeBoxOven(20,00,0));
   }

   @Test
    public void testCheckLogin(){
        try{
            assertEquals(LoginPossibilities.OK,PizzeriaServices.checkLogin(pizzeria,"fetch","fetch"));
            assertEquals(LoginPossibilities.PIZZERIA,PizzeriaServices.checkLogin(pizzeria,"PIZZERIA","PASSWORD"));
            assertEquals(LoginPossibilities.NO,PizzeriaServices.checkLogin(pizzeria,"boh","password casuale"));
            assertEquals(LoginPossibilities.NO,PizzeriaServices.checkLogin(pizzeria,"macchina","33"));
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
   }

    // questo test controlla l apertura in base alla data di apertura e chiusura e si basa sulla creazione della pizzeria
   @Test
    public void testCheckTimeOrder(){
        assertEquals(OpeningPossibilities.OPEN, TimeServices.checkTimeOrder(pizzeria));
        //assertEquals("CLOSED",pizzeria.checkTimeOrder());

   }

   @Test
    public void testCanCreateAccount(){
        assertEquals(AccountPossibilities.EXISTING,PizzeriaServices.canCreateAccount("fibirossa@gmail.com","Feb","zanzatroni","zanzatroni"));
        assertEquals(AccountPossibilities.SHORT,PizzeriaServices.canCreateAccount("fabio.rossanigo@gmail.com","fabio","e","e"));
        assertEquals(AccountPossibilities.OK,PizzeriaServices.canCreateAccount("macchina33@gmail.com","MEK","333","333"));
        assertEquals(AccountPossibilities.DIFFERENT,PizzeriaServices.canCreateAccount("macchina33@gmail.com","MEK","333","444"));
        assertEquals(AccountPossibilities.DIFFERENT,PizzeriaServices.canCreateAccount("macchina33@gmail.com","MACC","3"," "));
        assertEquals(AccountPossibilities.SHORT,PizzeriaServices.canCreateAccount("macchina33@unipv.it","m","333","333"));
        assertEquals(AccountPossibilities.EXISTING,PizzeriaServices.canCreateAccount("pizzeria@gmail.com","Pizzeria","PASSWORD","PASSWORD"));
   }





}
