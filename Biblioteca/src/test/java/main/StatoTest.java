package main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatoTest {

    @Test
    public void testEnumValues() {
        
        assertEquals(4, Stato.values().length, "Il numero di stati nell'enum deve essere 4");

        assertTrue(Stato.DISPONIBILE == Stato.valueOf("DISPONIBILE"));
        assertTrue(Stato.PRENOTATO == Stato.valueOf("PRENOTATO"));
        assertTrue(Stato.RITIRATO == Stato.valueOf("RITIRATO"));
        assertTrue(Stato.NON_DISPONIBILE == Stato.valueOf("NON_DISPONIBILE"));
    }

    @Test
    public void testEnumName() {
        
        assertEquals("DISPONIBILE", Stato.DISPONIBILE.name());
        assertEquals("PRENOTATO", Stato.PRENOTATO.name());
        assertEquals("RITIRATO", Stato.RITIRATO.name());
        assertEquals("NON_DISPONIBILE", Stato.NON_DISPONIBILE.name());
    }

    @Test
    public void testEnumOrdinal() {
       
        assertEquals(0, Stato.DISPONIBILE.ordinal());
        assertEquals(1, Stato.PRENOTATO.ordinal());
        assertEquals(2, Stato.RITIRATO.ordinal());
        assertEquals(3, Stato.NON_DISPONIBILE.ordinal());
    }
}

