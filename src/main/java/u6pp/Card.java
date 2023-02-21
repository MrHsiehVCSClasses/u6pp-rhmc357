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
        String col;
        String val;
    // start you code here
    Card(String color, String value){
        col = color;
        val = value;
    }
    String getColor(){
        return col;
    }
    String getValue(){
        return val;
    }
    boolean trySetColor(String color){
        boolean check = false;
        for (String c : COLORS){
            if ((c .equals(color))){
                check = true;
            }
        }
        if (check == false){
            System.out.println("not an actual color");
            return false;
        }
        if (color .equals(WILD) || color == null){
            System.out.println("either color is a wild or null");
            return false;
        }
        if (col .equals(WILD)){
            col = color;
            System.out.println("this is true the color has been changed");
            return true;
        }
        else{
            System.out.println("everything is false final statement");
            return false;
        }
    }
    
    Boolean canPlayOn(Card input){
        if (input == null){
            return false;
        }
        if (col .equals(WILD)){
            return true;
        }
        if (input.getColor() .equals(col)  || input.getValue() .equals(val) ){
            return true;
        }
        // if (trySetColor(input.getColor()) == false){
        //     return false;
        // }
        return false;
    }
}
