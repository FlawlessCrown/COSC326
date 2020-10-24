import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class HilbertCurve {
    static GraphicsConfiguration gc;
    static Integer order;
    static float ratio = 1;
    static Integer count = 0;
    static String base = "DRU";
    static Integer length;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String strinput = "";
        String temp = "";
        System.out.println("Order n, Ratio r");
        while (input.hasNextLine()) {
            strinput = input.nextLine();
            order = Integer.parseInt(strinput.split(" ")[0]);
            ratio = Float.parseFloat(strinput.split(" ")[1]);
            if (order > 1) {
                for (int i = 1; i <= order; i++) {
                    temp = base;
                    base = antiClockwise(temp);
                    base += 'D';
                    base += temp;
                    base += 'R';
                    base += temp;
                    base += 'U';
                    base += clockwise(temp);
                }
            }
            length = (1000/((int)Math.pow(2,order + 1) + 1));
            if (order == 1) {
                length = (1000/4);
            }
            JFrame frame = new JFrame(gc);
            Canvas canvas = new Canvas();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Hilbert Curve");
            if (order != 1) {
                frame.setSize((length * ((int)Math.pow(2,order + 1) + 1)), 
                (length * ((int)Math.pow(2,order + 1) + 1)));
            } else {
                frame.setSize(1000,1000);
            }
            frame.setLocationRelativeTo(null);
            frame.setUndecorated(true);
            frame.add(new PaintCurve());
            frame.setVisible(true);
            frame.add(canvas);
            Component.createImage(canvas, frame);
            break;
        }
        input.close();
    }

    /**
     * Rotates the Hilbert curve clockwise
     */
    public static String clockwise (String s) {
        String antiClockwiseStr = antiClockwise(s);
        String result = "";
        int i = 0;
        for (i = s.length()-1; i >= 0; i--) {
            if (antiClockwiseStr.charAt(i) == 'U') {
                result += 'D';
            }
            else if (antiClockwiseStr.charAt(i) == 'D') {
                result += 'U';
            }
            else {
                result += antiClockwiseStr.charAt(i);
            }
        }
        return result;
    }

    /**
     * Rotates the Hilbert curve antiClockwise
     */
    public static String antiClockwise (String s) {
        String result = "";
        char direction;
        int i = 0;
        for (i = 0; i < s.length(); i++) {
            direction = s.charAt(i);
            if (direction == 'U') {
                result += 'L';
            }
            else if (direction == 'D') {
                result += 'R';
            }
            else if (direction == 'L') {
                result += 'U';
            }
            else {
                result += 'D';
            }
        }
        return result;
    }

    public static class PaintCurve extends JPanel {
        public void paintComponent(final Graphics g) {
            g.setColor(Color.black);
            Integer lengthX = length;
            Integer lengthY = length;
            Integer startX = (int)1000/((int)Math.pow(2,order + 1) + 1), 
            startY = (int)1000/((int)Math.pow(2,order + 1) + 1);
            if (order == 1) {
                lengthX = 500;
                lengthY = 500;
                startX = 250;
                startY = 250;
            }
            for (int i = 0; i < base.length(); i++) {
                if (base.charAt(i) == 'U') {
                    if (base.charAt(i + 1) != 'U' || base.charAt(i + 1) != 'D' || 
                    base.charAt(i + 1) != 'R' || base.charAt(i + 1) != 'L') {
                        
                        g.drawLine(startX, startY, startX, startY - lengthY);
                        startY -= lengthY;
                    }
                    else {
                        g.drawLine(startX, startY, startX, startY - lengthY);
                        startY -= lengthY;
                    }
                }
                else if (base.charAt(i) == 'D') {
                    g.drawLine(startX, startY, startX, startY + lengthY);
                    startY += lengthY;
                }
                else if (base.charAt(i) == 'R') {
                    g.drawLine(startX, startY, startX + lengthX, startY);
                    startX += lengthX;
                }
                else {
                    g.drawLine(startX, startY, startX - lengthX, startY);
                    startX -= lengthX;
                }
            }
        }
    }
}