import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Quilt {
    static GraphicsConfiguration gc;
    public static String[][] squares = new String[8][4];
    public static Integer[][] coord = new Integer[16384][2];
    public static Integer maxNumber = 0;
    public static Float addRatio = 0.0f;
    public static void main(String[] args) {
        Integer count = 0;
        Integer length = 0;
        Scanner input = new Scanner(System.in);
        Float maxScale = 0.0f;
        while (input.hasNextLine() && count < 100) {
            String[] list = (input.nextLine()).split("\\s");
            length = length + Math.round(Float.valueOf(list[0]) * Float.valueOf(800));
            squares[count][0] = list[0];
            squares[count][1] = list[1];
            squares[count][2] = list[2];
            squares[count][3] = list[3];
            count++;
            maxNumber++;
            maxScale = maxScale + Float.parseFloat(list[0]);
        }
        input.close();
        float newLength = (float)length;
        addRatio = (800 / newLength - 1);
        JFrame frame = new JFrame(gc);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Frame le Quilt");
        frame.setSize(816, 839);
        frame.add(new PaintQuilt());
        frame.setVisible(true);
    }

    public static class PaintQuilt extends JPanel {
        public void paintComponent(final Graphics g) {
            // the variables for each corner
            Integer arrayNum = 0, count = 0;
            Integer i = 0, length = 0;
            Color rColor;
            // x y width height
            for (i = 0; i < maxNumber; i++) {
                length = Math.round(Float.valueOf(squares[i][0]) * Float.valueOf(800));
                float newNumber = (float) length;
                length = length + Math.round(newNumber * addRatio);
                // System.out.println(Math.round(newNumber * addRatio));
                // System.out.println((newNumber * addRatio));
                // produce the color from the specifiv rgb
                rColor = new Color(Integer.parseInt(squares[i][1]), 
                Integer.parseInt(squares[i][2]), Integer.parseInt(squares[i][3]));

                if (i == 0) {
                    g.setColor(rColor);
                    g.fillRect(800/2 - (length / 2), 800/2 - (length / 2), length, length);
                    
                    coord[0][0] = (800 / 2) - (length / 2);
                    coord[0][1] = (800 / 2) - (length / 2);
                    coord[1][0] = (800 / 2) + (length / 2);
                    coord[1][1] = (800 / 2) - (length / 2);
                    coord[2][0] = (800 / 2) - (length / 2);
                    coord[2][1] = (800 / 2) + (length / 2);
                    coord[3][0] = (800 / 2) + (length / 2);
                    coord[3][1] = (800 / 2) + (length / 2);
                    arrayNum = 4;
                    count = 0;
                } else {
                    Integer tempNum = count;
                    Integer tempNum2 = arrayNum;
                    g.setColor(rColor);
                    for (int j = count; j < arrayNum; j++) {
                        g.fillRect(coord[j][0] - (length / 2), coord[j][1] - (length / 2), length, length);
                        count++;
                    }
                    
                    for (int j = tempNum; j < tempNum2; j++) {
                        coord[arrayNum][0] = coord[j][0] + (length / 2);
                        coord[arrayNum++][1] = coord[j][1] - (length / 2);
                        coord[arrayNum][0] = coord[j][0] + (length / 2);
                        coord[arrayNum++][1] = coord[j][1] + (length / 2);
                        coord[arrayNum][0] = coord[j][0] - (length / 2);
                        coord[arrayNum++][1] = coord[j][1] - (length / 2);
                        coord[arrayNum][0] = coord[j][0] - (length / 2);
                        coord[arrayNum++][1] = coord[j][1] + (length / 2);
                    }
                    // for (int j = 0; j < 20; j++) {
                    //     System.out.println("plotting square on -> x: " + coord[j][0] + " y: " + coord[j][1]);
                    // }
                }
            }
        }
    }
}