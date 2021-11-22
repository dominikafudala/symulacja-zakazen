package sim.entity;

import sim.states.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

public class DrawWindow implements ActionListener {
    private static JFrame frame;
    private static JLabel label;
    private BufferedImage image;
    private final int height;
    private final int width;
    private final BufferedImage CLEAR;

    public DrawWindow(int height, int width) {
        this.height = height;
        this.width = width;
        this.frame = null;
        this.label = null;
        this.image = new BufferedImage(width , height, BufferedImage.TYPE_INT_ARGB);
        this.CLEAR = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void displayPopulation(Population population) {
        image.setData(CLEAR.getData());
        Graphics2D g = this.image.createGraphics();
        List<Person> popList = population.getPopulation();
        for(Person person: popList) {
            int x = (int)((person.getX() * 10)/.1);
            int y = (int)((person.getY() * 10) / .1);
            IState personState = person.getState();
            if(personState instanceof Immune) g.setPaint(Color.GREEN);
            else if(personState instanceof Symptoms) g.setPaint(Color.RED);
            else if(personState instanceof NoSymptoms) g.setPaint(Color.ORANGE);
            else if(personState instanceof Healthy) g.setPaint(Color.DARK_GRAY);
            g.fillRoundRect(x, y, 6, 6, 3, 3);
        }
        this.display(this.image);
    }

    private void display(BufferedImage image) {
        if (this.frame == null) {
            this.frame = new JFrame();
            this.frame.setTitle("Simulation");
            this.frame.setSize(image.getWidth(), image.getHeight());
            this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.label = new JLabel();
            this.label.setIcon(new ImageIcon(image));
            this.frame.getContentPane().add(label, BorderLayout.WEST);
            JButton button = new JButton("Save");
            button.addActionListener(this);
            this.label.setBounds(width, height, 200, 100);
            this.frame.getContentPane().add(button, BorderLayout.SOUTH);
            this.frame.setLocationRelativeTo(null);
            this.frame.pack();
            this.frame.setVisible(true);
        } else {
            this.label.setIcon(new ImageIcon(image));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Simulation.saveSimulation();
    }
}
