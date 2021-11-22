package lolz;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JPanel {

    public void paint(Graphics g) {
        Image img = createImageWithText();
        g.drawImage(img, 0,0,this);
    }

    private Image createImageWithText() {
        BufferedImage bufferedImage = new BufferedImage(1000,1000,BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();

        return bufferedImage;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new Test());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }
}
