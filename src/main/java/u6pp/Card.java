package u6pp;

public class Card {

    public static String RED = "RED";
    public static String GREEN = "GREEN";
    public static String BLUE = "BLUE";
    public static String YELLOW = "YELLOW";

    public static String ZERO = "0";
    public static String ONE = "1";
    public static String TWO = "2";
    public static String THREE = "3";
    public static String FOUR = "4";
    public static String FIVE = "5";
    public static String SIX = "6";
    public static String SEVEN = "7";
    public static String EIGHT = "8";
    public static String NINE = "9";

    public static String DRAW_2 = "DRAW_2";
    public static String REVERSE = "REVERSE";
    public static String SKIP = "SKIP";
    public static String WILD = "WILD";
    public static String WILD_DRAW_4 = "WILD_DRAW_4";

    // Wild color is the default color for wilds, before they are played. 
    public static String[] COLORS = {RED, GREEN, BLUE, YELLOW, WILD}; 
    public static String[] VALUES = {ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, 
        DRAW_2, REVERSE, SKIP, WILD, WILD_DRAW_4};

    private String color;
    private String value;
    private String playableColor;

    public Card(String color, String value) {
        this.color = WILD;
        this.value = WILD;
        setValue(value);
        setColorHelper(color);
        this.playableColor = this.color;
    }

    public boolean canPlayOn(Card otherCard) {
        if(otherCard == null) {
            return false;
        }
        return this.value.equals(WILD) || 
            this.value.equals(WILD_DRAW_4) ||
            this.value.equals(otherCard.value) || 
            this.color.equals(otherCard.playableColor);
    }

    public boolean trySetColor(String newColor) {
        if(newColor == null) {
            return false;
        }
        if(value.equalsIgnoreCase(Card.WILD) || value.equalsIgnoreCase(Card.WILD_DRAW_4)) {
            if(color.equalsIgnoreCase(Card.WILD) && !newColor.equalsIgnoreCase(Card.WILD)) {
                for(String c : COLORS) {
                    if(newColor.equalsIgnoreCase(c)) {
                        this.playableColor = c;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean setColorHelper(String color) {
        for(String c : COLORS) {
            if(color.equalsIgnoreCase(c)) {
                this.color = c;
                return true;
            }
        }
        return false;
    }

    private boolean setValue(String value) {
        for(String v : VALUES) {
            if(value.equalsIgnoreCase(v)) {
                this.value = v;
                return true;
            }
        }
        return false;
    }

    public String getValue() {
        return this.value;
    }

    public String getColor() {
        return this.playableColor;
    }

    public String toString() {
        if(this.value.equals(Card.WILD) || this.value.equals(Card.WILD_DRAW_4) ){
            if(!this.playableColor.equals(WILD)) {
                return this.value + " (" + this.playableColor + ")";
            }
            return this.value;
        }
        return "" + this.playableColor + " " + this.value;
    }

}
