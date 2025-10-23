
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Diwali {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        String text = "Happy Diwali";
        String[] colors = {
                "\u001B[31m", // Red
                "\u001B[32m", // Green
                "\u001B[33m", // Yellow
                "\u001B[34m", // Blue
                "\u001B[35m", // Magenta
                "\u001B[36m", // Cyan
                "\u001B[37m", // White
                "\u001B[38;5;208m" // Orange
        };
        String bold = "\u001B[1m";
        String resetColor = "\u001B[0m";
        Random random = new Random();

        Thread thread1 = new Thread(() -> {

            for (int i = 5; i > 0; i--) {
                try {
                    System.out.println(i);
                    toolkit.beep();
                    Thread.sleep(3000);

                } catch (Exception e) {
                    System.out.println(e);

                }

            }

            Toolkit.getDefaultToolkit().beep();
            System.out.print("*        *\r\n" + //
                    " **      **\r\n" + //
                    "  ***  ***\r\n" + //
                    "   ******\r\n" + //
                    "");

            for (int i = 0; i < text.length(); i++) {
                char currentChar = text.charAt(i);
                String randomColor = colors[random.nextInt(colors.length)];
                System.out.print(randomColor + bold + currentChar + resetColor);
            }

        });
        thread1.start();
    }

}
