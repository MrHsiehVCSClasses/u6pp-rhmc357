package u6pp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class CardTests {

    private Card redZero;
    private Card redOne;
    private Card blueZero;
    private Card blueOne;
    private Card wild;

    @BeforeEach
    public void beforeEach() {
        redZero = new Card(Card.RED, Card.ZERO);
        redOne = new Card(Card.RED, Card.ONE);
        blueZero = new Card(Card.BLUE, Card.ZERO);
        blueOne = new Card(Card.BLUE, Card.ONE);
        wild = new Card(Card.WILD, Card.WILD);
    }

    @Test
    public void getColor_defaultCorrectColors_correctValues() {
        assertEquals(Card.WILD, wild.getColor());
        assertEquals(Card.RED, redZero.getColor());
        assertEquals(Card.RED, redOne.getColor());
        assertEquals(Card.BLUE, blueZero.getColor());
        assertEquals(Card.BLUE, blueOne.getColor());
    }

    @Test
    public void getValue_correctValues() {
        assertEquals(Card.WILD, wild.getValue());
        assertEquals(Card.ZERO, redZero.getValue());
        assertEquals(Card.ONE, redOne.getValue());
        assertEquals(Card.ZERO, blueZero.getValue());
        assertEquals(Card.ONE, blueOne.getValue());
    }

    @Test
    public void setGetColor_setColorWildRed_changesColor() {
        wild.trySetColor(Card.RED);
        assertEquals(Card.RED, wild.getColor());
    }

    @Test
    public void setGetColor_setColorWildYellow_changesColor() {
        wild.trySetColor(Card.YELLOW);
        assertEquals(Card.YELLOW, wild.getColor());
    }

    @Test
    public void setGetColor_setColorNonWild_doesNotChangeColor() {
        redOne.trySetColor(Card.YELLOW);
        assertEquals(Card.RED, redOne.getColor());
    }

    @Test
    public void setColor_NonWild_noEffect() {
        redOne.trySetColor(Card.BLUE);
        assertTrue(redZero.canPlayOn(redOne));
        assertFalse(blueZero.canPlayOn(redOne));
    }

    @Test
    public void setColor_nonColorInput_returnsFalse() {
        assertFalse(wild.trySetColor("asdf"));
    }

    @Test
    public void setColor_colorInput_returnsTrue() {
        assertTrue(wild.trySetColor(Card.GREEN));
    }

    @Test
    public void setColor_nonWildCard_returnsFalse() {
        assertFalse(redOne.trySetColor(Card.RED));
    }

    @Test
    public void setColor_wildToWild_returnsFalse() {
        assertFalse(wild.trySetColor(Card.WILD));
    }

    @Test
    public void setColorNull_returnsFalse() {
        assertFalse(wild.trySetColor(null));
        // fails if throws exception
    }

    @Test
    public void canPlayOn_sameCardType_returnTrue() {
        assertTrue(redZero.canPlayOn(new Card(Card.RED, Card.ZERO)));
    }

    @Test
    public void canPlayOn_sameValue_returnTrue() {
        assertTrue(redZero.canPlayOn(blueZero));
    }

    @Test
    public void canPlayOn_sameColor_returnTrue() {
        assertTrue(redZero.canPlayOn(redOne));
    }

    @Test
    public void canPlayOn_differentColorValue_returnFalse() {
        assertFalse(redZero.canPlayOn(blueOne));
    }

    @Test
    public void canPlayOn_wildOnAnything_returnTrue() {
        assertTrue(wild.canPlayOn(redOne));
        assertTrue(wild.canPlayOn(blueZero));
        assertTrue(wild.canPlayOn(wild));
    }

    @Test
    public void canPlayOn_wildDraw4OnAnything_returnTrue() {
        Card wildDraw4 = new Card(Card.WILD, Card.WILD_DRAW_4);

        assertTrue(wildDraw4.canPlayOn(redOne));
        assertTrue(wildDraw4.canPlayOn(blueZero));
        assertTrue(wildDraw4.canPlayOn(wildDraw4));
    }

    @Test
    public void canPlayOn_unsetWild_returnFalse() {
        assertFalse(redZero.canPlayOn(wild));
        // false because the wild has not yet had its color set to anything. 
    }

    @Test
    public void canPlayOn_redOnWildRed_returnTrue() {
        wild.trySetColor(Card.RED);
        assertTrue(redZero.canPlayOn(wild));
    }

    @Test
    public void canPlayOn_redOnWildBlue_returnFalse() {
        wild.trySetColor(Card.BLUE);
        assertFalse(redZero.canPlayOn(wild));
    }

    @Test
    public void canPlayOn_nullArgument_returnFalse() {
        assertFalse(redZero.canPlayOn(null));
    }

}
