package u6pp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class PlayerTests {

    @Test
    public void getName_correctValue() {
        Player p = new Player("henry");
        assertEquals("henry", p.getName());
    }

    @Test
    public void getHand_startsEmpty() {
        Player p = new Player("henry");
        assertNotNull(p.getHand());
        assertTrue(p.getHand() instanceof ArrayList);
        assertEquals(0, p.getHand().size());
    }

}
