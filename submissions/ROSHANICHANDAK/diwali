import java.util.*;
import java.io.IOException;
public class diwali 
 {

    // ANSI color codes for colorful output
    public static final String RESET = "\u001B[0m";
    public static final String[] COLORS = {
        "\u001B[31m", // Red
        "\u001B[33m", // Yellow
        "\u001B[32m", // Green
        "\u001B[36m", // Cyan
        "\u001B[35m", // Purple
        "\u001B[34m"  // Blue
    };

    public static void main(String[] args) throws InterruptedException {
        for (int t = 0; t < 12; t++) {
            clearScreen();

            String color = COLORS[t % COLORS.length];

            
            for (int i = 1; i <= 5; i++) {
                printSpaces(5 - i);
                printStars(color, i);
            }

            
            for (int i = 4; i >= 1; i--) {
                printSpaces(5 - i);
                printStars(color, i);
            }

            
            System.out.println();
            System.out.println("    " + color + "* Happy Diwali *" + RESET);

            Thread.sleep(400); 
        }

        
        System.out.println();
        System.out.println(COLORS[2] + "*May your life shine as bright as a Diwali " + RESET);
    System.out.println(COLORS[4] + " Wishing you and your family a safe and happy festival. " + RESET);
        System.out.println("\n");}

    public static void printSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    
    public static void printStars(String color, int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(color + "* " + RESET);
        }
        System.out.println();
    }
    public static void clearScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

}
