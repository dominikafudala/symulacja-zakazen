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
    private JButton buttonSave, buttonUndo;

    public DrawWindow(int height, int width) {
        this.height = height;
        this.width = width;
        frame = null;
        label = null;
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
        if (frame == null) {
            frame = new JFrame();
            frame.setTitle("Simulation");
            frame.setSize(image.getWidth(), image.getHeight());
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            label = new JLabel();
            label.setIcon(new ImageIcon(image));
            frame.getContentPane().add(label, BorderLayout.WEST);
            buttonSave = new JButton("Save");
            buttonUndo = new JButton("Undo");
            buttonSave.addActionListener(this);
            buttonUndo.addActionListener(this);
            JPanel buttons = new JPanel();
            buttons.add(buttonSave);
            buttons.add(buttonUndo);
            buttonUndo.setEnabled(false);
            label.setBounds(width, height, 200, 100);
            frame.getContentPane().add(buttons, BorderLayout.SOUTH);
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);
        } else {
            label.setIcon(new ImageIcon(image));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonSave){
            Simulation.saveSimulation();
        } else if(e.getSource() == buttonUndo){
            Simulation.undoSimulation();
        }

        buttonUndo.setEnabled(Simulation.shouldBeEnabled());
    }
}
